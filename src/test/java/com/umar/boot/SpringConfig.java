package com.umar.boot;

import com.umar.domain.classes.Email;
import com.umar.validator.ValidationStrategy;
import com.umar.validator.field.FieldValidationStrategy;
import com.umar.validator.pattern.PatternValidationErrorGenerator;
import com.umar.validator.pattern.PatternValidationStrategy;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan(basePackages = "com.umar.validator")
public class SpringConfig {

    /*@Bean
    ErrorMessageService<Email> emailErrorMessageService() {
        return new Email.EmailErrorMessageService();
    }*/

    @Bean
    PatternValidationErrorGenerator<Email> emailPatternValidationErrorGenerator(){
        return new PatternValidationErrorGenerator<>(Email.REGEX);
    }

    @Bean
    public ValidationStrategy<Email> emailPatternValidationStrategy() {
        return new PatternValidationStrategy<>(emailPatternValidationErrorGenerator());
    }

    @Bean
    public ValidationStrategy<?> fieldValidationStrategy() {
        return new FieldValidationStrategy<>();
    }
}
