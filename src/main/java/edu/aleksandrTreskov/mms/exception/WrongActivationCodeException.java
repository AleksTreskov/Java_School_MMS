package edu.aleksandrTreskov.mms.exception;

public class WrongActivationCodeException extends RuntimeException {
    public WrongActivationCodeException(String message) {
        super(message);
    }
}
