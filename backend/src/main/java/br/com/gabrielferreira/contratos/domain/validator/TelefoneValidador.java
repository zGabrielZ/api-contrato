package br.com.gabrielferreira.contratos.domain.validator;

import br.com.gabrielferreira.contratos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.contratos.domain.model.Telefone;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class TelefoneValidador {

    public void validarCampos(Telefone telefone){
        telefone.setDdd(telefone.getDdd().trim());
        telefone.setNumero(telefone.getNumero().trim());
        if(StringUtils.isNotBlank(telefone.getDescricao())){
            telefone.setDescricao(telefone.getDescricao().trim());
        }
    }

    public void validarTipoTelefone(Telefone telefone){
        if(telefone.isResidencial() && telefone.getNumero().length() == 9){
            throw new RegraDeNegocioException(String.format("Este número informado %s não é um celular", telefone.getNumeroFormatado()));
        } else if(telefone.isCelular() && telefone.getNumero().length() == 8){
            throw new RegraDeNegocioException(String.format("Este número informado %s não é um telefone residencial", telefone.getNumeroFormatado()));
        }
    }
}
