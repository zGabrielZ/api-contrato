package br.com.gabrielferreira.contratos.domain.specification;

import br.com.gabrielferreira.contratos.domain.model.HistoricoSaldo;
import br.com.gabrielferreira.contratos.domain.model.Saldo;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.filter.HistoricoSaldoFilterModel;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.UTC;

@RequiredArgsConstructor
public class HistoricoSaldoSpecification implements Specification<HistoricoSaldo> {

    private final Long idUsuario;

    private final HistoricoSaldoFilterModel filtro;

    @Override
    public Predicate toPredicate(Root<HistoricoSaldo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Saldo, Usuario> joinUsuario = root.join("usuario");

        if(filtro.isIdExistente()){
            Predicate predicateId = criteriaBuilder.equal(root.get("id"), filtro.getId());
            predicates.add(predicateId);
        }

        if(filtro.isValorAtualInicialExistente()){
            Predicate predicateValorInicial = criteriaBuilder.greaterThanOrEqualTo(root.get("valorAtual"), filtro.getValorAtualInicial());
            predicates.add(predicateValorInicial);
        }

        if(filtro.isValorAtualFinalExistente()){
            Predicate predicateValorFinal = criteriaBuilder.lessThanOrEqualTo(root.get("valorAtual"), filtro.getValorAtualFinal());
            predicates.add(predicateValorFinal);
        }

        if(filtro.isQuantidadeInformadaInicialExistente()){
            Predicate predicateQuantidadeInicial = criteriaBuilder.greaterThanOrEqualTo(root.get("quantidadeInicial"), filtro.getQuantidadeInformadaInicial());
            predicates.add(predicateQuantidadeInicial);
        }

        if(filtro.isQuantidadeInformadaFinalExistente()){
            Predicate predicateQuantidadeFinal = criteriaBuilder.lessThanOrEqualTo(root.get("quantidadeInicial"), filtro.getQuantidadeInformadaFinal());
            predicates.add(predicateQuantidadeFinal);
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
