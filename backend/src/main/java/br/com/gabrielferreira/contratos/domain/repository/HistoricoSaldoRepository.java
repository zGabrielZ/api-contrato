package br.com.gabrielferreira.contratos.domain.repository;

import br.com.gabrielferreira.contratos.domain.model.HistoricoSaldo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoricoSaldoRepository extends JpaRepository<HistoricoSaldo, Long> {

    Page<HistoricoSaldo> findAll(Specification<HistoricoSaldo> specification, Pageable pageable);

    @Query("SELECT h FROM HistoricoSaldo h " +
            "JOIN FETCH h.usuario u " +
            "JOIN FETCH u.perfis p " +
            "WHERE u.id = :idUsuario and h.id = :id")
    Optional<HistoricoSaldo> buscarHistorico(@Param("idUsuario") Long idUsuario, @Param("id") Long id);
}
