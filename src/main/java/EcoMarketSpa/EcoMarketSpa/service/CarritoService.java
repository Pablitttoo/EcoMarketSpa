package EcoMarketSpa.EcoMarketSpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import EcoMarketSpa.EcoMarketSpa.model.CarritoCompra;
import EcoMarketSpa.EcoMarketSpa.repository.CarritoRepository;
import EcoMarketSpa.EcoMarketSpa.model.Producto;
import EcoMarketSpa.EcoMarketSpa.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public CarritoCompra crear(CarritoCompra carrito) {
        return carritoRepository.save(carrito);
    }

    public List<CarritoCompra> listar() {
        return carritoRepository.findAll();
    }

    public Optional<CarritoCompra> obtenerPorId(Long id) {
        return carritoRepository.findById(id);
    }

    public CarritoCompra agregarProducto(Long carritoId, Long productoId) {
        CarritoCompra carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        carrito.getProductos().add(producto);
        return carritoRepository.save(carrito);
    }

    public CarritoCompra eliminarProducto(Long carritoId, Long productoId) {
        CarritoCompra carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        carrito.getProductos().remove(producto); 
        return carritoRepository.save(carrito);
    }
}
