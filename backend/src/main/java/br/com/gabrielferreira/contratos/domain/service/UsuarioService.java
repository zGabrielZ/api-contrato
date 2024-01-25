package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.contratos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.contratos.domain.model.Perfil;
import br.com.gabrielferreira.contratos.domain.model.SaldoTotalUsuario;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.UsuarioRepository;
import br.com.gabrielferreira.contratos.domain.repository.filter.UsuarioFilterModel;
import br.com.gabrielferreira.contratos.domain.service.validator.UsuarioValidator;
import br.com.gabrielferreira.contratos.domain.specification.UsuarioSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioValidator usuarioValidator;

    @Transactional
    public Usuario cadastrarUsuario(Usuario usuario){
        usuarioValidator.validarCampos(usuario);
        usuarioValidator.validarEmail(usuario.getEmail(), usuario.getId());
        usuarioValidator.validarPerfil(usuario);

        SaldoTotalUsuario saldoTotalUsuario = SaldoTotalUsuario.builder()
                .valor(BigDecimal.ZERO)
                .build();
        usuario.setSaldoTotal(saldoTotalUsuario);

        usuario = usuarioRepository.save(usuario);
        return usuario;
    }

    public Usuario buscarUsuarioPorId(Long id){
        return usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado"));
    }

    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario usuario){
        Usuario usuarioEncontrado = buscarUsuarioPorId(id);

        usuarioValidator.validarCampos(usuario);
        usuarioValidator.validarEmail(usuario.getEmail(), usuarioEncontrado.getId());
        usuarioValidator.validarPerfil(usuario);

        preencherCamposUsuario(usuarioEncontrado, usuario);

        usuarioEncontrado = usuarioRepository.save(usuarioEncontrado);
        return usuarioEncontrado;
    }

    public boolean isUsuarioExistente(Long id){
        return usuarioRepository.buscarUsuarioExistente(id);
    }

    @Transactional
    public void deletarUsuarioPorId(Long id){
        Usuario usuario = buscarUsuarioPorId(id);
        usuarioRepository.delete(usuario);
    }

    public Page<Usuario> buscarUsuarios(Pageable pageable, UsuarioFilterModel filtro){
        if(filtro.isSaldoTotalInicialExistente() && !filtro.isSaldoTotalFinalExistente()){
            throw new RegraDeNegocioException("É necessário informar o saldo total final");
        }

        if(!filtro.isSaldoTotalInicialExistente() && filtro.isSaldoTotalFinalExistente()){
            throw new RegraDeNegocioException("É necessário informar o saldo total inicial");
        }

        if(filtro.isSaldoTotalInicialExistente() && filtro.isSaldoTotalFinalExistente()
            && filtro.getSaldoTotalInicial().compareTo(filtro.getSaldoTotalFinal()) > 0){
            throw new RegraDeNegocioException("Saldo total inicial é maior que o saldo total final");
        }

        pageable = verificarSaldoTotalOrderInformado(pageable);

        return usuarioRepository.findAll(new UsuarioSpecification(filtro), pageable);
    }

    private void preencherCamposUsuario(Usuario usuarioEncontrado, Usuario usuario){
        usuarioEncontrado.setNome(usuario.getNome());
        usuarioEncontrado.setSobrenome(usuario.getSobrenome());
        usuarioEncontrado.setEmail(usuario.getEmail());

        removerPerfisNaoExistentes(usuarioEncontrado.getPerfis(), usuario.getPerfis());
        incluirNovosPerfis(usuarioEncontrado.getPerfis(), usuario.getPerfis());
    }

    private void removerPerfisNaoExistentes(List<Perfil> perfisExistentes, List<Perfil> novosPerfis){
        perfisExistentes.removeIf(perfisExistente -> perfisExistente.isNaoContemPerfil(novosPerfis));
    }

    private void incluirNovosPerfis(List<Perfil> perfisExistentes, List<Perfil> novosPerfis){
        novosPerfis.forEach(novoPerfil -> {
            if(novoPerfil.isNaoContemPerfil(perfisExistentes)){
                perfisExistentes.add(novoPerfil);
            }
        });
    }

    private Pageable verificarSaldoTotalOrderInformado(Pageable pageable){
        List<Sort.Order> sort = pageable.getSort().stream()
                        .map(order -> {
                            if(order.getProperty().equals("saldoTotal")){
                                return new Sort.Order(order.getDirection(), "saldoTotal.valor");
                            } else {
                                return order;
                            }
                        }).toList();
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sort));
        return pageable;
    }
}
