package fr.test.marche_energie.domain.exceptions;

public class NonTrouveException extends RuntimeException {
  public NonTrouveException(String message) {
    super(message);
  }

  public NonTrouveException(String message, Throwable cause) {
    super(message, cause);
  }
}
