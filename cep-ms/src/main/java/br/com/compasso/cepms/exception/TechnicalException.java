package br.com.compasso.cepms.exception;

import org.springframework.web.client.RestClientResponseException;

public class TechnicalException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TechnicalException(RestClientResponseException e) {
		super(e.getRawStatusCode() + " - " + e.getResponseBodyAsString());
	}

}
