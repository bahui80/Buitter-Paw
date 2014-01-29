package it.itba.edu.ar.web;

import it.itba.edu.ar.web.buit.ProfilePage;
import it.itba.edu.ar.web.common.HibernateRequestCycleListener;

import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuitterApp extends WebApplication {

//	public static final ResourceReference ADD_ICON = new PackageResourceReference(DemoWicketApp.class, "resources/add.png");
//	public static final ResourceReference EDIT_ICON = new PackageResourceReference(DemoWicketApp.class, "resources/edit.gif");
//	public static final ResourceReference DELETE_ICON = new PackageResourceReference(DemoWicketApp.class, "resources/delete.gif");

	private final SessionFactory sessionFactory;
	
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
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new BuitterSession(request);
	}

	@Override
	protected IConverterLocator newConverterLocator() {
		ConverterLocator converterLocator = new ConverterLocator();
//		converterLocator.set(Professor.class, new ProfessorConverter());
//		converterLocator.set(Department.class, new DepartmentConverter());
//		converterLocator.set(Subject.class, new SubjectConverter(subjects));
		return converterLocator;
	}
	
}