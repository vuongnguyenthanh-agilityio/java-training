package chapter13.product;

import chapter13.validators.ValidationUtil;

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
      throw new ProductException(errors, null);
    }
  }
}
