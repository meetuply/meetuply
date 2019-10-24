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

    private AppUserDAO appUserDAO = new AppUserDAO();

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == AppUser.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppUser appUser = (AppUser) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.appUser.email", "Email is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.appUser.firstName", "Name is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.appUser.lastName", "Surname is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.appUser.password", "Password is required");

        if (appUser.getEmail().length() > 200) errors.rejectValue("email", "Length.appUser.email", "Email address is too long");
        if (appUser.getFirstName().length() > 100) errors.rejectValue("name", "Length.appUser.email", "Email address is too long");
        if (appUser.getLastName().length() > 100) errors.rejectValue("surname", "Length.appUser.email", "Email address is too long");
        if (appUser.getPassword().length() > 73) errors.rejectValue("password", "Length.appUser.password", "Password is too long");


        if (!this.emailValidator.isValid(appUser.getEmail())) {
            errors.rejectValue("email", "Pattern.appUser.email", "Not valid email");
        } else if (appUser.getUserId() == null) {
            AppUser dbUser = appUserDAO.findAppUserByEmail(appUser.getEmail());
            if (dbUser != null) {
                errors.rejectValue("email", "Duplicate.appUser.email", "User with such email is already registered");
            }
        }

        if (!errors.hasErrors()) {

        }
    }
}