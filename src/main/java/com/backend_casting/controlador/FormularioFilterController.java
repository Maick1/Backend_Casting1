package com.backend_casting.controlador;

import com.backend_casting.dto.FormularioFilterDTO;
import com.backend_casting.entity.Formulario;
import com.backend_casting.service.FormularioFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FormularioFilterController {

    private final FormularioFilterService formularioFilterService;

    @Autowired
    public FormularioFilterController(FormularioFilterService formularioFilterService) {
        this.formularioFilterService = formularioFilterService;
    }

    @GetMapping("/filtrar")
    public String filtrar(@RequestParam(required = false) String nombre,
                          @RequestParam(required = false) String sexo,
                          @RequestParam(required = false) String colorOjos,
                          @RequestParam(required = false) String colorCabello,
                          @RequestParam(required = false) String estatura,
                          @RequestParam(required = false) String colorPiel,
                          @RequestParam(required = false) String fechaIngreso,
                          @RequestParam(required = false) Boolean aprobados,
                          Model model) {

        FormularioFilterDTO formularioFilterDTO = new FormularioFilterDTO();
        formularioFilterDTO.setNombre(nombre);
        formularioFilterDTO.setSexo(sexo);
        formularioFilterDTO.setColorOjos(colorOjos);
        formularioFilterDTO.setColorCabello(colorCabello);
        formularioFilterDTO.setEstatura(estatura);
        formularioFilterDTO.setColorPiel(colorPiel);
        formularioFilterDTO.setFechaIngreso(fechaIngreso);
        formularioFilterDTO.setAprobados(aprobados);

        List<Formulario> listaFormularios = formularioFilterService.filtrarFormulario(formularioFilterDTO);
        model.addAttribute("listaFormularios", listaFormularios);
        return "filtrar";
    }
}
