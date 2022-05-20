package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.CatalogoClienteService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private ClienteRepository clienteRepository;
	private CatalogoClienteService catalogoClienteService;
	
	@GetMapping
	public List<Cliente> listar() {
		//-- antes de criar a classe de serviço, chamo diretamente o método do repositório
		//return clienteRepository.findAll();
		
		return catalogoClienteService.listar_todos();
		
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		
		//**** implementacao tradicional ****
		//		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		//		
		//		if (cliente.isPresent()) {
		//			return ResponseEntity.ok(cliente.get());
		//		}
		//		
		//		return ResponseEntity.notFound().build();
		
		//**** implementacao por interface funcional com lambda expression - forma 1:
		
		// return clienteRepository.findById(clienteId)
		//		.map(cliente -> ResponseEntity.ok(cliente))
		//		.orElse(ResponseEntity.notFound().build());

		//**** implementacao por interface funcional com lambda expression - forma 2:
		
		return clienteRepository.findById(clienteId)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		//-- antes de criar a classe de serviço, chamo diretamente o método do repositório
		//return clienteRepository.save(cliente);
		return catalogoClienteService.salvar(cliente);
		
	}
	
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId, @Valid @RequestBody Cliente cliente){
		
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cliente.setId(clienteId);
		
		//-- antes de criar a classe de serviço, chamo diretamente o método do repositório
		//cliente = clienteRepository.save(cliente);
		
		cliente = catalogoClienteService.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId){		
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		//-- antes de criar a classe de serviço, chamo diretamente o método do repositório
		//clienteRepository.deleteById(clienteId);		
		catalogoClienteService.excluir(clienteId);
		
		return ResponseEntity.noContent().build();
	}
	
}
