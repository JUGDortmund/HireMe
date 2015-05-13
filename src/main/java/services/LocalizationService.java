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

  private Optional<String> language;

  private String pattern;

  private Optional<String> generateLanguage(Context context){
    Optional<Result> resultOptional = Optional.absent();
    Optional<String> optionalValue = lang.getLanguage(context, resultOptional);
    return (optionalValue != null || optionalValue.get() != null) ? optionalValue : Optional.of("en-Us");
  }

  private String generatePattern(Optional<String> optionalValue){
    Locale locale = lang.getLocaleFromStringOrDefault(optionalValue);
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
    return ((SimpleDateFormat) dateFormat).toPattern().toLowerCase();
  }

  public Optional<String> getLanguage(Context context) {
    if (language == null) {
      language = generateLanguage(context);
    }
    return language;
  }

  public String getPattern(Context context) {
    if (pattern == null) {
      pattern = generatePattern(getLanguage(context));
    }
    return pattern;
  }
}
