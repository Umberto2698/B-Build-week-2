package Buildweek2.user.payloads;

import Buildweek2.user.UserRole;
import Buildweek2.user.validator.ValidRole;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record UserDTO(@NotEmpty(message = "The name is required.")
                      String name,
                      @NotEmpty(message = "The surname is required.")
                      String surname,
                      @NotEmpty(message = "The password is required.")
                      String password,
                      @NotEmpty(message = "The email is required.")
                      @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Insert a valid email.")
                      String email,
                      String username/*,
                      @ValidRole(enumClass = UserRole.class, message = "The type of device must be one of these:" +
                              "USER, ADMIN")
                      UserRole role*/
) {
}
