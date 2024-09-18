package com.company.projectmgt.api.controller;

import com.company.projectmgt.api.dto.LoginDto;
import com.company.projectmgt.api.dto.OtpDto;
import com.company.projectmgt.api.dto.RegisterDto;

import com.company.projectmgt.api.services.UserService;
import com.company.projectmgt.entity.Member;
import com.company.projectmgt.wrapper.ResponseWrapper;

import io.jmix.core.DataManager;
import io.jmix.rest.annotation.RestHttpMethod;
import io.jmix.rest.annotation.RestMethod;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    protected DataManager dataManager;
    @Autowired
    private UserService userService;

    @GetMapping("/member")
    public String member(Principal principal) {
        return principal.getName();
    }
  @RestMethod(httpMethods =  RestHttpMethod.POST)
  @PostMapping("/register")
public ResponseWrapper<RegisterDto> register(@RequestBody RegisterDto registerDto) {
    return userService.register(registerDto);

}

@GetMapping("/fetchMembers")
public ResponseWrapper<List<Member>> getData(){

   return userService.getAllUsers();
  }
@RestMethod(httpMethods =  RestHttpMethod.POST)
@PostMapping("/login")
    public ResponseWrapper<?> login(@RequestBody LoginDto loginDto) {
 return userService.login(loginDto);

}

    @RestMethod(httpMethods =  RestHttpMethod.POST)
    @PostMapping("/verifyOtp")
    public ResponseWrapper<?> verifyOtp(@RequestBody OtpDto otpDto) {
        return userService.verifyOtp(otpDto);

    }
}
