package com.backend_casting.service;

import com.backend_casting.repository.FormularioRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.backend_casting.entity.Formulario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormularioServicio {

    @Autowired
    private FormularioRepositorio formularioRepository;

    public Formulario getFormularioById(Long id) {
        return formularioRepository.findById(id).orElse(null);
    }

    public void save(Formulario formulario) {
        formularioRepository.save(formulario);
    }

    public Formulario get(Long id) {
        return formularioRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        Formulario formulario = getFormularioById(id);
        formulario.setDeleted(true);
        save(formulario);
    }


    public Page<Formulario> listAll(String palabraClave, Pageable pageable) {
        if (palabraClave != null) {
            return formularioRepository.findAllNotDeleted(palabraClave, pageable);
        }
        return formularioRepository.findByIsDeletedFalse(pageable);
    }

    public int countFormularios() {
        // Aquí deberías implementar un método personalizado en tu repositorio para contar solo los registros que no están marcados como eliminados.
        return (int) formularioRepository.countByIsDeletedFalse();
    }


    // Conteo total de personas con sexo masculino
    public long countHombres() {
        return formularioRepository.countBySexo("masculino");
    }

    // Conteo total de personas con sexo femenino
    public long countMujeres() {
        return formularioRepository.countBySexo("femenino");
    }

    // Conteo total de personas menores de edad
    public long countMenoresDeEdad() {
        return formularioRepository.countByM_edad("si");
    }

    public List<Formulario> getAllFormularios() {
        return formularioRepository.findAll();
    }


    //    Validar Cedulas repetidas
    public boolean existsByCedula(String cedula) {
        return formularioRepository.existsByCedula(cedula);
    }




//    Validar Cedula Ecuatoriana
    public boolean validarCedulaEcuador(String cedula) {
        // verificar longitud correcta
        if (cedula.length() != 10) {
            return false;
        }

        // verificar que los dos primeros dígitos representan una provincia válida
        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || provincia > 24) {
            return false;
        }

        // verificar tercer dígito
        int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
        if (tercerDigito < 0 || tercerDigito > 6) {
            return false;
        }

        // algoritmo de validación
        int total = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Integer.parseInt(cedula.substring(i, i + 1));
            if ((i % 2) == 0) { // posiciones impares
                digito *= 2;
                if (digito > 9) {
                    digito -= 9;
                }
            }
            total += digito;
        }

        // verificar último dígito
        total = 10 - (total % 10);
        if (total == 10) {
            total = 0;
        }

        int ultimoDigito = Integer.parseInt(cedula.substring(9, 10));

        return total == ultimoDigito;
    }

    public List<String> findNombresStartingWith(String term) {
        return formularioRepository.findByNombreStartingWith(term);
    }

}