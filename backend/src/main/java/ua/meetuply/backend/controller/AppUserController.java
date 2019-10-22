package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.ConfirmationToken;
import ua.meetuply.backend.model.Language;
import ua.meetuply.backend.service.ConfirmationService;
import ua.meetuply.backend.service.EmailService;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.service.AppUserService;
import ua.meetuply.backend.service.LanguageService;
import ua.meetuply.backend.validator.AppUserValidator;


import javax.validation.Valid;
import javax.annotation.Resource;
import java.net.InetAddress;
import java.security.Principal;


@RestController
@RequestMapping("api/user")
public class AppUserController {

    private static final String GREETING_TEMPLATE_NAME = "email-template.ftl";
    private static final String VERIFICATION_TEMPLATE_NAME = "verification-email.ftl";
    private static final String GREETING_SUBJECT = "Greeting";

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private LanguageService languageService;

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
    public @ResponseBody
    Iterable<AppUser> getAllMeetups() {
        return appUserService.getAppUsers();
    }

    @GetMapping("/{id}")
    public AppUser get(@PathVariable("id") Integer userId) {
        return appUserService.getUser(userId);
    }

    @GetMapping("/{id}/languages")
    public Iterable<Language> getLanguages(@PathVariable("id") Integer userId) {
        return languageService.getUserLanguages(userId);
    }

    @GetMapping("/{id}/subscribers")
    public Iterable<Integer> getSubscribers(@PathVariable("id") Integer userId) {
        return appUserService.getUserSubscribers(userId);
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

    @GetMapping("/address")
    public String adress() {
        return System.getenv("HOST_NAME");
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> registerUser(@Valid @RequestBody AppUser appUser) {
        appUserService.createAppUser(appUser);
        ConfirmationToken ct = confirmationService.generateToken(appUserService.getUserByEmail(appUser.getEmail()));

        emailService.sendVerificationEmail(appUser, VERIFICATION_TEMPLATE_NAME, "Verify your account",
                confirmationService.getConfirmLink(ct));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/confirm")
    public ResponseEntity<AppUser> confirmUser(@RequestParam("token") String confirmationToken) {

        AppUser user = confirmationService.confirmUser(confirmationToken);
        if (user != null) {
            emailService.sendGreetingEmail(user, GREETING_TEMPLATE_NAME, GREETING_SUBJECT);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/deactivate/{id}")
    public ResponseEntity<AppUser> deactivateUser(@PathVariable("id") Integer userId) {
        AppUser user = appUserService.getUser(userId);
        appUserService.deactivateUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/activate/{id}")
    public ResponseEntity<AppUser> activateUser(@PathVariable("id") Integer userId) {
        AppUser user = appUserService.getUser(userId);
        appUserService.activateDeactivatedUser(user);
        return ResponseEntity.ok().build();
    }
}