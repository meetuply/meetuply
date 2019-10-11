package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.meetuply.backend.formbean.AppUserForm;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.service.AppUserService;
import ua.meetuply.backend.validator.AppUserValidator;


@Controller
@RequestMapping("api/user")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppUserValidator appUserValidator;

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);
        if (target.getClass() == AppUserForm.class) {
            dataBinder.setValidator(appUserValidator);
        }
    }

    @RequestMapping("/members")
    public String viewMembers(Model model) {
        model.addAttribute("members", appUserService.getAppUsers());
        return "registration/membersPage";
    }

    @RequestMapping("/registerSuccessful")
    public String viewRegisterSuccessful(Model model) {
        return "registration/registerSuccessfulPage";
    }

    @RequestMapping("/login")
    public String viewLogin(Model model) {
        return "registration/loginPage";
    }

    // Show Register page.
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String viewRegister(Model model) {
        AppUserForm form = new AppUserForm();
        model.addAttribute("appUserForm", form);
        return "registration/registerPage";
    }

    // This method is called to save the registration information.
    // @Validated: To ensure that this Form
    // has been Validated before this method is invoked.
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveRegister(Model model,
                               @ModelAttribute("appUserForm") @Validated AppUserForm appUserForm,
                               BindingResult result,
                               final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "registration/registerPage";
        }
        AppUser newUser= null;
        try {
            appUserService.createAppUser(appUserForm);
        }
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "registration/registerPage";
        }
        redirectAttributes.addFlashAttribute("flashUser", newUser);
        return "redirect:/registerSuccessful";
    }
}