package ua.meetuply.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.meetuply.backend.formbean.MeetupForm;
import ua.meetuply.backend.model.Meetup;
import ua.meetuply.backend.service.MeetupService;

import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("api/meetup")
@RestController
public class MeetupController {

    @Autowired
    MeetupService meetupService;

    @GetMapping("/create")
    public String showForm() {
        return "creating meetup";
    }

    @PostMapping("/create")
    public String saveMeetup(Model model,
                             @ModelAttribute("meetupForm") @Validated MeetupForm meetupForm,
                             BindingResult result,
                             final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            result.getAllErrors().stream().map(Object::toString).forEach(r -> System.out.println(r + "\n"));
            return "result has errors";
        }
        try {
            meetupService.createMeetup(meetupForm);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            e.printStackTrace();
            return "Exception again";
        }
        return "creation successfull";
    }

    @GetMapping("/{meetupId}")
    public String showOne(@PathVariable("meetupId") Integer meetupId) {
        Meetup meetup = meetupService.getMeetupById(meetupId);
        return meetup.toString();
    }

    @GetMapping("/all")
    public String showAllMeetups() {
        return meetupService.getAllMeetups().stream().map(Objects::toString).collect(Collectors.joining("\n "));
    }
}
