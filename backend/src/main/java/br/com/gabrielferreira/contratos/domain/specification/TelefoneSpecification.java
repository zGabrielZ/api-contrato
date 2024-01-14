package br.com.gabrielferreira.contratos.domain.specification;

import br.com.gabrielferreira.contratos.domain.model.Telefone;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.filter.TelefoneFilterModel;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.*;

@RequiredArgsConstructor
public class TelefoneSpecification implements Specification<Telefone> {

    private final Long idUsuario;

    private final TelefoneFilterModel filtro;

    @Override
    public Predicate toPredicate(Root<Telefone> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Join<Telefone, Usuario> joinUsuario = root.join("usuario");

        List<Predicate> predicates = new ArrayList<>();

        if(filtro.isIdExistente()){
            Predicate predicateId = criteriaBuilder.equal(root.get("id"), filtro.getId());
            predicates.add(predicateId);
        }

        if(filtro.isDddExistente()){
            Predicate predicateDdd = criteriaBuilder.equal(root.get("ddd"), filtro.getDdd());
            predicates.add(predicateDdd);
        }

        if(filtro.isNumeroExistente()){
            Predicate predicateNumero = criteriaBuilder.equal(root.get("numero"), filtro.getNumero());
            predicates.add(predicateNumero);
        }

        if(filtro.isDescricaoExistente()){
            Predicate predicateDescricao = criteriaBuilder.like(root.get("descricao"), "%".concat(filtro.getDescricao()).concat("%"));
            predicates.add(predicateDescricao);
        }

        if(filtro.isTipoTelefoneExistente()){
            Predicate predicateTipoTelefone = criteriaBuilder.equal(root.get("tipoTelefone"), filtro.getTipoTelefone());
            predicates.add(predicateTipoTelefone);
        }

        if(filtro.isDataCadastroExistente()){
            ZonedDateTime dataCadastro = filtro.getDataCadastro().atStartOfDay(UTC);
            Predicate predicateDataCadastro = criteriaBuilder.greaterThanOrEqualTo(root.get("dataCadastro"), dataCadastro);
            predicates.add(predicateDataCadastro);
        }

        if(filtro.isDataAtualizacaoExistente()){
            ZonedDateTime dataAtualizacao = filtro.getDataAtualizacao().atStartOfDay(UTC);
            Predicate predicateDataAtualizacao = criteriaBuilder.greaterThanOrEqualTo(root.get("dataAtualizacao"), dataAtualizacao);
            predicates.add(predicateDataAtualizacao);
        }

        Predicate predicateIdUsuario = criteriaBuilder.equal(joinUsuario.get("id"), idUsuario);
        predicates.add(predicateIdUsuario);

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }
}
