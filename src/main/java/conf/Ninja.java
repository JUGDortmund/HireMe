package conf;

import exception.ElementNotFoundException;
import ninja.Context;
import ninja.NinjaDefault;
import ninja.Result;
import ninja.Results;
import ninja.exceptions.BadRequestException;
import ninja.exceptions.RenderingException;

public class Ninja extends NinjaDefault {

  private static final String INTERNALSERVERERRORRESULT_URL = "/505";
  private static final String BADREQUEST_URL = "/400";
  private static final String UNAUTHORIZED_URL = "/401";
  private static final String FORBIDDEN_URL = "/403";
  private static final String NOTFOUND_URL = "/404";

  @Override
  public Result getInternalServerErrorResult(Context context, Exception exception) {
    return getRedirectToInternalServerErrorResult(context, exception);
  }

  @Override
  public Result getBadRequestResult(Context context, Exception exception) {
    return getRedirectToBadRequestResult(context, exception);
  }

  @Override
  public Result getUnauthorizedResult(Context context) {
    return getRedirectToUnauthorizedResult(context);
  }

  @Override
  public Result getNotFoundResult(Context context) {
    return getRedirectToNotFoundResult(context);
  }

  @Override
  public Result getForbiddenResult(Context context) {
    return getRedirectToForbiddenResult(context);
  }

  @Override
  public Result onException(Context context, Exception exception) {

    Result result;

    if (exception instanceof BadRequestException) {
      result = getBadRequestResult(context, exception);
    } else if (exception instanceof RenderingException) {
      RenderingException renderingException = (RenderingException) exception;
      result = getRenderingExceptionResult(context, renderingException);
    } else if (exception instanceof ElementNotFoundException) {
      result = getNotFoundResult(context);
    } else {
      result = getInternalServerErrorResult(context, exception);
    }

    return result;

  }

  private Result getRedirectToInternalServerErrorResult(Context context, Exception exception) {
    return Results.redirect(INTERNALSERVERERRORRESULT_URL);
  }

  private Result getRedirectToBadRequestResult(Context context, Exception exception) {
    return Results.redirect(BADREQUEST_URL);
  }

  private Result getRedirectToNotFoundResult(Context context) {
    return Results.redirect(NOTFOUND_URL);
  }

  private Result getRedirectToUnauthorizedResult(Context context) {
    return Results.redirect(UNAUTHORIZED_URL);
  }

  private Result getRedirectToForbiddenResult(Context context) {
    return Results.redirect(FORBIDDEN_URL);
  }
}
