package com.gustavo.ecommerce_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "produtos")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private Double preco;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    private Boolean ativo;

    @Column(name = "url_imagem")
    private String urlImagem;
}