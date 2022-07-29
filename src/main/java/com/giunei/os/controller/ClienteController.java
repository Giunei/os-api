package com.giunei.os.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.giunei.os.domain.Cliente;
import com.giunei.os.domain.dtos.ClienteDto;
import com.giunei.os.services.ClienteService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	private ClienteService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDto> findById(@PathVariable Integer id) {
		ClienteDto objDto = new ClienteDto(service.findById(id));
		return ResponseEntity.ok().body(objDto);
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDto>> listAll() {
		List<ClienteDto> clientes = service.findAll().stream().map(obj -> new ClienteDto(obj))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(clientes);
	}
	
	@PostMapping
	public ResponseEntity<ClienteDto> create(@Valid @RequestBody ClienteDto objDto) {
		Cliente cliente = service.create(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDto> update(@PathVariable Integer id, @Valid @RequestBody ClienteDto objDTO) {
		ClienteDto newObj = new ClienteDto(service.update(id, objDTO));
		return ResponseEntity.ok().body(newObj);
	}
	
	//delete tecnico
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
