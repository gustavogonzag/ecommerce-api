package com.gustavo.ecommerce_api.service;

import com.gustavo.ecommerce_api.dto.*;
import com.gustavo.ecommerce_api.exception.RecursoNaoEncontradoException;
import com.gustavo.ecommerce_api.exception.RegraNegocioException;
import com.gustavo.ecommerce_api.model.*;
import com.gustavo.ecommerce_api.model.enums.StatusPedido;
import com.gustavo.ecommerce_api.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final BairroRepository bairroRepository;
    private final CupomRepository cupomRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         ProdutoRepository produtoRepository,
                         BairroRepository bairroRepository,
                         CupomRepository cupomRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.bairroRepository = bairroRepository;
        this.cupomRepository = cupomRepository;
    }

    public PedidoDTO salvar(PedidoDTO dto) {

        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            throw new RuntimeException("Pedido deve ter pelo menos um item");
        }

        Pedido pedido = new Pedido();

        // Cliente + Bairro
        ClientePedidoDTO c = dto.getCliente();

        Bairro bairro = bairroRepository.findById(c.getBairroId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Bairro não encontrado"));

        ClientePedido cliente = new ClientePedido(
                c.getNome(),
                c.getTelefone(),
                c.getEndereco(),
                bairro
        );

        pedido.setCliente(cliente);

        // Observação geral do pedido
        pedido.setObservacao(dto.getObservacao());

        // Cálculo do total
        double total = 0.0;

        List<ItemPedido> itens = new java.util.ArrayList<>();

        for (ItemPedidoDTO i : dto.getItens()) {

            if (i.getQuantidade() == null || i.getQuantidade() <= 0) {
                throw new RuntimeException("Quantidade inválida");
            }

            Produto produto = produtoRepository.findById(i.getProdutoId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));

            if (!produto.getAtivo()) {
                throw new RegraNegocioException(
                        "O produto '" + produto.getNome() + "' está indisponível no momento."
                );
            }

            double preco = produto.getPreco();
            double subtotalBase = preco * i.getQuantidade();

            // Acréscimos do item
            List<ItemPedidoAcrescimo> acrescimos = new java.util.ArrayList<>();
            double totalAcrescimos = 0.0;

            if (i.getAcrescimos() != null && !i.getAcrescimos().isEmpty()) {
                for (ItemPedidoAcrescimoDTO a : i.getAcrescimos()) {

                    ItemPedidoAcrescimo acrescimo = new ItemPedidoAcrescimo();
                    acrescimo.setNome(a.getNome());
                    acrescimo.setPreco(a.getPreco());

                    acrescimos.add(acrescimo);

                    if (a.getPreco() != null && a.getPreco() > 0) {
                        totalAcrescimos += a.getPreco() * i.getQuantidade();
                    }
                }
            }

            // Remoções do item
            List<ItemPedidoRemocao> remocoes = new java.util.ArrayList<>();

            if (i.getRemocoes() != null && !i.getRemocoes().isEmpty()) {
                for (ItemPedidoRemocaoDTO r : i.getRemocoes()) {
                    ItemPedidoRemocao remocao = new ItemPedidoRemocao();
                    remocao.setNome(r.getNome());

                    remocoes.add(remocao);
                }
            }

            double subtotal = subtotalBase + totalAcrescimos;
            total += subtotal;

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(i.getQuantidade());
            itemPedido.setPreco(preco);
            itemPedido.setObservacao(i.getObservacao());
            itemPedido.setAcrescimos(acrescimos);
            itemPedido.setRemocoes(remocoes);

            itens.add(itemPedido);
        }

        pedido.setItens(itens);

        // Taxa de entrega
        double taxaEntrega = bairro.getTaxaEntrega() != null ? bairro.getTaxaEntrega() : 0.0;
        total += taxaEntrega;

        // Cupom
        if (dto.getCupomId() != null) {

            Cupom cupom = cupomRepository.findById(dto.getCupomId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Cupom não encontrado"));

            if (!cupom.getAtivo()) {
                throw new RegraNegocioException("Cupom inativo");
            }

            if (cupom.getDataExpiracao() != null &&
                    cupom.getDataExpiracao().isBefore(java.time.LocalDate.now())) {
                throw new RegraNegocioException("Cupom expirado");
            }

            if (cupom.getTipo() == com.gustavo.ecommerce_api.model.enums.TipoCupom.PERCENTUAL) {
                total -= total * (cupom.getValor() / 100);
            } else {
                total -= cupom.getValor();
            }

            pedido.setCupom(cupom);
        }

        // Evita total negativo
        if (total < 0) {
            total = 0;
        }

        pedido.setTotal(total);

        // Outros campos
        pedido.setFormaPagamento(dto.getFormaPagamento());
        pedido.setStatus(
                dto.getStatus() != null
                        ? dto.getStatus()
                        : com.gustavo.ecommerce_api.model.enums.StatusPedido.PENDENTE
        );
        pedido.setDataCriacao(LocalDateTime.now());

        Pedido salvo = pedidoRepository.save(pedido);

        return toDTO(salvo);
    }

    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));
        return toDTO(pedido);
    }

    public void deletar(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));
        pedidoRepository.delete(pedido);
    }

    private PedidoDTO toDTO(Pedido p) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(p.getId());

        ClientePedidoDTO c = new ClientePedidoDTO();
        c.setNome(p.getCliente().getNome());
        c.setTelefone(p.getCliente().getTelefone());
        c.setEndereco(p.getCliente().getEndereco());
        c.setBairroId(p.getCliente().getBairro().getId());
        dto.setCliente(c);

        dto.setItens(p.getItens().stream().map(i -> {
            ItemPedidoDTO ip = new ItemPedidoDTO();
            ip.setProdutoId(i.getProduto().getId());
            ip.setQuantidade(i.getQuantidade());
            ip.setPreco(i.getPreco());
            ip.setObservacao(i.getObservacao());

            if (i.getAcrescimos() != null) {
                List<ItemPedidoAcrescimoDTO> acrescimosDTO = i.getAcrescimos()
                        .stream()
                        .map(a -> new ItemPedidoAcrescimoDTO(a.getNome(), a.getPreco()))
                        .collect(Collectors.toList());

                ip.setAcrescimos(acrescimosDTO);
            }

            if (i.getRemocoes() != null) {
                List<ItemPedidoRemocaoDTO> remocoesDTO = i.getRemocoes()
                        .stream()
                        .map(r -> new ItemPedidoRemocaoDTO(r.getNome()))
                        .collect(Collectors.toList());

                ip.setRemocoes(remocoesDTO);
            }

            return ip;
        }).collect(Collectors.toList()));

        dto.setCupomId(p.getCupom() != null ? p.getCupom().getId() : null);
        dto.setFormaPagamento(p.getFormaPagamento());
        dto.setStatus(p.getStatus());
        dto.setDataCriacao(p.getDataCriacao());
        dto.setTotal(p.getTotal());
        dto.setObservacao(p.getObservacao());

        return dto;
    }

    public PedidoDTO atualizarStatus(Long id, StatusPedido novoStatus) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));

        StatusPedido atual = pedido.getStatus();

        // 🔥 Regras de transição
        if (atual == StatusPedido.PENDENTE) {
            if (novoStatus != StatusPedido.APROVADO && novoStatus != StatusPedido.CANCELADO) {
                throw new RuntimeException("Status inválido para pedido PENDENTE");
            }
        }

        if (atual == StatusPedido.APROVADO) {
            if (novoStatus != StatusPedido.ENTREGUE && novoStatus != StatusPedido.CANCELADO) {
                throw new RuntimeException("Status inválido para pedido APROVADO");
            }
        }

        if (atual == StatusPedido.PRODUZINDO) {
            if (novoStatus != StatusPedido.ENTREGUE && novoStatus != StatusPedido.CANCELADO) {
                throw new RuntimeException("Status inválido para pedido APROVADO");
            }
        }

        if (atual == StatusPedido.ENTREGUE || atual == StatusPedido.CANCELADO) {
            throw new RuntimeException("Pedido não pode mais ser alterado");
        }

        pedido.setStatus(novoStatus);

        Pedido atualizado = pedidoRepository.save(pedido);

        return toDTO(atualizado);
    }

    public org.springframework.data.domain.Page<PedidoDTO> listarTodosPaginado(
            org.springframework.data.domain.Pageable pageable) {

        return pedidoRepository.findAll(pageable)
                .map(this::toDTO);
    }

    public org.springframework.data.domain.Page<PedidoDTO> listarPorStatusPaginado(
            StatusPedido status,
            org.springframework.data.domain.Pageable pageable) {

        return pedidoRepository.findByStatus(status, pageable)
                .map(this::toDTO);
    }
}