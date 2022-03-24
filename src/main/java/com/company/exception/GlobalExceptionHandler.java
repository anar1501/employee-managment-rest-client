package com.company.exception;

import com.company.resource.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception) {
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setCode(500);
//        errorResponse.setMessage(exception.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(UnconfirmedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleUnconfirmedException(UnconfirmedException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleWrongPasswordException(WrongPasswordException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyConfirmedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleAlreadyConfirmedException(AlreadyConfirmedException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExpirationCodeIsExpiredException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleExpirationCodeIsExpiredException(ExpirationCodeIsExpiredException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailIsInCorrectException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleEmailIsInCorrectException(EmailIsInCorrectException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SixDigitCodeInCorrectException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleSixDigitCodeInCorrectException(SixDigitCodeInCorrectException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }



}
