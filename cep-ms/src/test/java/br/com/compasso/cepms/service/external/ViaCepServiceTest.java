package br.com.compasso.cepms.service.external;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.compasso.cepms.exception.TechnicalException;
import br.com.compasso.cepms.service.external.dto.ViaCepAddressDTO;

@RunWith(MockitoJUnitRunner.class)
public class ViaCepServiceTest {

	@Mock
	private Environment env;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private ViaCepService viaCepService;

	@Test
	public void shouldReturnAValidCep() {
		when(env.getProperty(anyString())).thenReturn("http://viacep.com.br/ws");
		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), nullable(HttpEntity.class),
				eq(ViaCepAddressDTO.class)))
						.thenReturn(new ResponseEntity<ViaCepAddressDTO>(new ViaCepAddressDTO(), HttpStatus.OK));

		viaCepService.getAddressByCep("99010030");

		verify(env, only()).getProperty(anyString());
		verify(restTemplate, only()).exchange(eq("http://viacep.com.br/ws/99010030/json"), eq(HttpMethod.GET),
				nullable(HttpEntity.class), eq(ViaCepAddressDTO.class));

	}

	@Test
	public void shouldReturnAnError() {
		final HttpClientErrorException ex = new HttpClientErrorException(HttpStatus.BAD_GATEWAY,
				HttpStatus.BAD_REQUEST.getReasonPhrase(), "Erro na ViaCEP".getBytes(), null);

		when(env.getProperty(anyString())).thenReturn("http://viacep.com.br/ws");
		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), nullable(HttpEntity.class),
				eq(ViaCepAddressDTO.class))).thenThrow(ex);

		try {
			viaCepService.getAddressByCep("99010030");
			Assert.fail("Deveria ter lançado exceção");
		} catch (TechnicalException e) {
			/*
			 * Esperado
			 */
		}
		verify(env, only()).getProperty(anyString());
	}
}
