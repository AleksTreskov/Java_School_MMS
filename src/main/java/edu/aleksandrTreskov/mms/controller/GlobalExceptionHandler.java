package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoSuchElementException.class)
    public ErrorInfo handleNoSuchElementException(NoSuchElementException ex) {
        LOGGER.error(ex.getMessage());
        LOGGER.error(Arrays.toString(ex.getStackTrace()));
        return new ErrorInfo(ex.getMessage());
    }

    @ExceptionHandler(DiscountCodeException.class)
    public ErrorInfo handleDiscountCodeException(DiscountCodeException ex) {
        LOGGER.warn(ex.getMessage());
        LOGGER.warn(ex.getMessage());
        return new ErrorInfo(ex.getMessage());
    }
    @ExceptionHandler(WrongActivationCodeException.class)
    public ErrorInfo handleWrongActivationCodeException(WrongActivationCodeException ex) {
        LOGGER.warn(ex.getMessage());
        LOGGER.warn(ex.getMessage());
        return new ErrorInfo(ex.getMessage());
    }

    @ExceptionHandler(PasswordsNotMatchException.class)
    public ErrorInfo handleMismatchPassword(PasswordsNotMatchException ex) {
        LOGGER.warn(ex.getMessage());
        LOGGER.warn(Arrays.toString(ex.getStackTrace()));
        return new ErrorInfo(ex.getMessage());
    }

    @ExceptionHandler(OutOfStockException.class)
    public ErrorInfo handleOutOfStockException(OutOfStockException ex) {
        LOGGER.error(ex.getMessage());
        LOGGER.error(Arrays.toString(ex.getStackTrace()));
        return new ErrorInfo(ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ErrorInfo handleExistEmailException(EmailAlreadyExistsException ex) {
        LOGGER.error(ex.getMessage());
        LOGGER.error(Arrays.toString(ex.getStackTrace()));
        return new ErrorInfo(ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, FieldIsEmptyException.class})
    public ErrorInfo handleValidFieldsException(Exception ex) {
        LOGGER.error(ex.getMessage());
        LOGGER.error(Arrays.toString(ex.getStackTrace()));
        return new ErrorInfo("Check your fields: at least one of them is empty or not valid.");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleException(NoHandlerFoundException ex) {
        LOGGER.error(ex.getMessage());
        LOGGER.error(Arrays.toString(ex.getStackTrace()));
        ModelAndView mav = new ModelAndView();
        mav.addObject("exStackTrace", ex.getStackTrace());
        mav.addObject("exceptionMsg", ex.getMessage());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handlerNotFoundEntity(EntityNotFoundException ex) {
        LOGGER.error(ex.getMessage());
        LOGGER.error(Arrays.toString(ex.getStackTrace()));
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex.getMessage());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

    static class ErrorInfo {
        public boolean error;
        public String message;
        public Map<String, String> errors;


        ErrorInfo(String message) {
            this.message = message;
            this.error = true;
        }


    }
}
