package com.consumeapirest.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	// @Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			List<Produto> quote = restTemplate.getForObject("http://localhost:8082/produto/listar", List.class);
			System.out.print(quote.toString());
		};
	}

	@Primary
	@Bean
	public CommandLineRunner createProduto(RestTemplate restTemplate) throws Exception {
		return args -> {
			URI uri = new URI("http://localhost:8082/produto/cadastrar");
			Produto produto = new Produto();
			produto.descricao = "Coisei";
			produto.nome = "Cachaca";
			produto.preco = 12f;
			ResponseEntity<ResponsePadraoDTO> result = null;
			try {
				result = restTemplate.postForEntity(uri, produto,ResponsePadraoDTO.class);
				System.out.println(new Gson().toJson(result.getBody()));
			} catch (HttpClientErrorException httpClientOrServerExc) {
				ResponsePadraoDTO resultError = httpClientOrServerExc.getResponseBodyAs(ResponsePadraoDTO.class);
				//System.out.println(httpClientOrServerExc.getResponseBodyAsString());
				System.out.println(new Gson().toJson(resultError));
			} catch (HttpServerErrorException  httpServerErrorException ) {
				ResponsePadraoDTO resultError = httpServerErrorException.getResponseBodyAs(ResponsePadraoDTO.class);
				//System.out.println(httpServerErrorException.getResponseBodyAsString());
				System.out.println(new Gson().toJson(resultError));
			}

		};
	}
	
}
