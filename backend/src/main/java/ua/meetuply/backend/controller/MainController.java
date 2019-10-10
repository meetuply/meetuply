package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.meetuply.backend.dao.AppUserDAO;
import ua.meetuply.backend.formbean.AppUserForm;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.validator.AppUserValidator;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {
//
   @Autowired
   private AppUserDAO appUserDAO;

//   @Autowired
//   private AppUserValidator appUserValidator;
//
//   // Set a form validator
//   @InitBinder
//   protected void initBinder(WebDataBinder dataBinder) {
//      // Form target
//      Object target = dataBinder.getTarget();
//      if (target == null) {
//         return;
//      }
//      System.out.println("Target=" + target);
//
//      if (target.getClass() == AppUserForm.class) {
//         dataBinder.setValidator(appUserValidator);
//      }
//   }
//

   @RequestMapping("/user")
   public Principal user(Principal user) {
      return user;
   }

   @RequestMapping("/members")
   public Map<String,Object> home() {
      Map<String,Object> model = new HashMap<String,Object>();
      model.put("members", appUserDAO.getAppUsers());
      return model;
   }

//   @RequestMapping("/login")
//   public String viewLogin(Model model) {
//	   return "registration/loginPage";
//   }
//
//   // Show Register page.
//   @RequestMapping(value = "/register", method = RequestMethod.GET)
//   public String viewRegister(Model model) {
//
//      AppUserForm form = new AppUserForm();
//      model.addAttribute("appUserForm", form);
//      return "registration/registerPage";
//   }
//
//   // This method is called to save the registration information.
//   // @Validated: To ensure that this Form
//   // has been Validated before this method is invoked.
//   @RequestMapping(value = "/register", method = RequestMethod.POST)
//   public String saveRegister(Model model,
//         @ModelAttribute("appUserForm") @Validated AppUserForm appUserForm,
//         BindingResult result,
//         final RedirectAttributes redirectAttributes) {
//
//      // Validate result
//      if (result.hasErrors()) {
//         return "registration/registerPage";
//      }
//      AppUser newUser= null;
//      try {
//         newUser = appUserDAO.createAppUser(appUserForm);
//      }
//      // Other error!!
//      catch (Exception e) {
//         model.addAttribute("errorMessage", "Error: " + e.getMessage());
//         return "registration/registerPage";
//      }
//
//      redirectAttributes.addFlashAttribute("flashUser", newUser);
//      return "redirect:/registerSuccessful";
//   }

//   @RequestMapping(value = "/email", method = RequestMethod.GET)
//   @ResponseBody
//   public String currentUserName(Principal principal) {
//      return principal.getName();
//   }
 
}

