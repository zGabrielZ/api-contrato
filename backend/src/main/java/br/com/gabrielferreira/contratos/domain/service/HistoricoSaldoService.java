package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.contratos.domain.model.HistoricoSaldo;
import br.com.gabrielferreira.contratos.domain.model.Saldo;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.HistoricoSaldoRepository;
import br.com.gabrielferreira.contratos.domain.repository.filter.HistoricoSaldoFilterModel;
import br.com.gabrielferreira.contratos.domain.specification.HistoricoSaldoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HistoricoSaldoService {

    private final HistoricoSaldoRepository historicoSaldoRepository;

    private final UsuarioService usuarioService;

    @Transactional
    public void adicionarHistorico(Saldo saldo, Usuario usuario){
        HistoricoSaldo historicoSaldo = HistoricoSaldo.builder()
                .saldo(saldo)
                .usuario(usuario)
                .valorAtual(usuario.getSaldoTotal().getValor())
                .quantidadeInformada(saldo.getValor())
                .build();
        historicoSaldoRepository.save(historicoSaldo);
    }

    public Page<HistoricoSaldo> buscarHistoricoPorUsuario(Long idUsuario, HistoricoSaldoFilterModel filtro, Pageable pageable){
        if(!usuarioService.isUsuarioExistente(idUsuario)){
            throw new NaoEncontradoException("Usuário não encontrado");
        }
        return historicoSaldoRepository.findAll(new HistoricoSaldoSpecification(idUsuario, filtro), pageable);
    }
}
