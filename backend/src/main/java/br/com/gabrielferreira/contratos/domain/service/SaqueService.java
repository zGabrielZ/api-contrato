package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.model.Saldo;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.model.enums.TipoMovimentacaoEnum;
import br.com.gabrielferreira.contratos.domain.repository.SaldoRepository;
import br.com.gabrielferreira.contratos.domain.service.validator.SaqueValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SaqueService {

    private final SaldoRepository saldoRepository;

    private final UsuarioService usuarioService;

    private final SaldoTotalUsuarioService saldoTotalUsuarioService;

    private final HistoricoSaldoService historicoSaldoService;

    private final SaqueValidator saqueValidator;

    @Transactional
    public Saldo saque(Long idUsuario, Saldo saldo){
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        saqueValidator.validarSaldoTotalUsuario(usuario.getId(), saldo.getValor());

        saldo.setValor(saldo.getValor().multiply(BigDecimal.valueOf(-1.0)));
        saldo.setUsuario(usuario);
        saldo.setTipoMovimentacao(TipoMovimentacaoEnum.SAQUE);

        saldo = saldoRepository.save(saldo);
        saldoTotalUsuarioService.adicionarSaldoTotalUsuario(usuario, saldo);
        historicoSaldoService.adicionarHistorico(saldo, usuario);
        return saldo;
    }
}
