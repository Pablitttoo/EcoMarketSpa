package EcoMarketSpa.EcoMarketSpa;

import EcoMarketSpa.EcoMarketSpa.service.EnvioService;
import EcoMarketSpa.EcoMarketSpa.model.Envio;
import EcoMarketSpa.EcoMarketSpa.repository.EnvioRepository;
import EcoMarketSpa.EcoMarketSpa.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository; // Simula el repositorio de envios

    @Mock
    private UsuarioRepository usuarioRepository; // Simula el repositorio de usuarios

    @InjectMocks
    private EnvioService envioService; // Clase que se va a probar con los mocks inyectados

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks antes de cada test
    }

    @Test
    void testGuardar() {
        Envio envio = new Envio(); // Crea un objeto Envio vacío
        when(envioRepository.save(envio)).thenReturn(envio); // Simula el comportamiento del save

        Envio resultado = envioRepository.save(envio); // Ejecuta el método a probar

        assertNotNull(resultado); // Verifica que no sea nulo
        assertEquals(envio, resultado); // Verifica que sea el mismo objeto
        verify(envioRepository, times(1)).save(envio); // Verifica que el método save se ejecutó una vez
    }

    @Test
    void testEliminar() {
        Long id = 1L; // ID del envío
        doNothing().when(envioRepository).deleteById(id); // Simula que deleteById no hace nada

        envioRepository.deleteById(1L); // Llama al método a probar

        verify(envioRepository, times(1)).deleteById(id); // Verifica que se ejecutó una vez
    }

    @Test
    void testBuscarPorId() {
        Long id = 1L; // ID a buscar
        Envio envio = new Envio(); // Objeto simulado
        when(envioRepository.findById(id)).thenReturn(Optional.of(envio)); // Simula el findById

        Optional<Envio> resultado = envioRepository.findById(1L); // Ejecuta el método a probar

        assertTrue(resultado.isPresent()); // Verifica que se encontró algo
        assertEquals(envio, resultado.get()); // Verifica que sea el objeto esperado
    }

}