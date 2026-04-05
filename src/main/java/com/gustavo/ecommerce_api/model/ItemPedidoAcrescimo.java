package com.gustavo.ecommerce_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "itens_pedido_acrescimos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoAcrescimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Double preco;
}