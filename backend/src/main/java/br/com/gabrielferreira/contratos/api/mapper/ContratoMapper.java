package br.com.gabrielferreira.contratos.api.mapper;

import br.com.gabrielferreira.contratos.api.model.ContratoModel;
import br.com.gabrielferreira.contratos.api.model.ParcelaModel;
import br.com.gabrielferreira.contratos.api.model.input.ContratoInputModel;
import br.com.gabrielferreira.contratos.domain.model.Contrato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.*;

@Component
@RequiredArgsConstructor
public class ContratoMapper {

    private final ParcelaMapper parcelaMapper;

    public Contrato toContrato(ContratoInputModel contratoInputModel){
        return Contrato.builder()
                .numero(contratoInputModel.getNumero())
                .data(contratoInputModel.getData())
                .valorTotal(contratoInputModel.getValor())
                .numeroParcelas(contratoInputModel.getNumeroDeParcelas())
                .build();
    }

    public ContratoModel toContratoModel(Contrato contrato){
        List<ParcelaModel> parcelaModels = parcelaMapper.toParcelaModels(contrato.getParcelas());


        return ContratoModel.builder()
                .id(contrato.getId())
                .numero(contrato.getNumero())
                .data(contrato.getData())
                .valor(contrato.getValorTotal())
                .numeroDeParcelas(parcelaModels.size())
                .situacao(contrato.getSituacaoContrato().name())
                .parcelas(parcelaModels)
                .dataCadastro(toFusoPadraoSistema(contrato.getDataCadastro()))
                .dataAtualizacao(toFusoPadraoSistema(contrato.getDataAtualizacao()))
                .build();
    }
}
