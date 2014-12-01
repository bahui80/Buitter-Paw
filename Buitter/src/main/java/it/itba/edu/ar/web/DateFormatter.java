package it.itba.edu.ar.web;

import java.util.Locale;

import org.apache.wicket.datetime.DateConverter;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

public class DateFormatter extends DateConverter {

	public DateFormatter() {
		super(false);
	}
	
	@Override
	public String getDatePattern(Locale arg0) {
		return Locale.ENGLISH.toString();
	}

	@Override
	protected DateTimeFormatter getFormat(Locale arg0) {
		DateTimeFormatter fm =  new DateTimeFormatterBuilder()
		.appendMonthOfYearShortText()
		.appendLiteral(' ')
		.appendDayOfMonth(1)
		.appendLiteral(',')
		.appendLiteral(' ')
		.appendYear(4, 4)
		.appendLiteral(' ')
		.appendHourOfHalfday(1)
		.appendLiteral(':')
		.appendMinuteOfHour(2)
		.appendLiteral(':')
		.appendSecondOfMinute(2)
		.appendLiteral(' ')
		.appendHalfdayOfDayText()
        .toFormatter();
		
		fm.withLocale(arg0);
		return fm;
	}
}
