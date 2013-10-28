package it.itba.edu.ar.web.converters;

import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.repo.BuitRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BuitConverter implements Converter<String, Buit> {

	private BuitRepo buitRepo;
	
	@Autowired
	public BuitConverter(BuitRepo buitRepo) {
		this.buitRepo = buitRepo;
	}
	
	public Buit convert(String buitid) {
		return buitRepo.getBuit(Integer.valueOf(buitid));
	}
}
