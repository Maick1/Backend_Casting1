package com.backend_casting.controlador;

import com.backend_casting.entity.Casting;
import com.backend_casting.entity.Formulario;
import com.backend_casting.repository.FormularioRepositorio;
import com.backend_casting.service.CastingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/casting")
public class CastingController {

    private final CastingService castingService;
    private final FormularioRepositorio formularioRepository;

    @Autowired
    public CastingController(CastingService castingService, FormularioRepositorio formularioRepository) {
        this.castingService = castingService;
        this.formularioRepository = formularioRepository;
    }

    @GetMapping
    public String getAllCastings(Model model) {
        List<Casting> castings = castingService.getAllCastingsOrderedByCalificacion();
        model.addAttribute("castings", castings);
        return "re_casting/castings";
    }

    @GetMapping("/{id:\\d+}")
    public String getCastingById(@PathVariable Long id, Model model) {
        Casting casting = castingService.getCastingById(id);
        model.addAttribute("casting", casting);
        return "re_casting/detalleCasti";
    }

    @GetMapping("/new")
    public String showNewCastingForm(Model model) {
        model.addAttribute("casting", new Casting());
        List<Formulario> formularios = formularioRepository.findAll();
        model.addAttribute("formularios", formularios);
        return "re_casting/new_casting";
    }

    @PostMapping
    public String saveCasting(@ModelAttribute Casting casting) {
        castingService.saveCasting(casting);
        return "redirect:/casting";
    }

    @GetMapping("/delete/{id}")
    public String deleteCasting(@PathVariable Long id) {
        castingService.deleteCasting(id);
        return "redirect:/casting";
    }

    @GetMapping("/edit/{id}")
    public String showEditCastingForm(@PathVariable Long id, Model model) {
        Casting casting = castingService.getCastingById(id);
        model.addAttribute("casting", casting);
        return "re_casting/edit_casting";
    }


    @GetMapping("/calificar/{id}")
    public String showCalificarForm(@PathVariable Long id, Model model) {
        Casting casting = castingService.getCastingById(id);
        model.addAttribute("casting", casting);
        return "re_casting/calificar";
    }

    @PostMapping("/calificar/{id}")
    public String calificarCasting(@PathVariable Long id,
                                   @RequestParam int participantesCalificacion,
                                   @RequestParam int secundarioCalificacion,
                                   @RequestParam int extra1Calificacion,
                                   @RequestParam int extra2Calificacion,
                                   @RequestParam int extra3Calificacion) {
        Casting casting = castingService.getCastingById(id);
        casting.setParticipantesCalificacion(participantesCalificacion);
        casting.setSecundarioCalificacion(secundarioCalificacion);
        casting.setExtra1Calificacion(extra1Calificacion);
        casting.setExtra2Calificacion(extra2Calificacion);
        casting.setExtra3Calificacion(extra3Calificacion);
        castingService.saveCasting(casting);
        return "redirect:/casting";
    }

    @GetMapping("/re_casting/castings")
    public String showCastings() {
        return "re_casting/castings";
    }

    @GetMapping("/calificados")
    public String getCalificados(Model model) {
        List<Casting> castings = castingService.getAllCastingsOrderedByCalificacion();
        model.addAttribute("castings", castings);
        return "/rankingM";
    }



}
