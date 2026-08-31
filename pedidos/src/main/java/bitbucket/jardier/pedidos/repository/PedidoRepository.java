package bitbucket.jardier.pedidos.repository;

import bitbucket.jardier.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {

    Optional<Pedido> findByCodigoAndChavePagamento(Long codigo, String chavePagamento);
}
