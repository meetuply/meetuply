package ua.meetuply.backend.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.meetuply.backend.dao.BlogPostDAO;
import ua.meetuply.backend.formbean.BlogPostForm;

@Component
public class BlogPostValidator implements Validator {

    @Autowired
    private BlogPostDAO blogPostDAO;

    // The classes are supported by this validator.
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == BlogPostForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        BlogPostForm blogPostForm = (BlogPostForm) target;

        // Check the fields of AppUserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "blogPostTitle", "NotEmpty.blogPostForm.blogPostTitle");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "blogPostContent", "NotEmpty.blogPostForm.blogPostContent");
    }

}
