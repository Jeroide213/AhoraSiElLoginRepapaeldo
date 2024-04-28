package com.example.AhoraSiElLoginRepapaeldo.Service;

import com.example.AhoraSiElLoginRepapaeldo.Config.JwtTokenProvider;
import com.example.AhoraSiElLoginRepapaeldo.Model.User;
import com.example.AhoraSiElLoginRepapaeldo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<String> authenticate(String username, String password) {
        // Autenticar al usuario utilizando Spring Security AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        // Establecer la autenticación en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generar el token JWT con el nombre de usuario y los roles del usuario
        List<String> roles = obtenerRolesDelUsuario(username);
        String token = jwtTokenProvider.generateToken(username, roles);

        // Devolver el token JWT en la respuesta
        return ResponseEntity.ok(token);
    }

    // Método para obtener los roles del usuario
    private List<String> obtenerRolesDelUsuario(String username) {
        // Aquí puedes implementar la lógica para obtener los roles del usuario desde tu base de datos u otro origen de datos
        // Por ahora, devolvemos una lista vacía como ejemplo
        return Collections.emptyList();
    }
}