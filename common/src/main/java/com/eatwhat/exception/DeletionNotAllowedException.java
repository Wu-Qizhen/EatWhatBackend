package com.eatwhat.exception;

public class DeletionNotAllowedException extends BaseException {
    public DeletionNotAllowedException(String msg) {
        super(msg);
    }
}