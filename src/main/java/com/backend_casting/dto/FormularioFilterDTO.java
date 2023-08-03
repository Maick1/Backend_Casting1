package com.backend_casting.dto;

import lombok.Data;

@Data
public class FormularioFilterDTO {
    private String nombre;
    private String sexo;
    private String colorOjos;
    private String colorCabello;
    private String estatura;
    private String colorPiel;
    private String talla;
    private String fechaIngreso;
    private Boolean aprobados;
}
