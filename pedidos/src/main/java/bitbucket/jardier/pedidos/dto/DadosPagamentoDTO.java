package bitbucket.jardier.pedidos.dto;

import bitbucket.jardier.pedidos.enums.TipoPagamento;

public record DadosPagamentoDTO(String dados, TipoPagamento tipoPagamento) {
}
