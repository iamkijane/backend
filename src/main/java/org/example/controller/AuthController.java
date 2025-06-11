package org.example.controller;

import org.example.dto.request.LoginRequest;
import org.example.dto.response.LoginResponse;
import org.example.dto.response.SignupResponse;
import org.example.dto.request.SignupRequest;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            SignupResponse response = userService.signup(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", Map.of(
                            "code", "USER_ALREADY_EXISTS",
                            "message", e.getMessage()
                    )));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = userService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(401)
                    .body(Map.of("error", Map.of(
                            "code", "INVALID_CREDENTIALS",
                            "message", e.getMessage()
                    )));
        }
    }
    @PatchMapping("/admin/users/{username}/roles")
    public ResponseEntity<?> promoteToAdmin(@PathVariable String username) {
        try {
            userService.promoteToAdmin(username);
            return ResponseEntity.ok(Map.of("message", "관리자 권한 부여 성공"));
        } catch (Exception e) {
            return ResponseEntity.status(403).body(Map.of("error", Map.of(
                    "code", "ACCESS_DENIED",
                    "message", e.getMessage()
            )));
        }
    }
}
