package bitbucket.jardier.pedidos.model;

import bitbucket.jardier.pedidos.client.representation.ClienteRepresentation;
import bitbucket.jardier.pedidos.dto.DadosPagamentoDTO;
import bitbucket.jardier.pedidos.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "codigo_cliente", nullable = false)
    private Long codigoCliente;

    @Column(name = "data_pedido", nullable = false)
    private LocalDateTime dataPedido;

    @Column(name = "chave_pagamento")
    private String chavePagamento;

    @Column(name = "observacoes")
    private String observacoes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private StatusPedido status;

    @Column(name = "total", precision = 16, scale = 2, nullable = false)
    private BigDecimal total;

    @Column(name = "codigo_rastreio", length = 255)
    private String codigoRastreio;

    @Column(name = "url_nf")
    private String urlNf;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;

    @Transient
    private DadosPagamento dadosPagamento;

    @Transient
    private ClienteRepresentation dadosCliente;

}
