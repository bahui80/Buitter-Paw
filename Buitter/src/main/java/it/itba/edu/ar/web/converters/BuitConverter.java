package it.itba.edu.ar.web.converters;

import it.itba.edu.ar.domain.buit.Buit;
import it.itba.edu.ar.domain.buit.BuitRepo;

import org.springframework.core.convert.converter.Converter;

public class BuitConverter implements Converter<String, Buit> {

	private BuitRepo buitRepo;
	
	public BuitConverter(BuitRepo buitRepo) {
		this.buitRepo = buitRepo;
	}
	
	public Buit convert(String buitid) {
		return buitRepo.getBuit(Integer.valueOf(buitid));
	}
}
