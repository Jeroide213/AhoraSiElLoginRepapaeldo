package com.example.AhoraSiElLoginRepapaeldo.Config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    // Constantes para los nombres de las propiedades
    private static final String ROLES_CLAIM = "roles";

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(String username, List<String> roles) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .claim(ROLES_CLAIM, roles)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
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

    public List<String> obtenerRolesDelUsuario(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            if (claims.containsKey(ROLES_CLAIM) && claims.get(ROLES_CLAIM) != null) {
                return (List<String>) claims.get(ROLES_CLAIM);
            } else {
                // Si el claim de roles está ausente o es nulo, puedes lanzar una excepción o devolver una lista vacía según tus necesidades
                throw new IllegalArgumentException("El claim de roles no está presente en el token o es nulo.");
            }
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
            // Manejo de errores durante la obtención de los roles del usuario
            // Puedes lanzar una excepción personalizada o devolver una lista vacía según tu necesidad
            return Collections.emptyList();
        }
    }
}