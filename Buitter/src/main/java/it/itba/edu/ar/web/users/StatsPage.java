package it.itba.edu.ar.web.users;

import it.itba.edu.ar.domain.EntityModel;
import it.itba.edu.ar.domain.buit.Buit;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.web.BuitDateRangeFilter;
import it.itba.edu.ar.web.base.BasePage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.wicket.datetime.DateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import com.googlecode.wickedcharts.wicket15.highcharts.Chart;

public class StatsPage extends BasePage {
	private Date fromDate;
	private Date toDate;
	private String groupBy = "Hour";
	private static final List<String> CHOICES = Arrays.asList(new String[] { "Hour", "Day", "Month" });
	private IModel<Set<Buit>> modelBuits;
	private IModel<User> modelUser;
	private Options options = new Options();
	private Chart chart = new Chart("chart", options);
	
	public StatsPage(User user) {
		Form<Void> form = new Form<Void>("form") {
			@Override
			protected void onSubmit() {
				Map<String, Integer> data = groupedData(modelBuits.getObject(), groupBy);
				options.setChartOptions(new ChartOptions().setType(SeriesType.COLUMN));
				options.setTitle(new Title("Statistics"));
				options.setxAxis(new Axis().setCategories(new ArrayList<String>(data.keySet())));
				options.setyAxis(new Axis().setTitle(new Title("Amount of buits")));
				options.addSeries(new SimpleSeries().setData(new ArrayList<Number>(data.values())));
				chart.setOptions(options);
				super.onSubmit();
			}
		};
		RadioChoice<String> radioChoice = new RadioChoice<String>("radioChoice", new PropertyModel<String>(this, "groupBy"), CHOICES);
		DateConverter dc = new DateConverter(false) {
			@Override
			protected DateTimeFormatter getFormat(Locale arg0) {
				DateTimeFormatter fm =  new DateTimeFormatterBuilder().
						appendYear(4, 4).
						appendLiteral('-').
						appendMonthOfYear(2).
						appendLiteral('-').
						appendDayOfMonth(2).toFormatter();
				return fm;
			}
			
			@Override
			public String getDatePattern(Locale arg0) {
				return "YYYY-MM-DD";
			}
		};
		DateTextField fromDateTxtField = new DateTextField("fromDate", new PropertyModel<Date>(this, "fromDate"), dc) {
			@Override
			protected String getInputType() {
				return "date";
			}
		};
		
		DateTextField toDateTxtField = new DateTextField("toDate", new PropertyModel<Date>(this, "toDate"), dc) {
			@Override
			protected String getInputType() {
				return "date";
			}
		};
		form.add(fromDateTxtField);
		form.add(toDateTxtField);
		form.add(radioChoice);
		add(form);

		modelUser = new EntityModel<User>(User.class, user);
		modelBuits = new LoadableDetachableModel<Set<Buit>>() {
			@Override
			protected Set<Buit> load() {
				modelBuits.detach();
				modelUser.detach();
				return modelUser.getObject().filterMyBuits(new BuitDateRangeFilter(fromDate, toDate));
			}
		};
		add(chart);
//		Set<String> labels = data.keySet();
//		Collection<Integer> values = data.values();
				

		//
		// User user = userRepo.get((String) session.getAttribute("user"));
		//
		// SimpleDateFormat formatoDelTexto = new
		// SimpleDateFormat("yyyy-MM-dd");
		// Date toDate = null;
		// Date fromDate = null;
		// try {
		// toDate = formatoDelTexto.parse(todate);
		// fromDate = formatoDelTexto.parse(fromdate);
		// } catch (ParseException e) {
		// mav.setViewName("redirect:showstats");
		// return mav;
		// }
		//
		// if(groupby == null || !(groupby.equals("month") ||
		// groupby.equals("day") || (groupby.equals("hour")))) {
		// mav.setViewName("redirect:showstats");
		// return mav;
		// }
		//
		// Set<Buit> filteredBuits = user.filterMyBuits(new BuitDateRangeFilter(
		// fromDate, toDate));
		// Map<String, Integer> data = this.groupedData(filteredBuits, groupby);
		// Set<String> labels = data.keySet();
		//
		// Collection<Integer> values = data.values();
		//
		// mav.addObject("labels", labels);
		// mav.addObject("values", values);
		// mav.addObject("fromDate", fromDate);
		// mav.addObject("toDate", toDate);
		//
		// return mav;
	}
	
	@Override
	protected void onConfigure() {
		chart.setVisible(fromDate != null && toDate != null);
		replace(chart);
		super.onConfigure();
	}
	
	private Map<String, Integer> groupedData(Set<Buit> buits, String groupby) {
		Map<String, Integer> data = null;
		if (groupby.equals("Month")) {
			data = this.processMonthly(buits);
		} else if (groupby.equals("Day")) {
			data = this.processDayly(buits);
		} else if (groupby.equals("Hour")) {
			data = this.processHourly(buits);
		}
		return data;
	}

	@SuppressWarnings("deprecation")
	private Map<String, Integer> processDayly(Set<Buit> buits) {
		Map<String, Integer> data = new TreeMap<String, Integer>(
				new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						Integer day1 = -1;
						Integer day2 = -1;

						String keys[] = { "Domingo", "Lunes", "Martes",
								"Miercoles", "Jueves", "Viernes", "Sabado" };
						for (int i = 0; i < keys.length; i++) {
							if (keys[i].equals(o1))
								day1 = i;
							if (keys[i].equals(o2))
								day2 = i;
							if (day1 != -1 && day2 != -1)
								break;
						}
						return day1.compareTo(day2);
					}
				});

		String[] keys = { "Domingo", "Lunes", "Martes", "Miercoles", "Jueves",
				"Viernes", "Sabado" };
		this.initializeCount(keys, data);
		for (Buit b : buits) {
			int d = b.getDate().getDay();
			int i = data.get(keys[d]);
			data.put(keys[d], i + 1);
		}
		return data;
	}

	@SuppressWarnings("deprecation")
	private Map<String, Integer> processHourly(Set<Buit> buits) {
		Map<String, Integer> data = new TreeMap<String, Integer>(
				new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						Integer i1 = null;
						Integer i2 = null;
						try {
							i1 = Integer.valueOf(o1);
							i2 = Integer.valueOf(o2);
						} catch (NumberFormatException ex) {

						}

						return i1.compareTo(i2);
					}
				});

		String[] keys = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23" };
		this.initializeCount(keys, data);
		for (Buit b : buits) {
			int h = b.getDate().getHours();
			int i = data.get(keys[h]);
			data.put(keys[h], i + 1);
		}
		return data;
	}

	@SuppressWarnings("deprecation")
	private Map<String, Integer> processMonthly(Set<Buit> buits) {
		Map<String, Integer> data = new TreeMap<String, Integer>(
				new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						int month1 = 0;
						int month2 = 0;

						String keys[] = { "Enero", "Febrero", "Marzo", "Abril",
								"Mayo", "Junio", "Julio", "Agosto",
								"Septiembre", "Octubre", "Noviembre",
								"Diciembre" };
						for (int i = 0; i < keys.length; i++) {
							if (keys[i].equals(o1))
								month1 = i;
							if (keys[i].equals(o2))
								month2 = i;
							if (month1 != 0 && month2 != 0)
								break;
						}
						Date d1 = new Date(2013, month1, 1);
						Date d2 = new Date(2013, month2, 1);
						return d1.compareTo(d2);
					}
				});
		String keys[] = { "Enero", "Febrero", "Marzo", "Abril", "Mayo",
				"Junio", "Julio", "Agosto", "Septiembre", "Octubre",
				"Noviembre", "Diciembre" };
		this.initializeCount(keys, data);
		for (Buit b : buits) {
			int m = b.getDate().getMonth();
			int i = data.get(keys[m]);
			data.put(keys[m], i + 1);
		}
		return data;
	}

	private void initializeCount(String keysAux[], Map<String, Integer> data) {
		for (String s : keysAux) {
			data.put(s, 0);
		}
	}
	
}
