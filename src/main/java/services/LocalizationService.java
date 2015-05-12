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
    return optionalValue;
  }

  private String generateDateFormat(Optional<String> optionalValue){
    Locale locale = lang.getLocaleFromStringOrDefault(optionalValue);
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
    return ((SimpleDateFormat) dateFormat).toPattern().toLowerCase();
  }

  public Optional<String> getLanguage(Context context) {
    if (language == null) {
      Optional<String> tempLanguage = generateLanguage(context);
      language = tempLanguage != null ? tempLanguage : Optional.of("en-Us");
    }
    return language;
  }

  public String getPattern(Context context) {
    if (pattern == null) {
      pattern = generateDateFormat(getLanguage(context));
    }
    return pattern;
  }
}
