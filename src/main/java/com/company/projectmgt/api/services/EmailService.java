package com.company.projectmgt.api.services;


import com.company.projectmgt.api.dto.EmailDto;

import io.jmix.email.EmailInfo;
import io.jmix.email.EmailInfoBuilder;
import io.jmix.email.Emailer;

import io.jmix.rest.annotation.RestService;

import org.springframework.beans.factory.annotation.Autowired;

@RestService("email")
public class EmailService {
    @Autowired
    private Emailer emailer;

    public void sendByEmail(EmailDto emailDto) {

        EmailInfo emailInfo = EmailInfoBuilder.create()
                .setAddresses(emailDto.to())
                .setSubject(emailDto.subject())
                .setFrom("manaseschege0@gmail.com")
                .setBody(emailDto.text())
                .build();
        emailer.sendEmailAsync(emailInfo);
    }
}
