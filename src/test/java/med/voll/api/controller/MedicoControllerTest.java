package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosDetalhadosMedico;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico>dadosCadastroMedicoJason;

    @Autowired
    private JacksonTester<DadosDetalhadosMedico> dadosDetalhadosMedicoJason;

    @MockBean
    private MedicoRepository repository;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser
    void cadastrar_cenario1() throws Exception {
      var response = mvc
          .perform(post("/medicos"))
          .andReturn().getResponse();
      assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informações estão disponiveis")
    @WithMockUser
    void cadastrar_cenario2() throws Exception{
        
        var dadosCadastro = new DadosCadastroMedico(
            "medico",
            "medico@voll.med",
            "61999999999",
            "123456",
            Especialidade.CARDIOLOGIA,
            dadosCadastro());
        when(repository.save(any())).thenReturn(new Medico(dadosCadastro));

        var response = mvc
            .perform(post("/medicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosCadastroMedicoJason.write(dadosCadastro).getJson()))
            .andReturn().getResponse();

        var dadosDetalhamento = new DadosDetalhadosMedico(
            null,
            dadosCadastro.nome(),
            dadosCadastro.email(),
            dadosCadastro.crm(),
            dadosCadastro.telefone(),
            dadosCadastro.especialidade(),
            new Endereco(dadosCadastro.endereco())
        );
        var jsonEsperado = dadosDetalhadosMedicoJason.write(dadosDetalhamento).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }


    private DadosEndereco dadosCadastro() {
        return new DadosEndereco(
            "rua xpto",
            "bairro",
            "00000000",
            "Brasilia",
            "DF",
            "PI",
            "64000000"
        );
    }
}