package com.jlundho.kry.resource;

import com.jlundho.kry.models.api.CreateServiceRequest;
import com.jlundho.kry.models.api.UpdateServiceRequest;
import com.jlundho.kry.models.core.ServiceStatus;
import com.jlundho.kry.models.db.Service;
import com.jlundho.kry.service.HealthCheckService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ServiceResourceTest {

	final HealthCheckService healthCheckService = Mockito.mock(HealthCheckService.class);

	final ServiceResource serviceResource = new ServiceResource(
			healthCheckService
	);

	@Test
	void shouldCreateServiceWhenNameAndValidUrlExists() {
		final var serviceName = "NewService";
		final var serviceUrl = "https://google.com";

		final var createServiceRequest = new CreateServiceRequest(
				serviceName,
				serviceUrl
		);

		final var createdService = new Service(
				1L,
				serviceName,
				serviceUrl,
				ServiceStatus.OK
		);

		when(healthCheckService.createService(eq(createServiceRequest))).thenReturn(createdService);

		serviceResource.create(createServiceRequest);

		verify(healthCheckService, times(1)).createService(eq(createServiceRequest));
	}

	@Test
	void shouldFailRequestOnInvalidUrl() {
		final var serviceName = "NewService";
		final var serviceUrl = "mysql://google.com";

		final var createServiceRequest = new CreateServiceRequest(
				serviceName,
				serviceUrl
		);

		Throwable exception = assertThrows(ResponseStatusException.class,
				() -> serviceResource.create(createServiceRequest));
		assertEquals("400 BAD_REQUEST \"Invalid URL\"", exception.getMessage());
	}

	@Test
	void shouldFailRequestOnEmptyServiceName() {
		final var serviceName = "";
		final var serviceUrl = "mysql://google.com";

		final var createServiceRequest = new CreateServiceRequest(
				serviceName,
				serviceUrl
		);

		Throwable exception = assertThrows(ResponseStatusException.class,
				() -> serviceResource.create(createServiceRequest));
		assertEquals("400 BAD_REQUEST \"Invalid URL\"", exception.getMessage());
	}

	@Test
	void shouldFailRequestOnEmptyUrl() {
		final var serviceName = "ServiceName";
		final var serviceUrl = "";

		final var createServiceRequest = new CreateServiceRequest(
				serviceName,
				serviceUrl
		);

		Throwable exception = assertThrows(ResponseStatusException.class,
				() -> serviceResource.create(createServiceRequest));
		assertEquals("400 BAD_REQUEST \"Invalid URL\"", exception.getMessage());
	}


	@Test
	void shouldCallDeleteService() {
		final var serviceId = 1L;

		serviceResource.delete(serviceId);

		verify(healthCheckService, times(1)).deleteService(eq(serviceId));
	}

	@Test
	void shouldCallUpdateervice() {
		final var serviceId = 1L;
		final var updatedName = "UpdatedServiceName";
		final var updatedUrl = "UpdatedUrl";

		final var updateServiceRequest = new UpdateServiceRequest(
				updatedName,
				updatedUrl
		);

		serviceResource.update(serviceId, updateServiceRequest);

		verify(healthCheckService, times(1)).updateService(eq(serviceId), eq(updatedName), eq(updatedUrl));
	}
}
