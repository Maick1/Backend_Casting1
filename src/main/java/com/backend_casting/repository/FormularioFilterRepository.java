package com.backend_casting.repository;

import com.backend_casting.entity.Formulario;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface FormularioFilterRepository extends CrudRepository<Formulario, Long>, JpaSpecificationExecutor<Formulario> {

}
