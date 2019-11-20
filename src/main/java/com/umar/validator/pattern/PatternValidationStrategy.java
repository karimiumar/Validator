package com.umar.validator.pattern;

import com.umar.validator.ValidationError;
import com.umar.validator.ValidationStrategy;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

public class PatternValidationStrategy<T> implements ValidationStrategy<T> {

    private final PatternValidationErrorGenerator<T> patternValidationErrorGenerator;

    public PatternValidationStrategy(PatternValidationErrorGenerator<T> errorGenerator) {
        Objects.requireNonNull(errorGenerator, "PatternValidationErrorGenerator is required.");
        this.patternValidationErrorGenerator = errorGenerator;
    }

    @Override
    public Collection<ValidationError> validate(T t) {
        Optional<ValidationError> optionalError = patternValidationErrorGenerator.validate(t);
        if(optionalError.isPresent()) {
            ValidationError validationError = optionalError.get();
            return Collections.singletonList(validationError);
        }
        return Collections.emptyList();
    }
}
