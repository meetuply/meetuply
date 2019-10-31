package ua.meetuply.backend.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ua.meetuply.backend.controller.exception.MeetupNotFoundException;
import ua.meetuply.backend.controller.exception.MeetupStateException;
import ua.meetuply.backend.controller.exception.PermissionException;
import ua.meetuply.backend.controller.exception.UserNotFoundException;
import ua.meetuply.backend.model.ApiError;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ DataAccessException.class })
    private ResponseEntity<ApiError> dataAccessException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (ex.getCause() != null)
            return new ResponseEntity<>(new ApiError(ex.getCause().getMessage()), headers, HttpStatus.CONFLICT);
        else
            return new ResponseEntity<>(new ApiError(ex.getMessage()), headers, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            MeetupNotFoundException.class,
            UsernameNotFoundException.class
    })
    private ResponseEntity<ApiError> handleNotFoundException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ApiError(ex.getMessage()), headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            MeetupStateException.class
    })
    private ResponseEntity<ApiError> handleNotAcceptableException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ApiError(ex.getMessage()), headers, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({
            PermissionException.class
    })
    private ResponseEntity<ApiError> handleForbiddenException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ApiError(ex.getMessage()), headers, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ApiError> handleNotValidArgumentException(MethodArgumentNotValidException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        ApiError error = new ApiError(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(error, headers, HttpStatus.NOT_ACCEPTABLE);
    }
}

