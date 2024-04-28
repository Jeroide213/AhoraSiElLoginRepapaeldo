package com.example.AhoraSiElLoginRepapaeldo.Controller;

import com.example.AhoraSiElLoginRepapaeldo.Model.LoginRequest;
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
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Extraer el nombre de usuario y la contraseña del LoginRequest
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Autenticar al usuario y generar el token JWT
        ResponseEntity<String> response = authenticationService.authenticate(username, password);

        return response;
    }
}