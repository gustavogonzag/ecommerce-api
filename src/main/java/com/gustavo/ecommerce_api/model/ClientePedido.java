package com.gustavo.ecommerce_api.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ClientePedido {

    private String nome;
    private String telefone;
    private String endereco;

    @ManyToOne
    @JoinColumn(name = "bairro_id")
    private Bairro bairro;
}