package org.bitbucket.jardier.icompras.faturamento.mapper;

import org.bitbucket.jardier.icompras.faturamento.modelo.Cliente;
import org.bitbucket.jardier.icompras.faturamento.modelo.ItemPedido;
import org.bitbucket.jardier.icompras.faturamento.modelo.Pedido;
import org.bitbucket.jardier.icompras.faturamento.subscriber.representation.DetalheItemPedidoRepresentation;
import org.bitbucket.jardier.icompras.faturamento.subscriber.representation.DetalhePedidoRepresentation;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PedidoMapper {

    public Pedido map(DetalhePedidoRepresentation representation) {
        Cliente cliente = Cliente.builder()
                .nome(representation.nome())
                .cpf(representation.cpf())
                .logradouro(representation.logradouro())
                .numero(representation.numero())
                .bairro(representation.bairro())
                .email(representation.email())
                .telefone(representation.telefone())
                .build();

        List<ItemPedido> itens = representation.itens()
                .stream()
                .map(this::mapItem).toList();

        return Pedido.builder()
                .codigo(representation.codigo())
                .cliente(cliente)
                .dataPedido(representation.dataPedido())
                .total(representation.total())
                .itens(itens)
                .build();
    }

    private ItemPedido mapItem(DetalheItemPedidoRepresentation
                                       representation) {
        return ItemPedido.builder()
                .codigoProduto(representation.codigoProduto())
                .nome(representation.nome())
                .quantidade(representation.quantidade())
                .valorUnitario(representation.valorUnitario())
                .build();

    }
}
