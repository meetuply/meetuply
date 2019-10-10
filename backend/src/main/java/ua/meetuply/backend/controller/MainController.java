package ua.meetuply.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

   @RequestMapping("/")
   public String viewHome(Model model) {
//      System.out.println(AppUserDAO.instance.getAppUsers().size() + "---------");
      return "welcomePage";
   }
 
}

