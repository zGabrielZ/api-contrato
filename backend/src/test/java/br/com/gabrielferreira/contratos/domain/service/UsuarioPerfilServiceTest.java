package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.contratos.domain.model.Perfil;
import br.com.gabrielferreira.contratos.domain.repository.PerfilRepository;
import br.com.gabrielferreira.contratos.domain.repository.filter.PerfilFilterModel;
import br.com.gabrielferreira.contratos.domain.specification.PerfilSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static br.com.gabrielferreira.contratos.tests.PerfilFactory.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioPerfilServiceTest {

    @InjectMocks
    private UsuarioPerfilService usuarioPerfilService;

    @Mock
    private PerfilRepository perfilRepository;

    @Mock
    private UsuarioService usuarioService;

    private Long idUsuarioExistente;

    private Long idUsuarioInexistente;

    @BeforeEach
    void setUp(){
        idUsuarioExistente = 1L;
        idUsuarioInexistente = -1L;
    }

    @Test
    @DisplayName("Não deve buscar perfis quando não existir usuário")
    @Order(1)
    void naoDeveBuscarPerfisQuandoNaoExistirUsuario(){
        when(usuarioService.isUsuarioExistente(idUsuarioInexistente)).thenReturn(false);

        PageRequest pageRequest = PageRequest.of(0, 5);
        PerfilFilterModel filtro = criarFiltroPerfil(1L, null, null);

        assertThrows(NaoEncontradoException.class, () -> usuarioPerfilService.buscarPerfisPorUsuario(idUsuarioInexistente, pageRequest, filtro));
    }

    @Test
    @DisplayName("Deve buscar perfis quando existir usuário")
    @Order(1)
    void deveBuscarPerfisQuandoExistirUsuario(){
        when(usuarioService.isUsuarioExistente(idUsuarioExistente)).thenReturn(true);

        PageRequest pageRequest = PageRequest.of(0, 5);
        PerfilFilterModel filtro = criarFiltroPerfil(1L, null, null);

        when(perfilRepository.findAll(new PerfilSpecification(any(), any()), pageRequest)).
                thenReturn(criarPerfisPaginacao());

        Page<Perfil> perfis = usuarioPerfilService.buscarPerfisPorUsuario(idUsuarioExistente, pageRequest, filtro);

        assertFalse(perfis.isEmpty());
    }
}
