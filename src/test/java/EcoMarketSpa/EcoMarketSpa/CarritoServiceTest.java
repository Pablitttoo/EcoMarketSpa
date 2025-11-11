package EcoMarketSpa.EcoMarketSpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import EcoMarketSpa.EcoMarketSpa.model.CarritoCompra;
import EcoMarketSpa.EcoMarketSpa.model.Producto;
import EcoMarketSpa.EcoMarketSpa.repository.CarritoRepository;
import EcoMarketSpa.EcoMarketSpa.repository.ProductoRepository;

import EcoMarketSpa.EcoMarketSpa.service.CarritoService;

public class CarritoServiceTest {

    @InjectMocks
    private CarritoService carritoService;

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private ProductoRepository productoRepository;

    private CarritoCompra carrito;
    private Producto producto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        carrito = new CarritoCompra();
        carrito.setId(1);
        carrito.setProductos(new ArrayList<>());

        producto = new Producto();
        producto.setId(1L);
    }

    @Test
    void testAgregarProducto() {
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(carritoRepository.save(any(CarritoCompra.class))).thenReturn(carrito);

        CarritoCompra actualizado = carritoService.agregarProducto(1L, 1L);

        assertTrue(actualizado.getProductos().contains(producto));
        verify(carritoRepository).save(carrito);
    }

    @Test
    void testEliminarProducto() {
        carrito.getProductos().add(producto);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(carritoRepository.save(any(CarritoCompra.class))).thenReturn(carrito);

        CarritoCompra actualizado = carritoService.eliminarProducto(1L, 1L);

        assertFalse(actualizado.getProductos().contains(producto));
        verify(carritoRepository).save(carrito);
    }

    @Test
    void testEliminarProducto_CarritoNoExiste() {
        when(carritoRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            carritoService.eliminarProducto(99L, 1L);
        });

        assertEquals("Carrito no encontrado", exception.getMessage());
    }

    @Test
    void testEliminarProducto_ProductoNoExiste() {
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            carritoService.eliminarProducto(1L, 99L);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
    }
}


