package bitbucket.jardier.clientes.clientes.repository;

import bitbucket.jardier.clientes.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}
