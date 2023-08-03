package com.backend_casting.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@Entity
public class Formulario {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(name = "is_deleted")
        private boolean isDeleted = false;

        @Column(nullable = false, length = 60)
        @Size(max = 100, message = "El campo 'nombre' no puede exceder los 100 caracteres")
        private String nombre;

        @Column(nullable = false, length = 60)
        @Size(max = 100, message = "El campo 'apellido' no puede exceder los 100 caracteres")
        private String apellido;

//        @Pattern permite aplicar una expresión regular para validar el formato de un campo.
        @Pattern(regexp = "\\d{10}|\\d{10}-\\d{1}", message = "Cédula inválida")
        private String cedula;

        @Column(nullable = false, length = 60)
        @Email(message = "El campo 'correo' debe ser una dirección de correo válida")
        private String correo;

        @Column(nullable = false, length = 60)
        private String sexo;

        @Column(nullable = false, length = 60)
        private String f_nacimiento;

        @NotBlank(message = "El campo 'estatura' es obligatorio")
        @DecimalMin(value = "0", inclusive = false, message = "El campo 'estatura' debe ser mayor a 0")
        private String estatura;

        private String foto;

        @NotBlank(message = "El campo 'm_edad' es obligatorio")
        private String m_edad;

        @Size(max = 20, message = "El campo 'c_tutor' no puede exceder los 20 caracteres")
        private String celuar;

        @Size(max = 100, message = "El campo 'n_tutor' no puede exceder los 100 caracteres")
        private String n_tutor;

        @Size(max = 20, message = "El campo 'c_tutor' no puede exceder los 20 caracteres")
        private String c_tutor;

        @Size(max = 100, message = "El campo 'no_manager' no puede exceder los 100 caracteres")
        private String no_manager;

        @Size(max = 20, message = "El campo 'cel_manager' no puede exceder los 20 caracteres")
        private String cel_manager;


        @NotBlank(message = "El campo 'p_manager' es obligatorio")
        private String p_manager;


        @NotBlank(message = "El campo 'talla' es obligatorio")
        private String talla;

        @NotBlank(message = "El campo 'c_piel' es obligatorio")
        private String c_piel; // Cambiado de C_piel a c_piel

        @NotBlank(message = "El campo 'c_ojos' es obligatorio")
        private String c_ojos; // Cambiado de C_ojos a c_ojos

        @NotBlank(message = "El campo 'c_cabello' es obligatorio")
        private String c_cabello;

        @NotBlank(message = "El campo 'alergia' es obligatorio")
        private String alergia;

        @NotBlank(message = "El campo 'comerciales' es obligatorio")
        private String comerciales;

        @NotBlank(message = "El campo 'hobbie' es obligatorio")
        private String hobbie;

        @NotBlank(message = "El campo 'deporte' es obligatorio")
        private String deporte;

        @NotBlank(message = "El campo 'vehiculo' es obligatorio")
        private String vehiculo;

        @NotBlank(message = "El campo 'licencia' es obligatorio")
        private String licencia;

        private String fechaIngreso;

        private boolean aprobados;

        @ManyToMany
        @JoinTable(
                name = "postulante_casting",
                joinColumns = @JoinColumn(name = "formulario_id"),
                inverseJoinColumns = @JoinColumn(name = "casting_id")
        )
        private List<Casting> castings;

}




