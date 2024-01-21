package br.com.gabrielferreira.contratos.api.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioPerfilControllerIntegrationTest {

    private static final String URL = "/usuarios";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON;

    @Autowired
    protected MockMvc mockMvc;

    private Long idUsuarioExistente;

    private Long idUsuarioInexistente;

    @BeforeEach
    void setUp(){
        idUsuarioExistente = 1L;
        idUsuarioInexistente = -1L;
    }

    @Test
    @DisplayName("Não deve buscar perfis por usuário quando não existir usuário")
    @Order(1)
    void naoDeveBuscarPefisPorUsuarioQuandoNaoExistirUsuario() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}").concat("/perfis"), idUsuarioInexistente)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.mensagem").value("Usuário não encontrado"));
    }

    @Test
    @DisplayName("Deve buscar perfis quando existir usuário")
    @Order(2)
    void deveBuscarPerfisPorUsuario() throws Exception {
        String filtro = "?page=0&size=5&sort=id,desc&sort=descricao,desc&sort=autoriedade,desc";

        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}").concat("/perfis").concat(filtro), idUsuarioExistente)
                        .accept(MEDIA_TYPE_JSON));
        resultActions.andExpect(status().isOk());

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.content").exists());
    }

    @Test
    @DisplayName("Deve buscar perfis com parametros informados quando existir usuário")
    @Order(3)
    void deveBuscarPerfisPorUsuarioQuandoInformarParametros() throws Exception {
        String filtro = "?page=0&size=5&sort=id,desc&sort=descricao,desc&sort=autoriedade,desc" +
                "&id=1&descricao=Cliente&autoriedade=ROLE_CLIENTE";

        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}").concat("/perfis").concat(filtro), idUsuarioExistente)
                        .accept(MEDIA_TYPE_JSON));
        resultActions.andExpect(status().isOk());

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.content").exists());
    }
}
