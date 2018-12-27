package br.com.compasso.cepmessagingms.messaging;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import br.com.compasso.cepmessagingms.messaging.domain.CepToAddressRequest;
import br.com.compasso.cepms.dto.AddressDTO;
import br.com.compasso.cepms.proxy.CepMsProxy;

@EnableBinding(CepProcessor.class)
public class CepBrokerProcessor {

	private static final long MAX_RETRIES = 3L;
	private static final Logger log = LoggerFactory.getLogger(CepBrokerProcessor.class);

	private final CepProcessor processor;
	private final CepMsProxy cepMsProxy;

	public CepBrokerProcessor(CepProcessor processor, CepMsProxy cepMsProxy) {
		this.processor = processor;
		this.cepMsProxy = cepMsProxy;
	}

	@StreamListener(value = CepProcessor.CEP_TO_ADDRESS_REQUEST)
	public void processCepToAddressRequest(@Payload Message<CepToAddressRequest> cepRequest,
			@Headers MessageHeaders headers) throws Exception {

		log.info("Processando cep request: {}", cepRequest.toString());
		log.info("Message headers: {}", headers.toString());

		boolean shouldContinue = treatReprocessing(cepRequest, headers);
		if (!shouldContinue) {
			return;
		}

		try {

			AddressDTO address = this.cepMsProxy.getAddressByCep(cepRequest.getPayload().getCepNumber());
			this.processor.cepToAddressProcessed().send(MessageBuilder.withPayload(address).build());

		} catch (HttpServerErrorException e) {
			log.error("Falha ao invocar cep-ms: " + e.getResponseBodyAsString());
			log.error("Republicando mensagem original na fila de erros: cepRequestErrors");

			this.processor.cepRequestErrors().send(MessageBuilder.fromMessage(cepRequest)
					.setHeader("x-exception-message", e.getResponseBodyAsString()).build());

		} catch (HttpClientErrorException e) {
			log.error("Falha ao invocar cep-ms: " + e.getResponseBodyAsString());
			log.error("Enviando mensagem para a dead letter queue: " + CepProcessor.CEP_TO_ADDRESS_REQUEST + ".dlq");
			throw e;
		} catch (Exception e) {
			log.error("Falha desconhecida ao invocar cep-ms: " + e.getMessage());
			log.error("Enviando mensagem para a dead letter queue: " + CepProcessor.CEP_TO_ADDRESS_REQUEST + ".dlq");
			throw e;
		}
	}

	private boolean treatReprocessing(Message<CepToAddressRequest> cepRequest, MessageHeaders headers) {
		boolean shouldContinue = true;

		List<?> xDeathHeader = (List<?>) headers.get("x-death", List.class);

		if (xDeathHeader != null) {
			Map<?, ?> xDeath = (Map<?, ?>) xDeathHeader.get(0);
			if (xDeath != null) {
				long actualCount = ((Long) xDeath.get("count")).longValue();
				if (actualCount >= MAX_RETRIES) {
					String erro = String.format("Falha de processamento após %s tentativas", (actualCount + 1L));

					this.processor.cepRequestErrors().send(
							MessageBuilder.fromMessage(cepRequest).setHeader("x-exception-message", erro).build());

					shouldContinue = false;
				}
			}
		}

		return shouldContinue;
	}
}