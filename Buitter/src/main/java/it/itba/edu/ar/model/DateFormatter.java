package it.itba.edu.ar.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateFormatter {

	Timestamp date;
	SimpleDateFormat formatter;
	
	public DateFormatter(Timestamp date){
		this.date = date;
	}
	
	public String getDate(){
		return formatter.format(date);
	}
	
	public void setSimpleDateFormatter(SimpleDateFormat formatter){
		this.formatter = formatter;
	}
}
