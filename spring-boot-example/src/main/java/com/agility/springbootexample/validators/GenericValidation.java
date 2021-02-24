package com.agility.springbootexample.validators;

import java.util.function.Predicate;

public class GenericValidation <T> implements IValidation<T> {
  private Predicate<T> predicate;

  private GenericValidation(Predicate<T> predicate) {
    this.predicate = predicate;
  }

  public static <T> GenericValidation<T> from(Predicate<T> predicate) {
    return new GenericValidation<>(predicate);
  }

  @Override
  public GenericValidationResult test(T param) {
    return predicate.test(param) ? GenericValidationResult.success() : GenericValidationResult.failed();
  }
}
