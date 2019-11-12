package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.controller.exception.NotFoundException;
import ua.meetuply.backend.model.*;
import ua.meetuply.backend.service.*;
import ua.meetuply.backend.validator.AppUserValidator;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("api/user")
public class AppUserController {

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

    @Autowired
    private NotificationService notificationService;

    @Resource(name = "emailServiceImpl")
    private EmailService emailService;

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) return;
        if (target.getClass() == AppUser.class) {
            dataBinder.setValidator(appUserValidator);
        }
    }

    @GetMapping
    public AppUser user() {
        return appUserService.getCurrentUser();
    }

    @PutMapping
    public ResponseEntity update(@RequestBody AppUser newUser) throws Exception {
        appUserService.update(newUser);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/recover")
    public ResponseEntity changePassword(@RequestBody AppUser newUser, @RequestParam("token") String confirmationToken) throws Exception {
        AppUser user = appUserService.update(newUser, confirmationToken);
        emailService.sendSuccessRecoverEmail(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recover")
    public ResponseEntity requestRecover(@RequestParam("email") String email) throws NotFoundException {
        AppUser user = appUserService.getUserByEmail(email);
        if (user == null) throw new NotFoundException("Cannot find user with email " + email);
        ConfirmationToken ct = confirmationService.generateToken(user);
        emailService.sendRecoverEmail(user, confirmationService.getRecoveryLink(ct));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/{token}")
    public boolean tokenExists(@PathVariable("token") String token) {
        return confirmationService.exists(token);
    }


    @RequestMapping("/members")
    @GetMapping()
    public Iterable<AppUser> getAllMeetups() {
        return appUserService.getAppUsers();
    }

    @GetMapping("/{userId}/rooms")
    public Iterable<Integer> getRoomsByUserID(@PathVariable("userId") Integer userId) {
        return chatService.getChatRoomsByUser(userId);
    }


    @GetMapping("/{userId}/notifications")
    public List<SocketNotification> getAllUserNotifications(@PathVariable("userId") Integer userId) {
        return notificationService.getUserNotifications(userId);
    }

    @GetMapping("/{userId}/notifications/read")
    public List<SocketNotification> getAllUserReadNotifications(@PathVariable("userId") Integer userId) {
        return notificationService.getUserNotificationsByStatus(userId, true);
    }

    @GetMapping("/{userId}/notifications/unread")
    public List<SocketNotification> getAllUserUnreadNotifications(@PathVariable("userId") Integer userId) {
        return notificationService.getUserNotificationsByStatus(userId, false);
    }


    @GetMapping("/{userId}/roomsList")
    public Iterable<ChatroomThumbnail> getRoomsThumbnail(@PathVariable("userId") Integer userId) {
        return chatService.getChatRoomsThumbnails(userId);
    }

    @GetMapping("/members/{startRow}/{endRow}")
    public Iterable<AppUser> getUsersChunk(@PathVariable("startRow") Integer startRow, @PathVariable("endRow") Integer endRow) {
        return appUserService.getUsersChunk(startRow, endRow);
    }

    @GetMapping("/members/{startRow}/{endRow}/search")
    public Iterable<AppUser> getUsersChunkByName(@PathVariable("startRow") Integer startRow,
                                                 @PathVariable("endRow") Integer endRow,
                                                 @RequestParam("name") String name) {
        return appUserService.getUsersChunkByName(startRow, endRow, name);
    }

    @GetMapping("/members/all/{startRow}/{endRow}")
    public Iterable<AppUser> getUsersChunkForAdmin(@PathVariable("startRow") Integer startRow, @PathVariable("endRow") Integer endRow) {
        return appUserService.getUsersChunkForAdmin(startRow, endRow);
    }

    @GetMapping("/members/all/{startRow}/{endRow}/search")
    public Iterable<AppUser> getUsersChunkForAdminByName(@PathVariable("startRow") Integer startRow,
                                                 @PathVariable("endRow") Integer endRow,
                                                 @RequestParam("name") String name) {
        return appUserService.getUsersChunkForAdminByName(startRow, endRow, name);
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

    @GetMapping("/{id}/subscriptions")
    public Iterable<Integer> getSubscriptions(@PathVariable("id") Integer userId) {
        return appUserService.getUserSubscriptions(userId);
    }

    @GetMapping("/{id}/subscriptions/users")
    public Iterable<AppUser> getSubscriptionsUsers(@PathVariable("id") Integer userId) {
        return appUserService.getUserSubscriptionsUsers(userId);
    }

    @GetMapping("/{id}/fullName")
    public String getFullName(@PathVariable("id") Integer userId) {
        return appUserService.getUserFullName(userId);
    }

    @GetMapping("/address")
    public String adress() {
        return System.getenv("HOST_NAME");
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> registerUser(@Valid @RequestBody AppUser appUser) {
        appUserService.createAppUser(appUser);
        ConfirmationToken ct = confirmationService.generateToken(appUserService.getUserByEmail(appUser.getEmail()));

        emailService.sendVerificationEmail(appUser, "Verify your account",
                confirmationService.getConfirmLink(ct));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/confirm")
    public ResponseEntity<AppUser> confirmUser(@RequestParam("token") String confirmationToken) {
        AppUser user = confirmationService.confirmUser(confirmationToken);
        if (user != null) {
            emailService.sendGreetingEmail(user);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity deactivateUser(@PathVariable("id") Integer userId) throws NotFoundException {
        AppUser user = appUserService.getUser(userId);
        if (user == null) return ResponseEntity.notFound().build();
        appUserService.deactivateUser(user);
        emailService.sendDeactivatinEmail(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity activateUser(@PathVariable("id") Integer userId) {
        AppUser user = appUserService.getUser(userId);
        if (user == null) return ResponseEntity.notFound().build();
        appUserService.activateDeactivatedUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/following/{id}")
    public ResponseEntity follow(@PathVariable Integer id) {
        if (appUserService.getUserSubscriptions(appUserService.getCurrentUserID()).indexOf(id) != -1 ||
                appUserService.getCurrentUserID() == id) {
            return ResponseEntity.badRequest().build();
        }
        appUserService.follow(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/following/{id}")
    public ResponseEntity unfollow(@PathVariable("id") Integer id) {
        if (appUserService.getUserSubscriptions(appUserService.getCurrentUserID()).indexOf(id) == -1 ||
                appUserService.getCurrentUserID() == id) {
            return ResponseEntity.badRequest().build();
        }
        appUserService.unfollow(id);
        return ResponseEntity.ok().build();
    }
}