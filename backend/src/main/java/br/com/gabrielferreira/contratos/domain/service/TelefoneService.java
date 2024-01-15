package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.contratos.domain.model.Telefone;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.TelefoneRepository;
import br.com.gabrielferreira.contratos.domain.repository.filter.TelefoneFilterModel;
import br.com.gabrielferreira.contratos.domain.service.validator.TelefoneValidador;
import br.com.gabrielferreira.contratos.domain.specification.TelefoneSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TelefoneService {

    private final TelefoneRepository telefoneRepository;

    private final UsuarioService usuarioService;

    private final TelefoneValidador telefoneValidador;

    public Page<Telefone> buscarTelefones(Long idUsuario, Pageable pageable, TelefoneFilterModel filtro){
        if(!usuarioService.isUsuarioExistente(idUsuario)){
            throw new NaoEncontradoException("Usuário não encontrado");
        }
        return telefoneRepository.findAll(new TelefoneSpecification(idUsuario, filtro), pageable);
    }

    public Telefone buscarTelefonePorId(Long idUsuario, Long idTelefone){
        return telefoneRepository.buscarTelefone(idUsuario, idTelefone)
                .orElseThrow(() -> new NaoEncontradoException("Telefone não encontrado"));
    }

    public Long buscarQuantidadeTelefonePorUsuario(Long idUsuario){
        if(!usuarioService.isUsuarioExistente(idUsuario)){
            throw new NaoEncontradoException("Usuário não encontrado");
        }
        return telefoneRepository.buscarQuantidadeTelefonePorUsuario(idUsuario);
    }

    @Transactional
    public Telefone cadastrarTelefone(Long idUsuario, Telefone telefone){
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        telefone.setUsuario(usuario);

        telefoneValidador.validarCampos(telefone);
        telefoneValidador.validarTipoTelefone(telefone);
        telefoneValidador.validarTelefoneExistente(null, telefone);

        telefone = telefoneRepository.save(telefone);
        return telefone;
    }

    @Transactional
    public Telefone atualizarTelefone(Long idUsuario, Long id, Telefone telefone){
        Telefone telefoneEncontrado = buscarTelefonePorId(idUsuario, id);

        telefoneValidador.validarCampos(telefone);
        telefoneValidador.validarTipoTelefone(telefone);
        telefoneValidador.validarTelefoneExistente(telefoneEncontrado.getId(), telefone);

        preencherCamposTelefone(telefoneEncontrado, telefone);

        telefoneEncontrado = telefoneRepository.save(telefoneEncontrado);
        return telefoneEncontrado;
    }

    @Transactional
    public void deletarTelefonePorId(Long idUsuario, Long id){
        Telefone telefoneEncontrado = buscarTelefonePorId(idUsuario, id);
        telefoneRepository.delete(telefoneEncontrado);
    }

    private void preencherCamposTelefone(Telefone telefoneEncontrado, Telefone telefone){
        telefoneEncontrado.setDdd(telefone.getDdd());
        telefoneEncontrado.setNumero(telefone.getNumero());
        telefoneEncontrado.setDescricao(telefone.getDescricao());
        telefoneEncontrado.setTipoTelefone(telefone.getTipoTelefone());
    }
}
