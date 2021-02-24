package com.agility.springbootexample.product;

import com.agility.springbootexample.validators.ValidationUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ProductValidation implements IProductValidation {
  @Override
  public void validate(Product product) {
    StringBuffer errorFields = new StringBuffer();

    errorFields.append(ValidationUtil.notNullString
        .and(ValidationUtil.notEmptyString)
        .test(product.getName())
        .getFieldNameInvalid("The name is not empty.")
        .orElse("")
    );

    errorFields.append(ValidationUtil.greaterThanZero
        .test(product.getPrice())
        .getFieldNameInvalid("The price greater than zero")
        .orElse("")
    );

    String errors = errorFields.toString();
    if(!errors.isEmpty()) {
      throw new ProductBadRequestException(errors, null);
    }
  }
}
