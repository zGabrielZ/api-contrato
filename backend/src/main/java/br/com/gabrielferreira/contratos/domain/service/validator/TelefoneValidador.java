package br.com.gabrielferreira.contratos.domain.service.validator;

import br.com.gabrielferreira.contratos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.contratos.domain.model.Telefone;
import br.com.gabrielferreira.contratos.domain.repository.TelefoneRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TelefoneValidador {

    private final TelefoneRepository telefoneRepository;

    public void validarCampos(Telefone telefone){
        telefone.setDdd(telefone.getDdd().trim());
        telefone.setNumero(telefone.getNumero().trim());
        if(StringUtils.isNotBlank(telefone.getDescricao())){
            telefone.setDescricao(telefone.getDescricao().trim());
        }
    }

    public void validarTipoTelefone(Telefone telefone){
        if(telefone.isResidencial() && telefone.getNumero().length() == 9){
            throw new RegraDeNegocioException(String.format("Este número informado %s está com o tipo de telefone residencial", telefone.getNumeroFormatado()));
        } else if(telefone.isCelular() && telefone.getNumero().length() == 8){
            throw new RegraDeNegocioException(String.format("Este número informado %s está com o tipo de telefone celular", telefone.getNumeroFormatado()));
        }
    }

    public void validarTelefoneExistente(Long idTelefone, Telefone telefone){
        if(isTelefoneExistentePorUsuario(idTelefone, telefone)){
            throw new RegraDeNegocioException(String.format("Este telefone '%s' já foi cadastrado", telefone.getNumeroFormatado()));
        }
    }

    public boolean isTelefoneExistentePorUsuario(Long idTelefone, Telefone telefone){
        if(idTelefone == null){
            return telefoneRepository.buscarPorTelefone(telefone.getDdd(), telefone.getNumero(), telefone.getTipoTelefone())
                    .isPresent();
        } else {
            return telefoneRepository.buscarPorTelefone(telefone.getDdd(), telefone.getNumero(), telefone.getTipoTelefone())
                    .filter(id -> !id.equals(idTelefone))
                    .isPresent();
        }
    }
}
