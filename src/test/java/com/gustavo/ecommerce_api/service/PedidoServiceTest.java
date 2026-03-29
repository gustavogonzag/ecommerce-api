package com.gustavo.ecommerce_api.service;

import com.gustavo.ecommerce_api.dto.ClientePedidoDTO;
import com.gustavo.ecommerce_api.dto.ItemPedidoDTO;
import com.gustavo.ecommerce_api.dto.PedidoDTO;
import com.gustavo.ecommerce_api.exception.RecursoNaoEncontradoException;
import com.gustavo.ecommerce_api.model.*;
import com.gustavo.ecommerce_api.model.enums.FormaPagamento;
import com.gustavo.ecommerce_api.model.enums.StatusPedido;
import com.gustavo.ecommerce_api.repository.BairroRepository;
import com.gustavo.ecommerce_api.repository.CupomRepository;
import com.gustavo.ecommerce_api.repository.PedidoRepository;
import com.gustavo.ecommerce_api.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private BairroRepository bairroRepository;

    @Mock
    private CupomRepository cupomRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Bairro bairro;
    private Produto produto;
    private PedidoDTO pedidoDTO;

    @BeforeEach
    void setUp() {
        bairro = new Bairro();
        bairro.setId(1L);
        bairro.setNome("Centro");
        bairro.setTaxaEntrega(10.0);

        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Hambúrguer");
        produto.setDescricao("Artesanal");
        produto.setPreco(20.0);
        produto.setAtivo(true);
        produto.setUrlImagem("https://imagem.com/produto.png");

        ClientePedidoDTO cliente = new ClientePedidoDTO();
        cliente.setNome("Gustavo");
        cliente.setTelefone("999999999");
        cliente.setEndereco("Rua A");
        cliente.setBairroId(1L);

        ItemPedidoDTO item = new ItemPedidoDTO();
        item.setProdutoId(1L);
        item.setQuantidade(2);

        pedidoDTO = new PedidoDTO();
        pedidoDTO.setCliente(cliente);
        pedidoDTO.setItens(List.of(item));
        pedidoDTO.setFormaPagamento(FormaPagamento.PIX);
        pedidoDTO.setStatus(StatusPedido.PENDENTE);
    }

    @Test
    void deveCriarPedidoComSucesso() {
        when(bairroRepository.findById(1L)).thenReturn(Optional.of(bairro));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido pedido = invocation.getArgument(0);
            pedido.setId(1L);
            return pedido;
        });

        PedidoDTO resultado = pedidoService.salvar(pedidoDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(FormaPagamento.PIX, resultado.getFormaPagamento());
        assertEquals(StatusPedido.PENDENTE, resultado.getStatus());
        assertEquals(50.0, resultado.getTotal());

    }

    @Test
    void deveLancarErroQuandoBairroNaoExistir() {
        when(bairroRepository.findById(1L)).thenReturn(Optional.empty());

        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                () -> pedidoService.salvar(pedidoDTO)
        );

        assertEquals("Bairro não encontrado", exception.getMessage());
    }

    @Test
    void deveLancarErroQuandoProdutoNaoExistir() {
        when(bairroRepository.findById(1L)).thenReturn(Optional.of(bairro));
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                () -> pedidoService.salvar(pedidoDTO)
        );

        assertEquals("Produto não encontrado", exception.getMessage());
    }

    @Test
    void deveLancarErroQuandoPedidoNaoTiverItens() {
        pedidoDTO.setItens(List.of());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> pedidoService.salvar(pedidoDTO)
        );

        assertEquals("Pedido deve ter pelo menos um item", exception.getMessage());
    }

    @Test
    void deveLancarErroQuandoProdutoEstiverInativo() {
        produto.setAtivo(false);

        when(bairroRepository.findById(1L)).thenReturn(Optional.of(bairro));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> pedidoService.salvar(pedidoDTO)
        );

        assertEquals("Produto inativo", exception.getMessage());
    }

    @Test
    void deveAplicarCupomPercentualCorretamente() {
        Cupom cupom = new Cupom();
        cupom.setId(1L);
        cupom.setCodigo("DESC10");
        cupom.setTipo(com.gustavo.ecommerce_api.model.enums.TipoCupom.PERCENTUAL);
        cupom.setValor(10.0);
        cupom.setAtivo(true);
        cupom.setDataExpiracao(LocalDate.now().plusDays(1));

        pedidoDTO.setCupomId(1L);

        when(bairroRepository.findById(1L)).thenReturn(Optional.of(bairro));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(cupomRepository.findById(1L)).thenReturn(Optional.of(cupom));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido pedido = invocation.getArgument(0);
            pedido.setId(1L);
            return pedido;
        });

        PedidoDTO resultado = pedidoService.salvar(pedidoDTO);

        assertNotNull(resultado);
        assertEquals(45.0, resultado.getTotal());
    }
}