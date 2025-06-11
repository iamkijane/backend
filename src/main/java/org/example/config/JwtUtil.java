package org.example.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.entity.Role;

import java.util.Date;
import java.util.Set;
import java.util.Base64;

public class JwtUtil {

    private static final String SECRET_KEY = "rx5uuEgjQRFy/OuVwsXDNd8YISaPnjOaWPhes/OCnsbCq/0qQAsfJ+tg97nhd9Inko7kJQ664dpr0CgwFRcNlQ=="; // application.properties에서 가져오면 더 좋음
    private static final long EXPIRATION = 1000 * 60 * 60 * 2; // 2시간

    public static String generateToken(String username, Set<Role> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, Base64.getDecoder().decode(SECRET_KEY))
                .compact();
    }
}
