package chapter13.validators;

@FunctionalInterface
public interface Validation <T>{
  public GenericValidationResult test(T param);

  default Validation<T> and(Validation<T> other) {
    return param -> {
      GenericValidationResult result = this.test(param);
      return !result.isValid() ? result : other.test(param);
    };
  }

  default Validation<T> or(Validation<T> other) {
    return param -> {
      GenericValidationResult result = this.test(param);
      return result.isValid() ? result : other.test(param);
    };
  }
}
