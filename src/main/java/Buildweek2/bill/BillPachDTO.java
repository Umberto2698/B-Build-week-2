package Buildweek2.bill;

import Buildweek2.bill.validator.ValidBillState;

public record BillPachDTO(@ValidBillState(enumClass = BillState.class,
        message = "The Bill can be:" +
                "  PAID or UNPAID") BillState billState
) {
}
