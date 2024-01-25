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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private Long idUsuarioExistente;

    private Long idUsuarioInexistente;

    private UsuarioInputModel inputUpdate;

    @BeforeEach
    void setUp(){
        input = criarUsuarioInput();
        idUsuarioExistente = 1L;
        idUsuarioInexistente = -1L;
        inputUpdate = criarUsuarioInputUpdate();
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
        resultActions.andExpect(jsonPath("$.dataCadastro").exists());
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

    @Test
    @DisplayName("Deve buscar usuário por id quando existir dado informado")
    @Order(6)
    void deveBuscarUsuarioPorId() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}"), idUsuarioExistente)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.nome").exists());
        resultActions.andExpect(jsonPath("$.sobrenome").exists());
        resultActions.andExpect(jsonPath("$.email").exists());
        resultActions.andExpect(jsonPath("$.perfis").exists());
        resultActions.andExpect(jsonPath("$.dataCadastro").exists());
    }

    @Test
    @DisplayName("Não deve buscar usuário por id quando não existir dado informado")
    @Order(7)
    void naoDeveBuscarUsuarioPorId() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}"), idUsuarioInexistente)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.mensagem").value("Usuário não encontrado"));
    }

    @Test
    @DisplayName("Deve atualizar usuário quando informar dados")
    @Order(8)
    void deveAtualizarUsuarioQuandoInformarDados() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(inputUpdate);

        Long idEsperado = idUsuarioExistente;
        String nomeEsperado = inputUpdate.getNome();
        String sobrenomeEsperado = inputUpdate.getSobrenome();
        String emailEsperado = inputUpdate.getEmail();

        ResultActions resultActions = mockMvc
                .perform(put(URL.concat("/{id}"), idUsuarioExistente)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").value(idEsperado));
        resultActions.andExpect(jsonPath("$.nome").value(nomeEsperado));
        resultActions.andExpect(jsonPath("$.sobrenome").value(sobrenomeEsperado));
        resultActions.andExpect(jsonPath("$.email").value(emailEsperado));
        resultActions.andExpect(jsonPath("$.perfis").exists());
        resultActions.andExpect(jsonPath("$.dataCadastro").exists());
    }

    @Test
    @DisplayName("Não deve atualizar usuário quando informar email já existente")
    @Order(9)
    void naoDeveAtualizarUsuarioQuandoInformarEmailJaExistente() throws Exception{
        inputUpdate.setEmail("marcos@email.com");
        String jsonBody = objectMapper.writeValueAsString(inputUpdate);

        ResultActions resultActions = mockMvc
                .perform(put(URL.concat("/{id}"), idUsuarioExistente)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.mensagem").value("Este e-mail 'marcos@email.com' já foi cadastrado"));
    }

    @Test
    @DisplayName("Deve deletar usuário quando existir dados")
    @Order(10)
    void deveDeletarUsuarioQuandoExistirDados() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(delete(URL.concat("/{id}"), idUsuarioExistente)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Não deve deletar usuário quando não existir dados")
    @Order(11)
    void naoDeveDeletarEvento() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(delete(URL.concat("/{id}"), idUsuarioInexistente)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.mensagem").value("Usuário não encontrado"));
    }

    @Test
    @DisplayName("Deve buscar usuário")
    @Order(12)
    void deveBuscarUsuarios() throws Exception {
        String filtro = "?page=0&size=5&sort=id,desc&sort=nome,desc&sort=email,desc";

        ResultActions resultActions = mockMvc
                .perform(get(URL.concat(filtro))
                        .accept(MEDIA_TYPE_JSON));
        resultActions.andExpect(status().isOk());

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.content").exists());
    }
}
