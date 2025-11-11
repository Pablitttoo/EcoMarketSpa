package EcoMarketSpa.EcoMarketSpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import EcoMarketSpa.EcoMarketSpa.model.Tienda;
import EcoMarketSpa.EcoMarketSpa.repository.TiendaRepository;
import EcoMarketSpa.EcoMarketSpa.service.TiendaService;


public class TiendaServiceTest {



    @Mock
    private TiendaRepository tiendaRepository;

    @InjectMocks
    private TiendaService tiendaService;

    private Tienda tienda;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tienda = new Tienda();
        tienda.setId(1L);
        tienda.setNombre("EcoMarket Central");
        tienda.setDireccion("Av. Sustentable 456");
        tienda.setCiudad("Valparaíso");
        tienda.setTelefono("987654321");

    }

    @Test
    void testListarTiendas() {
        when(tiendaRepository.findAll()).thenReturn(List.of(tienda));

        List<Tienda> resultado = tiendaService.listarTiendas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("EcoMarket Central", resultado.get(0).getNombre());
    }

    @Test
    void testObtenerTiendaPorId() {
        when(tiendaRepository.findById(1L)).thenReturn(Optional.of(tienda));

        java.util.Optional<Tienda> resultado = tiendaService.obtenerTiendaPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("EcoMarket Central", resultado.get().getNombre());
    }

    @Test
    void testGuardarTienda() {
        when(tiendaRepository.save(tienda)).thenReturn(tienda);

        Tienda guardada = tiendaService.guardarTienda(tienda);

        assertNotNull(guardada);
        assertEquals("EcoMarket Central", guardada.getNombre());
        verify(tiendaRepository, times(1)).save(tienda);
    }

    @Test
    void testEliminarTienda() {
        doNothing().when(tiendaRepository).deleteById(1L);

        tiendaService.eliminarTienda(1L);

        verify(tiendaRepository, times(1)).deleteById(1L);
    }
}



