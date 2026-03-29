package com.gustavo.ecommerce_api.dto;

import com.gustavo.ecommerce_api.model.enums.FormaPagamento;
import com.gustavo.ecommerce_api.model.enums.StatusPedido;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;

    private ClientePedidoDTO cliente;

    private List<ItemPedidoDTO> itens;

    private Long cupomId;

    private FormaPagamento formaPagamento;

    private StatusPedido status;

    private LocalDateTime dataCriacao;

    private Double total;
}