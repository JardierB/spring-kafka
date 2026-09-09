package bitbucket.org.jardier.logistica.publisher;

import bitbucket.org.jardier.logistica.model.AtualizacaoEnvioPedido;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnvioPedidoPublisher {
    private final ObjectMapper mapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${icompras.config.kafka.topics.pedidos-enviados}")
    private String topico;

    public void enviar(AtualizacaoEnvioPedido atualizacaoEnvioPedido) {
        log.info("Publicando o envio do pedido {}", atualizacaoEnvioPedido.codigo());

        try {
            var json = mapper.writeValueAsString(atualizacaoEnvioPedido);
            kafkaTemplate.send(topico, "dados", json);

        }catch (Exception e) {
            log.error("Erro ao publicar o envio do pedido.", e);
        }
    }
}
