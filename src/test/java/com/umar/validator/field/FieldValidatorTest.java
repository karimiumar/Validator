package com.umar.validator.field;

import com.umar.boot.SpringConfig;
import com.umar.domain.classes.Country;
import com.umar.validator.ValidationStrategy;
import com.umar.validator.ValidationError;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = SpringConfig.class)
public class FieldValidatorTest {

    @Autowired private ValidationStrategy<Country> strategy;

    private static Stream<Arguments> validCountries() {
        return Stream.of(
                Arguments.of(new Country(6, "SINGAPORE", "SNG")),
                Arguments.of(new Country(7, "UNITED STATES", "US")),
                Arguments.of(new Country(8, "INDIA", "IND"))
        );
    }

    private static Stream<Arguments> inValidCountries() {
        return Stream.of(
                Arguments.of(new Country(1, "SINGAPORE", "")),
                Arguments.of(new Country(2, "", "US")),
                Arguments.of(new Country(3, null, "IND")),
                Arguments.of(new Country(4, "ITALY", null)),
                Arguments.of(new Country(5, null, null))
        );
    }

    @ParameterizedTest
    @MethodSource("inValidCountries")
    void areInvalidCountries(final Country country) {
        Collection<ValidationError> errors = strategy.validate(country);
        assertFalse(errors.isEmpty());
        String validationError = "is null or empty.";
        errors.forEach(e->assertTrue(e.getValidationError().get().contains(validationError)));
        errors.forEach(e->System.out.println(e.getValidationError().get()));
    }

    @ParameterizedTest
    @MethodSource("validCountries")
    void areValidCountries(final Country country) {
        Collection<ValidationError> errors = strategy.validate(country);
        assertTrue(errors.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("inValidCountries")
    void invalidCountriesHasErrorAsTrue(final Country country) {
        Collection<ValidationError> errors = strategy.validate(country);
        errors.forEach(e->assertTrue(e.isError()));
        errors.forEach(e->System.out.println( country + " has error: " + e.isError()));
    }
}
