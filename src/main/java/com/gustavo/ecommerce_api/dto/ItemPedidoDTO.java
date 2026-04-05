package com.gustavo.ecommerce_api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO {

    private Long produtoId;
    private Integer quantidade;
    private Double preco;
    private String observacao;
    private java.util.List<ItemPedidoAcrescimoDTO> acrescimos;
    private java.util.List<ItemPedidoRemocaoDTO> remocoes;
}