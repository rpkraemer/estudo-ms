package br.com.compasso.cepms;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.compasso.cepms.resource.dto.AddressDTO;
import br.com.compasso.cepms.resource.dto.ErrorDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CepMsApplicationTests extends BaseApplicationTests {

	private static final String VALID_CEP = "99010030";
	private static final String INVALID_CEP = "999";

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void shouldReturnAnAdressFromValidCep() {
		ResponseEntity<AddressDTO> response = restTemplate.getForEntity(cepSearchUrl(VALID_CEP), AddressDTO.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).hasNoNullFieldsOrProperties();
	}

	@Test
	public void shouldReturnAnErrorFromInvalidCep() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = restTemplate.getForEntity(cepSearchUrl(INVALID_CEP), String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThatJsonTypeIs(response.getBody(), ErrorDTO.class);
	}

	private String cepSearchUrl(final String cepNumber) {
		final String cepSearchUrl = "/cep/{cepNumber}";
		return UriComponentsBuilder.fromHttpUrl(baseUrl() + cepSearchUrl).buildAndExpand(cepNumber).toUriString();
	}

}
