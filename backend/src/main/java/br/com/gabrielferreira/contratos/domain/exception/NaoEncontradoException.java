package br.com.gabrielferreira.contratos.domain.exception;

import java.io.Serial;

public class NaoEncontradoException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NaoEncontradoException(String msg){
        super(msg);
    }
}
