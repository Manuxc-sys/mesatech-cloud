package mesatech.bff_service.dto;

import lombok.Data;

@Data
public class SolicitudDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Long categoriaId;
    private Long prioridadId;
    private String estado;
    private String usuarioSolicitante;
}
