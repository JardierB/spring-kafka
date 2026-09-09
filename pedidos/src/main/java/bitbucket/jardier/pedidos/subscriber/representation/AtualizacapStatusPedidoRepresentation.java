package bitbucket.jardier.pedidos.subscriber.representation;

import bitbucket.jardier.pedidos.enums.StatusPedido;
import lombok.Builder;

@Builder
public record AtualizacapStatusPedidoRepresentation(
        Long codigo,
        StatusPedido status,
        String urlNotaFiscal,
        String rastreio

) {
}
