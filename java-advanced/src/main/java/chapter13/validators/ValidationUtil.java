package chapter13.validators;

import java.util.List;

public class ValidationUtil {

  public static final Validation<String> notNullString = GenericValidation.from(s -> s != null);
  public static final Validation<String> notEmptyString = GenericValidation.from(s -> !s.isEmpty());
  public static final Validation<Object> notNullObject = GenericValidation.from(o -> o != null);
  public static final Validation<Double> greaterThanZero = GenericValidation.from(s -> s > 0);
  public static final Validation<List> notEmptyList = GenericValidation.from(s -> s != null && !s.isEmpty());
}
