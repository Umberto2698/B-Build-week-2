package Buildweek2.client.payloads;

import Buildweek2.client.BusinessName;
import Buildweek2.client.validator.ValidBusinessName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record NewClientDTO(
        @NotEmpty(message = "il campo contactName non può essere lasciato vuoto!")
        String contactName,
        @NotEmpty(message = "il campo contactSurname non può essere lasciato vuoto!")
        String contactSurname,
        @NotEmpty(message = "il campo contactEmail non può essere lasciato vuoto!")
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "inserisci un email valida")
        String contactEmail,
        @NotNull(message = "il campo contactPhone non può essere lasciato vuoto!")
        @Pattern(regexp = "^(\\\\+\\\\d{1,3}[- ]?)?\\\\d{8,}$", message = "inserisci un numero di telefono valido")
        Long contactPhone,
        @NotNull(message = "il campo phone non può essere lasciato vuoto!")
        @Pattern(regexp = "^(\\\\+\\\\d{1,3}[- ]?)?\\\\d{8,}$", message = "inserisci un numero di telefono valido")
        Long phone,
        @NotEmpty(message = "il campo companyName non può essere lasciato vuoto!")
        String companyName,
        @NotEmpty(message = "il campo VATNumber non può essere lasciato vuoto!")
        @Pattern(regexp = "^[0-9]{11}$",message = "inserisci una partita iva valida")
        String VATNumber,
        @NotEmpty(message = "il campo email non può essere lasciato vuoto!")
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "inserisci un email valida")
        String email,
        @NotNull(message = "il campo annualTurnHover non può essere lasciato vuoto!")
        Long annualTurnHover,
        @NotEmpty(message = "il campo pec non può essere lasciato vuoto!")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.pec\\.it$", message = "inserisci una pec valida")
        String pec,
        @NotEmpty(message = "il campo companyLogo non può essere lasciato vuoto!")
        String companyLogo,

        @ValidBusinessName(enumClass = BusinessName.class,
                message = "business name non valido")
        BusinessName businessName
) {
}
