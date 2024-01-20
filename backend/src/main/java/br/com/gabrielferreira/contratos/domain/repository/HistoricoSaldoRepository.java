package br.com.gabrielferreira.contratos.domain.repository;

import br.com.gabrielferreira.contratos.domain.model.HistoricoSaldo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoSaldoRepository extends JpaRepository<HistoricoSaldo, Long> {

    Page<HistoricoSaldo> findAll(Specification<HistoricoSaldo> specification, Pageable pageable);
}
