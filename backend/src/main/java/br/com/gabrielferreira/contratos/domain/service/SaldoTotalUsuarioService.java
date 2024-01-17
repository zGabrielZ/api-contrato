package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.model.Saldo;
import br.com.gabrielferreira.contratos.domain.model.SaldoTotalUsuario;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.model.enums.TipoMovimentacaoEnum;
import br.com.gabrielferreira.contratos.domain.repository.SaldoTotalUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaldoTotalUsuarioService {

    private final SaldoTotalUsuarioRepository saldoTotalUsuarioRepository;

    @Transactional
    public void adicionarSaldoTotalUsuario(Usuario usuario, Saldo saldo){
        SaldoTotalUsuario saldoTotalUsuario;
        if(usuario.getSaldoTotal() != null){
            saldoTotalUsuario = usuario.getSaldoTotal();
            saldoTotalUsuario.setValor(saldoTotalUsuario.getValor().add(saldo.getValor()));
            saldoTotalUsuarioRepository.save(saldoTotalUsuario);
        } else if(usuario.getSaldoTotal() == null && TipoMovimentacaoEnum.isDeposito(saldo.getTipoMovimentacao())){
            saldoTotalUsuario = SaldoTotalUsuario.builder()
                    .valor(saldo.getValor())
                    .build();
            usuario.setSaldoTotal(saldoTotalUsuario);
            saldoTotalUsuarioRepository.save(saldoTotalUsuario);
        }
    }
}
