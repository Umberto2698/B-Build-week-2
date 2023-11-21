package Buildweek2.user.payloads;

import Buildweek2.user.UserRole;
import Buildweek2.user.validator.ValidRole;

public record RoleUpdateDTO(
        @ValidRole(enumClass = UserRole.class, message = "The type of device must be one of these:" +
                "USER, ADMIN")
        String role) {
}
