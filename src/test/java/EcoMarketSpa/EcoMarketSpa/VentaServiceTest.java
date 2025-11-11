package EcoMarketSpa.EcoMarketSpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import EcoMarketSpa.EcoMarketSpa.model.Producto;
import EcoMarketSpa.EcoMarketSpa.model.Tienda;
import EcoMarketSpa.EcoMarketSpa.model.Usuario;
import EcoMarketSpa.EcoMarketSpa.model.Venta;
import EcoMarketSpa.EcoMarketSpa.repository.VentaRepository;
import EcoMarketSpa.EcoMarketSpa.service.VentaService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.mockito.*;

public class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @InjectMocks
    private VentaService ventaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListar() {
        Venta venta = crearVenta();
        when(ventaRepository.findAll()).thenReturn(List.of(venta));

        List<Venta> ventas = ventaService.listar();
        assertNotNull(ventas);
        assertEquals(1, ventas.size());
        assertEquals(venta.getIdVenta(), ventas.get(0).getIdVenta());
    }

    @Test
    void testBuscar() {
        Long id = 1L;
        Venta venta = crearVenta();
        when(ventaRepository.findById(id)).thenReturn(Optional.of(venta));

        Venta encontrada = ventaService.buscar(id);
        assertNotNull(encontrada);
        assertEquals(id, encontrada.getIdVenta());
    }

    @Test
    void testActualizarVenta() {
        Venta venta = crearVenta();
        when(ventaRepository.findById(venta.getIdVenta())).thenReturn(Optional.of(venta));
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);

        Venta actualizada = ventaService.actualizarVenta(venta.getIdVenta(), 5);
        assertNotNull(actualizada);
        assertEquals(5, actualizada.getCantidad());
    }

    @Test
    void testEliminar() {
        Long id = 1L;
        doNothing().when(ventaRepository).deleteById(id);

        ventaService.eliminar(id);
        verify(ventaRepository, times(1)).deleteById(id);
    }

    private Venta crearVenta() {
        Tienda tienda = new Tienda();
        tienda.setId(1L);
        tienda.setNombre("Tienda Central");
        tienda.setDireccion("Calle Falsa 123");
        tienda.setCiudad("Santiago");
        tienda.setTelefono("123456789");

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto Prueba");
        producto.setPrecio(1000.0);
        producto.setStock(10);
        producto.setTienda(tienda);
        producto.setCategoria(Producto.Categoria.ecologico);
        producto.setFechaIngreso(java.sql.Date.valueOf("2024-01-01"));
        producto.setFechaCaducidad(java.sql.Date.valueOf("2025-01-01"));

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombres("Juan");
        usuario.setApellidos("Pérez");
        usuario.setCorreo("juan@mail.com");
        usuario.setEdad(30);
        usuario.setContraseña("clave123");
        usuario.setRol(Usuario.Rol.CLIENTE);
        usuario.setDireccion("Calle Cliente 123");

        Venta venta = new Venta();
        venta.setIdVenta(1L);
        venta.setCantidad(2);
        venta.setPrecioUnitario(1000.0);
        venta.setUsuario(usuario);
        venta.setProducto(producto);
        venta.setFecha(LocalDate.now());

        return venta;
    }
}
