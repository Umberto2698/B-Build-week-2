package Buildweek2.bill.Payloads;

import Buildweek2.bill.Bill;

import java.util.List;

public record FindByPartialCompanyNameDTO(List<List<Bill>> bills) {
}
