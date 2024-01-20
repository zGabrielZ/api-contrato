package br.com.gabrielferreira.contratos.api.mapper;

import br.com.gabrielferreira.contratos.api.model.ParcelaModel;
import br.com.gabrielferreira.contratos.domain.model.Parcela;
import org.springframework.stereotype.Component;

import java.util.List;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.*;

@Component
public class ParcelaMapper {

    public ParcelaModel toParcelaModel(Parcela parcela){
        return ParcelaModel.builder()
                .id(parcela.getId())
                .data(parcela.getData())
                .valor(parcela.getValor())
                .situacao(parcela.getSituacaoParcela().name())
                .dataCadastro(toFusoPadraoSistema(parcela.getDataCadastro()))
                .dataAtualizacao(toFusoPadraoSistema(parcela.getDataAtualizacao()))
                .build();
    }

    public List<ParcelaModel> toParcelaModels(List<Parcela> parcelas){
        return parcelas.stream().map(this::toParcelaModel).toList();
    }
}
