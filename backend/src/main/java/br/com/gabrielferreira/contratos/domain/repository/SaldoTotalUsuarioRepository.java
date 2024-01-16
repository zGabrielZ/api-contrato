package br.com.gabrielferreira.contratos.domain.repository;

import br.com.gabrielferreira.contratos.domain.model.SaldoTotalUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaldoTotalUsuarioRepository extends JpaRepository<SaldoTotalUsuario, Long> {
}
