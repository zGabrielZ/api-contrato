package br.com.gabrielferreira.contratos.domain.repository;

import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.projection.UsuarioProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u.id as id, u.email as email FROM Usuario u " +
            "WHERE u.email = :email")
    Optional<UsuarioProjection> buscarPorEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuario u " +
            "JOIN FETCH u.perfis p " +
            "WHERE u.id = :id")
    Optional<Usuario> buscarPorId(@Param("id") Long id);

    @Query("SELECT CASE WHEN COUNT(u.id) > 0 THEN true ELSE false END FROM Usuario u " +
            "WHERE u.id = :id")
    Boolean buscarUsuarioExistente(@Param("id") Long id);

    Page<Usuario> findAll(Specification<Usuario> specification, Pageable pageable);

    @Query("SELECT saldoTotal.valor as valor FROM Usuario u " +
            "JOIN u.saldoTotal saldoTotal " +
            "WHERE u.id = :idUsuario")
    Optional<BigDecimal> buscarSaldoTotalAtual(@Param("idUsuario") Long idUsuario);
}
