package br.com.gabrielferreira.contratos.api.validator;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EnumValidator implements ConstraintValidator<EnumValid, String> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValido = false;
        if(StringUtils.isBlank(valor)){
            isValido = true;
        } else {
            Enum<?>[] enumConstants = enumClass.getEnumConstants();
            for (Enum<?> enumValor : enumConstants){
                if (enumValor.name().equals(valor)) {
                    isValido = true;
                    break;
                }
            }
        }

        return isValido;
    }
}
