package br.com.gabrielferreira.contratos.domain.service.validator;

import br.com.gabrielferreira.contratos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.ContratoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ContratoValidator {

    private final ContratoRepository contratoRepository;

    public void validarNumeroCadastrado(Long numero){
        if(isNumeroContratoExistente(numero)){
            throw new RegraDeNegocioException(String.format("Este número '%s' informado já foi cadastrado", numero));
        }
    }

    public void validarValorContratoComUsuarioSaldo(BigDecimal valor, Usuario usuario){
        BigDecimal saldoTotalUsuario = usuario.getSaldoTotal() != null ? usuario.getSaldoTotal().getValor() : BigDecimal.ZERO;
        if(valor.compareTo(saldoTotalUsuario) > 0){
            throw new RegraDeNegocioException("Valor informado no contrato é maior que o saldo total do usuário");
        }
    }

    public boolean isNumeroContratoExistente(Long numero){
        return contratoRepository.buscarContratoPorNumero(numero)
                .isPresent();
    }
}

