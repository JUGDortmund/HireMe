package exception;

/**
 * Exception to indicate error during conversion of a resource
 * 
 * @author plewe
 */
public class ResourceConversionException extends RuntimeException {

  final static String DEFAULT_MESSAGE = "The given resource could not be converted";

  public ResourceConversionException() {
    super(DEFAULT_MESSAGE);
  }

  public ResourceConversionException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ResourceConversionException(String message, Throwable cause) {
    super(message, cause);
  }

  public ResourceConversionException(String message) {
    super(message);
  }

  public ResourceConversionException(Throwable cause) {
    super(DEFAULT_MESSAGE, cause);
  }

}
