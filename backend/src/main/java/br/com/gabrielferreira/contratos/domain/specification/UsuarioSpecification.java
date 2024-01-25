package br.com.gabrielferreira.contratos.domain.specification;

import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.filter.UsuarioFilterModel;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.FUSO_HORARIO_PADRAO_SISTEMA;

@RequiredArgsConstructor
public class UsuarioSpecification implements Specification<Usuario> {

    private static final String SALDO_TOTAL = "saldoTotal";

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

        if(filtro.isSaldoTotalInicialExistente() && filtro.isSaldoTotalFinalExistente()){
            Predicate predicateSaldoTotalInicial = criteriaBuilder.greaterThanOrEqualTo(root.get(SALDO_TOTAL).get("valor"), filtro.getSaldoTotalInicial());
            Predicate predicateSaldoTotalFinal = criteriaBuilder.lessThanOrEqualTo(root.get(SALDO_TOTAL).get("valor"), filtro.getSaldoTotalFinal());

            Predicate predicateSaldoTotal = criteriaBuilder.and(predicateSaldoTotalInicial, predicateSaldoTotalFinal);
            predicates.add(predicateSaldoTotal);
        }

        if(filtro.isDataCadastroExistente()){
            LocalDate dataCadastro = filtro.getDataCadastro();

            ZonedDateTime dataCadastroInicio = ZonedDateTime.of(dataCadastro, LocalTime.of(0, 0, 0), FUSO_HORARIO_PADRAO_SISTEMA);
            ZonedDateTime dataCadastroFim = ZonedDateTime.of(dataCadastro, LocalTime.of(23, 59, 59), FUSO_HORARIO_PADRAO_SISTEMA);

            Predicate predicateDataCadastroInicio = criteriaBuilder.greaterThanOrEqualTo(root.get("dataCadastro"), dataCadastroInicio);
            Predicate predicateDataCadastroFim = criteriaBuilder.lessThanOrEqualTo(root.get("dataCadastro"), dataCadastroFim);

            Predicate predicateDataCadastro = criteriaBuilder.and(predicateDataCadastroInicio, predicateDataCadastroFim);
            predicates.add(predicateDataCadastro);
        }

        if(filtro.isDataAtualizacaoExistente()){
            LocalDate dataAtualizacao = filtro.getDataAtualizacao();

            ZonedDateTime dataAtualizacaoInicio = ZonedDateTime.of(dataAtualizacao, LocalTime.of(0, 0, 0), FUSO_HORARIO_PADRAO_SISTEMA);
            ZonedDateTime dataAtualizacaoFim = ZonedDateTime.of(dataAtualizacao, LocalTime.of(23, 59, 59), FUSO_HORARIO_PADRAO_SISTEMA);

            Predicate predicateDataAtualizacaoInicio = criteriaBuilder.greaterThanOrEqualTo(root.get("dataAtualizacao"), dataAtualizacaoInicio);
            Predicate predicateDataAtualizacaoFim = criteriaBuilder.lessThanOrEqualTo(root.get("dataAtualizacao"), dataAtualizacaoFim);

            Predicate predicateDataAtualizacao = criteriaBuilder.and(predicateDataAtualizacaoInicio, predicateDataAtualizacaoFim);
            predicates.add(predicateDataAtualizacao);
        }

        if (currentQueryIsCountRecords(query)) {
            root.join("perfis", JoinType.INNER);
            root.join(SALDO_TOTAL, JoinType.LEFT);
        } else {
            root.fetch("perfis", JoinType.INNER);
            root.fetch(SALDO_TOTAL, JoinType.LEFT);
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    private boolean currentQueryIsCountRecords(CriteriaQuery<?> criteriaQuery) {
        return criteriaQuery.getResultType() == Long.class || criteriaQuery.getResultType() == long.class;
    }
}
