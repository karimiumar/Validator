package com.umar.boot;

import com.umar.validator.field.FieldValidatorTest;
import com.umar.validator.pattern.EmailValidatorTest;
import org.junit.jupiter.api.Test;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SpringConfig.class)
@Suite.SuiteClasses({
    FieldValidatorTest.class,
    EmailValidatorTest.class
})
class BootValidatorApplicationTests {

    @Test
    void contextLoads() {

    }

}
