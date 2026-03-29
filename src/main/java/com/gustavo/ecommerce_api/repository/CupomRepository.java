package com.gustavo.ecommerce_api.repository;

import com.gustavo.ecommerce_api.model.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CupomRepository extends JpaRepository<Cupom, Long> {

    Optional<Cupom> findByCodigo(String codigo);

}