package com.backend_casting.service;

import com.backend_casting.config.FormularioSpecification;
import com.backend_casting.dto.FormularioFilterDTO;
import com.backend_casting.entity.Formulario;
import com.backend_casting.repository.FormularioFilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormularioFilterService {

    @Autowired
    private FormularioFilterRepository formularioRepository;

    public List<Formulario> filtrarFormulario(FormularioFilterDTO formularioFilterDTO){
        Specification<Formulario> spec = new FormularioSpecification(formularioFilterDTO);
        return formularioRepository.findAll(spec);
    }
}
