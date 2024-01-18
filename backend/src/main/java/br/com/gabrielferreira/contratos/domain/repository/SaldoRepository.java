package br.com.gabrielferreira.contratos.domain.repository;

import br.com.gabrielferreira.contratos.domain.model.Saldo;
import br.com.gabrielferreira.contratos.domain.model.enums.TipoMovimentacaoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaldoRepository extends JpaRepository<Saldo, Long> {

    Page<Saldo> findAll(Specification<Saldo> specification, Pageable pageable);

    @Query("SELECT s FROM Saldo s " +
            "JOIN FETCH s.usuario u " +
            "JOIN FETCH u.perfis p " +
            "WHERE u.id = :idUsuario AND " +
            "s.id = :id AND " +
            "s.tipoMovimentacao = :tipoMovimentacao")
    Optional<Saldo> buscarSaldoPorId(@Param("idUsuario") Long idUsuario, @Param("id") Long id, @Param("tipoMovimentacao") TipoMovimentacaoEnum tipoMovimentacao);
}
