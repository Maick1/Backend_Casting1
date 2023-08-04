package com.backend_casting.repository;

import com.backend_casting.entity.Casting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CastingRepository extends JpaRepository<Casting, Long> {
    @Query("SELECT c FROM Casting c WHERE c.isDeleted = false ORDER BY (c.participantesCalificacion + c.secundarioCalificacion + c.extra1Calificacion + c.extra2Calificacion + c.extra3Calificacion) DESC")
    List<Casting> findAllOrderByCalificacionDesc();

    List<Casting> findByIsDeletedFalse();
    long countByIsDeletedFalse();
    Casting findByNomCasting(String nomCasting);


}
