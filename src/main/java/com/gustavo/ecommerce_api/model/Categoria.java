package com.gustavo.ecommerce_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categorias")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
}