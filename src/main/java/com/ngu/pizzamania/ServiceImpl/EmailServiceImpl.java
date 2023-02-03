package com.ngu.pizzamania.ServiceImpl;

import com.ngu.pizzamania.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void SendEmail(SimpleMailMessage email) {
        this.javaMailSender.send(email);
    }
}
