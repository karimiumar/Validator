package com.umar.validator;

import java.util.Collection;

/**
 * A generic interface that declares validate() method.
 * All the subtypes will provide definition for validate() method
 *
 * @param <T> Requires a type T
 */
public interface ValidationStrategy<T> {
    Collection<ValidationError> validate(T t);
}
