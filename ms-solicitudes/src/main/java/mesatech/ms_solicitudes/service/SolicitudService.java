package mesatech.ms_solicitudes.service;

import lombok.RequiredArgsConstructor;
import mesatech.ms_solicitudes.entity.Solicitud;
import mesatech.ms_solicitudes.repository.SolicitudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;

    public List<Solicitud> listarTodas() {
        return solicitudRepository.findAll();
    }

    public Optional<Solicitud> buscarPorId(Long id) {
        return solicitudRepository.findById(id);
    }

    public Solicitud crear(Solicitud solicitud) {
        return solicitudRepository.save(solicitud);
    }

    public Solicitud actualizar(Long id, Solicitud solicitud) {
        return solicitudRepository.findById(id)
                .map(existente -> {
                    existente.setTitulo(solicitud.getTitulo());
                    existente.setDescripcion(solicitud.getDescripcion());
                    existente.setCategoriaId(solicitud.getCategoriaId());
                    existente.setPrioridadId(solicitud.getPrioridadId());
                    existente.setUsuarioSolicitante(solicitud.getUsuarioSolicitante());
                    existente.setEstado(solicitud.getEstado());
                    return solicitudRepository.save(existente);
                })
                .orElse(null);
    }

    public void eliminar(Long id) {
        solicitudRepository.deleteById(id);
    }

    public Solicitud actualizarEstado (Long id, String nuevoEstado) {
        Solicitud solicitudActual = solicitudRepository.findById(id).orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        if (solicitudActual.getEstado().equals("CERRADA") || solicitudActual.getEstado().equals("CANCELADA")) {
            throw new IllegalStateException("Error, solicitud cerrada o cancelada, no se puede cambiar estado");
        }

        if (nuevoEstado.equals("RESUELTA") && !solicitudActual.getEstado().equals("EN_PROCESO")) {
            throw new IllegalStateException("Error: una solicitud no puede psar a Resuelta sin antes estar En proceso");
        }
        solicitudActual.setEstado(nuevoEstado);
        return solicitudRepository.save(solicitudActual);

    }
}
