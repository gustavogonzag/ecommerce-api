package com.gustavo.ecommerce_api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientePedidoDTO {

    private String nome;
    private String telefone;
    private String endereco;
    private Long bairroId;
}