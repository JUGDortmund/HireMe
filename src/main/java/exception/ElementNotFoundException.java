package exception;

import ninja.Result;
import ninja.exceptions.NinjaException;

public class ElementNotFoundException extends NinjaException {

  final static String DEFAULT_MESSAGE = "The requested element was not found";

  public ElementNotFoundException() {
    super(Result.SC_404_NOT_FOUND, DEFAULT_MESSAGE);
  }

  public ElementNotFoundException(String message) {
    super(Result.SC_404_NOT_FOUND, message);
  }

  public ElementNotFoundException(String message, Throwable cause) {
    super(Result.SC_404_NOT_FOUND, message, cause);
  }

  public ElementNotFoundException(Throwable cause) {
    super(Result.SC_404_NOT_FOUND, DEFAULT_MESSAGE, cause);
  }
}
