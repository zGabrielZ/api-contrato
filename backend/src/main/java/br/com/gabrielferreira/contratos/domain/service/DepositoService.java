package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.contratos.domain.model.Saldo;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.model.enums.TipoMovimentacaoEnum;
import br.com.gabrielferreira.contratos.domain.repository.SaldoRepository;
import br.com.gabrielferreira.contratos.domain.repository.filter.SaldoFilterModel;
import br.com.gabrielferreira.contratos.domain.specification.SaldoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        saldoTotalUsuarioService.adicionarSaldoTotalUsuario(usuario, saldo);
        historicoSaldoService.adicionarHistorico(saldo, usuario);
        return saldo;
    }

    public Page<Saldo> buscarDepositosPaginados(Long idUsuario, SaldoFilterModel filtro, Pageable pageable){
        if(!usuarioService.isUsuarioExistente(idUsuario)){
            throw new NaoEncontradoException("Usuário não encontrado");
        }
        return saldoRepository.findAll(new SaldoSpecification(idUsuario, filtro, TipoMovimentacaoEnum.DEPOSITO), pageable);
    }

    public Saldo buscarDepositoPorId(Long idUsuario, Long id){
        return saldoRepository.buscarSaldoPorId(idUsuario, id, TipoMovimentacaoEnum.DEPOSITO)
                .orElseThrow(() -> new NaoEncontradoException("Déposito não encontrado"));
    }
}
