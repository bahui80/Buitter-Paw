package it.itba.edu.ar.domain.publicity;

import java.util.List;

public interface PublicityRepo {
	public List<Publicity> getAll();
	public Publicity getRandomPublicity();
}
