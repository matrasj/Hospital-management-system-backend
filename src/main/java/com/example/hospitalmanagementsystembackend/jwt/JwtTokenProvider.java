package com.example.hospitalmanagementsystembackend.jwt;

import com.example.hospitalmanagementsystembackend.exception.JwtTokenException;
import com.example.hospitalmanagementsystembackend.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private static final String INVALID_TOKEN_MESSAGE = "Token %s doest not match to any user";
    private static final String VALIDATION_ERROR_MESSAGE = "Token %s already expired or is invalid";
    @Value("${auth.jwt.expiration-ms}")
    private long validityInMs;

    @Value("${auth.jwt.secret-key}")
    private String secretKey;

    private final UserDetailsServiceImpl userDetailsService;
    public String resolveToken(HttpServletRequest request) {
        String authorizationBearerToken = request.getHeader("Authorization");

        if (authorizationBearerToken != null && authorizationBearerToken.startsWith("Bearer ")) {
            return authorizationBearerToken.substring("Bearer ".length());
        }
        return null;
    }

    public String generateJwtToken(Authentication authentication) {
        Claims claims = Jwts.claims();

        claims.setSubject(authentication.getName());
        claims.put("authorities",
                authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","))
        );

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((System.currentTimeMillis() + validityInMs)))
                .setSubject(authentication.getName())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getUsername(String jwtToken) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody().getSubject();
        } catch (Exception jwtTokenException) {
            throw new JwtTokenException(String.format(INVALID_TOKEN_MESSAGE, jwtToken));
        }
    }

    public Authentication getAuthentication(String jwtToken) {
        String username = getUsername(jwtToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
            return true;
        } catch (Exception exception) {
            throw new JwtTokenException(String.format(VALIDATION_ERROR_MESSAGE, jwtToken));
        }
    }
}
