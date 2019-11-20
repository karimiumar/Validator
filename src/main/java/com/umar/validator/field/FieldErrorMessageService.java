package com.umar.validator.field;

import com.umar.validator.ErrorMessageService;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

public class FieldErrorMessageService implements ErrorMessageService<Field> {
    @Override
    public Optional<String> getErrorMessage(Field field) {
        Objects.requireNonNull(field, "Object of type <java.lang.reflect.Field> is null.");
        String fieldName = field.getName();
        //return Optional.of(fieldName.substring(0,1).toUpperCase() + fieldName.substring(1));
        return Optional.of(StringUtils.capitalize(fieldName) + " is null or empty.");
    }
}
