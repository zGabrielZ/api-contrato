package br.com.gabrielferreira.contratos.tests;

import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.filter.UsuarioFilterModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import static br.com.gabrielferreira.contratos.tests.PerfilFactory.*;
import static br.com.gabrielferreira.contratos.common.utils.DataUtils.*;

public class UsuarioFactory {

    private UsuarioFactory(){}

    public static Usuario criarUsuario(){
        return Usuario.builder()
                .nome("Teste usuário nome")
                .sobrenome("Teste usuário sobrenome")
                .email("Teste usuário email")
                .perfis(criarPerfis())
                .build();
    }

    public static Usuario usuarioCriado(){
        Usuario usuario = criarUsuario();
        usuario.setId(1L);
        usuario.setDataCadastro(ZonedDateTime.now(UTC));
        usuario.setDataAtualizacao(ZonedDateTime.now(UTC));
        return usuario;
    }

    public static Usuario atualizarUsuario(){
        Usuario usuario = usuarioCriado();
        usuario.setNome("Teste usuário editado");
        usuario.setEmail("Teste usuário email");
        usuario.setSobrenome("Teste usuário sobrenome editado");
        return usuario;
    }

    public static UsuarioFilterModel criarFiltroUsuario(){
        return UsuarioFilterModel.builder()
                .id(1L)
                .nome("Teste nome")
                .sobrenome("Teste sobrenome")
                .email("Teste email")
                .dataCadastro(LocalDate.now(UTC))
                .dataAtualizacao(LocalDate.now(UTC))
                .build();
    }

    public static Page<Usuario> criarUsuariosPaginados(){
        return new PageImpl<>(List.of(usuarioCriado()));
    }
}
