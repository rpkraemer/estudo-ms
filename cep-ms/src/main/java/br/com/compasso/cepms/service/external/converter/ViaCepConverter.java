package br.com.compasso.cepms.service.external.converter;

import br.com.compasso.cepms.resource.dto.AddressDTO;
import br.com.compasso.cepms.service.external.dto.ViaCepAddressDTO;

public class ViaCepConverter {

	public AddressDTO toAddress(ViaCepAddressDTO address) {
		AddressDTO domainAddress = new AddressDTO();
		domainAddress.setCepNumber(address.getCep());
		domainAddress.setCity(address.getLocalidade());
		domainAddress.setComplements(address.getComplemento());
		domainAddress.setNeighborhood(address.getBairro());
		domainAddress.setState(address.getUf());
		domainAddress.setStreet(address.getLogradouro());

		return domainAddress;
	}

}
