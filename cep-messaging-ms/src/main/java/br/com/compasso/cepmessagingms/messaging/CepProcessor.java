package br.com.compasso.cepmessagingms.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CepProcessor {

	public static final String CEP_TO_ADDRESS_REQUEST = "cepToAddressRequest";
	public static final String CEP_TO_ADDRESS_PROCESSED = "cepToAddressProcessed";
	public static final String CEP_REQUEST_ERRORS = "cepRequestErrors";

	@Input(CEP_TO_ADDRESS_REQUEST)
	SubscribableChannel cepToAddressRequest();

	@Output(CEP_TO_ADDRESS_PROCESSED)
	MessageChannel cepToAddressProcessed();

	@Output(CEP_REQUEST_ERRORS)
	MessageChannel cepRequestErrors();

}