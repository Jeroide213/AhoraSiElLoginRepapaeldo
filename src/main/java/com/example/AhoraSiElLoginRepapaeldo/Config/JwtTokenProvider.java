package com.example.AhoraSiElLoginRepapaeldo.Config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    // Constantes para los nombres de las propiedades
    private static final String ROLES_CLAIM = "roles";

    public String generateToken(String username, List<String> roles) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .claim(ROLES_CLAIM, roles)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            List<SimpleGrantedAuthority> authorities = null;
            if (claims.containsKey(ROLES_CLAIM) && claims.get(ROLES_CLAIM) != null) {
                authorities = ((List<?>) claims.get(ROLES_CLAIM)).stream()
                        .map(authority -> new SimpleGrantedAuthority((String) authority))
                        .collect(Collectors.toList());
            } else {
                // Si el claim de roles está ausente o es nulo, puedes lanzar una excepción o devolver null según tus necesidades
                throw new IllegalArgumentException("El claim de roles no está presente en el token o es nulo.");
            }
            String username = claims.getSubject();
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
            // Manejo de errores durante la obtención de la autenticación
            // Puedes lanzar una excepción personalizada o devolver null según tu necesidad
            return null;
        }
    }

}
