package br.com.compasso.cepms.service.external;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.compasso.cepms.exception.TechnicalException;
import br.com.compasso.cepms.service.external.dto.ViaCepAddressDTO;

@Service
public class ViaCepService {

	public static final String EXTERNAL_SERVICES_VIACEP_URL = "external-services.viacep.url";

	private final RestTemplate restTemplate;
	private final Environment environment;

	public ViaCepService(RestTemplate restTemplate, Environment environment) {
		this.restTemplate = restTemplate;
		this.environment = environment;
	}

	public ViaCepAddressDTO getAddressByCep(final String cepNumber) {
		final String viaCepUrl = environment.getProperty(EXTERNAL_SERVICES_VIACEP_URL);

		final String url = UriComponentsBuilder.fromHttpUrl(viaCepUrl).path("/{cepNumber}/json")
				.buildAndExpand(cepNumber).toUriString();

		try {
			ResponseEntity<ViaCepAddressDTO> response = restTemplate.exchange(url, HttpMethod.GET, null,
					ViaCepAddressDTO.class);
			return response.getBody();
		} catch (RestClientResponseException e) {
			throw new TechnicalException(e);
		}
	}

}
