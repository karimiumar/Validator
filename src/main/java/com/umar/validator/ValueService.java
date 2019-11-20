package com.umar.validator;

import java.util.Optional;

@FunctionalInterface
public interface ValueService<T> {
    Optional<String> getValue(T t);
}
