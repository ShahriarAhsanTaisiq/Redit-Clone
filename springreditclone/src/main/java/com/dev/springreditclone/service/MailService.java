package com.dev.springreditclone.service;

import com.dev.springreditclone.model.NotificationEmail;

public interface MailService {
    public void sendMail(NotificationEmail notificationEmail);
}
