package com.company.projectmgt.api.controller;

import com.company.projectmgt.api.dto.LoginDto;
import com.company.projectmgt.api.dto.OtpDto;
import com.company.projectmgt.api.dto.RegisterDto;
//import com.company.projectmgt.api.repo.MemberRepository;
import com.company.projectmgt.api.services.UserService;
import com.company.projectmgt.entity.Member;
import com.company.projectmgt.wrapper.ResponseWrapper;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import io.jmix.core.DataManager;
import io.jmix.rest.annotation.RestHttpMethod;
import io.jmix.rest.annotation.RestMethod;
import io.jmix.rest.annotation.RestService;
import io.jmix.security.role.annotation.ResourceRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    protected DataManager dataManager;
    @Autowired
    private UserService userService;


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
