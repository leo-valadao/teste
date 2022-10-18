package br.com.aulaws.sysgestao.service;

import org.springframework.stereotype.Service;

import br.com.aulaws.sysgestao.domain.Endereco;
import br.com.aulaws.sysgestao.error.NotFoundException;
import br.com.aulaws.sysgestao.repository.EnderecoRepository;

@Service
public class EnderecoService {
    
    private EnderecoRepository enderecoRepository;

    public void deleteById(Endereco endereco) {
        if (!enderecoRepository.existsById(endereco.getId())) {
            throw new NotFoundException("Endereço não encontrado. id="+endereco.getId());
        }

        enderecoRepository.delete(endereco);
    }
}
