package br.com.compasso.cepms.service.external;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

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
		// Given
		given(env.getProperty(anyString())).willReturn("http://viacep.com.br/ws");
		given(restTemplate.exchange(anyString(), eq(HttpMethod.GET), nullable(HttpEntity.class),
				eq(ViaCepAddressDTO.class))).willReturn(validCepResponse());

		// When
		ViaCepAddressDTO foundedAddress = viaCepService.getAddressByCep("99010030");

		// Then
		then(env).should(only()).getProperty(anyString());
		then(restTemplate).should(only()).exchange(eq("http://viacep.com.br/ws/99010030/json"), eq(HttpMethod.GET),
				nullable(HttpEntity.class), eq(ViaCepAddressDTO.class));

		assertThat(foundedAddress).isEqualToComparingFieldByField(validCepResponse().getBody());
	}

	private ResponseEntity<ViaCepAddressDTO> validCepResponse() {
		ViaCepAddressDTO body = new ViaCepAddressDTO();
		body.setBairro("Teste");
		body.setCep("99010030");
		body.setComplemento("Teste");
		body.setLocalidade("Passo Fundo");
		body.setLogradouro("Teste");
		body.setUf("RS");

		return new ResponseEntity<ViaCepAddressDTO>(body, HttpStatus.OK);
	}

	@Test
	public void shouldReturnAnError() {
		final HttpClientErrorException ex = new HttpClientErrorException(HttpStatus.BAD_GATEWAY,
				HttpStatus.BAD_REQUEST.getReasonPhrase(), "Erro na ViaCEP".getBytes(), null);

		// Given
		given(env.getProperty(anyString())).willReturn("http://viacep.com.br/ws");
		given(restTemplate.exchange(anyString(), eq(HttpMethod.GET), nullable(HttpEntity.class),
				eq(ViaCepAddressDTO.class))).willThrow(ex);

		// When
		try {
			viaCepService.getAddressByCep("99010030");
			Assert.fail("Deveria ter lançado exceção");
		} catch (TechnicalException e) {
			// Then
			then(env).should(only()).getProperty(anyString());
		}
	}
}
