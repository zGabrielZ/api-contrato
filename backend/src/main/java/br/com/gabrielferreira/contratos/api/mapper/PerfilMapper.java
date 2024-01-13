package br.com.gabrielferreira.contratos.api.mapper;

import br.com.gabrielferreira.contratos.api.model.PerfilModel;
import br.com.gabrielferreira.contratos.api.model.input.PerfilInputModel;
import br.com.gabrielferreira.contratos.domain.model.Perfil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PerfilMapper {

    public PerfilModel toPerfilModel(Perfil perfil){
        return PerfilModel.builder()
                .id(perfil.getId())
                .descricao(perfil.getDescricao())
                .autoriedade(perfil.getAutoriedade())
                .build();
    }

    public List<PerfilModel> toPerfisModels(List<Perfil> perfis){
        return perfis.stream().map(this::toPerfilModel).toList();
    }

    public Perfil toPerfil(PerfilInputModel input){
        return Perfil.builder()
                .id(input.getId())
                .build();
    }

    public List<Perfil> toPerfis(List<PerfilInputModel> perfis){
        List<Perfil> novosPerfis = new ArrayList<>();
        perfis.forEach(perfil -> novosPerfis.add(toPerfil(perfil)));
        return novosPerfis;
    }
}
