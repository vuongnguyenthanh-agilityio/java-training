package com.agility.springbootexample.validators;

@FunctionalInterface
public interface IValidation<T>{
  public GenericValidationResult test(T param);

  default IValidation<T> and(IValidation<T> other) {
    return param -> {
      GenericValidationResult result = this.test(param);
      return !result.isValid() ? result : other.test(param);
    };
  }

  default IValidation<T> or(IValidation<T> other) {
    return param -> {
      GenericValidationResult result = this.test(param);
      return result.isValid() ? result : other.test(param);
    };
  }
}
