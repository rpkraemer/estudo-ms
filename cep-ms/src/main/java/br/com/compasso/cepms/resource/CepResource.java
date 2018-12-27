package br.com.compasso.cepms.resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.compasso.cepms.resource.dto.AddressDTO;
import br.com.compasso.cepms.service.external.ViaCepService;
import br.com.compasso.cepms.service.external.converter.ViaCepConverter;
import br.com.compasso.cepms.service.external.dto.ViaCepAddressDTO;

@RestController
@RequestMapping("/cep")
public class CepResource {

	private final ViaCepService viaCepService;

	public CepResource(ViaCepService viaCepService) {
		this.viaCepService = viaCepService;
	}

	@GetMapping("/{cepNumber}")
	@Cacheable("ceps")
	public ResponseEntity<AddressDTO> getAddressByCep(@PathVariable("cepNumber") String cepNumber) {
		final ViaCepAddressDTO address = viaCepService.getAddressByCep(cepNumber);
		return new ResponseEntity<AddressDTO>(new ViaCepConverter().toAddress(address), HttpStatus.OK);
	}
}
