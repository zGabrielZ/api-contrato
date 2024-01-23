package br.com.gabrielferreira.contratos.tests;

import br.com.gabrielferreira.contratos.api.model.input.PerfilInputModel;
import br.com.gabrielferreira.contratos.domain.model.Perfil;
import br.com.gabrielferreira.contratos.domain.repository.filter.PerfilFilterModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
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

        return Arrays.asList(perfil1, perfil2);
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

    public static List<PerfilInputModel> criarPerfisInput(){
        PerfilInputModel input1 = PerfilInputModel.builder()
                .id(1L)
                .build();

        PerfilInputModel input2 = PerfilInputModel.builder()
                .id(2L)
                .build();

        return Arrays.asList(input1, input2);
    }

    public static PerfilInputModel criarPerfilInput(){
        return PerfilInputModel.builder()
                .id(1L)
                .build();
    }
}
