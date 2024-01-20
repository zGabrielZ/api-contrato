package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.contratos.domain.model.Contrato;
import br.com.gabrielferreira.contratos.domain.model.Parcela;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.model.enums.SituacaoContratoEnum;
import br.com.gabrielferreira.contratos.domain.model.enums.SituacaoParcelaEnum;
import br.com.gabrielferreira.contratos.domain.repository.ContratoRepository;
import br.com.gabrielferreira.contratos.domain.service.validator.ContratoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratoService {

    private final ContratoRepository contratoRepository;

    private final UsuarioService usuarioService;

    private final ContratoValidator contratoValidator;

    private PagamentoOnline pagamentoOnline;

    @Value("${pagamento.online.servico}")
    private String pagamentoOnlineServico;

    @Transactional
    public Contrato cadastrarContrato(Long idUsuario, Contrato contrato){
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        contratoValidator.validarNumeroCadastrado(contrato.getNumero());
        contratoValidator.validarValorContratoComUsuarioSaldo(contrato.getValorTotal(), usuario);

        verificarServico();
        List<Parcela> parcelas = gerarParcelas(contrato);

        contrato.setParcelas(parcelas);
        contrato.setUsuario(usuario);
        contrato.setSituacaoContrato(SituacaoContratoEnum.INICIADO);
        contrato = contratoRepository.save(contrato);
        return contrato;
    }

    private List<Parcela> gerarParcelas(Contrato contrato){
        BigDecimal valorTotal = contrato.getValorTotal();
        BigDecimal valorTotalParcelado = valorTotal;
        LocalDate data = contrato.getData();
        int numeroParcelas = contrato.getNumeroParcelas();

        List<Parcela> parcelas = new ArrayList<>();
        for (int i = 0; i < numeroParcelas; i++) {
            LocalDate dataParcela = data.plusMonths(i);

            BigDecimal valorParcela = valorTotal.divide(BigDecimal.valueOf(numeroParcelas), RoundingMode.HALF_EVEN);
            valorTotalParcelado = valorTotalParcelado.subtract(valorParcela);

            int numeroValorParcela = i + 1;
            valorParcela = pagamentoOnline.calcularJuroSimples(valorParcela, numeroValorParcela);
            valorParcela = pagamentoOnline.calcularTaxaPagamento(valorParcela);

            Parcela parcela = Parcela.builder()
                    .data(dataParcela)
                    .valor(valorParcela)
                    .situacaoParcela(SituacaoParcelaEnum.EM_ANDAMENTO)
                    .contrato(contrato)
                    .build();
            parcelas.add(parcela);
        }
        return parcelas;
    }

    private void verificarServico(){
        if(pagamentoOnlineServico.equals(PayPalService.class.getSimpleName())){
            pagamentoOnline = new PayPalService();
        } else {
            throw new RegraDeNegocioException("É necessário informar o tipo de serviço para o pagamento");
        }
    }
}
