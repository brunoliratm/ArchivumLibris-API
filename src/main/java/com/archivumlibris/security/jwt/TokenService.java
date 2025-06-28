package com.archivumlibris.security.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.archivumlibris.application.service.user.UserService;
import com.archivumlibris.domain.model.user.User;
import com.archivumlibris.dto.request.auth.AuthRequestDTO;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private static final String ISSUER = "archivum-libris-api";
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    TokenService(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    public String createToken(AuthRequestDTO authRequestDTO) {
        try {
            var userPassword = new UsernamePasswordAuthenticationToken(authRequestDTO.email(),
                    authRequestDTO.password());
            var auth = this.authenticationManager.authenticate(userPassword);
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            User user = this.userService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return generateToken(user);
        } catch (Exception e) {
            throw new RuntimeException("Error while logging in", e);
        }
    }

    private String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create().withIssuer(ISSUER).withSubject(user.getEmail())
                    .withClaim("id", user.getId()).withClaim("email", user.getEmail())
                    .withArrayClaim("roles", new String[] {user.getRole().name()})
                    .withIssuedAt(genIssuedAtDate()).withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    private Instant genIssuedAtDate() {
        return Instant.now().minusSeconds(5);
    }

    private Instant genExpirationDate() {
        return Instant.now().plus(2, ChronoUnit.HOURS);
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(ISSUER).build().verify(token);

            return decodedJWT.getClaim("email").asString();

        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid token", exception);
        }
    }
}
