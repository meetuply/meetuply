package ua.meetuply.backend.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;
import ua.meetuply.backend.controller.exception.MeetupNotFoundException;
import ua.meetuply.backend.controller.exception.UserNotFoundException;
import ua.meetuply.backend.model.ApiError;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ MeetupNotFoundException.class, UserNotFoundException.class, DataAccessException.class})
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof DataAccessException) {
            HttpStatus status = HttpStatus.CONFLICT;
            DataAccessException dae = (DataAccessException) ex;

            return dataAccessException(dae, headers, status, request);
        } else if (ex instanceof UserNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            UserNotFoundException unfe = (UserNotFoundException) ex;

            return handleNotFoundException(unfe, headers, status, request);
        } else if (ex instanceof MeetupNotFoundException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            MeetupNotFoundException mnfe = (MeetupNotFoundException) ex;

            return handleNotFoundException(mnfe, headers, status, request);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }

    private ResponseEntity<ApiError> dataAccessException(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors;
        if (ex.getCause() != null) errors = Collections.singletonList(ex.getCause().getMessage());
        else errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors), headers, status, request);
    }

    private ResponseEntity<ApiError> handleNotFoundException(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors), headers, status, request);
    }

    private ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }
}
