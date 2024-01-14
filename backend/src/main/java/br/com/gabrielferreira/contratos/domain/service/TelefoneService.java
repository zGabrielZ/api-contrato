package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.contratos.domain.model.Telefone;
import br.com.gabrielferreira.contratos.domain.repository.TelefoneRepository;
import br.com.gabrielferreira.contratos.domain.repository.filter.TelefoneFilterModel;
import br.com.gabrielferreira.contratos.domain.specification.TelefoneSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelefoneService {

    private final TelefoneRepository telefoneRepository;

    private final UsuarioService usuarioService;

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
}
