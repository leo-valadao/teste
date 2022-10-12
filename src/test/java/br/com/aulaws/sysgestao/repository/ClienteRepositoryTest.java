package br.com.aulaws.sysgestao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.aulaws.sysgestao.domain.Cliente;
import br.com.aulaws.sysgestao.domain.Endereco;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

@DataJpaTest
public class ClienteRepositoryTest {
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void testAddCliente() {        
        Endereco endereco = new Endereco("Av. Goiás", "Central", "Goiânia", "GO", "74000000");

        Cliente cliente = new Cliente("Jorge","09866512898", "joge@gmail.com", endereco);
        
        Cliente saveCliente = clienteRepository.save(cliente);
        
        assertThat(saveCliente).isNotNull();
        assertThat(saveCliente.getId()).isGreaterThan(0);

        assertThat(saveCliente.getEndereco()).isNotNull();
        assertThat(saveCliente.getEndereco().getId()).isGreaterThan(0);
    }

}
