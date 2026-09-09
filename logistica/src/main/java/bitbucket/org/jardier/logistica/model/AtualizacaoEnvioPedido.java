package bitbucket.org.jardier.logistica.model;

import lombok.Builder;

@Builder
public record AtualizacaoEnvioPedido(Long codigo,
                                     StatusPedido status,
                                     String rastreio) {
}
