package com.example.AhoraSiElLoginRepapaeldo.Config;

import com.example.AhoraSiElLoginRepapaeldo.Service.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private final CustomUserDetailsService userDetailsService;

    public CustomAuthenticationManager(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Obtener el nombre de usuario y la contraseña proporcionados en la solicitud de autenticación
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Obtener los detalles del usuario desde tu implementación de UserDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Verificar si la contraseña proporcionada coincide con la contraseña almacenada en los detalles del usuario
        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        // Si las credenciales son válidas, construir un objeto Authentication con los detalles del usuario y devolverlo
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }
}