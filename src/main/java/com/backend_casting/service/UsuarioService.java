package com.backend_casting.service;

import com.backend_casting.entity.Rol;
import com.backend_casting.entity.Usuario;
import com.backend_casting.repository.RolRepository;
import com.backend_casting.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario obtenerPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public void guardarUsuario(Usuario usuario) {
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        Rol rolUsuario = rolRepository.findByNombre("USER");
        usuario.setRoles(new HashSet<>(Arrays.asList(rolUsuario)));
        usuarioRepository.save(usuario);
    }

    public void cambiarContraseña(Usuario usuario, String nuevaContraseña) {
        usuario.setContraseña(passwordEncoder.encode(nuevaContraseña));
        usuarioRepository.save(usuario);
    }



}
