package EcoMarketSpa.EcoMarketSpa;

import EcoMarketSpa.EcoMarketSpa.model.Producto;
import EcoMarketSpa.EcoMarketSpa.model.Producto.Categoria;
import EcoMarketSpa.EcoMarketSpa.repository.ProductoRepository;
import EcoMarketSpa.EcoMarketSpa.service.ProductoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks antes de cada test
    }

    @Test
    void testFindAll() {
        List<Producto> productos = List.of(new Producto(), new Producto());
        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> resultado = productoService.findAll();
        assertEquals(2, resultado.size());
    }

    @Test
    void testFindById() {
        long id = 1L;
        Producto producto = crearProducto();
        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));

        Producto found = productoService.findById(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    void testSave() {
        Producto producto = crearProducto();
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto saved = productoService.save(producto);
        assertNotNull(saved);
        assertEquals("Shampoo ecológico", saved.getNombre());
    }

    @Test
    void testDeleteById() {
        long id = 1L;
        doNothing().when(productoRepository).deleteById(id);

        productoService.delete(id);
        verify(productoRepository, times(1)).deleteById(id);
    }

    @Test
    void testConsultarStock() {
        long id = 1L;
        when(productoRepository.consultarStock(id)).thenReturn(25);

        Integer stock = productoService.consultarStock(id);
        assertNotNull(stock);
        assertEquals(25, stock);
    }

    private Producto crearProducto() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Shampoo ecológico");
        producto.setCategoria(Categoria.ecologico);
        producto.setPrecio(4990.0);
        producto.setStock(25);
        producto.setFechaIngreso(new Date());
        producto.setFechaCaducidad(new Date(System.currentTimeMillis() + 86400000L * 30)); // +30 días
        return producto;
    }
}