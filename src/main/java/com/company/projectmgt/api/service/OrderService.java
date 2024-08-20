package com.company.projectmgt.api.service;

import io.jmix.rest.annotation.RestHttpMethod;
import io.jmix.rest.annotation.RestMethod;
import io.jmix.rest.annotation.RestService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestService("welcome")
@RestController
public class OrderService {

    @RestMethod(httpMethods = RestHttpMethod.GET)
    @GetMapping("/welcome")
    public String getWelcomeMessage() {
        return "helloo mike";
    }
}
