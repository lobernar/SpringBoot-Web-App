package com.lobernar.myapp.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Utility class for handling JWT (JSON Web Token) operations such as creation and validation.
 * Provides methods to generate, parse, and validate JWT tokens for authentication.
 */

@Component
public class JwtUtils {

    /**
     * Secret key used for signing the JWT tokens.
     */
    @Value("${app.jwt.secret}")
    private String secretKey;

    /**
     * Expiration time for the JWT tokens in milliseconds.
     */
    @Value("${app.jwt.expiration}")
    private long expirationTime;


    /**
     * Creates a JWT token for a given user ID.
     * @param userId the ID of the user for whom the token is created.
     * @return the generated JWT token.
     */
    public String createToken(Integer userId){
        Map<String, Object> claims = new HashMap<>();
        return this.generateToken(claims, userId);
    }

    /**
     * Generates a JWT token with the specified claims and user ID.
     * @param claims the claims to be included in the token.
     * @param userId the ID of the user for whom the token is generated.
     * @return the generated JWT token.
     */
    public String generateToken(Map<String, Object> claims, Integer userId) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userId.toString())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * Retrieves the signing key used for signing the JWT tokens.
     * @return the signing key.
     */
    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts all claims from the given JWT token.
     * @param token the JWT token from which claims are to be extracted.
     * @return the claims contained in the token.
     */
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    /**
     * Extracts a specific claim from the given JWT token using the provided claim resolver.
     * @param token the JWT token from which the claim is to be extracted.
     * @param claimResolver a function to resolve the claim.
     * @param <T> the type of the claim.
     * @return the extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /**
     * Extracts the user ID from the given JWT token.
     * @param token the JWT token from which the user ID is to be extracted.
     * @return the user ID.
     */
    public Integer extractUserId(String token){
        String subject = extractClaim(token, Claims::getSubject);
        return Integer.valueOf(subject);
    }

    /**
     * Extracts the expiration date from the given JWT token.
     * @param token the JWT token from which the expiration date is to be extracted.
     * @return the expiration date.
     */
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Checks if the given JWT token is expired.
     * @param token the JWT token to be checked.
     * @return true if the token is expired, false otherwise.
     */
    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validates the given JWT token against the provided user ID.
     * @param token the JWT token to be validated.
     * @param userId the ID of the user to validate against.
     * @return true if the token is valid for the user, false otherwise.
     */
    public Boolean validateToken(String token, Integer userId){
        Integer tokenUserId = extractUserId(token);
        return (tokenUserId.equals(userId) && !isTokenExpired(token));
    }
}
