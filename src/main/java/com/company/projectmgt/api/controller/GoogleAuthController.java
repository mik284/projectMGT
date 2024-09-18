//package com.company.projectmgt.api.controller;
//
//import com.company.projectmgt.api.services.GoogleAuthService;
//import com.company.projectmgt.entity.Member;
//import io.jmix.core.DataManager;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.Principal;
//import java.time.LocalDate;
//import java.time.format.DateTimeParseException;
//import java.util.Map;
//
//@Slf4j
//@RestController
////@RequestMapping("/")
//public class GoogleAuthController {
//
//    @Autowired
//    private GoogleAuthService googleAuthService;
//
//    @Autowired
//    private DataManager dataManager;
//
//    @GetMapping("/login/oauth2/google")
//    public String login() {
//        return "login with google";
//    }
//
//
//    @GetMapping("/google-auth")
//    public ResponseEntity<?> authenticateGoogle(OAuth2AuthenticationToken authentication) {
//        try {
//            Map<String, Object> userInfo = googleAuthService.getUserInfo(authentication);
//            log.info("User Info: {}", userInfo);
//
//            String email = (String) userInfo.get("email");
//            if (email == null) {
//                email = (String) userInfo.get("sub");
//                if (email == null) {
//                    return ResponseEntity.badRequest().body("Authentication failed: Unique identifier not found in user info");
//                }
//            }
//
//            Member user = dataManager.create(Member.class);
//            user.setEmail(email);
//            user.setUsername(email);
//            user.setPhoneNumber(userInfo.get("phone_number") != null ? userInfo.get("phone_number").toString() : "");
//            user.setFullName(userInfo.get("name") != null ? userInfo.get("name").toString() : "");
//            if (userInfo.get("birthdate") instanceof String) {
//                String birthdateStr = (String) userInfo.get("birthdate");
//                try {
//                    LocalDate dob = LocalDate.parse(birthdateStr); // Adjust parsing based on format
//                    user.setDob(dob);
//                } catch (DateTimeParseException e) {
//                    log.error("Error parsing birthdate: {}", e.getMessage());
//                }
//            } else {
//                user.setDob(null); // or set a default value
//            }
//            dataManager.save(user);
//
//            return ResponseEntity.ok(email);
//        } catch (Exception e) {
//            log.error("Authentication failed: {}", e.getMessage());
//            return ResponseEntity.badRequest().body("Authentication failed: " + e.getMessage());
//        }
//    }
//}
