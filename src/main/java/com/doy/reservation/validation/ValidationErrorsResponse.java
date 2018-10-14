package com.doy.reservation.validation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorsResponse {
    private List<ValidationError> errors;

    public ValidationErrorsResponse() {
        errors = new ArrayList<>();
    }

    public void addValidationError(ValidationError error) {
        errors.add(error);
    }
}
