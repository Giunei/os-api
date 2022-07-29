package com.giunei.os.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giunei.os.domain.Cliente;
import com.giunei.os.domain.Pessoa;
import com.giunei.os.domain.dtos.ClienteDto;
import com.giunei.os.repositories.ClienteRepository;
import com.giunei.os.repositories.PessoaRepository;
import com.giunei.os.services.excepctions.DataIntegratyViolationException;
import com.giunei.os.services.excepctions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private PessoaRepository pessoaRepo;
	
	@Autowired
	public ClienteRepository repo;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Cliente create(ClienteDto cliente) {
		if(Objects.nonNull(findByCPF(cliente))) {
			throw new DataIntegratyViolationException("Já existe esse CPF cadastrado!");
		}
		return repo.save(new Cliente(null, cliente.getNome(), cliente.getCpf(), cliente.getTelefone()));
	}
	
	public Cliente update(Integer id, @Valid ClienteDto objDTO) {
		Cliente oldObj = findById(id);
		
		if(Objects.nonNull(objDTO) && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		
		return repo.save(oldObj);
	}
	
	public void delete(Integer id) {
		Cliente obj = findById(id);
		
		if(obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Cliente possui Ordens de Serviço, não pode ser deletado!");
		}
		
		repo.deleteById(id);
	}

	private Pessoa findByCPF(ClienteDto cliente) {
		Pessoa pessoa = pessoaRepo.findByCPF(cliente.getCpf());
		if(Objects.nonNull(pessoa)) {
			return pessoa;
		}
		return null;
	}
}
