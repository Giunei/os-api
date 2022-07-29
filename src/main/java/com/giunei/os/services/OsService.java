package com.giunei.os.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giunei.os.domain.Cliente;
import com.giunei.os.domain.OS;
import com.giunei.os.domain.Tecnico;
import com.giunei.os.domain.dtos.OSDTO;
import com.giunei.os.domain.enums.Prioridade;
import com.giunei.os.domain.enums.Status;
import com.giunei.os.repositories.OSRepository;
import com.giunei.os.services.excepctions.ObjectNotFoundException;

@Service
public class OsService {

	@Autowired
	private OSRepository repo;
	
	@Autowired
	private TecnicoService tecService;
	
	@Autowired
	private ClienteService clienteService;
	
	public OS findById(Integer id) {
		Optional<OS> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + OS.class.getName()));
	}
	
	public List<OS> findAll() {
		return repo.findAll();
	}

	public OS create(@Valid OSDTO obj) {
		return fromDTO(obj);
	}
	
	public OS update(OSDTO obj) {
		findById(obj.getId());
		return fromDTO(obj);
	}
	
	private OS fromDTO(OSDTO obj) {
		OS newObj = new OS();
		newObj.setId(obj.getId());
		newObj.setObservacoes(obj.getObservacoes());
		newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		newObj.setStatus(Status.toEnum(obj.getStatus()));
		
		Tecnico tec = tecService.findById(obj.getTecnico());
		Cliente cli = clienteService.findById(obj.getCliente());
		
		newObj.setTecnico(tec);
		newObj.setCliente(cli);
		
		if(newObj.getStatus().getCod().equals(2)) {
			newObj.setDataFechamento(LocalDateTime.now());
		}
		
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		findById(id);
		repo.deleteById(id);
	}

}
