package it.itba.edu.ar.web;

import it.itba.edu.ar.web.buit.FollowersPage;
import it.itba.edu.ar.web.buit.FollowingPage;
import it.itba.edu.ar.web.buit.HashtagPage;
import it.itba.edu.ar.web.buit.ProfilePage;
import it.itba.edu.ar.web.common.HibernateRequestCycleListener;

import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.cookies.CookieDefaults;
import org.apache.wicket.util.cookies.CookieUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuitterApp extends WebApplication {

	public static final ResourceReference NO_IMAGE = new PackageResourceReference(BuitterApp.class, "resources/nopicture.png");

	private final SessionFactory sessionFactory;
	private SessionProvider sessionProvider;
	private CookieService cookieService = new CookieService();
	
	@Autowired
	public BuitterApp(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}
	
	@Override
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		getRequestCycleListeners().add(new HibernateRequestCycleListener(sessionFactory));
		mountPage("/profile/${username}", ProfilePage.class);
		mountPage("/followers/${username}", FollowersPage.class);
		mountPage("/following/${username}", FollowingPage.class);
		mountPage("/hashtag/${hashtag}", HashtagPage.class);
		sessionProvider = new SessionProvider(cookieService);
	}

	@Override
	public Session newSession(Request request, Response response) {
		return sessionProvider.createNewSession(request);
	}

	@Override
	protected IConverterLocator newConverterLocator() {
		ConverterLocator converterLocator = new ConverterLocator();
//		converterLocator.set(Professor.class, new ProfessorConverter());
//		converterLocator.set(Department.class, new DepartmentConverter());
//		converterLocator.set(Subject.class, new SubjectConverter(subjects));
		return converterLocator;
	}

	public CookieService getCookieService() {
		return cookieService;
	}
	
}