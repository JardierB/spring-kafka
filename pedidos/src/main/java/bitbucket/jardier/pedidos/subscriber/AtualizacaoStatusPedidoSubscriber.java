package bitbucket.jardier.pedidos.subscriber;

import bitbucket.jardier.pedidos.service.AtualizacaoStatusPedidoService;
import bitbucket.jardier.pedidos.subscriber.representation.AtualizacapStatusPedidoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class AtualizacaoStatusPedidoSubscriber {

    private final AtualizacaoStatusPedidoService service;
    private final ObjectMapper mapper;


    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
            topics = {"${icompras.config.kafka.topics.pedidos-faturados}",
                    "${icompras.config.kafka.topics.pedidos-enviados}"})
    public void receberAtualizacao(String json) {
        log.info("Recebendo atualuzação {}", json);

        try {
            var representation = mapper.readValue(json, AtualizacapStatusPedidoRepresentation.class);
            service.atualizarStatus(representation.codigo()
                    , representation.status()
                    , representation.urlNotaFiscal()
                    , representation.rastreio());
            log.info("Pedido atualizado com sucesso. Número: {}", representation.codigo());

        } catch (Exception e) {
            log.error("Erro ao atualizar status do pedido: {}", e.getMessage());
        }


    }
}
