package com.abn.ems.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

import static com.abn.ems.constants.Constant.ALGORITHM;
import static com.abn.ems.constants.Constant.ROLES;

/**
 * Utility service for generating, parsing, and validating JSON Web Tokens (JWT).
 *
 * <p>The {@code JwtUtilService} provides methods for handling common JWT operations,
 * such as generating tokens with user details, validating tokens, and extracting claims.
 * This service uses a secret key for signing tokens and supports token expiration.</p>
 *
 * <h3>Key Features:</h3>
 * <ul>
 *     <li>Generates JWT tokens with custom claims.</li>
 *     <li>Validates tokens for authenticity and expiration.</li>
 *     <li>Extracts specific claims, such as username or roles, from the token payload.</li>
 * </ul>
 */
@Component
public class JwtUtilService {

    private final String secretKey;

    public JwtUtilService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a JWT token with the specified subject and claims.
     *
     * @param userName the subject (typically the username) of the token. Must not be null or empty.
     * @param role a map of additional claims to include in the token. Can be empty but not null.
     * @return a signed JWT token as a {@code String}.
     */

    public String generateToken(String userName,String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ROLES, role);
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .and()
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Validates a JWT token for authenticity and expiration.
     *
     * @param token the JWT token to validate. Must not be null or empty.
     * @return {@code true} if the token is valid; {@code false} otherwise.
     */
    public boolean validateToken(String token) {
        return getUserName(token) != null && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String getRole(String token) {
        Claims claims= extractAllClaims(token);
        return claims.get(ROLES,String.class);
    }

    /**
     * Extracts the username (subject) from a JWT token.
     *
     * @param token the JWT token from which to extract the username. Must not be null or empty.
     * @return the username (subject) as a {@code String}.
     */

    public String getUserName(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
