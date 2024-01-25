package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.contratos.domain.model.Telefone;
import br.com.gabrielferreira.contratos.domain.repository.TelefoneRepository;
import br.com.gabrielferreira.contratos.domain.service.validator.TelefoneValidador;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
}
