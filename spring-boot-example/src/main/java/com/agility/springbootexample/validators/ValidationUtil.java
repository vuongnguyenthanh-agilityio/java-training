package com.agility.springbootexample.validators;

import java.util.List;

public class ValidationUtil {

  public static final IValidation<String> notNullString = GenericValidation.from(s -> s != null);
  public static final IValidation<String> notEmptyString = GenericValidation.from(s -> !s.isEmpty());
  public static final IValidation<Object> notNullObject = GenericValidation.from(o -> o != null);
  public static final IValidation<Double> greaterThanZero = GenericValidation.from(s -> s > 0);
  public static final IValidation<List> notEmptyList = GenericValidation.from(s -> s != null && !s.isEmpty());
}
