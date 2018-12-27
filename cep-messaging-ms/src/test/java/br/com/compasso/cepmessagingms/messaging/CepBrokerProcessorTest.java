package br.com.compasso.cepmessagingms.messaging;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.compasso.cepmessagingms.messaging.domain.CepToAddressRequest;
import br.com.compasso.cepms.dto.AddressDTO;
import br.com.compasso.cepms.proxy.CepMsProxy;
import br.com.compasso.cepms.proxy.MockCepMsProxy;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CepBrokerProcessorTest {

	@Autowired
	CepProcessor processor;

	@Autowired
	MessageCollector collector;

	@MockBean
	CepMsProxy cepMsProxy;

	@Autowired
	ObjectMapper objMapper;

	JacksonTester<AddressDTO> addressDtoJson;

	@Before
	public void setUp() {
		JacksonTester.initFields(this, objMapper);
	}

	@Test
	public void shouldProcessAValidCepToAddressRequest() throws IOException {
		CepToAddressRequest payload = new CepToAddressRequest();
		payload.setCepNumber("99010030");

		final Message<CepToAddressRequest> inboundMessage = MessageBuilder.withPayload(payload).build();

		new MockCepMsProxy(cepMsProxy).toReturnValidAddress();

		assertThat(processor.cepToAddressRequest().send(inboundMessage)).isTrue();

		BlockingQueue<Message<?>> outboundMessages = collector.forChannel(processor.cepToAddressProcessed());

		final Message<?> message = outboundMessages.poll();
		final String outboundPayload = (String) message.getPayload();

		assertThat(addressDtoJson.parse(outboundPayload).getObject())
				.isEqualToComparingFieldByField(expectedValidAddress());
	}

	private AddressDTO expectedValidAddress() {
		AddressDTO address = new AddressDTO();
		address.setCepNumber("99010030");
		address.setCity("Passo Fundo");
		address.setComplements("Apto 1234");
		address.setNeighborhood("Centro");
		address.setState("RS");
		address.setStreet("Rua Morom");

		return address;
	}
}
