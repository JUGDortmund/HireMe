package conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ninja.Context;
import ninja.NinjaDefault;
import ninja.Result;

public class Ninja extends NinjaDefault{

  Logger LOG = LoggerFactory.getLogger(Ninja.class);

  @Override
  public void onRouteRequest(Context.Impl context) {
    LOG.debug(context.getRequestPath());
    super.onRouteRequest(context);
  }

  @Override
  public Result onException(Context context, Exception exception) {
    LOG.error(context.getRequestPath(), exception.getMessage());
    return super.onException(context, exception);
  }
}
