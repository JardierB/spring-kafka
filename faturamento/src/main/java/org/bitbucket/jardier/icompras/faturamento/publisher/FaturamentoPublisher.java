package org.bitbucket.jardier.icompras.faturamento.publisher;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bitbucket.jardier.icompras.faturamento.modelo.Pedido;
import org.bitbucket.jardier.icompras.faturamento.publisher.representation.AtualizarStatusPedido;
import org.bitbucket.jardier.icompras.faturamento.publisher.representation.StatusPedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@Slf4j
@RequiredArgsConstructor
public class FaturamentoPublisher {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper mapper;

    @Value("${icompras.config.kafka.topics.pedidos-faturados}")
    private String topico;

    public void publicar(Pedido pedido, String url) {

        try {
            var representation = AtualizarStatusPedido
                    .builder()
                    .codigo(pedido.codigo())
                    .status(StatusPedido.FATURADO)
                    .urlNotaFiscal(url)
                    .build();

            String json = mapper.writeValueAsString(representation);
            kafkaTemplate.send(topico, "dados", json);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }


    }

}
