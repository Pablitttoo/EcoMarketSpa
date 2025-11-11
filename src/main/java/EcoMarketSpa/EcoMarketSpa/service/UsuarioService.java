package EcoMarketSpa.EcoMarketSpa.service;

import EcoMarketSpa.EcoMarketSpa.model.Usuario;
import EcoMarketSpa.EcoMarketSpa.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

@Transactional
@Service

public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();

    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id.longValue());
    }

    public Usuario save(Usuario usu) {
        return usuarioRepository.save(usu);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

}
