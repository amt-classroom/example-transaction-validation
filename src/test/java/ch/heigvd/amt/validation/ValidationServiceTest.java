package ch.heigvd.amt.validation;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ValidationServiceTest {

    @Inject
    ValidationService validationService;

    @Test
    void manualValidation() {
        Person person = new Person();

        System.out.println("-----------------");
        validationService.manualValidation(person);

        person.firstName = "John";
        person.lastName = "Doe";
        person.yearOfBirth = 2000;
        person.email = "test";

        System.out.println("-----------------");
        validationService.manualValidation(person);

        person.email = "test@test.com";

        System.out.println("-----------------");
        validationService.manualValidation(person);
        System.out.println("The person is valid");
    }

    @Test
    void automaticValidation() {
        try {
            Person person = new Person();
            validationService.automaticValidation(person);
        }  catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(violation -> System.out.println(violation.getMessage()));
        }
    }
}