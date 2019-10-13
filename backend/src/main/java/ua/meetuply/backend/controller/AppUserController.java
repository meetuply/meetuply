package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.service.EmailService;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.service.AppUserService;
import ua.meetuply.backend.validator.AppUserValidator;

import javax.validation.Valid;

import javax.annotation.Resource;

@Controller
@RequestMapping("api/user")
public class AppUserController {

    private static final String GREETING_TEMPLATE_NAME = "templates/email-template.ftl";
    private static final String GREETING_SUBJECT = "Greeting";

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppUserValidator appUserValidator;

    @Resource(name = "emailServiceImpl")
    private EmailService emailService;

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);
        if (target.getClass() == AppUser.class) {
            dataBinder.setValidator(appUserValidator);
        }
    }

    @RequestMapping("/members")
    @GetMapping()
    public @ResponseBody Iterable<AppUser> getAllMeetups(){
        return appUserService.getAppUsers();
    }


    @RequestMapping("/registerSuccessful")
    public String viewRegisterSuccessful(Model model) {
        return "registration/registerSuccessfulPage";
    }

    @RequestMapping("/login")
    public String viewLogin(Model model) {
        return "registration/loginPage";
    }

    @GetMapping("/register")
    public String viewRegister() {
        return "registration/registerPage";
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> registerUser(@Valid @RequestBody AppUser appUser) {
        appUserService.createAppUser(appUser);
        emailService.sendEmail(appUser.getEmail(), GREETING_TEMPLATE_NAME, GREETING_SUBJECT);
        return ResponseEntity.ok().build();
    }
}