package br.com.aulaws.sysgestao.resource;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.aulaws.sysgestao.domain.Cliente;
import br.com.aulaws.sysgestao.service.ClienteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> obterTodosClientes() {
        List<Cliente> clientes = clienteService.findAll();
        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterPorId(@PathVariable("id") Long id) {
        Cliente cliente;
        cliente = clienteService.findById(id);

        cliente.add(linkTo(methodOn(ClienteResource.class).obterPorId
        (cliente.getId())).withSelfRel());

        return ResponseEntity.ok(cliente);
    }

    // Método HTTP PUT serve para atualizar um objeto/recurso já existente;
    // Verificar antes a existência do recurso a ser atualizado
    // PQ se não existir, o método ele vai gravar/criar um novo recurso ==> HTTP
    // POST
    @PutMapping
    public ResponseEntity<Cliente> atualizarCliente(@RequestBody Cliente cliente) {
        clienteService.findById(cliente.getId());
        clienteService.update(cliente);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarClientePorId(@PathVariable("id") Long id) {
        clienteService.deletePorId(id);
        return ResponseEntity.noContent().build();      
    }

    @PostMapping
    public ResponseEntity<Void> saveCliente(@RequestBody @Valid Cliente cliente, HttpServletRequest request) {

        clienteService.save(cliente);
        String path = request.getRequestURI()+"/"+cliente.getId();
        
        return ResponseEntity.created(URI.create(path)).build();
    }

}
