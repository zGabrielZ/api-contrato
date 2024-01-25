package br.com.gabrielferreira.contratos.api.mapper;

import br.com.gabrielferreira.contratos.api.model.QuantidadeTelefoneModel;
import br.com.gabrielferreira.contratos.api.model.TelefoneModel;
import br.com.gabrielferreira.contratos.api.model.input.TelefoneInputModel;
import br.com.gabrielferreira.contratos.api.model.params.TelefoneParamsModel;
import br.com.gabrielferreira.contratos.domain.model.Telefone;
import br.com.gabrielferreira.contratos.domain.model.enums.TipoTelefoneEnum;
import br.com.gabrielferreira.contratos.domain.repository.filter.TelefoneFilterModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.*;

@Component
public class TelefoneMapper {

    public Telefone toTelefone(TelefoneInputModel telefoneInputModel){
        return Telefone.builder()
                .ddd(telefoneInputModel.getDdd())
                .numero(telefoneInputModel.getNumero())
                .descricao(telefoneInputModel.getDescricao())
                .tipoTelefone(TipoTelefoneEnum.valueOf(telefoneInputModel.getTipoTelefone()))
                .build();
    }

    public TelefoneModel toTelefoneModel(Telefone telefone){
        return TelefoneModel.builder()
                .id(telefone.getId())
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .descricao(telefone.getDescricao())
                .tipoTelefone(telefone.getTipoTelefone().name())
                .tipoTelefoneDescricao(telefone.getTipoTelefone().getDescricao())
                .dataCadastro(toFusoPadraoSistema(telefone.getDataCadastro()))
                .dataAtualizacao(toFusoPadraoSistema(telefone.getDataAtualizacao()))
                .build();
    }

    public Page<TelefoneModel> toTelefonesModels(Page<Telefone> telefones){
        return telefones.map(this::toTelefoneModel);
    }

    public TelefoneFilterModel toTelefoneFilterModel(TelefoneParamsModel telefoneParamsModel){
        return TelefoneFilterModel.builder()
                .id(telefoneParamsModel.getId())
                .ddd(telefoneParamsModel.getDdd())
                .numero(telefoneParamsModel.getNumero())
                .descricao(telefoneParamsModel.getDescricao())
                .tipoTelefone(telefoneParamsModel.getTipoTelefone())
                .dataCadastro(telefoneParamsModel.getDataCadastro())
                .dataAtualizacao(telefoneParamsModel.getDataAtualizacao())
                .build();
    }

    public QuantidadeTelefoneModel toQuantidadeTelefoneModel(Long quantidade){
        return QuantidadeTelefoneModel.builder()
                .quantidadeDeTelefone(quantidade)
                .build();
    }
}
