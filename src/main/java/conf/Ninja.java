package conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ninja.Context;
import ninja.NinjaDefault;
import ninja.Result;

public class Ninja extends NinjaDefault{

  Logger LOG = LoggerFactory.getLogger(Ninja.class.getSimpleName());

  @Override
  public void onRouteRequest(Context.Impl context) {
    LOG.debug(context.getRequestPath());
    super.onRouteRequest(context);
  }

  @Override
  public Result onException(Context context, Exception exception) {
    LOG.error("XXXXXXXXXXXX+ON EXCEPTION+XXXXXXXXXXXXXXX");
    LOG.error(context.getRequestPath());
    LOG.error(exception.getMessage());
    LOG.error("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    /*return super.onException(context, exception);*/
    return super.onException(context,exception);
  }

  @Override
  public void renderErrorResultAndCatchAndLogExceptions(Result result, Context context) {
    LOG.error("XXXXXXXXXXXX+renderErrorResultAndCatchAndLogExceptions+XXXXXXXXXXXXXXX");
    LOG.error("");
    LOG.error("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    super.renderErrorResultAndCatchAndLogExceptions(result, context);
  }

  @Override
  public Result getInternalServerErrorResult(Context context, Exception exception) {
    LOG.error("XXXXXXXXXXXX+getInternalServerErrorResult+XXXXXXXXXXXXXXX");
    LOG.error(exception.getMessage());
    LOG.error("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    return super.getInternalServerErrorResult(context, exception);
  }

  @Override
  public Result getNotFoundResult(Context context) {
    LOG.error("XXXXXXXXXXXX+getNotFoundResult+XXXXXXXXXXXXXXX");
    LOG.error(context.getRequestPath());
    LOG.error("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    return super.getNotFoundResult(context);
  }
}
