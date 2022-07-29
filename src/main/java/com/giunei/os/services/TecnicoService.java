package com.giunei.os.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giunei.os.domain.Pessoa;
import com.giunei.os.domain.Tecnico;
import com.giunei.os.domain.dtos.TecnicoDto;
import com.giunei.os.repositories.PessoaRepository;
import com.giunei.os.repositories.TecnicoRepository;
import com.giunei.os.services.excepctions.DataIntegratyViolationException;
import com.giunei.os.services.excepctions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repo;
	
	@Autowired
	private PessoaRepository pessoaRepo;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
	}

	public List<Tecnico> findAll() {
		return repo.findAll();
	}

	public Tecnico create(TecnicoDto objDto) {
		if(Objects.nonNull(findByCPF(objDto))) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base dados!");
		}
		return repo.save(new Tecnico(null, objDto.getNome(), objDto.getCpf(), objDto.getTelefone()));
	}
	
	public Tecnico update(Integer id, @Valid TecnicoDto objDTO) {
		Tecnico oldObj = findById(id);
		
		if(Objects.nonNull(objDTO) && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		
		return repo.save(oldObj);
	}
	
	public void delete(Integer id) {
		Tecnico obj = findById(id);
		
		if(obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Técnico possui Ordens de Serviço, não pode ser deletado!");
		}
		
		repo.deleteById(id);
	}

	private Pessoa findByCPF(TecnicoDto objDTO) {
		Pessoa obj = pessoaRepo.findByCPF(objDTO.getCpf());
		if(Objects.nonNull(obj)) {
			return obj;
		}
		return null;
	}

}