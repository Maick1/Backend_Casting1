package com.backend_casting.config;

import com.backend_casting.dto.FormularioFilterDTO;
import com.backend_casting.entity.Formulario;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class FormularioSpecification implements Specification<Formulario> {

    private final FormularioFilterDTO filter;

    public FormularioSpecification(FormularioFilterDTO filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Formulario> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getNombre() != null && !filter.getNombre().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("nombre"), "%" + filter.getNombre() + "%"));
        }

        if (filter.getSexo() != null && !filter.getSexo().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("sexo"), filter.getSexo()));
        }

        if (filter.getColorOjos() != null && !filter.getColorOjos().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("c_ojos"), filter.getColorOjos())); // Cambiado de C_ojos a c_ojos
        }

        if (filter.getColorCabello() != null && !filter.getColorCabello().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("c_cabello"), filter.getColorCabello()));
        }

        if (filter.getEstatura() != null && !filter.getEstatura().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("estatura"), filter.getEstatura()));
        }

        if (filter.getColorPiel() != null && !filter.getColorPiel().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("c_piel"), filter.getColorPiel())); // Cambiado de C_piel a c_piel
        }


        if (filter.getFechaIngreso() != null && !filter.getFechaIngreso().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("fechaIngreso"), filter.getFechaIngreso()));
        }

        if (filter.getAprobados() != null) {
            predicates.add(criteriaBuilder.equal(root.get("aprobados"), filter.getAprobados()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
