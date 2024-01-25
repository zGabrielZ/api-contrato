package br.com.gabrielferreira.contratos.tests;

import br.com.gabrielferreira.contratos.domain.model.Telefone;
import br.com.gabrielferreira.contratos.domain.model.enums.TipoTelefoneEnum;

import java.time.ZonedDateTime;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.UTC;
import static br.com.gabrielferreira.contratos.tests.UsuarioFactory.*;

public class TelefoneFactory {

    private TelefoneFactory(){}

    public static Telefone criarTelefone(){
        return Telefone.builder()
                .ddd("11")
                .numero("999999999")
                .tipoTelefone(TipoTelefoneEnum.RESIDENCIAL)
                .usuario(usuarioCriado())
                .build();
    }

    public static Telefone telefoneCriado(){
        Telefone telefone = criarTelefone();
        telefone.setId(1L);
        telefone.setDataCadastro(ZonedDateTime.now(UTC));
        telefone.setDataAtualizacao(ZonedDateTime.now(UTC));
        return telefone;
    }
}
