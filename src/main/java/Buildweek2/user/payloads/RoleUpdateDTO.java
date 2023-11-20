package Buildweek2.user.payloads;

import Buildweek2.user.UserRole;
import Buildweek2.user.validator.ValidRole;
import jakarta.validation.constraints.NotNull;

public record RoleUpdateDTO(@NotNull(message = "The role is required.")
                            @ValidRole(enumClass = UserRole.class, message = "The type of device must be one of these:" +
                                    "USER, ADMIN")
                            UserRole role) {
}
