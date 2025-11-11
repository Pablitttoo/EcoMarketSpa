package EcoMarketSpa.EcoMarketSpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import EcoMarketSpa.EcoMarketSpa.model.Comprobante;
import EcoMarketSpa.EcoMarketSpa.repository.ComprobanteRepository;
import EcoMarketSpa.EcoMarketSpa.service.ComprobanteService;

public class ComprobanteServiceTest {


    @Mock
    private ComprobanteRepository comprobanteRepository; // Simula el repositorio

    @InjectMocks
    private ComprobanteService comprobanteService; // Clase a probar

    private Comprobante comprobante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks

        comprobante = new Comprobante();
        comprobante.setId(1);
        comprobante.setVentasId(1001);
        comprobante.setMontoTotal(15000);
        comprobante.setIva(2850);
        comprobante.setSubTotal(12150);
        comprobante.setMontoNeto(12150);
        comprobante.setDescuento(0);
        comprobante.setFechaEmision(new Date(System.currentTimeMillis()));
        comprobante.setMetodopago(Comprobante.metodoPago.transferencia);
        comprobante.setTipoComprobante(Comprobante.tipoComprobante.factura);
        comprobante.setFormapago(Comprobante.formaPago.contado);
        comprobante.setProductos(new ArrayList<>());
    }

    @Test
    void testGuardar() {
        when(comprobanteRepository.save(comprobante)).thenReturn(comprobante);

        Comprobante resultado = comprobanteService.agregarComprobante(comprobante);

        assertNotNull(resultado);
        assertEquals(comprobante, resultado);
        verify(comprobanteRepository, times(1)).save(comprobante);
    }

    @Test
    void testEliminar() {
        Long id = 1L;
        doNothing().when(comprobanteRepository).deleteById(id);

        comprobanteService.borrarComprobante(id);

        verify(comprobanteRepository, times(1)).deleteById(id);
    }

    @Test
    void testBuscarPorId() {
        Long id = 1L;
        when(comprobanteRepository.findById(id)).thenReturn(Optional.of(comprobante));

        Comprobante resultado = comprobanteService.buscarId(id);

        assertNotNull(resultado);
        assertEquals(comprobante, resultado);
        verify(comprobanteRepository, times(1)).findById(id);
    }

    @Test
    void testBuscarTodo() {
        List<Comprobante> lista = List.of(comprobante);
        when(comprobanteRepository.findAll()).thenReturn(lista);

        List<Comprobante> resultado = comprobanteService.buscarTodo();

        assertEquals(1, resultado.size());
        assertEquals(comprobante, resultado.get(0));
        verify(comprobanteRepository, times(1)).findAll();
    }
}