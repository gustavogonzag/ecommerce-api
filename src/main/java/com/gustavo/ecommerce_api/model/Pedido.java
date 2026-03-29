package com.gustavo.ecommerce_api.model;

import com.gustavo.ecommerce_api.model.enums.FormaPagamento;
import com.gustavo.ecommerce_api.model.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ClientePedido cliente;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id")
    private List<ItemPedido> itens;

    @ManyToOne
    @JoinColumn(name = "cupom_id")
    private Cupom cupom;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    private LocalDateTime dataCriacao;

    private Double total;
}