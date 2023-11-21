package Buildweek2.bill.Payloads;

import Buildweek2.bill.BillState;
import Buildweek2.bill.validator.ValidBillState;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record BillDTO(
        @NotNull(message = "Amount field is required!") @Min(value = 1, message = "Value amount must be greater than or equal to 1 ") long amount,
        @NotEmpty(message = "Client Id Field Required!") Long clientId,
        @NotEmpty(message = "User Id Field Required!") Long userId,
        @ValidBillState(enumClass = BillState.class,
                message = "The Bill can be:" +
                        "  PAID or UNPAID") BillState billState

) {
}
