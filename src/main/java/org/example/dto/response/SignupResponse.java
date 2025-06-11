package org.example.dto.response;

import org.example.entity.Role;
import java.util.Set;

public class SignupResponse {
    private String username;
    private String nickname;
    private Set<Role> roles;

    public SignupResponse(String username, String nickname, Set<Role> roles) {
        this.username = username;
        this.nickname = nickname;
        this.roles = roles;
    }

    public String getUsername() { return username; }
    public String getNickname() { return nickname; }
    public Set<Role> getRoles() { return roles; }
}
