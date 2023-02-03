package com.ngu.pizzamania.Service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    public void SendEmail(SimpleMailMessage email);
}
