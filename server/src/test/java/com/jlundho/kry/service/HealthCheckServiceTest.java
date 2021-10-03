package com.jlundho.kry.service;

import com.jlundho.kry.models.api.CreateServiceRequest;
import com.jlundho.kry.models.core.ServiceStatus;
import com.jlundho.kry.models.db.Service;
import com.jlundho.kry.store.ServiceStore;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class HealthCheckServiceTest {

	final ServiceStore serviceStore = Mockito.mock(ServiceStore.class);
	final PollService pollService = Mockito.mock(PollService.class);

	final HealthCheckService healthCheckService = new HealthCheckService(
			serviceStore,
		pollService
	);

	@Test
	void shouldCreateNewService() {
		final var serviceName = "NewService";
		final var serviceUrl = "http://google.com";

		final var createServiceRequest = new CreateServiceRequest(
				serviceName,
				"http://google.com"
		);
		final var createdService = new Service(
				1L,
				serviceName,
				serviceUrl,
				ServiceStatus.OK
		);

		when(serviceStore.save(any(Service.class))).thenReturn(createdService);

		healthCheckService.createService(createServiceRequest);

		verify(serviceStore, times(1)).save(any(Service.class));
	}

	@Test
	void shouldDeleteService() {
		final var serviceName = "ServiceToDelete";
		final var serviceUrl = "http://google.com";

		final var serviceToDelete = new Service(
				1L,
				serviceName,
				serviceUrl,
				ServiceStatus.OK
		);

		when(serviceStore.findById(eq(serviceToDelete.getId()))).thenReturn(Optional.of(serviceToDelete));

		healthCheckService.deleteService(serviceToDelete.getId());

		verify(serviceStore, times(1)).deleteById(eq(serviceToDelete.getId()));
	}

	@Test
	void shouldUpdateService() {
		final var serviceName = "ServicetoUpdate";
		final var serviceUrl = "http://google.com";

		final var serviceToUpdate = new Service(
				1L,
				serviceName,
				serviceUrl,
				ServiceStatus.OK
		);

		when(serviceStore.findById(eq(serviceToUpdate.getId()))).thenReturn(Optional.of(serviceToUpdate));

		healthCheckService.updateService(serviceToUpdate.getId(), serviceName, serviceUrl);

		verify(serviceStore, times(1)).save(eq(serviceToUpdate));
	}

	@Test
	void shouldNotUpdateServiceIIdDoesNotExist() {
		final var serviceName = "ExistingService";
		final var serviceUrl = "http://google.com";

		final var serviceToUpdateId = 1L;

		final var updateServiceRequest = new CreateServiceRequest(
				serviceName,
				serviceUrl
		);

		when(serviceStore.findById(eq(serviceToUpdateId))).thenReturn(Optional.empty());

		Throwable exception = assertThrows(ResponseStatusException.class,
				() -> healthCheckService.updateService(serviceToUpdateId, updateServiceRequest.getName(), updateServiceRequest.getUrl()));
		assertEquals(String.format("409 CONFLICT \"A service with id %s does not exist\"", serviceToUpdateId), exception.getMessage());

		verify(serviceStore, never()).save(any(Service.class));
	}


}
