package org.bitbucket.jardier.icompras.faturamento.publisher.representation;

import lombok.Builder;

@Builder
public record AtualizarStatusPedido(Long codigo,
                                    StatusPedido status,
                                    String urlNotaFiscal){}
