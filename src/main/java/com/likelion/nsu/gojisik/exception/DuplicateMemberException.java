package com.likelion.nsu.gojisik.exception;


public class DuplicateMemberException extends RuntimeException {
    public DuplicateMemberException() {
        super();
    }
    public DuplicateMemberException(String message) {
        super(message);
    }
    public DuplicateMemberException(Throwable cause) {
        super(cause);
    }
    public DuplicateMemberException(String message, Throwable cause) {
        super(message, cause);
    }
}