package br.com.compasso.cepms;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Assert;
import org.springframework.boot.web.server.LocalServerPort;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseApplicationTests {

	@LocalServerPort
	private int localServerPort;

	protected ObjectMapper objectMapper = new ObjectMapper();

	/*
	 * Return base URL for rest tests
	 */
	protected String baseUrl() {
		return String.format("http://localhost:%s", localServerPort);
	}

	/*
	 * Assert json string is of type clazz
	 */
	protected <T> void assertThatJsonTypeIs(final String json, Class<T> clazz) {
		try {
			T response = objectMapper.readValue(json, clazz);
			assertThat(response).isInstanceOf(clazz);
		} catch (IOException e) {
			Assert.fail(String.format("Response should be %s", clazz.getName()));
		}
	}

}
