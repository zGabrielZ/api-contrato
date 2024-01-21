package br.com.gabrielferreira.contratos.tests;

import br.com.gabrielferreira.contratos.domain.model.Perfil;
import br.com.gabrielferreira.contratos.domain.repository.filter.PerfilFilterModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class PerfilFactory {

    private PerfilFactory(){}

    public static Perfil criarPerfil(){
        return Perfil.builder()
                .id(1L)
                .descricao("Teste perfil")
                .autoriedade("Teste autoriedade")
                .build();
    }

    public static List<Perfil> criarPerfis(){
        Perfil perfil1 = criarPerfil();

        Perfil perfil2 = criarPerfil();
        perfil2.setId(2L);

        return List.of(perfil1, perfil2);
    }

    public static PerfilFilterModel criarFiltroPerfil(Long id, String descricao, String autoriedade){
        return PerfilFilterModel.builder()
                .id(id)
                .descricao(descricao)
                .autoriedade(autoriedade)
                .build();
    }

    public static Page<Perfil> criarPerfisPaginacao(){
        return new PageImpl<>(List.of(criarPerfil()));
    }
}