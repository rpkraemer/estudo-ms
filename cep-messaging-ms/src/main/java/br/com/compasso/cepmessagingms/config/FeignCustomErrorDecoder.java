package br.com.compasso.cepmessagingms.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignCustomErrorDecoder implements ErrorDecoder {
	private final ErrorDecoder defaultErrorDecoder = new ErrorDecoder.Default();

	public Exception decode(String methodKey, Response response) {

		if ((response.status() >= 400) && (response.status() <= 499)) {
			final byte[] body = getBodyAsByteArray(response);
			final HttpStatus status = HttpStatus.valueOf(response.status());
			return new HttpClientErrorException(status, status.getReasonPhrase(), body, null);
		}

		if ((response.status() >= 500) && (response.status() <= 599)) {
			final byte[] body = getBodyAsByteArray(response);
			final HttpStatus status = HttpStatus.valueOf(response.status());
			return new HttpServerErrorException(status, status.getReasonPhrase(), body, null);
		}

		return this.defaultErrorDecoder.decode(methodKey, response);
	}

	private byte[] getBodyAsByteArray(Response response) {
		try {
			InputStream bodyStream = response.body().asInputStream();
			return IOUtils.toByteArray(bodyStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}