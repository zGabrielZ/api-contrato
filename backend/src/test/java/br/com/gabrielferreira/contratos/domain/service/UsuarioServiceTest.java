package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.contratos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.repository.UsuarioRepository;
import br.com.gabrielferreira.contratos.domain.repository.filter.UsuarioFilterModel;
import br.com.gabrielferreira.contratos.domain.service.validator.UsuarioValidator;
import br.com.gabrielferreira.contratos.domain.specification.UsuarioSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static br.com.gabrielferreira.contratos.tests.UsuarioFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioValidator usuarioValidator;

    private Usuario usuario;

    private Usuario usuarioCriado;

    private Long idUsuarioExistente;

    private Long idUsuarioInexistente;

    private Usuario usuarioAtualizar;

    @BeforeEach
    void setUp(){
        usuario = criarUsuario();
        usuarioCriado = usuarioCriado();
        idUsuarioExistente = 1L;
        idUsuarioInexistente = -1L;
        usuarioAtualizar = atualizarUsuario();
    }

    @Test
    @DisplayName("Deve cadastrar usuário quando informar dados")
    @Order(1)
    void deveCadastrarUsuario(){
        doNothing().when(usuarioValidator).validarCampos(usuario);
        doNothing().when(usuarioValidator).validarEmail(usuario.getEmail(), usuario.getId());
        doNothing().when(usuarioValidator).validarPerfil(usuario);
        when(usuarioRepository.save(any())).thenReturn(usuarioCriado);

        Usuario usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);
        assertNotNull(usuarioCadastrado);
        verify(usuarioRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve buscar usuário quando informar id existente")
    @Order(2)
    void deveBuscarUsuarioPorId(){
        when(usuarioRepository.buscarPorId(idUsuarioExistente)).thenReturn(Optional.of(usuarioCriado));

        Usuario usuarioEncontrado = usuarioService.buscarUsuarioPorId(idUsuarioExistente);

        assertNotNull(usuarioEncontrado);
        verify(usuarioRepository, times(1)).buscarPorId(idUsuarioExistente);
    }

    @Test
    @DisplayName("Deve não buscar usuário quando informar id inexistente")
    @Order(3)
    void naoDeveBuscarUsuarioPorIdQuandoNaoExistir(){
        when(usuarioRepository.buscarPorId(idUsuarioInexistente)).thenReturn(Optional.empty());

        assertThrows(NaoEncontradoException.class, () -> usuarioService.buscarUsuarioPorId(idUsuarioInexistente));
        verify(usuarioRepository, times(1)).buscarPorId(idUsuarioInexistente);
    }

    @Test
    @DisplayName("Deve atualizar usuário quando existir dados")
    @Order(4)
    void deveAtualizarUsuarioQuandoExistir(){
        when(usuarioRepository.buscarPorId(idUsuarioExistente)).thenReturn(Optional.of(usuarioCriado));
        doNothing().when(usuarioValidator).validarCampos(usuarioAtualizar);
        doNothing().when(usuarioValidator).validarEmail(usuarioAtualizar.getEmail(), usuarioCriado.getId());
        doNothing().when(usuarioValidator).validarPerfil(usuarioAtualizar);
        when(usuarioRepository.save(any())).thenReturn(usuarioAtualizar);

        Usuario usuarioAtulizado = usuarioService.atualizarUsuario(idUsuarioExistente, usuarioAtualizar);
        assertNotNull(usuarioAtulizado);
        assertEquals("Teste usuário editado", usuarioAtulizado.getNome());
        assertEquals("Teste usuário email", usuarioAtulizado.getEmail());
        assertEquals("Teste usuário sobrenome editado", usuarioAtulizado.getSobrenome());
        verify(usuarioRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve deletar usuário quando existir dados")
    @Order(5)
    void deveDeletarUsuarioQuandoExistir(){
        when(usuarioRepository.buscarPorId(idUsuarioExistente)).thenReturn(Optional.of(usuarioCriado));
        doNothing().when(usuarioRepository).delete(usuarioCriado);

        assertDoesNotThrow(() -> usuarioService.deletarUsuarioPorId(idUsuarioExistente));
        verify(usuarioRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Deve buscar usuário paginados")
    @Order(6)
    void deveBuscarUsuarioPaginados(){
        PageRequest pageRequest = PageRequest.of(0, 5);
        UsuarioFilterModel filtro = criarFiltroUsuario();

        when(usuarioRepository.findAll(new UsuarioSpecification(any()), any()))
                .thenReturn(criarUsuariosPaginados());

        Page<Usuario> usuarios = usuarioService.buscarUsuarios(pageRequest, filtro);
        assertFalse(usuarios.isEmpty());
    }

    @Test
    @DisplayName("Não deve buscar usuários paginados quando informar saldo total inicial maior que saldo total final")
    @Order(7)
    void naoDeveBuscarUsuarioPaginadosQuandoInformarSaldoTotalInicialMaiorSaldoTotalFinal(){
        PageRequest pageRequest = PageRequest.of(0, 5);
        UsuarioFilterModel filtro = criarFiltroUsuario();
        filtro.setSaldoTotalInicial(BigDecimal.valueOf(100.00));
        filtro.setSaldoTotalFinal(BigDecimal.valueOf(50.00));

        assertThrows(RegraDeNegocioException.class, () -> usuarioService.buscarUsuarios(pageRequest, filtro));
    }

    @Test
    @DisplayName("Não deve buscar usuários paginados quando informar saldo total inicial e não informar saldo total final")
    @Order(8)
    void naoDeveBuscarUsuarioQuandoInformarSaldoTotalInicialENaoInformarSaldoTotalFinal(){
        PageRequest pageRequest = PageRequest.of(0, 5);
        UsuarioFilterModel filtro = criarFiltroUsuario();
        filtro.setSaldoTotalInicial(BigDecimal.valueOf(100.00));

        assertThrows(RegraDeNegocioException.class, () -> usuarioService.buscarUsuarios(pageRequest, filtro));
    }

    @Test
    @DisplayName("Não deve buscar usuários paginados quando informar saldo total inicial e não informar saldo total final")
    @Order(9)
    void naoDeveBuscarUsuariosPaginadosQuandoNaoInformarSaldoTotalInicialENaoInformarSaldoTotalFinal(){
        PageRequest pageRequest = PageRequest.of(0, 5);
        UsuarioFilterModel filtro = criarFiltroUsuario();
        filtro.setSaldoTotalFinal(BigDecimal.valueOf(100.00));

        assertThrows(RegraDeNegocioException.class, () -> usuarioService.buscarUsuarios(pageRequest, filtro));
    }
}
