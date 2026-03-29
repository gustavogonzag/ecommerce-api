package com.gustavo.ecommerce_api.service;

import com.gustavo.ecommerce_api.dto.BairroDTO;
import com.gustavo.ecommerce_api.exception.RecursoNaoEncontradoException;
import com.gustavo.ecommerce_api.model.Bairro;
import com.gustavo.ecommerce_api.repository.BairroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BairroService {

    private final BairroRepository bairroRepository;

    public BairroService(BairroRepository bairroRepository) {
        this.bairroRepository = bairroRepository;
    }

    public BairroDTO salvar(BairroDTO dto) {
        Bairro bairro = new Bairro();
        bairro.setNome(dto.getNome());
        bairro.setTaxaEntrega(dto.getTaxaEntrega());

        return toDTO(bairroRepository.save(bairro));
    }

    public List<BairroDTO> listarTodos() {
        return bairroRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public BairroDTO buscarPorId(Long id) {
        Bairro bairro = bairroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Bairro não encontrado"));

        return toDTO(bairro);
    }

    public BairroDTO atualizar(Long id, BairroDTO dto) {
        Bairro bairro = bairroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Bairro não encontrado"));

        bairro.setNome(dto.getNome());
        bairro.setTaxaEntrega(dto.getTaxaEntrega());

        return toDTO(bairroRepository.save(bairro));
    }

    public void deletar(Long id) {
        Bairro bairro = bairroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Bairro não encontrado"));

        bairroRepository.delete(bairro);
    }

    private BairroDTO toDTO(Bairro bairro) {
        BairroDTO dto = new BairroDTO();
        dto.setId(bairro.getId());
        dto.setNome(bairro.getNome());
        dto.setTaxaEntrega(bairro.getTaxaEntrega());
        return dto;
    }
}