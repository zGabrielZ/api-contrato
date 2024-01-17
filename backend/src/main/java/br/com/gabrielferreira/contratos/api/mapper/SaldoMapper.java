package br.com.gabrielferreira.contratos.api.mapper;

import br.com.gabrielferreira.contratos.api.model.SaldoModel;
import br.com.gabrielferreira.contratos.api.model.input.SaldoInputModel;
import br.com.gabrielferreira.contratos.domain.model.Saldo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.toFusoPadraoSistema;

@Component
@RequiredArgsConstructor
public class SaldoMapper {

    private final UsuarioMapper usuarioMapper;

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
                .usuario(usuarioMapper.toUsuarioResumidoModel(saldo.getUsuario()))
                .build();
    }
}
