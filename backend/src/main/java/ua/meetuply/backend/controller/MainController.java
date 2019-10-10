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

   @RequestMapping("/")
   public String viewHome(Model model) {
//      System.out.println(AppUserDAO.instance.getAppUsers().size() + "---------");
      return "welcomePage";
   }
 
}

