package med.voll.api.domain.consulta.validacoes.agendamento;

import java.time.DayOfWeek;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta{
  public void validar(DadosAgendamentoConsulta dados){
    var dataConsulta = dados.data();
    var domingo = dados.data().getDayOfWeek().equals(DayOfWeek.SUNDAY);
    var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
    var depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;

    if(domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica){
      throw new ValidacaoException("Consulta fora do horário de funcionamento da clinica");
    }

  }
}
