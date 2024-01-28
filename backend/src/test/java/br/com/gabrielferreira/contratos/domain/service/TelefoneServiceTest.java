package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.contratos.domain.model.Telefone;
import br.com.gabrielferreira.contratos.domain.repository.TelefoneRepository;
import br.com.gabrielferreira.contratos.domain.repository.filter.TelefoneFilterModel;
import br.com.gabrielferreira.contratos.domain.service.validator.TelefoneValidador;
import br.com.gabrielferreira.contratos.domain.specification.TelefoneSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import static br.com.gabrielferreira.contratos.tests.TelefoneFactory.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TelefoneServiceTest {

    @InjectMocks
    private TelefoneService telefoneService;

    @Mock
    private TelefoneRepository telefoneRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private TelefoneValidador telefoneValidador;

    private Telefone telefone;

    private Telefone telefoneCriado;

    private Telefone telefoneAtualizar;

    private Long idUsuarioExistente;

    private Long idTelefoneExistente;

    private Long idUsuarioInexistente;

    private Long idTelefoneInexistente;

    @BeforeEach
    void setUp(){
        telefone = criarTelefone();
        telefoneCriado = telefoneCriado();
        idUsuarioExistente = 1L;
        idTelefoneExistente = 1L;
        idUsuarioInexistente = -1L;
        idTelefoneInexistente = -1L;
        telefoneAtualizar = atualizarTelefone();
    }

    @Test
    @DisplayName("Deve cadastrar telefone quando informar dados")
    @Order(1)
    void deveCadastrarTelefone(){
        when(usuarioService.buscarUsuarioPorId(any())).thenReturn(telefone.getUsuario());
        doNothing().when(telefoneValidador).validarCampos(telefone);
        doNothing().when(telefoneValidador).validarTipoTelefone(telefone);
        doNothing().when(telefoneValidador).validarTelefoneExistente(telefone.getId(), telefone);
        when(telefoneRepository.save(any())).thenReturn(telefoneCriado);

        Telefone telefoneCadastrado = telefoneService.cadastrarTelefone(telefone.getUsuario().getId(), telefone);
        assertNotNull(telefoneCadastrado);
        verify(telefoneRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve buscar telefone quando informar id do usuário e do telefone")
    @Order(2)
    void deveBuscarTelefonePorId(){
        when(telefoneRepository.buscarTelefone(idUsuarioExistente, idTelefoneExistente)).thenReturn(Optional.of(telefoneCriado));

        Telefone telefoneEncontrado = telefoneService.buscarTelefonePorId(idUsuarioExistente, idTelefoneExistente);

        assertNotNull(telefoneEncontrado);
        verify(telefoneRepository, times(1)).buscarTelefone(idUsuarioExistente, idTelefoneExistente);
    }

    @Test
    @DisplayName("Deve não buscar telefone quando informar id do usuário e do telefone inexistente")
    @Order(3)
    void naoDeveBuscarTelefonePorIdQuandNaoExistir(){
        when(telefoneRepository.buscarTelefone(idUsuarioInexistente, idTelefoneInexistente)).thenReturn(Optional.empty());

        assertThrows(NaoEncontradoException.class, () -> telefoneService.buscarTelefonePorId(idUsuarioInexistente, idTelefoneInexistente));
        verify(telefoneRepository, times(1)).buscarTelefone(idUsuarioInexistente, idTelefoneInexistente);
    }

    @Test
    @DisplayName("Deve buscar quantidade de telefones quando existir id do usuário")
    @Order(4)
    void deveBuscarQuantidadeDeTelefones(){
        when(usuarioService.isUsuarioExistente(idUsuarioExistente)).thenReturn(true);
        when(telefoneRepository.buscarQuantidadeTelefonePorUsuario(idUsuarioExistente)).thenReturn(2L);

        Long qtdTelefones = telefoneService.buscarQuantidadeTelefonePorUsuario(idUsuarioExistente);

        assertEquals(2, qtdTelefones);
        verify(telefoneRepository, times(1)).buscarQuantidadeTelefonePorUsuario(idUsuarioExistente);
    }

    @Test
    @DisplayName("Deve deletar telefone quando existir dados")
    @Order(5)
    void deveDeletarTelefoneQuandoExistir(){
        when(telefoneRepository.buscarTelefone(idUsuarioExistente, idTelefoneExistente)).thenReturn(Optional.of(telefoneCriado));

        assertDoesNotThrow(() -> telefoneService.deletarTelefonePorId(idUsuarioExistente, idTelefoneExistente));
        verify(telefoneRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Deve atualizar telefone quando existir dados")
    @Order(6)
    void deveAtualizarTelefoneQuandoExistir(){
        when(telefoneRepository.buscarTelefone(idUsuarioExistente, idTelefoneExistente)).thenReturn(Optional.of(telefoneCriado));
        doNothing().when(telefoneValidador).validarCampos(telefoneAtualizar);
        doNothing().when(telefoneValidador).validarTipoTelefone(telefoneAtualizar);
        doNothing().when(telefoneValidador).validarTelefoneExistente(telefoneCriado.getId(), telefoneAtualizar);
        when(telefoneRepository.save(any())).thenReturn(telefoneAtualizar);

        Telefone telefoneAtulizado = telefoneService.atualizarTelefone(idUsuarioExistente, idTelefoneExistente, telefoneAtualizar);
        assertNotNull(telefoneAtulizado);
        assertEquals("22", telefoneAtulizado.getDdd());
        assertEquals("888888888", telefoneAtulizado.getNumero());
        verify(telefoneRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve buscar telefones paginados")
    @Order(7)
    void deveBuscarTelefonesPaginados(){
        PageRequest pageRequest = PageRequest.of(0, 5);
        TelefoneFilterModel filtro = criarFiltroTelefone();

        when(usuarioService.isUsuarioExistente(idUsuarioExistente)).thenReturn(true);
        when(telefoneRepository.findAll(new TelefoneSpecification(idUsuarioExistente, any()), any()))
                .thenReturn(criarTelefonesPaginados());

        Page<Telefone> telefones = telefoneService.buscarTelefones(idUsuarioExistente, pageRequest, filtro);
        assertFalse(telefones.isEmpty());
    }
}
