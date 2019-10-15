package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.ConfirmationToken;
import ua.meetuply.backend.service.ConfirmationService;
import ua.meetuply.backend.service.EmailService;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.service.AppUserService;
import ua.meetuply.backend.validator.AppUserValidator;


import javax.validation.Valid;
import javax.annotation.Resource;
import java.security.Principal;


@RestController
@RequestMapping("api/user")
public class AppUserController {

    private static final String GREETING_TEMPLATE_NAME = "email-template.ftl";
    private static final String GREETING_SUBJECT = "Greeting";

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ConfirmationService confirmationService;

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

    @RequestMapping("/")
    public Principal user(Principal user) {
        return user;
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
        ConfirmationToken ct = confirmationService.generateToken(appUserService.getUserByEmail(appUser.getEmail()));

        //TODO confirmation email
        emailService.sendEmail(appUser.getEmail(), GREETING_TEMPLATE_NAME, GREETING_SUBJECT);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/confirm")
    public ResponseEntity<AppUser> confirmUser(@RequestParam("token") String confirmationToken) {

        if (confirmationService.confirmUser(confirmationToken)) {
            // TODO welcome email
        } else {
            //TODO "The link is invalid or broken!"
        }

//        emailService.sendEmail(appUser.getEmail(), GREETING_TEMPLATE_NAME, GREETING_SUBJECT);
        return ResponseEntity.ok().build();
    }
}