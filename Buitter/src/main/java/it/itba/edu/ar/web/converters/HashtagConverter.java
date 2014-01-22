package it.itba.edu.ar.web.converters;

import it.itba.edu.ar.domain.buit.BuitRepo;
import it.itba.edu.ar.domain.buit.Hashtag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HashtagConverter implements Converter<String, Hashtag> {

	private BuitRepo buitRepo;
	
	@Autowired
	public HashtagConverter(BuitRepo buitRepo) {
		this.buitRepo = buitRepo;
	}
	
	public Hashtag convert(String hashtag) {
		return buitRepo.getHashtag(hashtag);
	}
}
