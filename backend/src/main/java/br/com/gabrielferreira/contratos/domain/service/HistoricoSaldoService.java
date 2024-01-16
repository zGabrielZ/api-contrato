package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.model.HistoricoSaldo;
import br.com.gabrielferreira.contratos.domain.model.Saldo;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.model.enums.TipoMovimentacaoEnum;
import br.com.gabrielferreira.contratos.domain.repository.HistoricoSaldoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class HistoricoSaldoService {

    private final HistoricoSaldoRepository historicoSaldoRepository;

    @Transactional
    public void adicionarHistorico(Saldo saldo, Usuario usuario){
        BigDecimal quantidadeInformada = saldo.getValor();
        if(TipoMovimentacaoEnum.isSaque(saldo.getTipoMovimentacao())){
            quantidadeInformada = quantidadeInformada.multiply(BigDecimal.valueOf(-1.0));
        }

        HistoricoSaldo historicoSaldo = HistoricoSaldo.builder()
                .saldo(saldo)
                .usuario(usuario)
                .valorAtual(usuario.getSaldoTotal().getValor())
                .quantidadeInformada(quantidadeInformada)
                .build();
        historicoSaldoRepository.save(historicoSaldo);
    }
}
