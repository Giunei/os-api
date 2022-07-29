package com.giunei.os.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giunei.os.domain.Cliente;
import com.giunei.os.domain.OS;
import com.giunei.os.domain.Tecnico;
import com.giunei.os.domain.enums.Prioridade;
import com.giunei.os.domain.enums.Status;
import com.giunei.os.repositories.ClienteRepository;
import com.giunei.os.repositories.OSRepository;
import com.giunei.os.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private OSRepository osRepository;

	public void instanciaDB() {
//		Tecnico t1 = new Tecnico(null, "Valdir Cezar", "534.689.300-89", "(88) 98888-8888");
//		Tecnico t2 = new Tecnico(null, "Giunei PMJ", "753.426.890-74", "(88) 98888-6988");
//		Cliente c1 = new Cliente(null, "Betina Camopos", "076.124.240-61", "(88) 98888-8878");
//		OS os1 = new OS(null, Prioridade.ALTA, "Teste create OD", Status.ANDAMENTO, t1, c1);
//		
//		t1.getList().add(os1);
//		t2.getList().add(os1);
//		c1.getList().add(os1);	
//		
//		tecnicoRepository.saveAll(Arrays.asList(t1));
//		clienteRepository.saveAll(Arrays.asList(c1));
//		osRepository.saveAll(Arrays.asList(os1));
	}
}
