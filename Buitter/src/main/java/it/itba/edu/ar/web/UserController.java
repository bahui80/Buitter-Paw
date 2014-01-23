package it.itba.edu.ar.web;

import it.itba.edu.ar.domain.buit.Buit;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.validator.UserForm;
import it.itba.edu.ar.web.validator.UserFormValidator;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class UserController {

	private UserRepo userRepo;
	private UserFormValidator validator;
	
	public UserController(UserRepo userRepo, UserFormValidator validator) {
		this.userRepo = userRepo;
		this.validator = validator;
	}

	/*
	 * POST METHODS
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView showstats(
			@RequestParam(value = "fromdate", required = false) String fromdate,
			@RequestParam(value = "todate", required = false) String todate,
			@RequestParam(value = "groupby", required = false) String groupby,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		User user = userRepo.get((String) session.getAttribute("user"));

		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		Date toDate = null;
		Date fromDate = null;
		try {
			toDate = formatoDelTexto.parse(todate);
			fromDate = formatoDelTexto.parse(fromdate);
		} catch (ParseException e) {
			mav.setViewName("redirect:showstats");
			return mav;
		}
		
		if(groupby == null || !(groupby.equals("month") || groupby.equals("day") || (groupby.equals("hour")))) {
			mav.setViewName("redirect:showstats");
			return mav;
		}

		Set<Buit> filteredBuits = user.filterMyBuits(new BuitDateRangeFilter(
				fromDate, toDate));
		Map<String, Integer> data = this.groupedData(filteredBuits, groupby);
		Set<String> labels = data.keySet();

		Collection<Integer> values = data.values();

		mav.addObject("labels", labels);
		mav.addObject("values", values);
		mav.addObject("fromDate", fromDate);
		mav.addObject("toDate", toDate);

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView forgotpassword(
			@RequestParam(value = "username") User user,
			@RequestParam(value = "recover", required = false) String state,
			@RequestParam(value = "answer", required = false) String answer,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "password2", required = false) String password2) {
		
		boolean error = false;
		ModelAndView mav = new ModelAndView();

		if (state.equals("recover")) {
			if (user != null) {
				mav.addObject("correct_username", user.getUsername());
				mav.addObject("question", user.getSecretQuestion());
			} else {
				// mav.addObject("user_username", username);
				mav.addObject("error_username", "Incorrect username");
			}
		} else {
			mav.addObject("correct_username", user.getUsername());
			mav.addObject("question", user.getSecretQuestion());
			mav.addObject("user_answer", answer);
			if (!user.getSecretAnswer().equals(answer)) {
				error = true;
				mav.addObject("error_answer", "Incorrect secret answer");
			}
			error = checkPassword(password, password2, mav);
			if (error) {
				error = false;
			} else {
				user.setPassword(password);

				return new ModelAndView("redirect:login");
			}
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView login(
			@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "continue", required = false) String cont,
			@RequestParam(value = "username", required = false) User user,
			@RequestParam(value = "password", required = false) String password,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		// checkeo que no venga del formulario de logout
		if (logout != null) {
			mav.setViewName("redirect:../user/logout");
			return mav;
		}
		if (cont != null) {
			mav.setViewName("redirect:../home/home");
			return mav;
		}
		// checkeo que no este loggeado ya
		String usernameCookie = (String) session.getAttribute("user");
		if (usernameCookie != null) {
			mav.setViewName("redirect:login");
			return mav;
		}

		// logueo porque no esta ni loggeado ni se quiere desloguear

		if (userRepo.login(user, password)) {
			session.setAttribute("user", user.getUsername());
			return new ModelAndView("redirect:../home/home");
		} else {
			// mav.addObject("user_username", @RequestParam("username"));
			mav.addObject("error_login", "Username or password incorrect");

			return mav;
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView editprofile(UserForm userForm, Errors errors, HttpSession session) {
		ModelAndView mav = new ModelAndView("user/registration");
		
		mav.addObject("action", "editprofile");
		validator.validate(userForm, errors);
		
		if(errors.hasErrors()) {
			return mav;
		}
		
		try {
			userForm.update(userRepo.get((String) session.getAttribute("user")));
		} catch (IOException e) {
			errors.rejectValue("photo", "unopen");
			return mav;
		}
		
		mav.setViewName("redirect:../home/home");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView registration(UserForm userForm, Errors errors, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user;
		mav.addObject("action", "registration");
		validator.validate(userForm, errors);
		
		if(errors.hasErrors()) {
			return mav; 
		}
		
		
		try {
			user = userForm.build();
		} catch (IOException e1) {
			errors.rejectValue("photo", "unopen");
			return mav;
		}
			
		try {
			userRepo.add(user);
		} catch (HibernateException e) {
			errors.rejectValue("username", "repeated");
			return mav;
		}
		session.setAttribute("user", userForm.getUsername());
		mav.setViewName("redirect:../home/home");
		return mav;	
	}

	/*
	 * GET METHODS
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView login(HttpSession session) {
		ModelAndView mav = new ModelAndView();

		if (session.getAttribute("user") != null) {
			mav.addObject("error_logged_in", "error");
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView forgotpassword() {
		return new ModelAndView();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView logout(HttpSession session) {
		session.invalidate();

		return new ModelAndView("redirect:../home/home");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView editprofile(HttpSession session) {
		ModelAndView mav = new ModelAndView();

		String username = (String) session.getAttribute("user");
		if (username != null) {
			User user = userRepo.get(username);
			mav.addObject("userForm", new UserForm(user));
			mav.setViewName("user/registration");
		} else {
			mav.setViewName("error");
		}

		mav.addObject("action", "editprofile");

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showstats(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		if(session.getAttribute("user") == null) {
			mav.setViewName("error");
		}

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView registration(HttpSession session) {
		ModelAndView mav = new ModelAndView();

		String username = (String) session.getAttribute("user");

		if (username != null) {
			mav.addObject("error_logged_in", "error");
			return new ModelAndView("redirect:login");
		}

		mav.addObject("action", "registration");
		mav.addObject("userForm", new UserForm());

		return mav;
	}

	/*
	 * OTHER METHODS
	 */
	
	private boolean checkPassword(String password, String password2, ModelAndView mav) {
		if (!password.equals("")) {
			if (password.trim().length() == 0 || !password.matches("[a-zA-Z0-9]+")) {
				mav.addObject("error_password",
						"Invalid password format");
				return true;
			}
			if (password.length() > 32) {
				mav.addObject("error_password",
						"The password must contain up to 32 characters");
				return true;
			} else if (!password2.equals(password)) {
				mav.addObject("error_password2",
						"The passwords must match");
				return true;
			}
			mav.addObject("user_password2", password2);
			mav.addObject("user_password", password);
		} else {
			mav.addObject("error_password", "You must insert a password");
			return true;
		}
		return false;
	}
	
	private Map<String, Integer> groupedData(Set<Buit> buits, String groupby) {
		Map<String,Integer> data = null;
		if (groupby.equals("month")) {
			data = this.processMonthly(buits);
		} else if (groupby.equals("day")) {
			data = this.processDayly(buits);
		} else if (groupby.equals("hour")) {
			data = this.processHourly(buits);
		}
		 return data;
	}

	@SuppressWarnings("deprecation")
	private Map<String,Integer> processDayly(Set<Buit> buits) {
		Map<String, Integer> data = new TreeMap<String, Integer>(
				new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						Integer day1 = -1;
						Integer day2 = -1;
						
						String keys[] = {  "Domingo", "Lunes", "Martes", "Miercoles", "Jueves",
								"Viernes", "Sabado" };
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
	private Map<String,Integer> processHourly(Set<Buit> buits) {
		Map<String, Integer> data = new TreeMap<String, Integer>(
				new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						Integer i1 = null;
						Integer i2 = null;
						try{
							i1 = Integer.valueOf(o1);
							i2 = Integer.valueOf(o2);
						}catch(NumberFormatException ex){
							
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
	private Map<String,Integer> processMonthly(Set<Buit> buits) {
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
