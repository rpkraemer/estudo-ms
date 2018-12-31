package br.com.compasso.cepms.proxy;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import br.com.compasso.cepms.dto.AddressDTO;

public class MockCepMsProxy {

	private final CepMsProxy mock;

	public MockCepMsProxy(CepMsProxy mock) {
		this.mock = mock;
	}

	public void toReturn(AddressDTO address) {
		when(mock.getAddressByCep(anyString())).thenReturn(address);
	}

}
