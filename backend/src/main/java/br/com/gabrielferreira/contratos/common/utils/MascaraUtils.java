package br.com.gabrielferreira.contratos.common.utils;

import br.com.gabrielferreira.contratos.domain.exception.MsgErroException;

import javax.swing.text.MaskFormatter;

public class MascaraUtils {

    private MascaraUtils(){}

    public static String toMascaraTelefoneResidencial(String ddd, String numero){
        try {
            MaskFormatter telefoneResidencialFormatacao = new MaskFormatter("(##) ####-####");
            telefoneResidencialFormatacao.setValueContainsLiteralCharacters(false);
            return telefoneResidencialFormatacao.valueToString(ddd.concat(numero));
        } catch (Exception e){
            throw new MsgErroException("Erro na formatação do telefone residencial");
        }
    }

    public static String toMascaraTelefoneCelular(String ddd, String numero){
        try {
            MaskFormatter telefoneCelularFormatacao = new MaskFormatter("(##) #####-####");
            telefoneCelularFormatacao.setValueContainsLiteralCharacters(false);
            return telefoneCelularFormatacao.valueToString(ddd.concat(numero));
        } catch (Exception e){
            throw new MsgErroException("Erro na formatação do telefone celular");
        }
    }
}
