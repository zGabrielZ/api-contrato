package br.com.gabrielferreira.contratos.domain.exception;

import java.io.Serial;

public class MsgErroException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public MsgErroException(String msg){
        super(msg);
    }
}
