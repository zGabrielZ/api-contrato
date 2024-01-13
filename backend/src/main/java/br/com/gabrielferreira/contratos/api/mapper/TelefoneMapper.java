package br.com.gabrielferreira.contratos.api.mapper;

import br.com.gabrielferreira.contratos.api.model.TelefoneModel;
import br.com.gabrielferreira.contratos.api.model.input.TelefoneInputModel;
import br.com.gabrielferreira.contratos.domain.model.Telefone;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.*;

@Component
public class TelefoneMapper {

    public Telefone toTelefone(TelefoneInputModel telefone){
        return Telefone.builder()
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .descricao(telefone.getDescricao())
                .tipoTelefone(telefone.getTipoTelefone())
                .build();
    }

    public List<Telefone> toTelefones(List<TelefoneInputModel> telefones){
        List<Telefone> novosTelefones = new ArrayList<>();
        telefones.forEach(telefone -> novosTelefones.add(toTelefone(telefone)));
        return novosTelefones;
    }

    public TelefoneModel toTelefoneModel(Telefone telefone){
        return TelefoneModel.builder()
                .id(telefone.getId())
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .descricao(telefone.getDescricao())
                .tipoTelefone(telefone.getTipoTelefone())
                .dataCadastro(toFusoPadraoSistema(telefone.getDataCadastro()))
                .dataAtualizacao(toFusoPadraoSistema(telefone.getDataAtualizacao()))
                .build();
    }

    public List<TelefoneModel> toTelefonesModels(List<Telefone> telefones){
        return telefones.stream().map(this::toTelefoneModel).toList();
    }
}
