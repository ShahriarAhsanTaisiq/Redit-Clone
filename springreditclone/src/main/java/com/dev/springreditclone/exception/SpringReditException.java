package com.dev.springreditclone.exception;

import org.springframework.mail.MailException;

public class SpringReditException extends RuntimeException {
    public SpringReditException(String message) {
        super(message);
    }
}
