package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.meetuply.backend.dao.AppUserDAO;
import ua.meetuply.backend.formbean.AppUserForm;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.validator.AppUserValidator;

import java.util.List;

@Controller
public class AppUserController {

    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    private AppUserValidator appUserValidator;

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        // Form target
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
        List<AppUser> list = appUserDAO.getAppUsers();
        model.addAttribute("members", list);

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

        // Validate result
        if (result.hasErrors()) {
            return "registration/registerPage";
        }
        AppUser newUser= null;
        try {
            newUser = appUserDAO.createAppUser(appUserForm);
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "registration/registerPage";
        }

        redirectAttributes.addFlashAttribute("flashUser", newUser);
        return "redirect:/registerSuccessful";
    }

}
