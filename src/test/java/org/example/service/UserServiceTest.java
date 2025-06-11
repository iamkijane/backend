package org.example.service;

import org.example.dto.request.SignupRequest;
import org.example.dto.response.SignupResponse;
import org.example.entity.Role;
import org.example.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void signup_success() {
        SignupRequest request = new SignupRequest("newUser", "password123", "닉네임");
        SignupResponse response = userService.signup(request);

        assertEquals("newUser", response.getUsername());
        assertEquals("닉네임", response.getNickname());
        assertTrue(response.getRoles().stream().anyMatch(r -> r.name().equals("USER")));
    }

    @Test
    void signup_fail_duplicate_user() {
        SignupRequest request = new SignupRequest("dupUser", "pw", "nick");
        userService.signup(request);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.signup(request);
        });
    }

    @Test
    void login_success() {
        SignupRequest request = new SignupRequest("loginUser", "pw123", "닉네임");
        userService.signup(request);

        String token = userService.login("loginUser", "pw123");

        assertNotNull(token);
    }

    @Test
    void login_fail_wrong_credentials() {
        SignupRequest request = new SignupRequest("wrongUser", "pw123", "닉네임");
        userService.signup(request);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.login("wrongUser", "wrongPw");
        });

        assertEquals("아이디 또는 비밀번호가 올바르지 않습니다.", exception.getMessage());
    }

    @Test
    void promoteToAdmin_success() {
        SignupRequest request = new SignupRequest("adminUser", "pw", "닉");
        userService.signup(request);

        userService.promoteToAdmin("adminUser");

        User user = userService.findByUsername("adminUser");
        assertTrue(user.getRoles().contains(Role.ADMIN));
    }

    @Test
    void promoteToAdmin_fail_user_not_found() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            userService.promoteToAdmin("nonexistentUser");
        });

        assertEquals("해당 사용자를 찾을 수 없습니다.", e.getMessage());
    }
}