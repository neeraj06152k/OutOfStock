package com.example.ecom.libraries;

import com.example.ecom.models.User;

import java.util.List;

public class SendNotificationImpl implements SendNotification{
    private Sendgrid sendgrid = new Sendgrid();

    @Override
    public void notify(String email, String subject, String body) {
        sendgrid.sendEmailAsync(email, subject, body);
    }
}
