package br.com.gabrielferreira.contratos.tests;

import br.com.gabrielferreira.contratos.domain.model.Telefone;
import br.com.gabrielferreira.contratos.domain.model.enums.TipoTelefoneEnum;
import br.com.gabrielferreira.contratos.domain.repository.filter.TelefoneFilterModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.ZonedDateTime;
import java.util.List;

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

    public static Telefone atualizarTelefone(){
        Telefone telefone = telefoneCriado();
        telefone.setDdd("22");
        telefone.setNumero("888888888");
        return telefone;
    }

    public static TelefoneFilterModel criarFiltroTelefone(){
        return TelefoneFilterModel.builder()
                .id(1L)
                .descricao("Teste")
                .ddd("11")
                .numero("999999999")
                .build();
    }

    public static Page<Telefone> criarTelefonesPaginados(){
        return new PageImpl<>(List.of(telefoneCriado()));
    }
}
