package Buildweek2.Address;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AddressDTO(@NotEmpty(message = "The street is a required field")
                         String street,
                         @NotEmpty(message = "The civic is a required field")
                         String civic,
                         @NotEmpty(message = "The location is a required field")
                         String location,
                         @NotNull(message = "The postalCode is a required field")
                         Long postalCode,
                         @NotEmpty(message = "The location is a required field")
                         Long municipally,
                         @NotEmpty(message = "The company id is a required field")
                         String company

) {
}