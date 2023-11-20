package Buildweek2.exceptions.ExceptionPayloads;

import java.util.Date;

public record ErrorsResponseDTO(String message, Date timestamp) {
}
