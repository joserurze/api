package med.voll.api.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.ValidacaoException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;

@RestControllerAdvice
public class TratadorDeErros {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity tratarErros404() {

    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity tratarErros400(MethodArgumentNotValidException ex) {
    var erros = ex.getFieldErrors();
    return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
  }

  @ExceptionHandler(ValidacaoException.class)
  public ResponseEntity tratarErrosRegraDeNegocio(ValidacaoException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }

  private record DadosErroValidacao(String campo, String mensagem) {

    public DadosErroValidacao(FieldError erro) {
      this(erro.getField(), erro.getDefaultMessage());
    }
  }
}
