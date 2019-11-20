package com.umar.validator.field;

import com.umar.validator.ErrorMessageService;
import com.umar.validator.ValidationError;
import com.umar.validator.ValidationStrategy;
import com.umar.validator.annotations.IgnoreValidation;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

import static com.umar.validator.util.Util.isNullOrEmpty;

public class FieldValidationStrategy<T> implements ValidationStrategy<T> {

    public FieldValidationStrategy() {

    }

    @Override
    public Collection<ValidationError> validate(T t) {
        if(isNullOrEmpty(t)) {
            String errorMsg = "Incoming object of T is null";
            ValidationError error = new ValidationError(errorMsg);
            return Collections.singletonList(error);
        }
        List<ValidationError> errors = new ArrayList<>();
        try {
            Field [] fields = t.getClass().getDeclaredFields();
            for(Field field: fields) {
                field.setAccessible(true);
                boolean ignoreFieldValidation = field.isAnnotationPresent(IgnoreValidation.class);
                Object value = field.get(t);
                if(!ignoreFieldValidation && isNullOrEmpty(value)) {
                    ErrorMessageService<Field> errorMessageService = o -> Optional.of(StringUtils.capitalize(o.getName()) + " is null or empty.");//create a function
                    Optional<String> optionalError = errorMessageService.getErrorMessage(field);
                    String validationErrMsg = optionalError.map(String::valueOf).orElseThrow(()-> new RuntimeException("Failed to map Optional error message during Field Validation"));
                    ValidationError error = new ValidationError(validationErrMsg);
                    errors.add(error);
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException e ) {
            String exception = e.getMessage();
            ValidationError error = new ValidationError(exception);
            return Collections.singletonList(error);
        }
        return errors;
    }
}
