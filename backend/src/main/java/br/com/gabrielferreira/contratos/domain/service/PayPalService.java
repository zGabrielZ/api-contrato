package br.com.gabrielferreira.contratos.domain.service;

import java.math.BigDecimal;

public class PayPalService implements PagamentoOnline {

    @Override
    public BigDecimal getJuroSimples() {
        return BigDecimal.valueOf(0.01);
    }

    @Override
    public BigDecimal getTaxaPagamento() {
        return BigDecimal.valueOf(0.02);
    }

    @Override
    public BigDecimal calcularJuroSimples(BigDecimal valorParcela, Integer valorNumeroParcela){
        BigDecimal valorParcelado = getJuroSimples().multiply(BigDecimal.valueOf(valorNumeroParcela));
        return valorParcela.add(valorParcela.multiply(valorParcelado));
    }

    @Override
    public BigDecimal calcularTaxaPagamento(BigDecimal valorParcela){
        return valorParcela.add(valorParcela.multiply(getTaxaPagamento()));
    }
}
