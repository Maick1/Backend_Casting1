package com.backend_casting.controlador;

import com.backend_casting.entity.Usuario;
import com.backend_casting.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Método para mostrar la página de restablecimiento de contraseña
    @GetMapping("/usuarios/restablecer_contraseña")
    public String mostrarFormularioRestablecimiento() {
        return "restablecer_contraseña";
    }

    // Método para verificar el nombre de usuario y la respuesta de seguridad
    @PostMapping("/usuarios/verificar_respuesta")
    public String verificarRespuesta(@RequestParam("nombreUsuario") String nombreUsuario,
                                     @RequestParam("respuestaSeguridad") String respuestaSeguridad, Model model) {
        Usuario usuario = usuarioService.obtenerPorNombreUsuario(nombreUsuario);
        if (usuario == null || !usuario.getRespuestaSeguridad().equals(respuestaSeguridad)) {
            model.addAttribute("error", true);
            return "restablecer_contraseña";
        }
        model.addAttribute("success", true);
        return "nueva_contraseña";
    }

    // Método para cambiar la contraseña
    @PostMapping("/usuarios/cambiar_contraseña")
    public String cambiarContraseña(@RequestParam("nombreUsuario") String nombreUsuario,
                                    @RequestParam("nuevaContraseña") String nuevaContraseña,
                                    Model model) {
        Usuario usuario = usuarioService.obtenerPorNombreUsuario(nombreUsuario);
        if (usuario == null) {
            model.addAttribute("error", true);
            return "restablecer_contraseña";
        }
        usuarioService.cambiarContraseña(usuario, nuevaContraseña);
        model.addAttribute("success", true);
        return "redirect:/login?cambioExitoso";
    }
}

