package com.company.projectmgt.api.controller;

import com.company.projectmgt.api.service.OrderService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import io.jmix.rest.annotation.RestMethod;
import io.jmix.rest.annotation.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@AnonymousAllowed
@RequestMapping("/api/v1")
@RestController
public class OrderController {


    @GetMapping
    public String getMe() {
        return "hello manases";
    }
}