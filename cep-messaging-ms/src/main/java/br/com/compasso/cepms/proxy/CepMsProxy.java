package br.com.compasso.cepms.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.compasso.cepms.dto.AddressDTO;

@FeignClient("cep-ms")
public interface CepMsProxy {

	@GetMapping({ "/cep/{cepNumber}" })
	AddressDTO getAddressByCep(@PathVariable("cepNumber") String cepNumber);
}
