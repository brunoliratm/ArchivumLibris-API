package com.archivumlibris.security.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.archivumlibris.domain.model.user.User;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String createToken(String email, String password,
            AuthenticationManager authenticationManager) {
        try {
            var userPassword = new UsernamePasswordAuthenticationToken(email, password);
            var auth = authenticationManager.authenticate(userPassword);
            return generateToken((User) auth.getPrincipal());
        } catch (Exception e) {
            throw new RuntimeException("Error while logging in", e.getCause());
        }
    }

    private String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create().withIssuer("api-v1-auth").withClaim("id", user.getId())
                    .withClaim("email", user.getEmail())
                    .withArrayClaim("roles", new String[] {user.getRole().name()})
                    .withIssuedAt(genIssuedAtDate()).withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    private Instant genIssuedAtDate() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            DecodedJWT decodedJWT =
                    JWT.require(algorithm).withIssuer("api-v1-auth").build().verify(token);

            if (decodedJWT.getExpiresAt().before(new Date())) {
                throw new RuntimeException("Token expired");
            }

            return decodedJWT.getClaim("email").asString();

        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid token", exception);
        }
    }
}
