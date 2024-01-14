package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.contratos.domain.model.Perfil;
import br.com.gabrielferreira.contratos.domain.repository.PerfilRepository;
import br.com.gabrielferreira.contratos.domain.repository.filter.PerfilFilterModel;
import br.com.gabrielferreira.contratos.domain.specification.PerfilSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioPerfilService {

    private final PerfilRepository perfilRepository;

    private final UsuarioService usuarioService;

    public Page<Perfil> buscarPerfisPorUsuario(Long idUsuario, Pageable pageable, PerfilFilterModel filtro){
        if(!usuarioService.isUsuarioExistente(idUsuario)){
            throw new NaoEncontradoException("Usuário não encontrado");
        }
        return perfilRepository.findAll(new PerfilSpecification(idUsuario, filtro), pageable);
    }
}
