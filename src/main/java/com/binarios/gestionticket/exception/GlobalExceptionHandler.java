package com.binarios.gestionticket.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateResource.class)
    protected ResponseEntity<Object> handleDuplicateResourceException(DuplicateResource ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT)
                .message("Duplicate Resource.")
                .errors(details)
                .build();
        return ResponseEntityBuilder.build(apiError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message("There is no data to return back.")
                .errors(details)
                .build();
        return ResponseEntityBuilder.build(apiError);
    }

    @ExceptionHandler(UnexpectedProblemException.class)
    protected ResponseEntity<Object> handleUnexpectedProblemException(UnexpectedProblemException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("There was an unexpected problem.")
                .errors(details)
                .build();
        return ResponseEntityBuilder.build(apiError);
    }

    @ExceptionHandler(NoAuthorithyException.class)
    protected ResponseEntity<Object> handleUnexpectedProblemException(NoAuthorithyException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED)
                .message("You do not have the authority.")
                .errors(details)
                .build();
        return ResponseEntityBuilder.build(apiError);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllException(Exception exception, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(exception.getMessage());

        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message("Other exception occurs")
                .errors(details)
                .build();
        return ResponseEntityBuilder.build(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getObjectName() + " : " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message("Validation Errors")
                .errors(details)
                .build();
        return ResponseEntityBuilder.build(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message("Malformed JSON found.")
                .errors(details)
                .build();
        return ResponseEntityBuilder.build(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message("Unsupported Media Type")
                .errors(details)
                .build();
        return ResponseEntityBuilder.build(apiError);
    }

//    @Override
//    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        List<String> details = new ArrayList<>();
//        details.add(ex.getMessage());
//
//        ApiError apiError = ApiError.builder()
//                .timestamp(LocalDateTime.now())
//                .status(HttpStatus.NOT_FOUND)
//                .message("Method not supported")
//                .errors(details)
//                .build();
//        return ResponseEntityBuilder.build(apiError);
//    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message("Method not supported")
                .errors(details)
                .build();
        return ResponseEntityBuilder.build(apiError);
    }


}