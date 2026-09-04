package org.bitbucket.jardier.icompras.faturamento;

import lombok.extern.slf4j.Slf4j;
import org.bitbucket.jardier.icompras.faturamento.bucket.BucketService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class FaturamentoApplication {

	private boolean bucket;

//	@Bean
	public CommandLineRunner commandLineRunner(BucketService service) {
		return args -> {
			boolean bucket = service.removerBucket("icompras.faturas");
			log.info("BUCKET removido? {}", bucket);
		};

	}
	public static void main(String[] args) {
		SpringApplication.run(FaturamentoApplication.class, args);
	}

}
