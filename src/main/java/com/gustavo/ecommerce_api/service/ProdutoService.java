package com.gustavo.ecommerce_api.service;

import com.gustavo.ecommerce_api.dto.ProdutoDTO;
import com.gustavo.ecommerce_api.exception.RecursoNaoEncontradoException;
import com.gustavo.ecommerce_api.model.Categoria;
import com.gustavo.ecommerce_api.model.Produto;
import com.gustavo.ecommerce_api.repository.CategoriaRepository;
import com.gustavo.ecommerce_api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository,
                          CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public ProdutoDTO salvar(ProdutoDTO dto) {

        Produto produto = new Produto();

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setAtivo(dto.getAtivo());
        produto.setUrlImagem(dto.getUrlImagem());

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));

        produto.setCategoria(categoria);

        Produto salvo = produtoRepository.save(produto);

        return toDTO(salvo);
    }

    public List<ProdutoDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private ProdutoDTO toDTO(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();

        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());
        dto.setPreco(produto.getPreco());
        dto.setAtivo(produto.getAtivo());
        dto.setUrlImagem(produto.getUrlImagem());

        if (produto.getCategoria() != null) {
            dto.setCategoriaId(produto.getCategoria().getId());
        }

        return dto;
    }

    public ProdutoDTO buscarPorId(Long id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));

        return toDTO(produto);
    }

    public ProdutoDTO atualizar(Long id, ProdutoDTO dto) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setAtivo(dto.getAtivo());
        produto.setUrlImagem(dto.getUrlImagem());

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));

        produto.setCategoria(categoria);

        Produto atualizado = produtoRepository.save(produto);

        return toDTO(atualizado);
    }

    public void deletar(Long id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));

        produtoRepository.delete(produto);
    }

}
