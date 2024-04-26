package com.example.AhoraSiElLoginRepapaeldo.Controller;

import com.example.AhoraSiElLoginRepapaeldo.Model.User;
import com.example.AhoraSiElLoginRepapaeldo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/crearUsuario")
    public ResponseEntity<String> crearUsuario(@RequestBody User usuario) {
        try {
            // Lógica para crear el usuario en la base de datos externa
            userService.crearUsuario(usuario);

            // Devuelve una respuesta con estado 200 (OK) y un mensaje
            return ResponseEntity.ok("Usuario creado exitosamente en la API externa.");
        } catch (Exception e) {
            // Devuelve una respuesta con estado 500 (INTERNAL_SERVER_ERROR) y un mensaje de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el usuario en la API externa: " + e.getMessage());
        }
    }
}
