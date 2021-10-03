package com.jlundho.kry.service;

import com.jlundho.kry.models.api.CreateServiceRequest;
import com.jlundho.kry.models.core.ServiceStatus;
import com.jlundho.kry.models.db.Service;
import com.jlundho.kry.store.ServiceStore;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.inject.Inject;
import java.util.List;

@Component
public class HealthCheckService {

  private ServiceStore serviceStore;
  private PollService pollService;

  @Inject
  public HealthCheckService(
    ServiceStore serviceStore,
    PollService pollService
  ) {
    this.serviceStore = serviceStore;
    this.pollService = pollService;
  }

  public List<Service> getServices() {
    return serviceStore.findAll();
  }

  public Service createService(CreateServiceRequest request) {
    final var initialStatus = pollService.pollServiceUrl(request.getUrl());

    final var persisted = serviceStore.save(new Service(
      null,
      request.getName(),
      request.getUrl(),
        initialStatus
    ));
    return persisted;
  }

  public void setServiceStatus(Service service, ServiceStatus status) {
    service.setStatus(status);
    serviceStore.save(service);
  }

  public void deleteService(Long id) {
    serviceStore.findById(id)
      .ifPresent(service -> serviceStore.deleteById(id));
  }

  public Service updateService(Long id, String serviceName, String url) {
    final var service = serviceStore.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, String.format("A service with id %s does not exist", id)));

    final var serviceToUpdate = new Service(service);
    serviceToUpdate.setName(serviceName);
    serviceToUpdate.setUrl(url);
    return serviceStore.save(service);
  }
}
