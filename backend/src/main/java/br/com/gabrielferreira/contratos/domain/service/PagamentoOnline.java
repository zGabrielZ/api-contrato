package br.com.gabrielferreira.contratos.domain.service;

import java.math.BigDecimal;

public interface PagamentoOnline {

    BigDecimal getJuroSimples();

    BigDecimal getTaxaPagamento();

    BigDecimal calcularJuroSimples(BigDecimal valorParcela, Integer valorNumeroParcela);

    BigDecimal calcularTaxaPagamento(BigDecimal valorParcela);
}
