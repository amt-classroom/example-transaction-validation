package ch.heigvd.amt.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

import java.util.Set;

@ApplicationScoped
public class ValidationService {

    @Inject
    Validator validator;

    public void manualValidation(Person person) {
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        violations.forEach(violation -> System.out.println(violation.getMessage()));
    }

    public void automaticValidation(@Valid Person person) {
        // The person is automatically validated
    }
}
