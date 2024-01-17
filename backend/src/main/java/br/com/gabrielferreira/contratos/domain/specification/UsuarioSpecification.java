package br.com.gabrielferreira.contratos.domain.specification;

import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.filter.UsuarioFilterModel;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.UTC;

@RequiredArgsConstructor
public class UsuarioSpecification implements Specification<Usuario> {

    private final UsuarioFilterModel filtro;

    @Override
    public Predicate toPredicate(Root<Usuario> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if(filtro.isIdExistente()){
            Predicate predicateId = criteriaBuilder.equal(root.get("id"), filtro.getId());
            predicates.add(predicateId);
        }

        if(filtro.isNomeExistente()){
            Predicate predicateNome = criteriaBuilder.like(root.get("nome"), "%".concat(filtro.getNome()).concat("%"));
            predicates.add(predicateNome);
        }

        if(filtro.isSobrenomeExistente()){
            Predicate predicateSobrenome = criteriaBuilder.like(root.get("sobrenome"), "%".concat(filtro.getSobrenome()).concat("%"));
            predicates.add(predicateSobrenome);
        }

        if(filtro.isEmailExistente()){
            Predicate predicateEmail = criteriaBuilder.equal(root.get("email"), filtro.getEmail());
            predicates.add(predicateEmail);
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

        if (currentQueryIsCountRecords(query)) {
            root.join("perfis", JoinType.INNER);
            root.join("saldoTotal", JoinType.LEFT);
        } else {
            root.fetch("perfis", JoinType.INNER);
            root.fetch("saldoTotal", JoinType.LEFT);
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    private boolean currentQueryIsCountRecords(CriteriaQuery<?> criteriaQuery) {
        return criteriaQuery.getResultType() == Long.class || criteriaQuery.getResultType() == long.class;
    }
}
