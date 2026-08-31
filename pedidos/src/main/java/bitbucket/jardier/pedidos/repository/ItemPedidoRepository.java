package bitbucket.jardier.pedidos.repository;

import bitbucket.jardier.pedidos.model.ItemPedido;
import bitbucket.jardier.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido,Long> {

    List<ItemPedido> findByPedido(Pedido pedido);
}
