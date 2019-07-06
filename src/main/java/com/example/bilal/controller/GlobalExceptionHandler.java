package com.example.bilal.controller;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.bilal.exception.AlreadyExistException;
import com.example.bilal.util.Utils;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        LOG.error("[NotFoundException] Exception: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseBody
    public ResponseEntity<?> handleAlreadyException(AlreadyExistException ex) {
        LOG.error("[AlreadyException] Exception: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        LOG.error("[ValidationException] Exception: {}", ex.getBindingResult().getFieldErrors());
        Iterator ite = ex.getBindingResult().getFieldErrors().iterator();
        String exceptionString = null;
        while (ite.hasNext()) {
            FieldError err = (FieldError) ite.next();
            exceptionString = exceptionString != null ? Utils.addFieldValidationException(exceptionString, err.getDefaultMessage()) : err.getDefaultMessage();
        }
        return ResponseEntity.badRequest().body(exceptionString);
    }

}
