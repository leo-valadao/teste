package br.com.aulaws.sysgestao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.aulaws.sysgestao.domain.Cliente;
import br.com.aulaws.sysgestao.domain.Endereco;
import br.com.aulaws.sysgestao.error.NotFoundException;
import br.com.aulaws.sysgestao.repository.ClienteRepository;
import br.com.aulaws.sysgestao.repository.EnderecoRepository;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoService enderecoService;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente não encontrado. id="+id));
    }

    public void save(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public void update(Cliente cliente) {
        if(!clienteRepository.existsById(cliente.getId())){
            throw new NotFoundException("Cliente não encontrado: id="+cliente.getId());
        }

        clienteRepository.saveAndFlush(cliente);
    }

    public void deletePorId(Long id) {
        if(!clienteRepository.existsById(id)){
            throw new NotFoundException("Cliente não encontrado. id="+id);
        }

        clienteRepository.deleteById(id);
    }

    public Cliente updatePartial(Long id, Endereco endereco) {
        if(!clienteRepository.existsById(id)){
            throw new NotFoundException("Cliente não encontrado. id="+id);
        }

        Cliente cliente = clienteRepository.findById(id).get();

        Endereco enderecoAtual = cliente.getEndereco();
        cliente.setEndereco(endereco);
        this.save(cliente);

        if (enderecoAtual != null) {
            this.enderecoService.deleteById(enderecoAtual);
        }

        return cliente;
    }


}
