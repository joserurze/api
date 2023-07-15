package med.voll.api.domain.paciente;

import med.voll.api.endereco.Endereco;

public record DadosDetalhadosPacientes(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco
) {
    public DadosDetalhadosPacientes(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(),paciente.getTelefone(),paciente.getCpf(),paciente.getEndereco());
    }
}
