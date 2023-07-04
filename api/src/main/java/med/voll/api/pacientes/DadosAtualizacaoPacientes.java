package med.voll.api.pacientes;

import jakarta.validation.constraints.NotNull;
import med.voll.api.paciente.endereco.DadosEndereco;

public record DadosAtualizacaoPacientes(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
