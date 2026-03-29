package com.gustavo.ecommerce_api.service;

import com.gustavo.ecommerce_api.dto.CategoriaDTO;
import com.gustavo.ecommerce_api.exception.RecursoNaoEncontradoException;
import com.gustavo.ecommerce_api.model.Categoria;
import com.gustavo.ecommerce_api.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaDTO salvar(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());

        Categoria salva = categoriaRepository.save(categoria);

        return toDTO(salva);
    }

    public List<CategoriaDTO> listarTodos() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private CategoriaDTO toDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());
        return dto;
    }

    public CategoriaDTO buscarPorId(Long id) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));

        return toDTO(categoria);
    }

    public CategoriaDTO atualizar(Long id, CategoriaDTO dto) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));

        categoria.setNome(dto.getNome());

        Categoria atualizada = categoriaRepository.save(categoria);

        return toDTO(atualizada);
    }

    public void deletar(Long id) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));

        categoriaRepository.delete(categoria);
    }
}