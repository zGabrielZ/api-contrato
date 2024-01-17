package br.com.gabrielferreira.contratos.domain.service.validator;

import br.com.gabrielferreira.contratos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.contratos.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SaqueValidator {

    private final UsuarioRepository usuarioRepository;

    public void validarSaldoTotalUsuario(Long idUsuario, BigDecimal valorSaque){
        Optional<BigDecimal> valorTotalUsuario = usuarioRepository.buscarSaldoTotalAtual(idUsuario);
        if(valorTotalUsuario.isEmpty()){
            throw new RegraDeNegocioException("Não é possível sacar valor nenhum pois o usuário não depositou nenhum valor");
        }

        BigDecimal valor = valorTotalUsuario.get();
        if(valorSaque.compareTo(valor) > 0){
            throw new RegraDeNegocioException("Não é possível sacar valor nenhum pois a quantia informada é maior que o seu déposito");
        }
    }
}
