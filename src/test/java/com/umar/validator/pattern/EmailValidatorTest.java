package com.umar.validator.pattern;

import com.umar.boot.SpringConfig;
import com.umar.domain.classes.Email;
import com.umar.validator.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SpringConfig.class)
public class EmailValidatorTest {

    //@Autowired private ErrorMessageService<Email> errorMsgService;
    @Autowired private PatternValidationErrorGenerator<Email> generator;
    @Autowired private ValidationStrategy<Email> strategy;

    private static Stream<Arguments> validEmails() {
        return Stream.of(
                Arguments.of(new Email("mkyong@yahoo.com")),
                Arguments.of(new Email("mkyong-100@yahoo.com")),
                Arguments.of(new Email("mkyong.100@yahoo.com")),
                Arguments.of(new Email("mkyong111@mkyong.com")),
                Arguments.of(new Email("mkyong-100@mkyong.net")),
                Arguments.of(new Email("mkyong.100@mkyong.com.au")),
                Arguments.of(new Email("mkyong@1.com")),
                Arguments.of(new Email("mkyong@gmail.com.com")),
                Arguments.of(new Email("mkyong+1203@gmail.com.com")),
                Arguments.of(new Email("mkyong-4556@gmail.com.com"))
        );
    }

    private static Stream<Arguments> inValidEmails() {
        return Stream.of(
                Arguments.of(new Email("mkyongyahoo.com")),
                Arguments.of(new Email("mkyong-100@.yahoo.com")),
                Arguments.of(new Email("mkyong.100@y.c")),
                Arguments.of(new Email("mkyong111@.com")),
                Arguments.of(new Email(".mkyong-100@mkyong.net")),
                Arguments.of(new Email("mkyong.100%@mkyong.com.au")),
                Arguments.of(new Email("mkyong..2002@1.com")),
                Arguments.of(new Email("mkyong.@gmail.com@com")),
                Arguments.of(new Email("mkyong+1203.@gmail.com.com")),
                Arguments.of(new Email("mkyong-4556@gmail.com.1a"))
        );
    }

    @ParameterizedTest
    @MethodSource("validEmails")
    void areValidEmails(final Email email) {
        Collection<ValidationError> errors = strategy.validate(email);
        errors.forEach((e) -> assertEquals(e.getValidationError().get(), ""));
        errors.forEach((e) -> assertFalse(e.isError()));
    }

    @Test
    void inValidEmailHasValidationError() {
        Email email = new Email("mkyongyahoo.com");
        Collection<ValidationError> errors = strategy.validate(email);
        errors.forEach((e) -> assertTrue(e.isError()));
        errors.forEach((e) -> System.out.println("mkyongyahoo.com is erroneous email: " + e.isError()));
    }

    @ParameterizedTest
    @MethodSource("inValidEmails")
    void areInValidEmails(final Email email) {
        Collection<ValidationError> errors = strategy.validate(email);
        assertFalse(errors.isEmpty());
        errors.forEach((e) -> assertTrue(e.isError()));
        String errorMessage = " is not a valid email address.";
        errors.forEach((e) -> assertTrue(e.getValidationError().get().contains(errorMessage)));
        errors.forEach(System.out::println);
    }

    @Test
    void emailIsNull() {
        Collection<ValidationError> errors = strategy.validate(null);
        assertFalse(errors.isEmpty());
        errors.forEach(System.out::println);
        String expectedError = "Incoming object is null or empty.";
        errors.forEach((error) -> assertEquals(expectedError, error.getValidationError().get()));
    }

    @Test
    void emailIdIsEmpty() {
        Email emptyEmail = new Email("");
        Collection<ValidationError> errors = strategy.validate(emptyEmail);
        assertFalse(errors.isEmpty());
        errors.forEach(System.out::println);
        String expectedError = "Value of com.umar.domain.classes.Email's field: emailId is null or empty.";
        errors.forEach((error) -> assertEquals(expectedError, error.getValidationError().get()));
    }
    
    @Test
    void emailIdIsBlank() {
        Email emptyEmail = new Email(" ");
        Collection<ValidationError> errors = strategy.validate(emptyEmail);
        assertFalse(errors.isEmpty());
        errors.forEach(System.out::println);
        String expectedError = "  is not a valid email address.";
        errors.forEach((error) -> assertEquals(expectedError, error.getValidationError().get()));
    }

    @Test
    void emailIdIsNull() {
        Email nullEmail = new Email(null);
        try {
            Collection<ValidationError> errors = strategy.validate(nullEmail);
        } catch (Exception e) {
            String expectedError = "Email Id is required.";
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            assertEquals(expectedError, errorMessage);
        }

    }

    /*@Test
    void emailParamInEmailErrorMessageIsNull() {
        try {
            errorMsgService.getErrorMessage(null);
        } catch (Exception e) {
            String expected = "Email is required.";
            assertEquals(expected, e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    /*
    @Test
    void emailParamInEmailValueServiceIsNull() {
        try {
            emailValueService.getValue(null);
        } catch (Exception e) {
            String expected = "Email is required.";
            assertEquals(expected, e.getMessage());
            System.out.println(e.getMessage());
        }
    }*/
}
