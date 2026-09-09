package bitbucket.org.jardier.logistica.subscriber.representation;

import bitbucket.org.jardier.logistica.model.StatusPedido;
import lombok.Builder;

@Builder
public record AtualizacaoFaturamentoRepresentation(Long codigo,
                                                   StatusPedido status,
                                                   String urlNotaFiscal) {
}
