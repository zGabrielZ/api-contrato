package br.com.gabrielferreira.contratos.domain.service.validator;

import br.com.gabrielferreira.contratos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.contratos.domain.model.Perfil;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.UsuarioRepository;
import br.com.gabrielferreira.contratos.domain.service.PerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    private final PerfilService perfilService;

    public void validarCampos(Usuario usuario){
        usuario.setNome(usuario.getNome().trim());
        usuario.setSobrenome(usuario.getSobrenome().trim());
        usuario.setEmail(usuario.getEmail().trim());
    }

    public void validarEmail(String email, Long id){
        if(isEmailExistente(email, id)){
            throw new RegraDeNegocioException(String.format("Este e-mail '%s' já foi cadastrado", email));
        }
    }

    public boolean isEmailExistente(String email, Long id){
        if(id == null){
            return usuarioRepository.buscarPorEmail(email)
                    .isPresent();
        } else {
            return usuarioRepository.buscarPorEmail(email)
                    .filter(u -> !u.getId().equals(id))
                    .isPresent();
        }
    }

    public void validarPerfil(Usuario usuario){
        validarPerfilDuplicados(usuario.getPerfis());
        validarIdPerfil(usuario);
    }

    public void validarPerfilDuplicados(List<Perfil> perfis){
        List<Long> idsPerfis = perfis.stream().map(Perfil::getId).toList();
        idsPerfis.forEach(idPerfil -> {
            int duplicados = Collections.frequency(idsPerfis, idPerfil);

            if(duplicados > 1){
                throw new RegraDeNegocioException("Não vai ser possível cadastrar este usuário pois tem perfis duplicados ou mais de duplicados");
            }
        });
    }

    public void validarIdPerfil(Usuario usuario){
        List<Perfil> perfis = new ArrayList<>();
        usuario.getPerfis().forEach(perfil -> perfis.add(perfilService.buscarPerfilPorId(perfil.getId())));
        usuario.getPerfis().clear();
        usuario.setPerfis(perfis);
    }
}
