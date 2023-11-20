package Buildweek2.client.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ChangeClientInfoDTO(
        @NotEmpty(message = "field contactName cannot be empty!")
        String contactName,
        @NotEmpty(message = "field contactSurname cannot be empty!")
        String contactSurname,
        @NotEmpty(message = "field contactEmail cannot be empty!")
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "insert a valid email")
        String contactEmail,
        @NotNull(message = "field contactPhone cannot be empty!")
        @Pattern(regexp = "^(\\\\+\\\\d{1,3}[- ]?)?\\\\d{8,}$", message = "insert a valid phone number")
        Long contactPhone,
        @NotNull(message = "field phone cannot be empty!")
        @Pattern(regexp = "^(\\\\+\\\\d{1,3}[- ]?)?\\\\d{8,}$", message = "insert a valid phone number")
        Long phone,
        @NotEmpty(message = "field email cannot be empty!")
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "insert a valid email")
        String email,
        @NotEmpty(message = "field pec cannot be empty!")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.pec\\.it$", message = "insert a valid pec")
        String pec
) {
}
