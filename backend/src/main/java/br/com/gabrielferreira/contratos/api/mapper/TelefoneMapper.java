package br.com.gabrielferreira.contratos.api.mapper;

import br.com.gabrielferreira.contratos.api.model.TelefoneModel;
import br.com.gabrielferreira.contratos.api.model.input.TelefoneInputModel;
import br.com.gabrielferreira.contratos.api.model.params.TelefoneParamsModel;
import br.com.gabrielferreira.contratos.domain.model.Telefone;
import br.com.gabrielferreira.contratos.domain.model.enums.TipoTelefoneEnum;
import br.com.gabrielferreira.contratos.domain.repository.filter.TelefoneFilterModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.*;

@Component
public class TelefoneMapper {

    public Telefone toTelefone(TelefoneInputModel telefone){
        return Telefone.builder()
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .descricao(telefone.getDescricao())
                .tipoTelefone(TipoTelefoneEnum.valueOf(telefone.getTipoTelefone()))
                .build();
    }

    public Set<Telefone> toTelefones(Set<TelefoneInputModel> telefones){
        Set<Telefone> novosTelefones = new HashSet<>();
        telefones.forEach(telefone -> novosTelefones.add(toTelefone(telefone)));
        return novosTelefones;
    }

    public TelefoneModel toTelefoneModel(Telefone telefone){
        return TelefoneModel.builder()
                .id(telefone.getId())
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .descricao(telefone.getDescricao())
                .tipoTelefone(telefone.getTipoTelefone().name())
                .dataCadastro(toFusoPadraoSistema(telefone.getDataCadastro()))
                .dataAtualizacao(toFusoPadraoSistema(telefone.getDataAtualizacao()))
                .build();
    }

    public Set<TelefoneModel> toTelefonesModels(Set<Telefone> telefones){
        Set<TelefoneModel> telefoneModels = new HashSet<>();
        telefones.forEach(telefone -> telefoneModels.add(toTelefoneModel(telefone)));
        return telefoneModels;
    }

    public Page<TelefoneModel> toTelefonesModels(Page<Telefone> telefones){
        return telefones.map(this::toTelefoneModel);
    }

    public TelefoneFilterModel toTelefoneFilterModel(TelefoneParamsModel telefoneParamsModel){
        return TelefoneFilterModel.builder()
                .id(telefoneParamsModel.getId())
                .ddd(telefoneParamsModel.getDdd())
                .numero(telefoneParamsModel.getNumero())
                .descricao(telefoneParamsModel.getDescricao())
                .tipoTelefone(telefoneParamsModel.getTipoTelefone())
                .dataCadastro(telefoneParamsModel.getDataCadastro())
                .dataAtualizacao(telefoneParamsModel.getDataAtualizacao())
                .build();
    }
}
