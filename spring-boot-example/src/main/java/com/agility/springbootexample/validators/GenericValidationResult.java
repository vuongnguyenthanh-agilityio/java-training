package com.agility.springbootexample.validators;

import java.util.Optional;

public class GenericValidationResult {
  private boolean isValid;

  private GenericValidationResult (boolean isValid) {
    this.isValid = isValid;
  }

  public boolean isValid() {
    return this.isValid;
  }

  public static GenericValidationResult success() {
    return new GenericValidationResult(true);
  }

  public static GenericValidationResult failed() {
    return new GenericValidationResult(false);
  }

  public Optional<String> getFieldNameInvalid(String field) {
    return this.isValid ? Optional.empty() : Optional.of(field);
  }
}
