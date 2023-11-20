package Buildweek2.user.payloads;

import jakarta.validation.constraints.Pattern;

public record UserUpdateInfoDTO(String name,
                                String surname,
                                @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Insert a valid email.")
                                String email,
                                String username,
                                String password) {
}
