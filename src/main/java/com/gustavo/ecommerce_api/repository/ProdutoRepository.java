package com.gustavo.ecommerce_api.repository;

import com.gustavo.ecommerce_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
