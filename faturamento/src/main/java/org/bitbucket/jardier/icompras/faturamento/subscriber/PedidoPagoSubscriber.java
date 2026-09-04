package org.bitbucket.jardier.icompras.faturamento.subscriber;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bitbucket.jardier.icompras.faturamento.mapper.PedidoMapper;
import org.bitbucket.jardier.icompras.faturamento.modelo.Pedido;
import org.bitbucket.jardier.icompras.faturamento.service.GeradorNotaFiscalService;
import org.bitbucket.jardier.icompras.faturamento.subscriber.representation.DetalhePedidoRepresentation;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoPagoSubscriber {

    private final ObjectMapper mapper;
    private final GeradorNotaFiscalService service;
    private final PedidoMapper pedidoMapper;

    @KafkaListener(groupId = "icompras-faturamento",
    topics = "${icompras.config.kafka.topics.pedidos-pagos}")
    public void listen(String json) {
        log.info("Recebendo pedidopara faturamento {}", json);
        var representation = mapper.readValue(json, DetalhePedidoRepresentation.class);
        Pedido pedido = pedidoMapper.map(representation);
        service.gerar(pedido);

    }

}
