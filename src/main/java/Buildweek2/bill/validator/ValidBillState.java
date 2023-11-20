package Buildweek2.bill.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BillValidator.class)
public @interface ValidBillState {


        String message() default "The value entered is invalid";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        Class<? extends Enum<?>> enumClass();

}
