package services;


import com.google.common.base.Optional;
import com.google.inject.Inject;

import ninja.Context;
import ninja.Result;
import ninja.i18n.Lang;
import ninja.i18n.Messages;

public class LocalizationService {

  private static String DEFAULT_DATEFORMAT = "MM/yyyy";

  @Inject
  private Lang lang;

  @Inject
  Messages msg;

  public Optional<String> getLanguage(Context context) {
    Optional<Result> resultOptional = Optional.absent();
    return lang.getLanguage(context, resultOptional);
  }

  private String generatePattern(Optional<String> language) {
    return msg.get("dateFormat", language).or(DEFAULT_DATEFORMAT);
  }

  public String getPattern(Context context) {
    return generatePattern(getLanguage(context));
  }
}
