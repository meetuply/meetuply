package ua.meetuply.backend.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.meetuply.backend.dao.BlogCommentDAO;
import ua.meetuply.backend.formbean.BlogCommentForm;

@Component
public class BlogCommentValidator implements Validator {

    @Autowired
    private BlogCommentDAO blogCommentDAO;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == BlogCommentForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        BlogCommentForm blogCommentForm = (BlogCommentForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "blogCommentContent", "NotEmpty.blogCommentForm.blogCommentContent");
    }

}
