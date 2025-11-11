package EcoMarketSpa.EcoMarketSpa.service;

import org.springframework.stereotype.Service;

import EcoMarketSpa.EcoMarketSpa.model.Tienda;
import EcoMarketSpa.EcoMarketSpa.repository.TiendaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TiendaService {

    private final TiendaRepository tiendaRepository;

    public TiendaService(TiendaRepository tiendaRepository) {
        this.tiendaRepository = tiendaRepository;
    }

    public List<Tienda> listarTiendas() {
        return tiendaRepository.findAll();
    }

    public Optional<Tienda> obtenerTiendaPorId(Long id) {
        return tiendaRepository.findById(id);
    }

    public Tienda guardarTienda(Tienda tienda) {
        return tiendaRepository.save(tienda);
    }

    public void eliminarTienda(Long id) {
        tiendaRepository.deleteById(id);
    }
}
