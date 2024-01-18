package br.com.gabrielferreira.contratos.domain.repository;

import br.com.gabrielferreira.contratos.domain.model.Saldo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaldoRepository extends JpaRepository<Saldo, Long> {

    Page<Saldo> findAll(Specification<Saldo> specification, Pageable pageable);
}
