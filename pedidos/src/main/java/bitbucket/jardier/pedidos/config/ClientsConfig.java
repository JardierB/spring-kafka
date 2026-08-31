package bitbucket.jardier.pedidos.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "bitbucket.jardier.pedidos.client")
public class ClientsConfig {
}
