package org.example.entity;

import org.example.entity.Role;
import java.util.HashSet;
import java.util.Set;

public class User {
    private static Long idCounter = 1L;

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private Set<Role> roles = new HashSet<>();

    public User(String username, String password, String nickname) {
        this.id = idCounter++;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.roles.add(Role.USER);
    }
}