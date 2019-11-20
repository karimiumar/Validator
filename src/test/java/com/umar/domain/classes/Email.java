package com.umar.domain.classes;

import com.umar.validator.ErrorMessageService;
import com.umar.validator.ValueService;
import com.umar.validator.annotations.ErrorMessage;
import com.umar.validator.annotations.PatternValue;

import java.util.Objects;
import java.util.Optional;

public class Email{

    public static final String REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @PatternValue(message = @ErrorMessage(errorMessage = "is not a valid email address."))
    private final String emailId;

    public Email(String emailId){
        this.emailId = emailId;
    }

    public String getEmailId(){
        return emailId;
    }

    @Override
    public String toString(){
        return String.format("[Email: %s]",emailId);
    }

    public static class EmailErrorMessageService implements ErrorMessageService<Email> {
        @Override
        public Optional<String> getErrorMessage(Email email){
            Objects.requireNonNull(email,"Email is required.");
            return Optional.of(String.format("%s is not a valid email address.", email.getEmailId()));
        }
    }

    public static class EmailValueService implements ValueService<Email> {
        @Override
        public Optional<String> getValue(Email email){
            Objects.requireNonNull(email,"Email is required.");
            Objects.requireNonNull(email.getEmailId(),"Email Id is required.");
            return Optional.of(email.getEmailId());
        }
    }
}
