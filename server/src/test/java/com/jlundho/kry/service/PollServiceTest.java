package com.jlundho.kry.service;

import com.jlundho.kry.models.api.CreateServiceRequest;
import com.jlundho.kry.models.core.ServiceStatus;
import com.jlundho.kry.models.db.Service;
import com.jlundho.kry.store.ServiceStore;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PollServiceTest {

	final RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

	final PollService pollService = new PollService(
			restTemplate
	);

	@Test
	void shouldReturnOKServiceStatusWhenPollSucceeds() {
		final var serviceUrl = "http://google.com";

		final var responseEntity = new ResponseEntity(HttpStatus.OK);

		when(restTemplate.getForEntity(serviceUrl, String.class)).thenReturn(responseEntity);

		final var serviceStatus = pollService.pollServiceUrl(serviceUrl);
		assertEquals(serviceStatus, ServiceStatus.OK);
	}

	@Test
	void shouldReturnFAILServiceStatusWhenPollFails() {
		final var serviceUrl = "http://google.com";

		final var responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);

		when(restTemplate.getForEntity(serviceUrl, String.class)).thenReturn(responseEntity);

		final var serviceStatus = pollService.pollServiceUrl(serviceUrl);
		assertEquals(serviceStatus, ServiceStatus.FAIL);
	}

	@Test
	void shouldReturnFAILServiceStatusWhenPreflightRequestFails() {
		final var serviceUrl = "http://nonExistingWebsite.com";

		final var serviceStatus = pollService.pollServiceUrl(serviceUrl);
		assertEquals(serviceStatus, ServiceStatus.FAIL);
	}


}
