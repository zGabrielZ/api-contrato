package br.com.gabrielferreira.contratos.api.mapper;

import br.com.gabrielferreira.contratos.api.model.UsuarioModel;
import br.com.gabrielferreira.contratos.api.model.input.UsuarioInputModel;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.*;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    private final TelefoneMapper telefoneMapper;

    private final PerfilMapper perfilMapper;

    public Usuario toUsuario(UsuarioInputModel usuario){
        return Usuario.builder()
                .nome(usuario.getNome())
                .sobrenome(usuario.getSobrenome())
                .email(usuario.getEmail())
                .telefones(telefoneMapper.toTelefones(usuario.getTelefones()))
                .perfis(perfilMapper.toPerfis(usuario.getPerfis()))
                .build();
    }

    public UsuarioModel toUsuarioModel(Usuario usuario){
        return UsuarioModel.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .sobrenome(usuario.getSobrenome())
                .email(usuario.getEmail())
                .telefones(telefoneMapper.toTelefonesModels(usuario.getTelefones()))
                .perfis(perfilMapper.toPerfisModels(usuario.getPerfis()))
                .dataCadastrado(toFusoPadraoSistema(usuario.getDataCadastro()))
                .dataAtualizacao(toFusoPadraoSistema(usuario.getDataAtualizacao()))
                .build();
    }
}
