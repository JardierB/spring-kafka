package bitbucket.jardier.pedidos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class PedidosApplication {

	//@Bean
	//Publicando no Kafka
	public CommandLineRunner commandLineRunner(KafkaTemplate<String,String> kafkaTemplate) {
		return args -> kafkaTemplate.send("icompras.pedidos-pagos","dados", "nova mensagem");
	}

	public static void main(String[] args) {
		SpringApplication.run(PedidosApplication.class, args);
	}

}
