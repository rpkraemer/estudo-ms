package br.com.compasso.cepmessagingms.messaging.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public class CepToAddressRequest {

	private String cepNumber;

	public String getCepNumber() {
		return this.cepNumber;
	}

	public void setCepNumber(String cepNumber) {
		this.cepNumber = cepNumber;
	}

	public String toString() {
		return "CepRequest [cepNumber=" + this.cepNumber + "]";
	}
}
