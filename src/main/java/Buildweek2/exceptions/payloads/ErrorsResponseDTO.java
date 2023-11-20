package Buildweek2.exceptions.payloads;

import java.util.Date;

public record ErrorsResponseDTO(String message, Date timestamp) {
}
