package Buildweek2.exceptions;

public class NotFoundException extends RuntimeException {
  public NotFoundException(Long id) {
    super("There is no item with this id: " + id);
  }

  public NotFoundException(String message) {
    super(message);
  }


}
