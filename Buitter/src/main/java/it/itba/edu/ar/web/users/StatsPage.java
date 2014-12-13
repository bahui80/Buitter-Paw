package it.itba.edu.ar.web.users;

import it.itba.edu.ar.domain.EntityModel;
import it.itba.edu.ar.domain.buit.Buit;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.web.BuitDateRangeFilter;
import it.itba.edu.ar.web.base.BasePage;
import it.itba.edu.ar.web.validator.DateRangeValidator;

import java.awt.Color;
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
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.CssStyle;
import com.googlecode.wickedcharts.highcharts.options.DataLabels;
import com.googlecode.wickedcharts.highcharts.options.HorizontalAlignment;
import com.googlecode.wickedcharts.highcharts.options.Labels;
import com.googlecode.wickedcharts.highcharts.options.Legend;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.Tooltip;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import com.googlecode.wickedcharts.wicket15.highcharts.Chart;

public class StatsPage extends BasePage {
	private transient Date fromDate;
	private transient Date toDate;
	private String groupBy = "Hour";
	private static final List<String> CHOICES = Arrays.asList(new String[] {
			"Hour", "Day", "Month" });
	private IModel<Set<Buit>> modelBuits;
	private IModel<User> modelUser;
	private Options options = new Options();
	private Chart chart = new Chart("chart", options);

	public StatsPage(User user) {

		modelUser = new EntityModel<User>(User.class, user);

		Form<Void> form = new Form<Void>("form");
		RadioChoice<String> radioChoice = new RadioChoice<String>("radioChoice", new PropertyModel<String>(this, "groupBy"), CHOICES);
		DateConverter dc = new DateConverter(false) {
			@Override
			protected DateTimeFormatter getFormat(Locale arg0) {
				DateTimeFormatter fm = new DateTimeFormatterBuilder()
						.appendYear(4, 4).appendLiteral('-')
						.appendMonthOfYear(2).appendLiteral('-')
						.appendDayOfMonth(2).toFormatter();
				return fm;
			}

			@Override
			public String getDatePattern(Locale arg0) {
				return "YYYY-MM-DD";
			}
		};
		DateTextField fromDateTxtField = new DateTextField("fromDate",
				new PropertyModel<Date>(this, "fromDate"), dc) {
			@Override
			protected String getInputType() {
				return "date";
			}
		};
		fromDateTxtField.setRequired(true);
		form.add(new ComponentFeedbackPanel("fromDate_error", fromDateTxtField));

		DateTextField toDateTxtField = new DateTextField("toDate",
				new PropertyModel<Date>(this, "toDate"), dc) {
			@Override
			protected String getInputType() {
				return "date";
			}
		};
		toDateTxtField.setRequired(true);
		toDateTxtField.add(new DateRangeValidator(new PropertyModel<Date>(this, "fromDate")));
		form.add(new ComponentFeedbackPanel("toDate_error", toDateTxtField));

		form.add(fromDateTxtField);
		form.add(toDateTxtField);
		form.add(radioChoice);

		modelBuits = new LoadableDetachableModel<Set<Buit>>() {
			@Override
			protected Set<Buit> load() {
				return modelUser.getObject().filterMyBuits(
						new BuitDateRangeFilter(fromDate, toDate));
			}
		};

		form.add(new Button("confirm") {
			@Override
			public void onSubmit() {
				modelUser.detach();
				modelBuits.detach();
				Map<String, Integer> data = groupedData(modelBuits.getObject(),
						groupBy);
				options.clearSeries();
				options.setChartOptions(new ChartOptions().setType(
						SeriesType.COLUMN).setMargin(
						Arrays.asList(new Integer[] { 50, 50, 100, 80 })));
				options.setTitle(new Title("Statistics"));
				options.setxAxis(new Axis().setCategories(
						new ArrayList<String>(data.keySet()))
						.setLabels(
								new Labels()
										.setAlign(HorizontalAlignment.RIGHT)
										.setRotation(-45)
										.setStyle(
												new CssStyle().setProperty(
														"fontFamily",
														"Verdana, sans-serif")
														.setProperty(
																"fontSize",
																"13px"))));
				options.setyAxis(new Axis().setTitle(new Title(
						"Amount of buits")));
				options.setTooltip(new Tooltip()
						.setPointFormat("Buits between " + fromDate + " and "
								+ toDate + ": <b>{point.y:.1f}</b>"));
				options.addSeries(new SimpleSeries()
						.setDataLabels(
								new DataLabels(true)
										.setAlign(HorizontalAlignment.RIGHT)
										.setColor(Color.WHITE)
										.setRotation(-90)
										.setX(4)
										.setY(10)
										.setStyle(
												new CssStyle()
														.setProperty(
																"fontSize",
																"13px")
														.setProperty(
																"fontFamily",
																"Verdana, sans-serif")
														.setProperty(
																"textShadow",
																"0 0 3px black")))
						.setData(new ArrayList<Number>(data.values())));
				options.setLegend(new Legend(false));
				Chart chartNew = new Chart("chart", options);
				chart = (Chart) chart.replaceWith(chartNew);
			};
		});
		add(form);

		add(chart);
	}

	@Override
	protected void onConfigure() {
		super.onConfigure();
		chart.setVisible(fromDate != null && toDate != null);
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

						String keys[] = { "Sunday", "Monday", "Tuesday",
								"Wednesday", "Thursday", "Friday", "Saturday" };
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

		String[] keys = { "Sunday", "Monday", "Tuesday", "Wednesday",
				"Thursday", "Friday", "Saturday" };
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

						String keys[] = { "January", "February", "March",
								"April", "May", "June", "July", "August",
								"September", "October", "November", "December" };
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
		String keys[] = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
				"December" };
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
