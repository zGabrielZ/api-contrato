package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.contratos.domain.model.Perfil;
import br.com.gabrielferreira.contratos.domain.model.Telefone;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.UsuarioRepository;
import br.com.gabrielferreira.contratos.domain.service.validator.TelefoneValidador;
import br.com.gabrielferreira.contratos.domain.service.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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

        Set<Telefone> telefones = usuario.getTelefones();
        telefoneValidator.validarTelefones(telefones);

        for (Telefone telefone : usuario.getTelefones()) {
            telefone.setUsuario(usuario);
        }

        usuario = usuarioRepository.save(usuario);
        return usuario;
    }

    public Usuario buscarUsuarioPorId(Long id){
        return usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado"));
    }

    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario usuario){
        Usuario usuarioEncontrado = usuarioRepository.buscarUsuarioCompletoPorId(id)
                        .orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado"));

        usuarioValidator.validarCampos(usuario);
        usuarioValidator.validarEmail(usuario.getEmail(), usuarioEncontrado.getId());
        usuarioValidator.validarPerfil(usuario);

        Set<Telefone> telefones = usuario.getTelefones();
        telefoneValidator.validarTelefones(telefones);

        preencherCamposUsuario(usuarioEncontrado, usuario);

        usuarioEncontrado = usuarioRepository.save(usuarioEncontrado);
        return usuarioEncontrado;
    }

    public boolean isUsuarioExistente(Long id){
        return usuarioRepository.buscarUsuarioExistente(id);
    }

    private void preencherCamposUsuario(Usuario usuarioEncontrado, Usuario usuario){
        usuarioEncontrado.setNome(usuario.getNome());
        usuarioEncontrado.setSobrenome(usuario.getSobrenome());
        usuarioEncontrado.setEmail(usuario.getEmail());

        List<Perfil> novosPerfis = usuario.getPerfis();
        List<Perfil> perfisExistentes = usuarioEncontrado.getPerfis();

        perfisExistentes.removeIf(perfisExistente -> perfisExistente.isNaoContemPerfil(novosPerfis));
        novosPerfis.forEach(novoPerfil -> {
            if(novoPerfil.isNaoContemPerfil(perfisExistentes)){
                perfisExistentes.add(novoPerfil);
            }
        });

        Set<Telefone> novosTelefones = usuario.getTelefones();
        Set<Telefone> telefonesExistentes = usuarioEncontrado.getTelefones();

        telefonesExistentes.removeIf(telefonesExistente -> telefonesExistente.isNaoContemTelefone(novosTelefones));
        novosTelefones.forEach(novoTelefone -> {
            if(novoTelefone.isNaoContemTelefone(telefonesExistentes)){
                novoTelefone.setUsuario(usuarioEncontrado);
                telefonesExistentes.add(novoTelefone);
            }
        });
    }
}
