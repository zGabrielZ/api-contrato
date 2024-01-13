package br.com.gabrielferreira.contratos.domain.repository;

import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.projection.UsuarioProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u.id as id, u.email as email FROM Usuario u " +
            "WHERE u.email = :email")
    Optional<UsuarioProjection> buscarPorEmail(@Param("email") String email);
}