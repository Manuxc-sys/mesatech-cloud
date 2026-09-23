package mesatech.ms_solicitudes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Titulo obligatorio")
    @Column(nullable = false, length =150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "categoria obligatoria")
    @Column(nullable = false)
    private Long categoriaId;

    private Long prioridadId;

    private String usuarioSolicitante;

    private String estado;

    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
    }
}
