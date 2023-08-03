package com.backend_casting.controlador;

import com.backend_casting.entity.Formulario;
import com.backend_casting.service.ContactoServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ContactoControlador {

    private final ContactoServicio contactoServicio;

    public ContactoControlador(ContactoServicio contactoServicio) {
        this.contactoServicio = contactoServicio;
    }

    @GetMapping("/contactar/{id}")
    public String showFormulario(@PathVariable Long id, Model model) {
        Formulario formulario = contactoServicio.getFormulario(id);
        String emailMessage = "Notificación de selección";
        model.addAttribute("formulario", formulario);
        model.addAttribute("emailMessage", emailMessage);

        return "contactar";
    }

    @PostMapping("/enviar")
    public String enviarEmail(@ModelAttribute("emailMessage") String emailMessage, @RequestParam("toEmail") String toEmail, @ModelAttribute("formulario") Formulario formulario) {
        contactoServicio.sendEmail(emailMessage, toEmail);
        return "redirect:/clasificar";
    }

}


