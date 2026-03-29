package com.gustavo.ecommerce_api.repository;

import com.gustavo.ecommerce_api.model.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BairroRepository extends JpaRepository<Bairro, Long> {
}