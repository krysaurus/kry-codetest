package com.jlundho.kry.resource;


import com.jlundho.kry.models.api.CreateServiceRequest;
import com.jlundho.kry.models.api.ServiceIOv1;
import com.jlundho.kry.models.api.UpdateServiceRequest;
import com.jlundho.kry.models.db.Service;
import com.jlundho.kry.service.HealthCheckService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static com.jlundho.kry.resource.validator.ServiceResourceValidator.validate;

@RestController
@RequestMapping(value = "/service")
public class ServiceResource {

    HealthCheckService healthCheckService;

    @Inject
    public ServiceResource(
        HealthCheckService healthCheckService
    ) {
        this.healthCheckService = healthCheckService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ServiceIOv1> getServices() {
        return healthCheckService.getServices()
            .stream()
            .map(this::toService)
            .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ServiceIOv1 create(
        @RequestBody CreateServiceRequest request
    ) {
        validate(request);
        return toService(healthCheckService.createService(request));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        healthCheckService.deleteService(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public void update(@PathVariable("id") Long id,
        @RequestBody UpdateServiceRequest request
    ) {
        validate(request);
        healthCheckService.updateService(id, request.getName(), request.getUrl());
    }

    private ServiceIOv1 toService(Service service) {
    return new ServiceIOv1(
        service.getId(),
        service.getName(),
        service.getUrl(),
        service.getStatus(),
        service.getUpdatedAt(),
        service.getCreatedAt()
    );
  }
}
