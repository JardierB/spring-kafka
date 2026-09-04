package org.bitbucket.jardier.icompras.faturamento.modelo;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class ItemPedido {

    private Long codigoProduto;
    private String nome;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal total;

}
