package com.umar.validator;

import java.util.Optional;

@FunctionalInterface
public interface ErrorMessageService<T> {
    Optional<String> getErrorMessage(T t);
}
