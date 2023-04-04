package com.example.masterwork.errorhandling;

import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExceptionHandlerUtility {

  public String createErrorMessageForMissingFields(List<FieldError> fieldErrors) {
    if (fieldErrors == null || fieldErrors.isEmpty()) return "";

    String suffix;
    String missingFields = fieldErrors.stream()
            .map(FieldError::getField)
            .collect(Collectors.joining(", "));
    if (fieldErrors.size() == 1) {
      suffix = " is required.";
    } else {
      suffix = " are required.";
      int lastDelimiter = missingFields.lastIndexOf(",");
      StringBuilder sb = new StringBuilder(missingFields);
      sb.replace(lastDelimiter, lastDelimiter + 1, " and");
      missingFields = sb.toString();
    }
    String capitalizedLetter = missingFields.substring(0, 1).toUpperCase();
    String remainingLetters = missingFields.substring(1);
    return capitalizedLetter + remainingLetters + suffix;
  }

}
