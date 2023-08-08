package com.backend_casting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.backend_casting.entity.Formulario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FormularioRepositorio extends JpaRepository<Formulario, Long> {

    long countByIsDeletedFalse();

    @Query("SELECT p FROM Formulario p WHERE p.isDeleted = false AND " +
            "CONCAT(p.id, p.nombre, p.apellido, p.sexo) " +
            "LIKE %?1%")
    Page<Formulario> findAllNotDeleted(String palabraClave, Pageable pageable);


    long countBySexo(String sexo);

    @Query("SELECT COUNT(f) FROM Formulario f WHERE f.m_edad= ?1")
    long countByM_edad(String m_edad);

//Buscar en mi entidad si existe una cedula
    public boolean existsByCedula(String cedula);

    Page<Formulario> findByIsDeletedFalse(Pageable pageable);

    List<String> findByNombreStartingWith(String term);

}
