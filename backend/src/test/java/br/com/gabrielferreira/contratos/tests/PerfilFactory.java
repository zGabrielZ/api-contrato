package br.com.gabrielferreira.contratos.tests;

import br.com.gabrielferreira.contratos.domain.model.Perfil;

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
}
