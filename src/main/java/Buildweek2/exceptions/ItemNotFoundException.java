package Buildweek2.exceptions;

public class ItemNotFoundException extends RuntimeException {
  public ItemNotFoundException(Long id) {
    super("Nessun elemento con questo id: " + id);
  }

  public ItemNotFoundException(String email) {
    super("Utente con email " + email + " non trovato!");
  }
}
