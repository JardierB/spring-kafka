package org.bitbucket.jardier.icompras.faturamento.modelo;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record Pedido(Long codigo,
                     Cliente cliente,
                     String dataPedido,
                     BigDecimal total,
                     List<ItemPedido> itens) {
}
