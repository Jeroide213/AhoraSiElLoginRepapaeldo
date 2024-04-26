package com.example.AhoraSiElLoginRepapaeldo.Service;

import com.example.AhoraSiElLoginRepapaeldo.Model.User;
import com.example.AhoraSiElLoginRepapaeldo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void crearUsuario(User user) {
        // Aquí puedes realizar validaciones adicionales antes de crear el usuario, como verificar si el usuario ya existe
        if (user != null && !userRepository.existsByUsername(user.getUsername())) {
            // Puedes aplicar lógica adicional aquí, como la encriptación de contraseñas antes de guardar el usuario
            userRepository.save(user);
        } else {
            throw new RuntimeException("El usuario ya existe o los datos son inválidos");
        }
    }
}