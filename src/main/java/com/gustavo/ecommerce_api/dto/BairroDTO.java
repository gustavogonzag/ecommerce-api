package com.gustavo.ecommerce_api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BairroDTO {

    private Long id;
    private String nome;
    private Double taxaEntrega;
}