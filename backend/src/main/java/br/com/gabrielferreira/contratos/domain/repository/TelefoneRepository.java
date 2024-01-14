package br.com.gabrielferreira.contratos.domain.repository;

import br.com.gabrielferreira.contratos.domain.model.Telefone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

    Page<Telefone> findAll(Specification<Telefone> specification, Pageable pageable);

    @Query("SELECT t FROM Telefone t " +
            "JOIN FETCH t.usuario u " +
            "JOIN FETCH u.perfis p " +
            "WHERE u.id = :idUsuario and t.id = :idTelefone")
    Optional<Telefone> buscarTelefone(@Param("idUsuario") Long idUsuario, @Param("idTelefone") Long idTelefone);
}
