package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.model.Saldo;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.model.enums.TipoMovimentacaoEnum;
import br.com.gabrielferreira.contratos.domain.repository.SaldoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepositoService {

    private final SaldoRepository saldoRepository;

    private final UsuarioService usuarioService;

    private final SaldoTotalUsuarioService saldoTotalUsuarioService;

    private final HistoricoSaldoService historicoSaldoService;

    @Transactional
    public Saldo depositar(Long idUsuario, Saldo saldo){
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);

        saldo.setUsuario(usuario);
        saldo.setTipoMovimentacao(TipoMovimentacaoEnum.DEPOSITO);

        saldo = saldoRepository.save(saldo);
        saldoTotalUsuarioService.adicionarSaldoTotalUsuario(usuario, saldo.getValor());
        historicoSaldoService.adicionarHistorico(saldo, usuario);
        return saldo;
    }
}
