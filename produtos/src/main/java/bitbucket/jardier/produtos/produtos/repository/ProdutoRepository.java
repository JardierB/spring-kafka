package bitbucket.jardier.produtos.produtos.repository;

import bitbucket.jardier.produtos.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
}
