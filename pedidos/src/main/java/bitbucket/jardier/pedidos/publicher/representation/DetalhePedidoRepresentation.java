package bitbucket.jardier.pedidos.publicher.representation;

import bitbucket.jardier.pedidos.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DetalhePedidoRepresentation(
        Long codigo,
        Long codigoCliente,
        String nome,
        String cpf,
        String logradouro,
        String numero,
        String bairro,
        String email,
        String telefone,
        String dataPedido,
        BigDecimal total,
        StatusPedido status,
        String urlNotaFiscal,
        String codigoRatreio,
        List<DetalheItemPedidoRepresentation> itens
) {
}
