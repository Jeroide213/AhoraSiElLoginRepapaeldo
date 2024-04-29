package com.example.AhoraSiElLoginRepapaeldo.Controller;

import com.example.AhoraSiElLoginRepapaeldo.Model.User;
import com.example.AhoraSiElLoginRepapaeldo.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        // Autenticar al usuario y generar el token JWT
        ResponseEntity<String> response = authenticationService.authenticate(username, password);

        // Verificar si la autenticación fue exitosa
        if (response.getStatusCode().is2xxSuccessful()) {
            // Si la autenticación fue exitosa, devolver el token JWT en la respuesta
            return ResponseEntity.ok(response.getBody());
        } else {
            // Si la autenticación falló, devolver un mensaje de error adecuado
            return ResponseEntity.status(response.getStatusCode()).body("Error de autenticación: Credenciales incorrectas");
        }
    }

}