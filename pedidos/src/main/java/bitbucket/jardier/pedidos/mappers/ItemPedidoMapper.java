package bitbucket.jardier.pedidos.mappers;

import bitbucket.jardier.pedidos.dto.ItemPedidoDTO;
import bitbucket.jardier.pedidos.model.ItemPedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedido map(ItemPedidoDTO dto);

}
