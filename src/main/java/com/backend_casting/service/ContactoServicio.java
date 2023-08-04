package com.backend_casting.service;

import com.backend_casting.entity.Formulario;
import com.backend_casting.repository.FormularioRepositorio;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class ContactoServicio {

    private final FormularioRepositorio formularioRepositorio;
    private final JavaMailSender emailSender;

    public ContactoServicio(FormularioRepositorio formularioRepositorio, JavaMailSender emailSender) {
        this.formularioRepositorio = formularioRepositorio;
        this.emailSender = emailSender;
    }

    public Formulario getFormulario(Long id) {
        return formularioRepositorio.findById(id).orElse(null);
    }

    public void sendEmail(String message, String toEmail) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String subject = "Notificación de selección";

            String body = "Nos complace informarle que, tras una cuidadosa revisión, ha sido seleccionado(a), " +
                    "estamos emocionados por la posibilidad de trabajar con usted. En breve, le proporcionaremos más " +
                    "detalles, si tiene alguna pregunta, no dude en ponerse en contacto con nosotros. Le agradecemos " +
                    "sinceramente su tiempo Atentamente, " + "Jefe de Casting ShowUnemi";

            helper.setTo(toEmail);  // para indicar a quién se enviará el correo electrónico
            helper.setSubject(subject);  // para establecer el asunto del correo electrónico
            helper.setText(body, true);  // para establecer el cuerpo del correo electrónico, true indica que el mensaje es HTML
            helper.setFrom("may.vero.tesis@gmail.com");  // para establecer el correo electrónico del remitente

            emailSender.send(mimeMessage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendEmail2(String message, String toEmail) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String subject = "Notificación de recordatorio";

            String body = "Tienes un nuevo recordatorio para " + message + ". Por favor, revisa tu aplicación para más detalles.";

            helper.setTo(toEmail);  // para indicar a quién se enviará el correo electrónico
            helper.setSubject(subject);  // para establecer el asunto del correo electrónico
            helper.setText(body, true);  // para establecer el cuerpo del correo electrónico, true indica que el mensaje es HTML
            helper.setFrom("may.vero.tesis@gmail.com");  // para establecer el correo electrónico del remitente

            emailSender.send(mimeMessage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }






}

