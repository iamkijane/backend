package org.example.service;

import org.example.dto.request.SignupRequest;
import org.example.dto.response.SignupResponse;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.config.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private final Map<String, User> userStorage = new ConcurrentHashMap<>();

    public SignupResponse signup(SignupRequest request) {
        if (userStorage.containsKey(request.getUsername())) {
            throw new IllegalArgumentException("이미 가입된 사용자입니다.");
        }

        User newUser = new User(request.getUsername(), request.getPassword(), request.getNickname());
        userStorage.put(newUser.getUsername(), newUser);

        return new SignupResponse(newUser.getUsername(), newUser.getNickname(), newUser.getRoles());
    }

    public String login(String username, String password) {
        User user = userStorage.get(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        return JwtUtil.generateToken(user.getUsername(), user.getRoles());
    }
    public void promoteToAdmin(String username) {
        User user = userStorage.get(username);
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
        user.getRoles().add(Role.ADMIN);
    }
}
