package br.com.gabrielferreira.contratos.api.controller;

import br.com.gabrielferreira.contratos.api.model.input.PerfilInputModel;
import br.com.gabrielferreira.contratos.api.model.input.UsuarioInputModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static br.com.gabrielferreira.contratos.tests.UsuarioFactory.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioControllerIntegrationTest {

    private static final String URL = "/usuarios";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    private UsuarioInputModel input;

    @BeforeEach
    void setUp(){
        input = criarUsuarioInput();
    }

    @Test
    @DisplayName("Deve cadastrar usuário quando informar dados")
    @Order(1)
    void deveCadastrarUsuarioQuandoInformarDados() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(input);

        String nomeEsperado = input.getNome();
        String sobrenomeEsperado = input.getSobrenome();
        String emailEsperado = input.getEmail();

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.nome").value(nomeEsperado));
        resultActions.andExpect(jsonPath("$.sobrenome").value(sobrenomeEsperado));
        resultActions.andExpect(jsonPath("$.email").value(emailEsperado));
        resultActions.andExpect(jsonPath("$.perfis").exists());
        resultActions.andExpect(jsonPath("$.dataCadastrado").exists());
    }

    @Test
    @DisplayName("Não deve cadastrar usuário quando não informar dados")
    @Order(2)
    void naoDeveCadastrarUsuarioQuandoNaoInformarDados() throws Exception{
        input.setNome(null);
        input.setSobrenome(null);
        input.setEmail(null);
        input.setPerfis(null);

        String jsonBody = objectMapper.writeValueAsString(input);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.titulo").value("Erro validação de campos"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Ocorreu um erro de validação nos campos"));
        resultActions.andExpect(jsonPath("$.campos").exists());
    }

    @Test
    @DisplayName("Não deve cadastrar usuário quando informar email existente")
    @Order(3)
    void naoDeveCadastrarUsuarioQuandoInformarEmailExistente() throws Exception{
        input.setEmail("jose@email.com");

        String jsonBody = objectMapper.writeValueAsString(input);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.titulo").value("Regra de negócio"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Este e-mail 'jose@email.com' já foi cadastrado"));
    }

    @Test
    @DisplayName("Não deve cadastrar usuário quando informar perfis duplicados")
    @Order(4)
    void naoDeveCadastrarUsuarioQuandoInformarPerfisDuplicados() throws Exception{
        PerfilInputModel perfil1 = input.getPerfis().get(0);
        PerfilInputModel perfil2 = input.getPerfis().get(0);
        input.setPerfis(Arrays.asList(perfil1, perfil2));

        String jsonBody = objectMapper.writeValueAsString(input);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.titulo").value("Regra de negócio"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Não vai ser possível cadastrar este usuário pois tem perfis duplicados ou mais de duplicados"));
    }

    @Test
    @DisplayName("Não deve cadastrar usuário quando informar perfis inexistente")
    @Order(5)
    void naoDeveCadastrarUsuarioQuandoInformarPerfisInexistente() throws Exception{
        PerfilInputModel perfil1 = input.getPerfis().get(0);
        perfil1.setId(-1L);
        input.setPerfis(List.of(perfil1));

        String jsonBody = objectMapper.writeValueAsString(input);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.titulo").value("Não encontrado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Perfil não encontrado"));
    }
}
