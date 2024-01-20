package br.com.gabrielferreira.contratos.api.mapper;

import br.com.gabrielferreira.contratos.api.model.HistoricoSaldoModel;
import br.com.gabrielferreira.contratos.api.model.params.HistoricoSaldoParamsModel;
import br.com.gabrielferreira.contratos.domain.model.HistoricoSaldo;
import br.com.gabrielferreira.contratos.domain.repository.filter.HistoricoSaldoFilterModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.*;

@Component
public class HistoricoSaldoMapper {

    public HistoricoSaldoFilterModel toHistoricoSaldoFilterModel(HistoricoSaldoParamsModel historicoSaldoParamsModel){
        return HistoricoSaldoFilterModel.builder()
                .id(historicoSaldoParamsModel.getId())
                .valorAtualInicial(historicoSaldoParamsModel.getValorAtualInicial())
                .valorAtualFinal(historicoSaldoParamsModel.getValorAtualFinal())
                .quantidadeInformadaInicial(historicoSaldoParamsModel.getQuantidadeInformadaInicial())
                .quantidadeInformadaFinal(historicoSaldoParamsModel.getQuantidadeInformadaFinal())
                .dataCadastro(historicoSaldoParamsModel.getDataCadastro())
                .dataAtualizacao(historicoSaldoParamsModel.getDataAtualizacao())
                .build();
    }

    public HistoricoSaldoModel toHistoricoSaldoModel(HistoricoSaldo historicoSaldo){
        return HistoricoSaldoModel.builder()
                .id(historicoSaldo.getId())
                .valorAtual(historicoSaldo.getValorAtual())
                .quantidadeInformada(historicoSaldo.getQuantidadeInformada())
                .dataCadastro(toFusoPadraoSistema(historicoSaldo.getDataCadastro()))
                .dataAtualizacao(toFusoPadraoSistema(historicoSaldo.getDataAtualizacao()))
                .build();
    }

    public Page<HistoricoSaldoModel> toHistoricoSaldoModels(Page<HistoricoSaldo> historicoSaldos){
        return historicoSaldos.map(this::toHistoricoSaldoModel);
    }
}
