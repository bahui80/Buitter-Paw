package it.itba.edu.ar.converters;

import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

public class EnhancedFormattingConversionServiceFactoryBean extends
		FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		registry.addConverter(new StringToHashtagConverter());
		registry.addConverter(new UsernameToUserConverter());
		registry.addConverter(new IdToUserConverter());
	}

}
