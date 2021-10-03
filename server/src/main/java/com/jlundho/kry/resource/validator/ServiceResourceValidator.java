package com.jlundho.kry.resource.validator;

import com.jlundho.kry.models.api.CreateServiceRequest;
import com.jlundho.kry.models.api.UpdateServiceRequest;
import com.jlundho.kry.models.api.Validation;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class ServiceResourceValidator {

  public static Validation validate(CreateServiceRequest request) {
    final var validation = validate(request.getName(), request.getUrl());
    if (validation.isFailed()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validation.getFailureReason());
    }
    return validation;
  }

  public static Validation validate(UpdateServiceRequest request) {
    return validate(request.getName(), request.getUrl());
  }

  private static Validation validate(String name, String url) {
    try {
      new URL(url).toURI();
    } catch (MalformedURLException | URISyntaxException exception) {
      return new Validation(true, "Invalid URL");
    }
    if ("".equals(name)) {
      return new Validation(true, "Service name should not be empty");
    }
    return new Validation(false, null);
  }
}
