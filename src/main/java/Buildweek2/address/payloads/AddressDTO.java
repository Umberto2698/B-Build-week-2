package Buildweek2.address.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AddressDTO(@NotEmpty(message = "The street is a required field")
                         String street,
                         @NotEmpty(message = "The civic is a required field")
                         String civic,
                         @NotEmpty(message = "The location is a required field")
                         String location,
                         @NotNull(message = "The postalCode is a required field")
                         String postalCode,
                         @NotEmpty(message = "The municipallyId is a required field")
                         Long municipallyId,
                         @NotEmpty(message = "The clientId is a required field")
                         Long clientId
) {
}