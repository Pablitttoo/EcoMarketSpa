package EcoMarketSpa.EcoMarketSpa.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EcoMarketSpa.EcoMarketSpa.model.Producto;
import EcoMarketSpa.EcoMarketSpa.model.Usuario;
import EcoMarketSpa.EcoMarketSpa.model.Venta;
import EcoMarketSpa.EcoMarketSpa.repository.ProductoRepository;
import EcoMarketSpa.EcoMarketSpa.repository.UsuarioRepository;
import EcoMarketSpa.EcoMarketSpa.repository.VentaRepository;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoService productoService;

    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    public Venta buscar(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    public Venta crearVenta(Long usuarioId, Long productoId, Integer cantidad) {
        Usuario usuario = usuarioRepository.findById(usuarioId.longValue()).orElseThrow();
        Producto producto = productoRepository.findById(productoId).orElseThrow();

        productoService.restarStock(productoId, cantidad);
        // Usa tu función existente

        Venta venta = new Venta();
        venta.setUsuario(usuario);
        venta.setProducto(producto);
        venta.setCantidad(cantidad);
        venta.setPrecioUnitario(producto.getPrecio());
        venta.setFecha(LocalDate.now());

        return ventaRepository.save(venta);
    }

    public void eliminar(Long id) {
        ventaRepository.deleteById(id);
    }

    public Venta actualizarVenta(Long id, Integer nuevaCantidad) {
        Venta venta = ventaRepository.findById(id).orElse(null);
        if (venta != null) {
            venta.setCantidad(nuevaCantidad);
            venta.setPrecioUnitario(venta.getProducto().getPrecio());
            return ventaRepository.save(venta);
        }
        return null;
    }

}
