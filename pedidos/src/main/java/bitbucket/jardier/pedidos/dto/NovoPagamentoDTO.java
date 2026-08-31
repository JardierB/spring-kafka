package bitbucket.jardier.pedidos.dto;

import bitbucket.jardier.pedidos.enums.TipoPagamento;

public record NovoPagamentoDTO(
        Long codigoPedido,
        String dados,
        TipoPagamento tipoPagamento
) {
}
