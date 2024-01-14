package br.com.gabrielferreira.contratos.domain.service.validator;

import br.com.gabrielferreira.contratos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.contratos.domain.model.Telefone;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

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

    public void validarTelefonesDuplicados(List<Telefone> telefones){
        telefones.forEach(telefone -> {
            int duplicados = Collections.frequency(telefones, telefone);

            if(duplicados > 1){
                throw new RegraDeNegocioException("Não vai ser possível cadastrar este usuário pois tem telefones duplicados ou mais de duplicados");
            }
        });
    }

    public void validarTelefones(List<Telefone> telefones){
        telefones.forEach(telefone -> {
            validarCampos(telefone);
            validarTipoTelefone(telefone);
        });
        validarTelefonesDuplicados(telefones);
    }
}
