package com.gustavo.ecommerce_api.model;

import com.gustavo.ecommerce_api.model.enums.TipoCupom;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "cupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cupom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @Enumerated(EnumType.STRING)
    private TipoCupom tipo; // PERCENTUAL ou FIXO

    private Double valor;

    private Boolean ativo;

    private LocalDate dataExpiracao;
}