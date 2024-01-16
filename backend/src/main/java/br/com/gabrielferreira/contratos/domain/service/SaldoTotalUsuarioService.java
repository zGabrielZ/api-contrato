package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.model.SaldoTotalUsuario;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.SaldoTotalUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SaldoTotalUsuarioService {

    private final SaldoTotalUsuarioRepository saldoTotalUsuarioRepository;

    @Transactional
    public void adicionarSaldoTotalUsuario(Usuario usuario, BigDecimal saldo){
        SaldoTotalUsuario saldoTotalUsuario;
        if(usuario.getSaldoTotal() != null){
            saldoTotalUsuario = usuario.getSaldoTotal();
            saldoTotalUsuario.setValor(saldoTotalUsuario.getValor().add(saldo));
            saldoTotalUsuarioRepository.save(saldoTotalUsuario);
        } else {
            saldoTotalUsuario = SaldoTotalUsuario.builder()
                    .valor(saldo)
                    .build();
            usuario.setSaldoTotal(saldoTotalUsuario);
            saldoTotalUsuarioRepository.save(saldoTotalUsuario);
        }
    }
}
