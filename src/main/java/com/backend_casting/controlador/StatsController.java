package com.backend_casting.controlador;

import com.backend_casting.service.CastingService;
import com.backend_casting.service.FormularioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stats")
public class StatsController {
    @Autowired
    private CastingService castingService;

    @Autowired
    private FormularioServicio formularioService;

    @GetMapping
    public String getStats(Model model) {
        model.addAttribute("totalCastings", castingService.countCastings());
        model.addAttribute("totalFormularios", formularioService.countFormularios());
        model.addAttribute("totalCosts", castingService.sumCosts());
        model.addAttribute("totalHombres", formularioService.countHombres());
        model.addAttribute("totalMujeres", formularioService.countMujeres());
        model.addAttribute("totalMenoresDeEdad", formularioService.countMenoresDeEdad());
        return "Gr_stats/stats";
    }
}
