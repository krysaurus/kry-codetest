package com.jlundho.kry.poller;

import com.jlundho.kry.models.core.ServiceStatus;
import com.jlundho.kry.models.db.Service;
import com.jlundho.kry.service.HealthCheckService;
import com.jlundho.kry.service.PollService;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.IOException;
import java.net.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class HttpPoller {

  private static final Logger log = Logger.getLogger( HttpPoller.class.getName() );
  private final int POLLING_INTERVAL_IN_MS = 10000;

    private final Map<Long, Instant> pollMap;

    private final HealthCheckService healthCheckService;
    private final PollService pollService;

    @Inject
    public HttpPoller(
        HealthCheckService healthCheckService,
        PollService pollService
    ) {
        this.healthCheckService = healthCheckService;
        this.pollService = pollService;
        this.pollMap = new HashMap();
    }

    @Scheduled(fixedDelay = POLLING_INTERVAL_IN_MS)
    public void scheduledPoll() {
      healthCheckService.getServices()
        .stream()
        .forEach(service -> {
            final var serviceKey = service.getId();
            if (!pollMap.containsKey(serviceKey)) {
                pollMap.put(serviceKey, Instant.now());
                return;
            }

          final var lastTimePolled = pollMap.get(service.getId());
          if (lastTimePolled.isBefore(Instant.now().minusMillis(POLLING_INTERVAL_IN_MS))) {
            final var pollResult = pollService.pollServiceUrl(service.getUrl());

            pollMap.put(serviceKey, Instant.now());

            if (ServiceStatus.FAIL.equals(service.getStatus()) && ServiceStatus.OK.equals(pollResult)) {
              setHealthCheckOK(service);
            } else if (ServiceStatus.OK.equals(service.getStatus()) && ServiceStatus.FAIL.equals(pollResult)) {
              setHealthCheckFailed(service);
            }
          }
        });
    }

  private void setHealthCheckOK(Service service) {
    log.info(String.format("Set health of %s to OK", service.getName()));
    healthCheckService.setServiceStatus(service, ServiceStatus.OK);
  }

  private void setHealthCheckFailed(Service service) {
    log.info(String.format("Set health of %s to FAIL", service.getName()));
    healthCheckService.setServiceStatus(service, ServiceStatus.FAIL);
  }
}
