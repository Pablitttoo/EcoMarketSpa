package EcoMarketSpa.EcoMarketSpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import EcoMarketSpa.EcoMarketSpa.model.Proveedor;
import EcoMarketSpa.EcoMarketSpa.model.Tienda;
import EcoMarketSpa.EcoMarketSpa.repository.ProveedorRepository;
import EcoMarketSpa.EcoMarketSpa.service.ProveedorService;


public class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    private Proveedor proveedor;

    @BeforeEach
    void setUp() {
        Tienda tienda = new Tienda();
        tienda.setId(1L);
        tienda.setNombre("EcoTienda");

        proveedor = new Proveedor();
        proveedor.setId(1L);
        proveedor.setMarca("BioMarca");
        proveedor.setTelefono("987654321");
        proveedor.setCorreo("bio@marca.com");
        proveedor.setTienda(tienda);
    }

    @Test
    void testGuardarProveedor() {
        when(proveedorRepository.save(proveedor)).thenReturn(proveedor);

        Proveedor resultado = proveedorService.guardar(proveedor);

        assertNotNull(resultado);
        assertEquals("BioMarca", resultado.getMarca());
        verify(proveedorRepository, times(1)).save(proveedor);
    }

    @Test
    void testObtenerTodos() {
        List<Proveedor> lista = List.of(proveedor);
        when(proveedorRepository.findAll()).thenReturn(lista);

        List<Proveedor> resultado = proveedorService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("BioMarca", resultado.get(0).getMarca());
    }

    @Test
    void testObtenerPorId() {
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));

        java.util.Optional<Proveedor> resultado = proveedorService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("bio@marca.com", resultado.get().getCorreo());
    }

    @Test
    void testEliminarProveedor() {
        proveedorService.eliminar(1L);

        verify(proveedorRepository, times(1)).deleteById(1L);
    }
}



