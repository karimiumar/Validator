package com.umar.validator.pattern;

import com.umar.validator.ValidationError;
import com.umar.validator.annotations.PatternValue;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.umar.validator.util.Util.isNullOrEmpty;

public class PatternValidationErrorGenerator<T> {

    private Pattern pattern;

    public PatternValidationErrorGenerator(String regex) {
        Objects.requireNonNull(regex, "Regex for Pattern matching is required.");
        if (isNullOrEmpty(regex)) {
            throw new RuntimeException("Regex for pattern matching is empty.");
        } else {
            compile(regex);
        }
    }

    Optional<ValidationError> validate(T t) {
        if (isNullOrEmpty(t)) {
            return getValidationError("Incoming object is null or empty.");
        }
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean isPatternNotated = field.isAnnotationPresent(PatternValue.class);
            if (isPatternNotated) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(t);
                    if (isNullOrEmpty(value)) {
                        return emptyValueError(t, field);
                    }
                    if (value instanceof CharSequence) {
                        return matchingError(field, value);
                    } else {
                        return getValidationError(field.getName() + " doesn't contain String or CharSequence value.");
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(PatternValidationErrorGenerator.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    String exception = ex.getMessage();
                    return getValidationError(exception);
                }
            }
        }
        return Optional.empty();
    }

    private Optional<ValidationError> getValidationError(String exception) {
        ValidationError error = new ValidationError(exception);
        return Optional.of(error);
    }

    private Optional<ValidationError> matchingError(Field field, Object value) {
        Matcher matcher = pattern.matcher((CharSequence) value);
        if (!matcher.matches()) {
            String annotatedMsg = field.getAnnotation(PatternValue.class).message().errorMessage();
            return getValidationError(String.format("%s %s", value, annotatedMsg));
        }
        return Optional.empty();
    }

    private Optional<ValidationError> emptyValueError(T t, Field field) {
        String errorMsg = "Value of " + t.getClass().getName() +"'s field: " + field.getName() + " is null or empty.";
        ValidationError error = new ValidationError(errorMsg);
        return Optional.of(error);
    }

    private void compile(final String regex) {
        pattern = Pattern.compile(regex);
    }
}

/**
 * Explore if the returns can be converted to test() Predicates?
 *
 * List<Predicate<MyObject>> predicates = Arrays.asList(MyObject::isValid,
 *                                                     (m)-> m.getColor()==BLUE);
 * And now apply rules :
 *
 * MyObject myObj = ...;
 * for (Predicate<MyObject> predicate : predicates) {
 *     if (!predicate.test(myObj)) {
 *       return false;
 *     }
 * }
 */