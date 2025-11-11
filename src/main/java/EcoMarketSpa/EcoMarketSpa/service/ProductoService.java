package EcoMarketSpa.EcoMarketSpa.service;

import EcoMarketSpa.EcoMarketSpa.model.Producto;
import EcoMarketSpa.EcoMarketSpa.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto findById(long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public void delete(long id) {
        productoRepository.deleteById(id);
    }

    public void agregarStock(Long productoId, int cantidad) {
        Producto pro = productoRepository.findById(productoId).orElseThrow();
        pro.setStock(pro.getStock() + cantidad);
        productoRepository.save(pro);
    }

    public void restarStock(Long productoId, int cantidad) {
        Producto p = productoRepository.findById(productoId).orElseThrow();
        if (p.getStock() >= cantidad) {
            p.setStock(p.getStock() - cantidad);
            productoRepository.save(p);
        } else {
            throw new IllegalArgumentException("Stock insuficiente");
        }
    }

    public Integer consultarStock(Long id) {
        return productoRepository.consultarStock(id);
    }

}
