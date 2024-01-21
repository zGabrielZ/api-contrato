package br.com.gabrielferreira.contratos.domain.service;

import br.com.gabrielferreira.contratos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.contratos.domain.model.Perfil;
import br.com.gabrielferreira.contratos.domain.repository.PerfilRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static br.com.gabrielferreira.contratos.tests.PerfilFactory.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PerfilServiceTest {

    @InjectMocks
    private PerfilService perfilService;

    @Mock
    private PerfilRepository perfilRepository;

    private Long idPerfilExistente;

    private Long idPerfilInexistente;

    @BeforeEach
    void setUp(){
        idPerfilExistente = 1L;
        idPerfilInexistente = -1L;
    }

    @Test
    @DisplayName("Deve buscar uma lista de perfis quando existir dados")
    @Order(1)
    void deveBuscarUmaListaDePerfis(){
        when(perfilRepository.buscarPerfis()).thenReturn(criarPerfis());

        List<Perfil> perfis = perfilService.buscarPerfis();

        assertFalse(perfis.isEmpty());
        assertEquals(1L, perfis.get(0).getId());
        assertEquals(2L, perfis.get(1).getId());
        verify(perfilRepository, times(1)).buscarPerfis();
    }

    @Test
    @DisplayName("Deve buscar perfil quando existir dados")
    @Order(2)
    void deveBuscarPerfilQuandoExistir(){
        when(perfilRepository.findById(idPerfilExistente)).thenReturn(Optional.of(criarPerfil()));

        Perfil perfil = perfilService.buscarPerfilPorId(idPerfilExistente);

        assertNotNull(perfil);
        assertEquals(1L, perfil.getId());
        verify(perfilRepository, times(1)).findById(idPerfilExistente);
    }

    @Test
    @DisplayName("Não deve buscar perfil quando não existir dados")
    @Order(3)
    void naoDeveBuscarPerfilQuandoNaoExistir(){
        when(perfilRepository.findById(idPerfilInexistente)).thenReturn(Optional.empty());

        assertThrows(NaoEncontradoException.class, () -> perfilService.buscarPerfilPorId(idPerfilInexistente));
        verify(perfilRepository, times(1)).findById(idPerfilInexistente);
    }
}
