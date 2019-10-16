package ua.meetuply.backend.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.meetuply.backend.dao.AppUserDAO;
import ua.meetuply.backend.model.AppUser;

@Component
public class AppUserValidator implements Validator {

    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    private AppUserDAO appUserDAO;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == AppUser.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppUser appUser = (AppUser) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.appUser.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.appUser.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.appUser.lastName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.appUser.password");

        if (!this.emailValidator.isValid(appUser.getEmail())) {
            errors.rejectValue("email", "Pattern.appUser.email");
        } else if (appUser.getUserId() == null) {
            AppUser dbUser = appUserDAO.findAppUserByEmail(appUser.getEmail());
            if (dbUser != null) {
                errors.rejectValue("email", "Duplicate.appUser.email");
            }
        }

        if (!errors.hasErrors()) {

        }
    }
}