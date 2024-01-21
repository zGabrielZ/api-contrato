package br.com.gabrielferreira.contratos.domain.specification;

import br.com.gabrielferreira.contratos.domain.model.Perfil;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.filter.PerfilFilterModel;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PerfilSpecification implements Specification<Perfil> {

    private final Long idUsuario;

    private final PerfilFilterModel filtro;

    @Override
    public Predicate toPredicate(Root<Perfil> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Join<Perfil, Usuario> joinUsuario = root.join("usuarios");

        List<Predicate> predicates = new ArrayList<>();

        if(filtro.isIdExistente()){
            Predicate predicateId = criteriaBuilder.equal(root.get("id"), filtro.getId());
            predicates.add(predicateId);
        }

        if(filtro.isDescricaoExistente()){
            Predicate predicateDescricao = criteriaBuilder.like(root.get("descricao"), "%".concat(filtro.getDescricao()).concat("%"));
            predicates.add(predicateDescricao);
        }

        if(filtro.isAutoriedadeExistente()){
            Predicate predicateAutoriedade = criteriaBuilder.equal(root.get("autoriedade"), filtro.getAutoriedade());
            predicates.add(predicateAutoriedade);
        }

        Predicate predicateIdUsuario = criteriaBuilder.equal(joinUsuario.get("id"), idUsuario);
        predicates.add(predicateIdUsuario);

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }
}
