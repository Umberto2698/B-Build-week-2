package Buildweek2.client.payloads;

import Buildweek2.client.BusinessName;
import Buildweek2.client.validator.ValidBusinessName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.Date;

public record NewClientDTO(
        @NotEmpty(message = "field contactName cannot be empty!")
        String contactName,
        @NotEmpty(message = "field contactSurname cannot be empty!")
        String contactSurname,
        @NotEmpty(message = "field contactEmail cannot be empty!")
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "insert a valid email")
        String contactEmail,
        @NotNull(message = "field contactPhone cannot be empty!")
        @Pattern(regexp = "^(\\\\+\\\\d{1,3}[- ]?)?\\\\d{8,}$", message = "insert a valid phone number")
        String contactPhone,
        @NotNull(message = "field phone cannot be empty!")
        @Pattern(regexp = "^(\\\\+\\\\d{1,3}[- ]?)?\\\\d{8,}$", message = "insert a valid phone number")
        String phone,
        Date insertDate,
        LocalDate lastContractDate,
        @NotEmpty(message = "field companyName cannot be empty!")
        String companyName,
        @NotEmpty(message = "field VATNumber cannot be empty!")
        @Pattern(regexp = "^[0-9]{11}$", message = "insert a valid VAT")
        String VATNumber,
        @NotEmpty(message = "field email cannot be empty!")
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "insert a valid email")
        String email,
        @NotNull(message = "field annualTurnHover cannot be empty!")
        Long annualTurnHover,
        @NotEmpty(message = "field pec cannot be empty!")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.pec\\.it$", message = "insert a valid pec")
        String pec,
        @NotEmpty(message = "field companyLogo cannot be empty!")
        String companyLogo,

        @ValidBusinessName(enumClass = BusinessName.class,
                message = "business name not valid")
        String businessName
) {
}
