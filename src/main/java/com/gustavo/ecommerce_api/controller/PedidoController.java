package com.gustavo.ecommerce_api.controller;

import com.gustavo.ecommerce_api.dto.AtualizarStatusDTO;
import com.gustavo.ecommerce_api.dto.PedidoDTO;
import com.gustavo.ecommerce_api.model.enums.StatusPedido;
import com.gustavo.ecommerce_api.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public PedidoDTO salvar(@RequestBody PedidoDTO dto) {
        return pedidoService.salvar(dto);
    }

    @GetMapping("/{id}")
    public PedidoDTO buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        pedidoService.deletar(id);
    }

    @PatchMapping("/{id}/status")
    public PedidoDTO atualizarStatus(
            @PathVariable Long id,
            @RequestBody AtualizarStatusDTO dto) {

        return pedidoService.atualizarStatus(id, dto.getStatus());
    }

    @GetMapping
    public org.springframework.data.domain.Page<PedidoDTO> listar(
            @RequestParam(required = false) StatusPedido status,
            org.springframework.data.domain.Pageable pageable) {

        if (status != null) {
            return pedidoService.listarPorStatusPaginado(status, pageable);
        }

        return pedidoService.listarTodosPaginado(pageable);
    }
}