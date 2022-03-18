package edu.aleksandrTreskov.mms.exception;

public class PasswordsNotMatchException extends RuntimeException {
    public PasswordsNotMatchException(String message) {
        super(message);
    }
}
