package br.com.gabrielferreira.contratos.api.mapper;

import br.com.gabrielferreira.contratos.api.model.SaldoModel;
import br.com.gabrielferreira.contratos.api.model.input.SaldoInputModel;
import br.com.gabrielferreira.contratos.api.model.params.SaldoParamsModel;
import br.com.gabrielferreira.contratos.domain.model.Saldo;
import br.com.gabrielferreira.contratos.domain.repository.filter.SaldoFilterModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.toFusoPadraoSistema;

@Component
public class SaldoMapper {

    public Saldo toSaldo(SaldoInputModel saldoInputModel){
        return Saldo.builder()
                .valor(saldoInputModel.getValor())
                .build();
    }

    public SaldoModel toSaldoModel(Saldo saldo){
        return SaldoModel.builder()
                .id(saldo.getId())
                .valor(saldo.getValor())
                .tipoMovimentacao(saldo.getTipoMovimentacao().name())
                .dataCadastro(toFusoPadraoSistema(saldo.getDataCadastro()))
                .dataAtualizacao(toFusoPadraoSistema(saldo.getDataAtualizacao()))
                .build();
    }

    public SaldoFilterModel toSaldoFilterModel(SaldoParamsModel saldoParamsModel){
        return SaldoFilterModel.builder()
                .id(saldoParamsModel.getId())
                .valorInicial(saldoParamsModel.getValorInicial())
                .valorFinal(saldoParamsModel.getValorFinal())
                .dataCadastro(saldoParamsModel.getDataCadastro())
                .dataAtualizacao(saldoParamsModel.getDataAtualizacao())
                .build();
    }

    public Page<SaldoModel> toSaldosModels(Page<Saldo> saldos){
        return saldos.map(this::toSaldoModel);
    }
}
