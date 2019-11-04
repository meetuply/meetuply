package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.*;

import ua.meetuply.backend.service.*;
import ua.meetuply.backend.validator.AppUserValidator;

import javax.annotation.Resource;
import javax.validation.Valid;


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

    @Autowired
    private ChatService chatService;

    @Resource(name = "emailServiceImpl")
    private EmailService emailService;

    @RequestMapping("/")
    public AppUser user() {
        return appUserService.getCurrentUser();
    }

    @RequestMapping("/members")
    @GetMapping()
    public @ResponseBody
    Iterable<AppUser> getAllMeetups() {
        return appUserService.getAppUsers();
    }

    @GetMapping("/{userId}/rooms")
    public @ResponseBody
    Iterable<Integer> getRoomsByUserID(@PathVariable("userId") Integer userId){
        return chatService.getChatRoomsByUser(userId);
    }

    @GetMapping("/{userId}/roomsList")
    public @ResponseBody
    Iterable<ChatroomThumbnail> getRoomsThumbnail(@PathVariable("userId") Integer userId){
        return chatService.getChatRoomsThumbnails(userId);
    }

    @GetMapping("/members/{startRow}/{endRow}")
    public @ResponseBody
    Iterable<AppUser> getUsersChunk(@PathVariable("startRow") Integer startRow, @PathVariable("endRow") Integer endRow) {
        return appUserService.getUsersChunk(startRow, endRow);
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

    @GetMapping("/{id}/fullName")
    public String getFullName(@PathVariable("id") Integer userId) {
        return appUserService.getUserFullName(userId);
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

    @PutMapping("/deactivate/{id}")
    public ResponseEntity deactivateUser(@PathVariable("id") Integer userId) {
        AppUser user = appUserService.getUser(userId);
        if(user == null) return ResponseEntity.notFound().build();
        appUserService.deactivateUser(user);
        emailService.sendDeactivatinEmail(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity activateUser(@PathVariable("id") Integer userId) {
        AppUser user = appUserService.getUser(userId);
        if(user == null) return ResponseEntity.notFound().build();
        appUserService.activateDeactivatedUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/following/{id}")
    public ResponseEntity follow(@PathVariable Integer id) {
        if (appUserService.getUserSubscriptions(appUserService.getCurrentUserID()).indexOf(id) != -1 ||
            appUserService.getCurrentUserID()==id) {
            return ResponseEntity.badRequest().build();
        }
        appUserService.follow(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/following/{id}")
    public ResponseEntity unfollow(@PathVariable("id") Integer id) {
        if (appUserService.getUserSubscriptions(appUserService.getCurrentUserID()).indexOf(id) == -1 ||
                appUserService.getCurrentUserID()==id) {
            return ResponseEntity.badRequest().build();
        }
        appUserService.unfollow(id);
        return ResponseEntity.ok().build();
    }
}