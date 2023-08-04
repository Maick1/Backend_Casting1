package com.backend_casting.controlador;

import com.backend_casting.entity.Reminder;
import com.backend_casting.entity.Usuario;
import com.backend_casting.repository.ReminderService;
import com.backend_casting.service.ContactoServicio; // Importa tu servicio de contacto
import com.backend_casting.service.UsuarioService;
import com.backend_casting.util.ReminderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Controller
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ContactoServicio contactoServicio;

    @Autowired
    private ReminderServiceImpl reminderServiceImpl;

    @Value("${notification.email}")
    private String notificationEmail;

    @GetMapping("/reminders")
    public String viewRemindersPage(Model model) {
        model.addAttribute("listReminders", reminderService.getAllReminders());
        model.addAttribute("reminder", new Reminder());
        return "reminders";
    }

    @PostMapping("/saveReminder")
    public String saveReminder(@ModelAttribute("reminder") Reminder reminder, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "reminders";
        }

        // Obtener el usuario actualmente autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario currentUser = usuarioService.obtenerPorNombreUsuario(auth.getName());

        // Asociar el usuario al recordatorio
        reminder.setUsuario(currentUser);

        // Guardar el recordatorio
        reminderService.saveReminder(reminder);

        // Obtener la diferencia de tiempo entre el recordatorio y el momento actual en minutos
        long minutesUntilReminder = ChronoUnit.MINUTES.between(LocalDateTime.now(), reminder.getReminderTime());

        // Enviar un correo electrónico al usuario solo si la diferencia de tiempo es mayor a 5 minutos
        if (minutesUntilReminder > 5) {
            String emailMessage = "Nueva nota agregada: " + reminder.getNote();
            contactoServicio.sendEmail2(emailMessage, notificationEmail);
        }

        return "redirect:/reminders";
    }
    @DeleteMapping("/deleteReminder/{id}")
    public String deleteReminder(@PathVariable("id") Long id) {
        reminderServiceImpl.deleteReminder(id);
        return "redirect:/reminders";
    }

}
