package br.com.compasso.cepms.proxy;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import br.com.compasso.cepms.dto.AddressDTO;

public class MockCepMsProxy {

	private final CepMsProxy mock;

	public MockCepMsProxy(CepMsProxy mock) {
		this.mock = mock;
	}

	public void toReturnValidAddress() {
		AddressDTO address = new AddressDTO();
		address.setCepNumber("99010030");
		address.setCity("Passo Fundo");
		address.setComplements("Apto 1234");
		address.setNeighborhood("Centro");
		address.setState("RS");
		address.setStreet("Rua Morom");

		when(mock.getAddressByCep(anyString())).thenReturn(address);
	}

}
