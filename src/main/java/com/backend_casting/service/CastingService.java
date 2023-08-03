package com.backend_casting.service;

import com.backend_casting.entity.Casting;
import com.backend_casting.repository.CastingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CastingService {
    private final CastingRepository castingRepository;

    @Autowired
    public CastingService(CastingRepository castingRepository) {
        this.castingRepository = castingRepository;
    }

    public Casting getCastingById(Long id) {
        return castingRepository.findById(id).orElse(null);
    }

    public Casting saveCasting(Casting casting) {
        return castingRepository.save(casting);
    }

    public void deleteCasting(Long id) {
        Casting casting = getCastingById(id);
        if (casting != null) {
            casting.setIsDeleted(true);
            saveCasting(casting);
        }
    }

    public long countCastings() {
        return castingRepository.countByIsDeletedFalse();
    }

    public double sumCosts() {
        return castingRepository.findAll().stream()
                .filter(casting -> !casting.getIsDeleted())
                .mapToDouble(Casting::getCosto)
                .sum();
    }

    public List<Casting> getAllCastingsOrderedByCalificacion() {
        return castingRepository.findAllOrderByCalificacionDesc();
    }
}
