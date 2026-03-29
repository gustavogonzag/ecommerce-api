package com.gustavo.ecommerce_api.controller;

import com.gustavo.ecommerce_api.dto.BairroDTO;
import com.gustavo.ecommerce_api.service.BairroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bairros")
public class BairroController {

    private final BairroService bairroService;

    public BairroController(BairroService bairroService) {
        this.bairroService = bairroService;
    }

    @PostMapping
    public BairroDTO salvar(@RequestBody BairroDTO dto) {
        return bairroService.salvar(dto);
    }

    @GetMapping
    public List<BairroDTO> listar() {
        return bairroService.listarTodos();
    }

    @GetMapping("/{id}")
    public BairroDTO buscarPorId(@PathVariable Long id) {
        return bairroService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public BairroDTO atualizar(@PathVariable Long id, @RequestBody BairroDTO dto) {
        return bairroService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        bairroService.deletar(id);
    }
}