package it.itba.edu.ar.web.converters;

import it.itba.edu.ar.domain.buit.BuitRepo;
import it.itba.edu.ar.domain.buit.Hashtag;

import org.springframework.core.convert.converter.Converter;

public class HashtagConverter implements Converter<String, Hashtag> {

	private BuitRepo buitRepo;
	
	public HashtagConverter(BuitRepo buitRepo) {
		this.buitRepo = buitRepo;
	}
	
	public Hashtag convert(String hashtag) {
		return buitRepo.getHashtag(hashtag);
	}
}
