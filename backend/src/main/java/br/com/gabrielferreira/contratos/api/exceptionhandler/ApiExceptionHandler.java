package br.com.gabrielferreira.contratos.api.exceptionhandler;

import br.com.gabrielferreira.contratos.api.mapper.ErroPadraoMapper;
import br.com.gabrielferreira.contratos.domain.exception.MsgErroException;
import br.com.gabrielferreira.contratos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.contratos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.contratos.domain.exception.model.ErroPadraoModel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.ZonedDateTime;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.*;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private final ErroPadraoMapper erroPadraoMapper;

    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<ErroPadraoModel> naoEncontradoException(NaoEncontradoException e, HttpServletRequest request){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErroPadraoModel erroPadraoModel = erroPadraoMapper.toErroPadrao(toFusoPadraoSistema(ZonedDateTime.now()), httpStatus.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(erroPadraoModel);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroPadraoModel> naoEncontradoException(RegraDeNegocioException e, HttpServletRequest request){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErroPadraoModel erroPadraoModel = erroPadraoMapper.toErroPadrao(toFusoPadraoSistema(ZonedDateTime.now()), httpStatus.value(), "Regra de negócio", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(erroPadraoModel);
    }

    @ExceptionHandler(MsgErroException.class)
    public ResponseEntity<ErroPadraoModel> msgErroException(MsgErroException e, HttpServletRequest request){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErroPadraoModel erroPadraoModel = erroPadraoMapper.toErroPadrao(toFusoPadraoSistema(ZonedDateTime.now()), httpStatus.value(), "Erro", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(erroPadraoModel);
    }
}
