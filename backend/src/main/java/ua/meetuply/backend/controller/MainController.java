package ua.meetuply.backend.controller;

import java.util.List;

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
import ua.meetuply.backend.dao.BlogPostDAO;
import ua.meetuply.backend.formbean.AppUserForm;
import ua.meetuply.backend.formbean.BlogPostForm;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.BlogPost;
import ua.meetuply.backend.validator.AppUserValidator;
import ua.meetuply.backend.validator.BlogPostValidator;

@Controller
public class MainController {
 
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

      if (target.getClass() == BlogPostForm.class) {
         dataBinder.setValidator(blogPostValidator);
      }
   }
 
   @RequestMapping("/")
   public String viewHome(Model model) {
 
      return "welcomePage";
   }
 
   @RequestMapping("/members")
   public String viewMembers(Model model) {
 
      List<AppUser> list = appUserDAO.getAppUsers();
 
      model.addAttribute("members", list);
 
      return "membersPage";
   }
 
   @RequestMapping("/registerSuccessful")
   public String viewRegisterSuccessful(Model model) {
 
      return "registerSuccessfulPage";
   }
  
   @RequestMapping("/login")
   public String viewLogin(Model model) {
	   return "loginPage";
   }  
 
   // Show Register page.
   @RequestMapping(value = "/register", method = RequestMethod.GET)
   public String viewRegister(Model model) {
 
      AppUserForm form = new AppUserForm();
 
      model.addAttribute("appUserForm", form);
 
      return "registerPage";
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
         return "registerPage";
      }
      AppUser newUser= null;
      try {
         newUser = appUserDAO.createAppUser(appUserForm);
      }
      // Other error!!
      catch (Exception e) {
         model.addAttribute("errorMessage", "Error: " + e.getMessage());
         return "registerPage";
      }
 
      redirectAttributes.addFlashAttribute("flashUser", newUser);
       
      return "redirect:/registerSuccessful";
   }

   //Blog section

   @Autowired
   private BlogPostDAO blogPostDAO;

   @Autowired
   private BlogPostValidator blogPostValidator;

   @RequestMapping("/blogPosts")
   public String viewBlogPosts(Model model) {

      List<BlogPost> list = blogPostDAO.getBlogPosts();

      model.addAttribute("blogPosts", list);

      return "listBlogPostsPage";
   }

   @RequestMapping(value = "/newBlogPost", method = RequestMethod.GET)
   public String viewNewBlogPost(Model model) {

      BlogPostForm form = new BlogPostForm();

      model.addAttribute("blogPostForm", form);

      return "newBlogPostPage";
   }

   @RequestMapping(value = "/newBlogPost", method = RequestMethod.POST)
   public String saveNewBlogPost(Model model, //
                                 @ModelAttribute("blogPostForm") @Validated BlogPostForm blogPostForm, //
                                 BindingResult result, //
                                 final RedirectAttributes redirectAttributes) {

      // Validate result
      if (result.hasErrors()) {
         return "newBlogPostPage";
      }
      BlogPost bp= null;
      try {
         bp = blogPostDAO.createBlogPost(blogPostForm);
      }
      catch (Exception e) {
         model.addAttribute("errorMessage", "Error: " + e.getMessage());
         return "newBlogPostPage";
      }

      redirectAttributes.addFlashAttribute("flashBlogPost", bp);

      return "redirect:/blogPosts";
   }
 
}

