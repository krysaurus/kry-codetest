package com.jlundho.kry.service;

import com.jlundho.kry.models.core.ServiceStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class PollService {

  private static final Logger log = Logger.getLogger( PollService.class.getName() );

  RestTemplate restTemplate;

  @Inject
  public PollService(
    RestTemplate restTemplate
  ) {
    this.restTemplate = restTemplate;
  }

  public ServiceStatus pollServiceUrl(String serviceUrl) {
    try {
      URL u = new URL(serviceUrl);
      HttpURLConnection huc = (HttpURLConnection) u.openConnection (); huc.setRequestMethod("HEAD");

      if (huc.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM) {
        String redirect = huc.getHeaderField("Location");
        log.log(Level.INFO, String.format("Redirecting to %s", redirect));

        if (redirect != null) {
          return pollServiceUrl(redirect);  // Possible infinite recursion
        }
        return ServiceStatus.FAIL;
      }

      if (huc.getResponseCode() != HttpURLConnection.HTTP_OK) {
        log.log(Level.WARNING, String.format("Preflight request failed for url %s", serviceUrl));
        return ServiceStatus.FAIL;
      }
    } catch (IOException e) {
      log.log(Level.WARNING, String.format("Poll failed for url %s", serviceUrl));
      log.log(Level.WARNING, e.getMessage());
      return ServiceStatus.FAIL;
    }

    final var pollResult = restTemplate.getForEntity(serviceUrl, String.class);

    if (pollResult.getStatusCode().is2xxSuccessful()) {
      return ServiceStatus.OK;
    }
    return ServiceStatus.FAIL;
  }
}
