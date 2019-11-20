package com.umar.validator;

import java.util.Optional;

public class ValidationError {

    private final String validationError;

    public ValidationError(String error) {
        this.validationError = error;
    }

    public Optional<String> getValidationError() {
        return Optional.of(validationError);
    }

    public boolean isError() {
        return !validationError.isEmpty();
    }

    @Override
    public String toString() {
        return validationError;
    }
}
