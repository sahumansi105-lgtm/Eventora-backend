package com.pronight.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.pronight.dto.LoginResponse;
import com.pronight.entity.OtpEntity;
import com.pronight.entity.UserEntity;
import com.pronight.repository.OtpRepo;
import com.pronight.repository.UserRepo;
import com.pronight.security.JwtUtil;
import com.pronight.service.EmailService;
import com.pronight.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OtpRepo otpRepo;

    @Autowired
    private JwtUtil jwtUtil;

    // ==========================
    // GET ALL USERS
    // ==========================
    @GetMapping("/all")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    // ==========================
    // GET USER BY ID
    // ==========================
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        Optional<UserEntity> user = userService.getById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }

        return ResponseEntity.status(404)
                .body("User Not Found");
    }

    // ==========================
    // REGISTER USER
    // ==========================
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody UserEntity user) {

        try {

            UserEntity saved =
                    userService.registerUser(user);

            return ResponseEntity.ok(saved);

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // ==========================
    // LOGIN
    // ==========================
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody Map<String, String> loginData) {

        String email = loginData.get("email");
        String password = loginData.get("password");

        UserEntity user =
                userService.login(email, password);

        if (user == null) {
            throw new RuntimeException(
                    "Invalid email or password");
        }

        String token =
                jwtUtil.generateToken(
                        user.getEmail(),
                        user.getRole());

        return new LoginResponse(
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }

    // ==========================
    // UPDATE USER
    // ==========================
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody UserEntity user) {

        Optional<UserEntity> updated =
                userService.updateUser(id, user);

        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        }

        return ResponseEntity.notFound().build();
    }

    // ==========================
    // DELETE USER
    // ==========================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id) {

        boolean deleted =
                userService.deleteUser(id);

        if (deleted) {
            return ResponseEntity.ok(
                    "User Deleted Successfully");
        }

        return ResponseEntity.notFound().build();
    }

    // ==========================
    // SEND OTP
    // ==========================
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(
            @RequestBody Map<String, String> request) {

        String email = request.get("email");

        Optional<UserEntity> user =
                userRepo.findByEmail(email);

        if (user.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Email Not Registered");
        }

        String otp =
                String.valueOf(
                        100000 +
                        new Random().nextInt(900000));

        OtpEntity otpEntity =
                new OtpEntity(email, otp);

        otpRepo.save(otpEntity);

        emailService.sendOtp(email, otp);

        return ResponseEntity.ok(
                "OTP Sent Successfully");
    }

    // ==========================
    // VERIFY OTP
    // ==========================
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(
            @RequestBody Map<String, String> request) {

        String email = request.get("email");
        String otp = request.get("otp");

        OtpEntity storedOtp =
                otpRepo.findTopByEmailOrderByIdDesc(email);

        if (storedOtp == null) {
            return ResponseEntity.badRequest()
                    .body("OTP Not Found");
        }

        if (storedOtp.getOtp().equals(otp)) {
            return ResponseEntity.ok("Verified");
        }

        return ResponseEntity.badRequest()
                .body("Invalid OTP");
    }

    // ==========================
    // RESET PASSWORD
    // ==========================
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody Map<String, String> request) {

        String email = request.get("email");
        String newPass = request.get("newPass");

        Optional<UserEntity> userOptional =
                userRepo.findByEmail(email);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("User Not Found");
        }

        UserEntity user =
                userOptional.get();

        user.setPassword(newPass);

        userRepo.save(user);

        return ResponseEntity.ok(
                "Password Updated Successfully");
    }

    // ==========================
    // USER PROFILE
    // ==========================
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        UserEntity user =
                userRepo.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("User not found"));

        return ResponseEntity.ok(user);
    }
    
    
    @PutMapping("/profile/update")
    public ResponseEntity<?> updateProfile(
            @RequestBody UserEntity updatedUser) {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setFullName(updatedUser.getFullName());
        user.setPhoneNumber(updatedUser.getPhoneNumber());

        userRepo.save(user);

        return ResponseEntity.ok(user);
    }
}