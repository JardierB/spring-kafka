package bitbucket.jardier.pedidos.model;

import bitbucket.jardier.pedidos.enums.TipoPagamento;
import lombok.Data;

@Data
public class DadosPagamento {

    private String dados;
    private TipoPagamento tipoPagamento;
}
