package com.gustavo.ecommerce_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "itens_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private Integer quantidade;

    private Double preco;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_pedido_id")
    private java.util.List<ItemPedidoAcrescimo> acrescimos;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_pedido_id")
    private java.util.List<ItemPedidoRemocao> remocoes;
}