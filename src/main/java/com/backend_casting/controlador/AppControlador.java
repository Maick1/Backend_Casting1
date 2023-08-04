package com.backend_casting.controlador;

import com.backend_casting.entity.Formulario;

import com.backend_casting.service.FormularioServicio;

import com.backend_casting.util.EmpleadoExporterPDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AppControlador {

    private final FormularioServicio formularioServicio;
    private static final Logger logger = LoggerFactory.getLogger(AppControlador.class);

    @Autowired
    public AppControlador(FormularioServicio formularioServicio) {
        this.formularioServicio = formularioServicio;
    }

    //  maneja las solicitudes GET a las rutas "/" y "/login". Simplemente devuelve la vista "index".
    @GetMapping({"/", "/login"})
    public String index() {
        return "index";
    }

    //    Crea un nuevo objeto Formulario, lo añade al modelo y devuelve la vista "nuevo_producto".
    @GetMapping("/nuevo")
    public String mostrarFormularioDeRegistro(Model model) {
        Formulario formulario = new Formulario();
        model.addAttribute("formulario", formulario);
        return "nuevo_producto";
    }

    @PostMapping("/guardar")
    public String guardarFormulario(@Validated @ModelAttribute Formulario formulario, BindingResult result,
                                    Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes attributes) {

        // Valido la cedula
        if (!formularioServicio.validarCedulaEcuador(formulario.getCedula())) {
            attributes.addFlashAttribute("error", "Error: La cédula no es válida.");
            return "nuevo_producto";
        }

        // Valido la cedula repetida no dejo ingresar
        if (result.hasErrors()) {
            model.addAttribute("formulario", formulario);
            attributes.addFlashAttribute("warning", "Error en el Formulario");
            return "nuevo_producto";
        }

        // Comprobación modificada de la cédula
        if (formulario.getId() == 0 && formularioServicio.existsByCedula(formulario.getCedula())) {
            attributes.addFlashAttribute("error", "Error: La cédula ya existe en el sistema.");
            return "nuevo_producto";
        }


        if (result.hasErrors()) {
            model.addAttribute("formulario", formulario);
            attributes.addFlashAttribute("warning", "Error en el Formulario");
            return "nuevo_producto";
        }
//Valido si ocurre errores al subir la foto
        try {
            if (!foto.isEmpty()) {
                // Path directorioImagenes = Paths.get("src//main//resources//static/img");
                String rutaAbsoluta = "C://Participantes//recursos";

                byte[] bytesImg = foto.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + foto.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);

                formulario.setFoto(foto.getOriginalFilename());
            }
//Guardo exitosamente el formulario
            formularioServicio.save(formulario);
            attributes.addFlashAttribute("success", "Formulario guardado exitosamente");
        } catch (IOException e) {
            logger.error("Error al guardar el formulario", e);
            attributes.addFlashAttribute("error", "Error al guardar el formulario: " + e.getMessage());
            return "nuevo_producto";
        }

        return "redirect:/menu";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView mostrarFormularioDeEditarFormulario(@PathVariable(name = "id") Long id, RedirectAttributes attributes) {
        ModelAndView modelo = new ModelAndView("EditarFormulario");

        try {
            Formulario formulario = formularioServicio.getFormularioById(id);
            modelo.addObject("formulario", formulario);
        } catch (RuntimeException e) {
            logger.error("Error al obtener el formulario para editar", e);
            attributes.addFlashAttribute("error", e.getMessage());
            modelo.setViewName("redirect:/menu");
        }

        return modelo;
    }

    @GetMapping("/detalle/{id}")
    public String mostrarDetallesFormulario(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        try {
            Formulario formulario = formularioServicio.getFormularioById(id);
            model.addAttribute("formulario", formulario);
        } catch (RuntimeException e) {
            logger.error("Error al obtener los detalles del formulario", e);
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/menu";
        }

        return "detalles";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarFormulario(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes) {
        try {
            formularioServicio.delete(id);
            redirectAttributes.addFlashAttribute("success", "Formulario marcado como eliminado exitosamente");
        } catch (RuntimeException e) {
            logger.error("Error al marcar el formulario como eliminado", e);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/menu";
    }


    @GetMapping("/exportarPDF/{id}")
    public ResponseEntity<Void> exportarListadoDeEmpleadosEnPDF(@PathVariable("id") String formularioId, HttpServletResponse response) {
        try {
            response.setHeader("Content-Type", "application/pdf");

            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String fechaActual = dateFormatter.format(new Date());

            String cabecera = "Content-Disposition";
            String valor = "attachment; filename=Formulario_" + fechaActual + ".pdf";

            response.setHeader(cabecera, valor);

            Long id = Long.valueOf(formularioId);
            Formulario formulario = formularioServicio.get(id);

            List<Formulario> formularios = new ArrayList<>();
            formularios.add(formulario);

            EmpleadoExporterPDF exporter = new EmpleadoExporterPDF(formularios);
            exporter.exportar(response);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error al exportar el formulario a PDF", e);
            return ResponseEntity.status(500).build();
        }
    }

    //    Controladdor para listar los usuarios
    @GetMapping("/menu")
    public String menu(Model model, @Param("palabraClave") String palabraClave,
                       @RequestParam(defaultValue = "0") int page) {
        PageRequest pageable = PageRequest.of(page, 5, Sort.by("nombre").ascending());
        Page<Formulario> listaFormularios = formularioServicio.listAll(palabraClave, pageable);
        model.addAttribute("listaFormularios", listaFormularios);
        model.addAttribute("palabraClave", palabraClave);
        model.addAttribute("currentPage", page);
        return "menu";
    }

    // Controlador para clasificcar mostrar lista
    @GetMapping("/clasificar")
    public String aprobados(Model model, @Param("palabraClave") String palabraClave,
                            @RequestParam(defaultValue = "0") int page) {
        PageRequest pageable = PageRequest.of(page, 6, Sort.by("nombre").ascending());
        Page<Formulario> listaFormularios = formularioServicio.listAll(palabraClave, pageable);
        model.addAttribute("listaFormularios", listaFormularios);
        model.addAttribute("palabraClave", palabraClave);
        model.addAttribute("currentPage", page);
        return "aprobados/AproUser";
    }
// verifica la respuesta. Si data.exists es verdadero,
// se muestra una alerta de error y se detiene la ejecución
// de la función. Si data.exists es falso, se procede a enviar
// el formulario.
    @GetMapping("/checkCedula")
    public ResponseEntity<Map<String, Boolean>> checkCedula(@RequestParam String cedula) {
        boolean exists = formularioServicio.existsByCedula(cedula);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }


}