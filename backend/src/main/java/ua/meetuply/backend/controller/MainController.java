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
import ua.meetuply.backend.dao.BlogCommentDAO;
import ua.meetuply.backend.dao.BlogPostDAO;
import ua.meetuply.backend.formbean.AppUserForm;
import ua.meetuply.backend.formbean.BlogCommentForm;
import ua.meetuply.backend.formbean.BlogPostForm;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.BlogComment;
import ua.meetuply.backend.model.BlogPost;
import ua.meetuply.backend.validator.AppUserValidator;
import ua.meetuply.backend.validator.BlogCommentValidator;
import ua.meetuply.backend.validator.BlogPostValidator;

import java.util.List;

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
//      System.out.println(AppUserDAO.instance.getAppUsers().size() + "---------");
      return "welcomePage";
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

   //Blog section

   @Autowired
   private BlogPostDAO blogPostDAO;

   @Autowired
   private BlogPostValidator blogPostValidator;

   @RequestMapping("/blogPosts")
   public String viewBlogPosts(Model model) {

      List<BlogPost> list = blogPostDAO.getBlogPosts();
      model.addAttribute("blogPosts", list);
      return "blog/listBlogPostsPage";
   }

   @RequestMapping(value = "/newBlogPost", method = RequestMethod.GET)
   public String viewNewBlogPost(Model model) {
      BlogPostForm form = new BlogPostForm();
      model.addAttribute("blogPostForm", form);
      return "blog/newBlogPostPage";
   }

   @RequestMapping(value = "/newBlogPost", method = RequestMethod.POST)
   public String saveNewBlogPost(Model model, //
                                 @ModelAttribute("blogPostForm") @Validated BlogPostForm blogPostForm, //
                                 BindingResult result, //
                                 final RedirectAttributes redirectAttributes) {

      // Validate result
      if (result.hasErrors()) {
         return "blog/newBlogPostPage";
      }
      BlogPost bp= null;
      try {
         bp = blogPostDAO.createBlogPost(blogPostForm);
      }
      catch (Exception e) {
         model.addAttribute("errorMessage", "Error: " + e.getMessage());
         return "blog/newBlogPostPage";
      }

      redirectAttributes.addFlashAttribute("flashBlogPost", bp);

      return "redirect:/blogPosts";
   }

//   @RequestMapping(value = "/email", method = RequestMethod.GET)
//   @ResponseBody
//   public String currentUserName(Principal principal) {
//      return principal.getName();
//   }


   //Blog comment section

   @Autowired
   private BlogCommentDAO blogCommentDAO;

   @Autowired
   private BlogCommentValidator blogCommentValidator;

   @RequestMapping(value = "/postComment/{postid}", method = RequestMethod.GET)
   public String viewCurrentBlogPost(@PathVariable("postid") long postid, Model model) {
      BlogPost post = blogPostDAO.getBlogPostById(postid);
      model.addAttribute("blogPost", post);

      List<BlogComment> list = blogCommentDAO.findBlogCommentsByPost(post);
      model.addAttribute("blogComments", list);

      BlogCommentForm form = new BlogCommentForm();
      model.addAttribute("blogCommentForm", form);
      return "blog/currentBlogPostPage";
   }


 
}

