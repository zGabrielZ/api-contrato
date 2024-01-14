package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.contratos.domain.model.Telefone;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.UsuarioRepository;
import br.com.gabrielferreira.contratos.domain.service.validator.TelefoneValidador;
import br.com.gabrielferreira.contratos.domain.service.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioValidator usuarioValidator;

    private final TelefoneValidador telefoneValidator;

    @Transactional
    public Usuario cadastrarUsuario(Usuario usuario){
        usuarioValidator.validarCampos(usuario);
        usuarioValidator.validarEmail(usuario.getEmail(), usuario.getId());
        usuarioValidator.validarPerfil(usuario);

        List<Telefone> telefones = usuario.getTelefones();
        telefones.forEach(telefoneValidator::validarCampos);
        telefones.forEach(telefoneValidator::validarTipoTelefone);

        for (Telefone telefone : telefones) {
            telefone.setUsuario(usuario);
        }

        usuario = usuarioRepository.save(usuario);
        return usuario;
    }

    public Usuario buscarUsuarioPorId(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado"));
    }

    public boolean isUsuarioExistente(Long id){
        return usuarioRepository.buscarUsuarioExistente(id);
    }
}
