package br.com.gabrielferreira.contratos.api.mapper;

import br.com.gabrielferreira.contratos.api.model.UsuarioModel;
import br.com.gabrielferreira.contratos.api.model.UsuarioResumidoModel;
import br.com.gabrielferreira.contratos.api.model.input.UsuarioInputModel;
import br.com.gabrielferreira.contratos.api.model.params.UsuarioParamsModel;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.filter.UsuarioFilterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.*;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    private final PerfilMapper perfilMapper;

    public Usuario toUsuario(UsuarioInputModel usuario){
        return Usuario.builder()
                .nome(usuario.getNome())
                .sobrenome(usuario.getSobrenome())
                .email(usuario.getEmail())
                .perfis(perfilMapper.toPerfis(usuario.getPerfis()))
                .build();
    }

    public UsuarioModel toUsuarioModel(Usuario usuario){
        return UsuarioModel.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .sobrenome(usuario.getSobrenome())
                .email(usuario.getEmail())
                .perfis(perfilMapper.toPerfisModels(usuario.getPerfis()))
                .saldoTotal(usuario.getSaldoTotal() != null ? usuario.getSaldoTotal().getValor() : null)
                .dataCadastrado(toFusoPadraoSistema(usuario.getDataCadastro()))
                .dataAtualizacao(toFusoPadraoSistema(usuario.getDataAtualizacao()))
                .build();
    }

    public UsuarioModel toUsuarioSemTelefoneModel(Usuario usuario){
        return UsuarioModel.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .sobrenome(usuario.getSobrenome())
                .email(usuario.getEmail())
                .perfis(perfilMapper.toPerfisModels(usuario.getPerfis()))
                .saldoTotal(usuario.getSaldoTotal() != null ? usuario.getSaldoTotal().getValor() : null)
                .dataCadastrado(toFusoPadraoSistema(usuario.getDataCadastro()))
                .dataAtualizacao(toFusoPadraoSistema(usuario.getDataAtualizacao()))
                .build();
    }

    public UsuarioResumidoModel toUsuarioResumidoModel(Usuario usuario){
        return UsuarioResumidoModel.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .sobrenome(usuario.getSobrenome())
                .email(usuario.getEmail())
                //.saldoTotal(usuario.getSaldoTotal() != null ? usuario.getSaldoTotal().getValor() : null)
                .dataCadastrado(usuario.getDataCadastro())
                .dataAtualizacao(usuario.getDataAtualizacao())
                .build();
    }

    public Page<UsuarioResumidoModel> toUsuariosResumidosModels(Page<Usuario> usuarios){
        return usuarios.map(this::toUsuarioResumidoModel);
    }

    public UsuarioFilterModel toUsuarioFilterModel(UsuarioParamsModel usuarioParamsModel){
        return UsuarioFilterModel.builder()
                .id(usuarioParamsModel.getId())
                .nome(usuarioParamsModel.getNome())
                .sobrenome(usuarioParamsModel.getSobrenome())
                .email(usuarioParamsModel.getEmail())
                .dataCadastro(usuarioParamsModel.getDataCadastro())
                .dataAtualizacao(usuarioParamsModel.getDataAtualizacao())
                .build();
    }
}
