package it.itba.edu.ar.web;

import java.util.Date;

import it.itba.edu.ar.model.Buit;

public class BuitDateRangeFilter implements BuitFilter {

	private Date fromDate;
	private Date toDate;
	
	public BuitDateRangeFilter(Date fromDate, Date toDate) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
	}
	
	@Override
	public boolean eval(Buit b) {
		Date d = b.getDate();
		if((d.compareTo(fromDate) < 0) || (d.compareTo(toDate) > 0))
			return false;
		return true;
	}

}
