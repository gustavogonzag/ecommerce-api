package com.gustavo.ecommerce_api.controller;

import com.gustavo.ecommerce_api.dto.CategoriaDTO;
import com.gustavo.ecommerce_api.service.CategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public CategoriaDTO salvar(@RequestBody CategoriaDTO dto) {
        return categoriaService.salvar(dto);
    }

    @GetMapping
    public List<CategoriaDTO> listar() {
        return categoriaService.listarTodos();
    }

    @GetMapping("/{id}")
    public CategoriaDTO buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public CategoriaDTO atualizar(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
        return categoriaService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
    }
}