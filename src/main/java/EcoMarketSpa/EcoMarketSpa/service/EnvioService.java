package EcoMarketSpa.EcoMarketSpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EcoMarketSpa.EcoMarketSpa.model.Envio;
import EcoMarketSpa.EcoMarketSpa.model.Envio.EstadoEnvio;
import EcoMarketSpa.EcoMarketSpa.model.Usuario;
import EcoMarketSpa.EcoMarketSpa.repository.EnvioRepository;
import EcoMarketSpa.EcoMarketSpa.repository.UsuarioRepository;

import java.util.Optional;
import java.util.List;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Envio crearEnvioCliente(Long clienteId, String fechaSalida, String fechaEntrega) {
        Usuario cliente = usuarioRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (cliente.getRol() != Usuario.Rol.CLIENTE) {
            throw new RuntimeException("El usuario no es un cliente");
        }

        Envio envio = new Envio();
        envio.setCliente(cliente);
        envio.setDireccionDestino(cliente.getDireccion());
        envio.setFechaSalida(fechaSalida);
        envio.setFechaEntrega(fechaEntrega);
        envio.setEstado(EstadoEnvio.EN_PREPARACION);

        return envioRepository.save(envio);
    }

    public List<Envio> obtenerTodos() {
        return envioRepository.findAll();
    }

    public Optional<Envio> obtenerPorId(long id) {
        return envioRepository.findById(id);
    }

    public List<Envio> obtenerPorEstado(EstadoEnvio estado) {
        return envioRepository.findByEstado(estado);
    }

    public void eliminarEnvio(Long id) {
        envioRepository.deleteById(id);
    }

    public Envio actualizarEstado(Long id, EstadoEnvio nuevoEstado) {
        return envioRepository.findById(id)
                .map(envio -> {
                    envio.setEstado(nuevoEstado);
                    return envioRepository.save(envio);
                })
                .orElseThrow(() -> new RuntimeException("Envio no encontrado"));
    }
}