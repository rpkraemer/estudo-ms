package br.com.compasso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import br.com.compasso.cepmessagingms.config.FeignCustomErrorDecoder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CepMessagingMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CepMessagingMsApplication.class, args);
	}

	@Bean
	public FeignCustomErrorDecoder feignCustomErrorDecoder() {
		return new FeignCustomErrorDecoder();
	}
}