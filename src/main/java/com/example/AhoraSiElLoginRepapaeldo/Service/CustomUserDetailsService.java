package com.example.AhoraSiElLoginRepapaeldo.Service;

import com.example.AhoraSiElLoginRepapaeldo.Model.Rol;
import com.example.AhoraSiElLoginRepapaeldo.Model.User;
import com.example.AhoraSiElLoginRepapaeldo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con nombre de usuario: " + username);
        }

        // Convert user's role from enum to Spring Security's GrantedAuthority
        GrantedAuthority authority = convertRoleToAuthority(user.getRol());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(authority));
    }

    private GrantedAuthority convertRoleToAuthority(Rol userRole) {
        // Map your enum values to Spring Security's Role objects
        switch (userRole) {
            case ALUMNO:
                return new SimpleGrantedAuthority(Rol.ALUMNO.name());
            case PROFESOR:
                return new SimpleGrantedAuthority(Rol.PROFESOR.name());
            case DIRECTIVO:
                return new SimpleGrantedAuthority(Rol.DIRECTIVO.name());
            default:
                throw new IllegalStateException("Unexpected Rol value: " + userRole);
        }
    }
}