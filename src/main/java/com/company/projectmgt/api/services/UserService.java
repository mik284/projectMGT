package com.company.projectmgt.api.services;

import com.company.projectmgt.api.dto.ForgotPasswordDto;
import com.company.projectmgt.api.dto.LoginDto;
import com.company.projectmgt.api.dto.OtpDto;
import com.company.projectmgt.api.dto.RegisterDto;
import com.company.projectmgt.entity.Member;
import com.company.projectmgt.entity.Otp;
import com.company.projectmgt.wrapper.ResponseWrapper;
import io.jmix.core.DataManager;
import io.jmix.rest.annotation.RestMethod;
import io.jmix.rest.annotation.RestService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import io.jmix.email.EmailException;
import io.jmix.email.EmailInfo;
import io.jmix.email.EmailInfoBuilder;
import io.jmix.email.Emailer;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;



@Log4j2
@RestService("service")
public class UserService {
    @Autowired
    protected DataManager dataManager;
    @Autowired
    private Emailer emailer;

    public void sendOtpToUser(String email, int otpCode) throws EmailException {
        String emailBody = String.format("Your OTP for login authentication is: %d", otpCode);
        EmailInfo emailInfo = EmailInfoBuilder.create()
                .setAddresses(email)
                .setSubject("OTP for Login Authentication")
                .setFrom(null)
                .setBody(emailBody)
                .build();

        emailer.sendEmail(emailInfo);
    }

    @RestMethod
    public ResponseWrapper<RegisterDto> register(RegisterDto registerDto) {
        ResponseWrapper<RegisterDto> responseWrapper = new ResponseWrapper<>();

        // Check if a user with the provided username already exists
        Optional<Member> existingUser = dataManager.load(Member.class)
                .query("select m from Member_ m where m.username = :username")
                .parameter("username", registerDto.getUsername())
                .optional();

        if (existingUser.isPresent()) {
            // If the user already exists, return an error response
            responseWrapper.setCode(409);
            responseWrapper.setMessage("Username already exists");
        } else {
            // Proceed with registration
            Member member = dataManager.create(Member.class);
            member.setFullName(registerDto.getFullName());
            member.setEmail(registerDto.getEmail());
            member.setPassword(registerDto.getPassword());
            member.setDob(registerDto.getDob());
            member.setUsername(registerDto.getUsername());
            member.setPhoneNumber(registerDto.getPhone());

            dataManager.save(member);
            responseWrapper.setCode(200);
            responseWrapper.setMessage("Successfully registered");
            responseWrapper.setData(registerDto);
        }

        return responseWrapper;
    }

    @RestMethod
    public ResponseWrapper<?> login(LoginDto loginDto) {
        ResponseWrapper<LoginDto> responseWrapper = new ResponseWrapper<>();
        try {
            Member existingUser = dataManager.load(Member.class)
                    .query("select m from Member_ m where m.username = :username")
                    .parameter("username", loginDto.getUsername())
                    .optional()
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!existingUser.getPassword().equals(loginDto.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }

            int otpCode = otpGenerator();
            Otp otp = dataManager.create(Otp.class);
            otp.setOtpUsed(false);
            otp.setOtpCode(otpCode);
            otp.setPurpose("login");
            otp.setMemberId(existingUser);
            otp.setExpirationTime(new Date(System.currentTimeMillis() + 30 * 60 * 1000));

            try {
                sendOtpToUser(existingUser.getEmail(), otpCode);
                dataManager.save(otp);
                responseWrapper.setMessage("OTP successfully sent. Please check your email.");
                responseWrapper.setCode(200);


            } catch (EmailException e) {
                responseWrapper.setCode(500);
                responseWrapper.setMessage("Failed to send OTP. Please try again later.");
            }

        } catch (RuntimeException e) {
            if (e.getMessage().equals("User not found")) {
                responseWrapper.setCode(404);
            } else if (e.getMessage().equals("Invalid credentials")) {
                responseWrapper.setCode(401);
            } else {
                responseWrapper.setCode(500);
            }
            responseWrapper.setMessage(e.getMessage());
        } catch (Exception e) {
            responseWrapper.setCode(500);
            responseWrapper.setMessage("An error occurred during login. Please try again later.");
            // Log the exception for debugging
            // logger.error("Error during login process", e);
        }

        return responseWrapper;
    }

    @RestMethod
    public ResponseWrapper<Boolean> verifyOtp(OtpDto otpDto) {
        ResponseWrapper<Boolean> responseWrapper = new ResponseWrapper<>();
        try {
            Member user = dataManager.load(Member.class)
                    .query("select m from Member_ m where m.username = :username")
                    .parameter("username", otpDto.getUserName())
                    .optional()
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Otp otp = dataManager.load(Otp.class)
                    .query("select o from Otp o where o.memberId = :memberId and o.otpUsed = false")
                    .parameter("memberId", user)
                    .maxResults(1)
                    .optional()
                    .orElseThrow(() -> new RuntimeException("No valid OTP found"));

            if (otp.getOtpCode() == otpDto.getOtp() && !isOtpExpired(otp)) {
                otp.setOtpUsed(true);
                dataManager.save(otp);
                responseWrapper.setCode(200);
                responseWrapper.setMessage("OTP verified successfully");
                responseWrapper.setData(true);
            } else {
                responseWrapper.setCode(400);
                responseWrapper.setMessage("Invalid or expired OTP");
                responseWrapper.setData(false);
            }
        } catch (RuntimeException e) {
            responseWrapper.setCode(404);
            responseWrapper.setMessage(e.getMessage());
            responseWrapper.setData(false);
        } catch (Exception e) {
            responseWrapper.setCode(500);
            responseWrapper.setMessage("An error occurred during OTP verification");
            responseWrapper.setData(false);
        }
        return responseWrapper;
    }

    @RestMethod
    public ResponseWrapper<?> forgotPassword(ForgotPasswordDto forgotPasswordDto) {
        ResponseWrapper<?> responseWrapper = new ResponseWrapper<>();
        try {
            Member user = dataManager.load(Member.class)
                    .query("select m from Member_ m where m.email = :email")
                    .parameter("email", forgotPasswordDto.getEmail())
                    .optional()
                    .orElseThrow(() -> new RuntimeException("User not found"));

            int otpCode = otpGenerator();
            Otp otp = dataManager.create(Otp.class);
            otp.setOtpUsed(false);
            otp.setOtpCode(otpCode);
            otp.setPurpose("password_reset");
            otp.setMemberId(user);


            try {
                sendOtpToUser(user.getEmail(), otpCode);
                dataManager.save(otp);
                responseWrapper.setMessage("Password reset OTP sent. Please check your email.");
                responseWrapper.setCode(200);
            } catch (EmailException e) {
                responseWrapper.setCode(500);
                responseWrapper.setMessage("Failed to send OTP. Please try again later.");
            }
        } catch (RuntimeException e) {
            responseWrapper.setCode(404);
            responseWrapper.setMessage(e.getMessage());
        } catch (Exception e) {
            responseWrapper.setCode(500);
            responseWrapper.setMessage("An error occurred during password reset request. Please try again later.");
        }
        return responseWrapper;
    }

    @RestMethod
    public ResponseWrapper<?> resetPassword(OtpDto otpDto, String newPassword) {
        ResponseWrapper<?> responseWrapper = new ResponseWrapper<>();
        try {
            ResponseWrapper<Boolean> verificationResponse = verifyOtp(otpDto);
            if (verificationResponse.getCode() == 200) {
                Member user = dataManager.load(Member.class)
                        .query("select m from Member_ m where m.username = :username")
                        .parameter("username", otpDto.getUserName())
                        .one();
                user.setPassword(newPassword); // You should hash this password before saving
                dataManager.save(user);
                responseWrapper.setCode(200);
                responseWrapper.setMessage("Password reset successfully");
            } else {
                responseWrapper.setCode(verificationResponse.getCode());
                responseWrapper.setMessage(verificationResponse.getMessage());
            }
        } catch (Exception e) {
            responseWrapper.setCode(500);
            responseWrapper.setMessage("An error occurred during password reset. Please try again later.");
        }
        return responseWrapper;
    }



    private boolean isOtpExpired(Otp otp) {
        log.debug("Entering isOtpExpired method");
        try {
            Date expirationTime = otp.getExpirationTime();
            log.debug("Expiration time: " + expirationTime);
            log.debug("Expiration time class: " + (expirationTime != null ? expirationTime.getClass().getName() : "null"));

            if (expirationTime == null) {
                log.debug("Expiration time is null, considering OTP expired");
                return true;
            }

            boolean isExpired = expirationTime.before(new Date());
            log.debug("Is OTP expired? " + isExpired);
            return isExpired;
        } catch (Exception e) {
            log.error("Exception in isOtpExpired method", e);
            return true; // Assume expired on error
        } finally {
            log.debug("Exiting isOtpExpired method");
        }
    }

    @RestMethod
    public ResponseWrapper<List<Member>> getAllUsers() {
        ResponseWrapper<List<Member>> responseWrapper = new ResponseWrapper<>();
        List<Member> existingUser = dataManager.load(Member.class).query("select m from Member_ m").list();

        if (!existingUser.isEmpty()) {
            responseWrapper.setMessage("Successfully retrieved users");
            responseWrapper.setCode(200);
            responseWrapper.setData(existingUser);
        } else {
            responseWrapper.setCode(404);
            responseWrapper.setMessage("user not found");
        }
        return responseWrapper;
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}
