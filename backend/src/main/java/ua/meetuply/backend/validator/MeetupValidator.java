package ua.meetuply.backend.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.meetuply.backend.formbean.MeetupForm;

@Component
public class MeetupValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == MeetupForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeetupForm meetupForm = (MeetupForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "meetupPlace", "NotEmpty.meetupForm.meetupPlace");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "meetupTitle", "NotEmpty.meetupForm.meetupTitle");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "meetupRegisteredAttendees", "NotEmpty.meetupForm.meetupRegisteredAttendees");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "meetupMinAttendees", "NotEmpty.meetupForm.meetupTitle");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "meetupMaxAttendees", "NotEmpty.meetupForm.meetupMaxAttendees");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "meetupStartDateTime", "NotEmpty.meetupForm.meetupStartDateTime");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "meetupFinishDateTime", "NotEmpty.meetupForm.meetupFinishDateTime");
    }
}