package com.gustavo.ecommerce_api.controller;

import com.gustavo.ecommerce_api.dto.ProdutoDTO;
import com.gustavo.ecommerce_api.model.Produto;
import com.gustavo.ecommerce_api.service.ProdutoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ProdutoDTO salvar(@RequestBody ProdutoDTO dto) {
        return produtoService.salvar(dto);
    }

    @GetMapping
    public List<ProdutoDTO> listar() {
        return produtoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ProdutoDTO buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ProdutoDTO atualizar(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        return produtoService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        produtoService.deletar(id);
    }

}
