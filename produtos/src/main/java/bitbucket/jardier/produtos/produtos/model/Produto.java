package bitbucket.jardier.produtos.produtos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "valor_unitario", precision = 16, scale = 2, nullable = false)
    private BigDecimal valorUnitario;

    @Column(name = "ativo")
    private boolean ativo = true;
}

