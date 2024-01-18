package br.com.gabrielferreira.contratos.domain.specification;

import br.com.gabrielferreira.contratos.domain.model.Saldo;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.model.enums.TipoMovimentacaoEnum;
import br.com.gabrielferreira.contratos.domain.repository.filter.SaldoFilterModel;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.UTC;

@RequiredArgsConstructor
public class SaldoSpecification implements Specification<Saldo> {

    private final Long idUsuario;

    private final SaldoFilterModel filtro;

    private final TipoMovimentacaoEnum tipoMovimentacaoEnum;

    @Override
    public Predicate toPredicate(Root<Saldo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Saldo, Usuario> joinUsuario = root.join("usuario");

        Predicate predicateTipoMovimentacao;
        if(TipoMovimentacaoEnum.isDeposito(tipoMovimentacaoEnum)){
             predicateTipoMovimentacao = criteriaBuilder.equal(root.get("tipoMovimentacao"), TipoMovimentacaoEnum.DEPOSITO);
        } else {
             predicateTipoMovimentacao = criteriaBuilder.equal(root.get("tipoMovimentacao"), TipoMovimentacaoEnum.SAQUE);
        }
        predicates.add(predicateTipoMovimentacao);

        if(filtro.isIdExistente()){
            Predicate predicateId = criteriaBuilder.equal(root.get("id"), filtro.getId());
            predicates.add(predicateId);
        }

        if(filtro.isValorInicialExistente()){
            Predicate predicateValorInicial = criteriaBuilder.greaterThanOrEqualTo(root.get("valor"), filtro.getValorInicial());
            predicates.add(predicateValorInicial);
        }

        if(filtro.isValorFinalExistente()){
            Predicate predicateValorFinal = criteriaBuilder.lessThanOrEqualTo(root.get("valor"), filtro.getValorFinal());
            predicates.add(predicateValorFinal);
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
