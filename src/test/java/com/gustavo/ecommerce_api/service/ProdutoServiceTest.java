package com.gustavo.ecommerce_api.service;

import com.gustavo.ecommerce_api.dto.ProdutoDTO;
import com.gustavo.ecommerce_api.exception.RecursoNaoEncontradoException;
import com.gustavo.ecommerce_api.model.Categoria;
import com.gustavo.ecommerce_api.model.Produto;
import com.gustavo.ecommerce_api.repository.CategoriaRepository;
import com.gustavo.ecommerce_api.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveSalvarProdutoComCategoriaValida() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Bebidas");

        ProdutoDTO dto = new ProdutoDTO();
        dto.setNome("Coca-Cola");
        dto.setDescricao("Refrigerante");
        dto.setPreco(10.0);
        dto.setCategoriaId(1L);
        dto.setAtivo(true);

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(produtoRepository.save(any(Produto.class))).thenAnswer(invocation -> {
            Produto p = invocation.getArgument(0);
            p.setId(1L);
            return p;
        });

        ProdutoDTO resultado = produtoService.salvar(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Coca-Cola", resultado.getNome());
    }

    @Test
    void deveLancarErroQuandoCategoriaNaoExistir() {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setCategoriaId(1L);

        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> produtoService.salvar(dto)
        );
    }

    @Test
    void deveBuscarProdutoPorId() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto");

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        ProdutoDTO resultado = produtoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveLancarErroAoBuscarProdutoInexistente() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> produtoService.buscarPorId(1L)
        );
    }

    @Test
    void deveAtualizarProduto() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);

        Produto produto = new Produto();
        produto.setId(1L);

        ProdutoDTO dto = new ProdutoDTO();
        dto.setNome("Atualizado");
        dto.setCategoriaId(1L);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        ProdutoDTO resultado = produtoService.atualizar(1L, dto);

        assertEquals("Atualizado", resultado.getNome());
    }

    @Test
    void deveDeletarProduto() {
        Produto produto = new Produto();
        produto.setId(1L);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        doNothing().when(produtoRepository).delete(produto);

        assertDoesNotThrow(() -> produtoService.deletar(1L));
    }
}