package com.example.AhoraSiElLoginRepapaeldo.Controller;

import com.example.AhoraSiElLoginRepapaeldo.Exceptions.CustomAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomAuthenticationException.class)
    @ResponseBody
    public ResponseEntity<String> handleCustomAuthenticationException(CustomAuthenticationException ex) {
        // Manejo de excepciones personalizadas aquí
        // Puedes devolver una respuesta JSON personalizada con un mensaje de error
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}
