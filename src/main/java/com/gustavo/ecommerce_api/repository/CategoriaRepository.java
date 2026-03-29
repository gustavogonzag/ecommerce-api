package com.gustavo.ecommerce_api.repository;

import com.gustavo.ecommerce_api.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
