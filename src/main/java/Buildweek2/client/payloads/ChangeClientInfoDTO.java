package Buildweek2.client.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ChangeClientInfoDTO(
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
        @NotEmpty(message = "il campo email non può essere lasciato vuoto!")
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "inserisci un email valida")
        String email,
        @NotEmpty(message = "il campo pec non può essere lasciato vuoto!")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.pec\\.it$", message = "inserisci una pec valida")
        String pec
) {
}
