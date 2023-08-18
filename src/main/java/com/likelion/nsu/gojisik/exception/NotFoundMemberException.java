package com.likelion.nsu.gojisik.exception;

public class NotFoundMemberException extends RuntimeException {
    public NotFoundMemberException() {
        super();
    }
    public NotFoundMemberException(String message) {
        super(message);
    }
    public NotFoundMemberException(Throwable cause) {
        super(cause);
    }
    public NotFoundMemberException(String message, Throwable cause) {
        super(message, cause);
    }
}