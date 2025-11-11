package EcoMarketSpa.EcoMarketSpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EcoMarketSpa.EcoMarketSpa.model.Comprobante;
import EcoMarketSpa.EcoMarketSpa.repository.ComprobanteRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComprobanteService {
    @Autowired
    private ComprobanteRepository comprobanteRepository;

    public List<Comprobante> buscarTodo() {
        return comprobanteRepository.findAll();
    }

    public Comprobante buscarId(long id) {
        return comprobanteRepository.findById(id).get();
    }

    public Comprobante agregarComprobante(Comprobante boleta) {
        return comprobanteRepository.save(boleta);
    }

    public void borrarComprobante(Long id) {
        comprobanteRepository.deleteById(id);
    }

}
