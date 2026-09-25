package mesatech.bff_service.dto;

import lombok.Data;

@Data
public class SolicitudDetalleDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String categoria;
    private String prioridad;
    private String estado;
    private String usuarioSolicitante;
}