package services;


import com.google.common.base.Optional;
import com.google.inject.Inject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import ninja.Context;
import ninja.Result;
import ninja.i18n.Lang;

public class LocalizationService {
  @Inject
  private Lang lang;

  public Optional<String> getLanguage(Context context) {
    Optional<Result> resultOptional = Optional.absent();
    return lang.getLanguage(context, resultOptional);
  }

  private String generatePattern(Optional<String> optionalValue) {
    Locale locale = lang.getLocaleFromStringOrDefault(optionalValue);
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
    return ((SimpleDateFormat) dateFormat).toPattern().toLowerCase();
  }

  public String getPattern(Context context) {
    return generatePattern(getLanguage(context));
  }
}
