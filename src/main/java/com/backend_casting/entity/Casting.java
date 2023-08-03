package com.backend_casting.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Casting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;
    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @NotNull
    @Size(max = 500, message = "El campo 'nombre' no puede exceder los 500 caracteres")
    private String nomCasting;

    @NotNull
    @Size(max = 1000, message = "El campo 'descripcion' no puede exceder los 1000 caracteres")
    private String descripcion;

    @NotNull
    @Max(value = 999999999, message = "El campo 'costo' no puede exceder los 999999999")
    private double costo;

    @Size(max = 500, message = "El campo 'participantes' no puede exceder los 500 caracteres")
    private String participantes;

    private Integer participantesCalificacion;

    @Size(max = 500, message = "El campo 'secundario' no puede exceder los 500 caracteres")
    private String secundario;

    private Integer secundarioCalificacion;

    @Size(max = 500, message = "El campo 'extra1' no puede exceder los 500 caracteres")
    private String extra1;

    private Integer extra1Calificacion;

    @Size(max = 500, message = "El campo 'extra2' no puede exceder los 500 caracteres")
    private String extra2;

    private Integer extra2Calificacion;

    @Size(max = 500, message = "El campo 'extra3' no puede exceder los 500 caracteres")
    private String extra3;

    private Integer extra3Calificacion;

    @NotNull
    private Integer calificacion = 0;

    @ManyToMany(mappedBy = "castings")
    private List<Formulario> formularios;
}
