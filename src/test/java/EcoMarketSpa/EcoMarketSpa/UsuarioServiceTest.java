package EcoMarketSpa.EcoMarketSpa;

import EcoMarketSpa.EcoMarketSpa.service.UsuarioService;
import EcoMarketSpa.EcoMarketSpa.model.Usuario;
import EcoMarketSpa.EcoMarketSpa.model.Usuario.Rol;
import EcoMarketSpa.EcoMarketSpa.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombres("Pablo");
        usuario.setApellidos("González");
        usuario.setCorreo("pablo@example.com");
        usuario.setContraseña("1234");
        usuario.setDireccion("Calle Falsa 123");
        usuario.setEdad(30);
        usuario.setRol(Rol.CLIENTE);
    }

    @Test
    void testGuardarUsuario() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.save(usuario);

        assertNotNull(resultado);
        assertEquals("Pablo", resultado.getNombres());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testBuscarTodos() {
        List<Usuario> lista = List.of(usuario);
        when(usuarioRepository.findAll()).thenReturn(lista);

        List<Usuario> resultado = usuarioService.findAll();

        assertEquals(1, resultado.size());
        assertEquals("Pablo", resultado.get(0).getNombres());
    }

    @Test
    void testBuscarPorId() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Pablo", resultado.get().getNombres());
    }

    @Test
    void testEliminarPorId() {
        usuarioService.deleteById(1L);

        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}
