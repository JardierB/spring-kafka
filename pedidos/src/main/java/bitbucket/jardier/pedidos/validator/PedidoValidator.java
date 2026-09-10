package bitbucket.jardier.pedidos.validator;

import bitbucket.jardier.pedidos.client.ClientesClient;
import bitbucket.jardier.pedidos.client.ProdutosClient;
import bitbucket.jardier.pedidos.client.representation.ClienteRepresentation;
import bitbucket.jardier.pedidos.client.representation.ProdutoRepresentation;
import bitbucket.jardier.pedidos.exception.ValidationException;
import bitbucket.jardier.pedidos.model.ItemPedido;
import bitbucket.jardier.pedidos.model.Pedido;
import feign.Feign;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClientesClient clientesClient;

    public void validar(Pedido pedido) {
        Long codigocliente = pedido.getCodigoCliente();
        validarCliente(codigocliente);

        pedido.getItens().forEach(this::validarItem);

    }

    private void validarCliente(Long codigoCliente) {
        try {

            var response = clientesClient.obterDados(codigoCliente);
            ClienteRepresentation cliente = response.getBody();
            log.info("Cliente de código {} encontrado: {}", cliente.codigo(), cliente.nome());

            if (cliente.isInativo()) {
                throw new ValidationException("codigoCliente", "Cliente inativo!");

            }


        } catch (FeignException.NotFound e) {
            var message = String.format("Cliente de código %d não encontrado!", codigoCliente);
            throw new ValidationException("codigoCliente", message);
        }


    }

    private void validarItem(ItemPedido item) {
        try {
            var response = produtosClient.obterDados(item.getCodigoProduto());
            ProdutoRepresentation produto = response.getBody();
            log.info("Produto de código {} encontrado {}", produto.codigo(), produto.nome());

            if (produto.isInativo()) {
                throw new ValidationException("codigoProduto", "Produto inativo");
            }

        } catch (FeignException.NotFound e) {
            var message = String.format("Produto de código %d não encontrado!", item.getCodigoProduto());
            throw new ValidationException("codigoProduto", message);

        }
    }
}
