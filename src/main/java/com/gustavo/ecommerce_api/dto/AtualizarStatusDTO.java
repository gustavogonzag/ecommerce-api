package com.gustavo.ecommerce_api.dto;

import com.gustavo.ecommerce_api.model.enums.StatusPedido;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarStatusDTO {

    private StatusPedido status;
}