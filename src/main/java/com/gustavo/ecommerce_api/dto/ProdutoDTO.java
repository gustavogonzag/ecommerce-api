package com.gustavo.ecommerce_api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;

    private String nome;

    private String descricao;

    private Double preco;

    private Long categoriaId;

    private Boolean ativo;

    private String urlImagem;
}
