package org.bitbucket.jardier.icompras.faturamento.modelo;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ItemPedido(Long codigoProduto, String nome, Integer quantidade, BigDecimal valorUnitario) {

//    public BigDecimal getTotal() {
//        return BigDecimal.valueOf(this.quantidade).multiply(this.valorUnitario);
//    }

}
