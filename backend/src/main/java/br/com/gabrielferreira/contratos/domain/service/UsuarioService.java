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

        List<Telefone> telefones = usuario.getTelefones();
        telefoneValidator.validarTelefones(telefones);

        preencherCamposUsuario(usuarioEncontrado, usuario);

        usuarioEncontrado = usuarioRepository.save(usuarioEncontrado);
        return usuarioEncontrado;
    }

    public boolean isUsuarioExistente(Long id){
        return usuarioRepository.buscarUsuarioExistente(id);
    }

    @Transactional
    public void deletarUsuarioPorId(Long id){
        Usuario usuario = buscarUsuarioPorId(id);
        usuarioRepository.delete(usuario);
    }

    private void preencherCamposUsuario(Usuario usuarioEncontrado, Usuario usuario){
        usuarioEncontrado.setNome(usuario.getNome());
        usuarioEncontrado.setSobrenome(usuario.getSobrenome());
        usuarioEncontrado.setEmail(usuario.getEmail());

        removerPerfisNaoExistentes(usuario.getPerfis(), usuarioEncontrado.getPerfis());
        incluirNovosPerfis(usuario.getPerfis(), usuarioEncontrado.getPerfis());

        removerTelefonesNaoExistentes(usuario.getTelefones(), usuarioEncontrado.getTelefones());
        incluirNovosTelefones(usuario.getTelefones(), usuarioEncontrado.getTelefones(), usuarioEncontrado);
    }

    private void removerPerfisNaoExistentes(List<Perfil> perfisExistentes, List<Perfil> novosPerfis){
        perfisExistentes.removeIf(perfisExistente -> perfisExistente.isNaoContemPerfil(novosPerfis));
    }

    private void incluirNovosPerfis(List<Perfil> perfisExistentes, List<Perfil> novosPerfis){
        novosPerfis.forEach(novoPerfil -> {
            if(novoPerfil.isNaoContemPerfil(perfisExistentes)){
                perfisExistentes.add(novoPerfil);
            }
        });
    }

    private void incluirNovosTelefones(List<Telefone> novosTelefones, List<Telefone> telefonesExistentes, Usuario usuarioEncontrado){
        novosTelefones.forEach(novoTelefone -> {
            if(novoTelefone.isNaoContemTelefone(telefonesExistentes)){
                novoTelefone.setUsuario(usuarioEncontrado);
                telefonesExistentes.add(novoTelefone);
            }
        });
    }

    private void removerTelefonesNaoExistentes(List<Telefone> novosTelefones, List<Telefone> telefonesExistentes){
        telefonesExistentes.removeIf(telefonesExistente -> telefonesExistente.isNaoContemTelefone(novosTelefones));
    }
}
