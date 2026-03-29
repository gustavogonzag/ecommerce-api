package com.gustavo.ecommerce_api.repository;

import com.gustavo.ecommerce_api.model.Pedido;
import com.gustavo.ecommerce_api.model.enums.StatusPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findByStatus(StatusPedido status, Sort sort);
    Page<Pedido> findByStatus(StatusPedido status, Pageable pageable);
}