package it.itba.edu.ar.web;

import it.itba.edu.ar.domain.buit.Buit;
import it.itba.edu.ar.domain.buit.BuitRepo;
import it.itba.edu.ar.domain.buit.Hashtag;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.base.BasePage;
import it.itba.edu.ar.web.buit.HashtagPage;
import it.itba.edu.ar.web.buit.ProfilePage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class HomePage extends BasePage {
	@SpringBean	
	private UserRepo userRepo;
	@SpringBean	
	private BuitRepo buitRepo;
	private Integer time = 1;
	private static final int trendingQuantity = 10;
	private long DAY_IN_MS = 1000 * 60 * 60 * 24;

	
	public HomePage() {
		final BuitterSession session = BuitterSession.get();
		
		WebMarkupContainer emptyUserContainer = new WebMarkupContainer("emptyUserContainer") {
			public boolean isVisible() {
				return !session.isSignedIn();
			}
		};
		
		WebMarkupContainer notEmptyUserContainer = new WebMarkupContainer("notEmptyUserContainer") {
			public boolean isVisible() {
				return session.isSignedIn();
			}
		};
		
		WebMarkupContainer whoToFollowContainer = new WebMarkupContainer("whoToFollowContainer") {
			public boolean isVisible() {
				return session.isSignedIn();
			}
		};
		
		IModel<List<User>> modelUsers = new LoadableDetachableModel<List<User>>() {
			@Override
			protected List<User> load() {
				List<User> userList = userRepo.getAll();
				if(userList.size() > 6) {
					return userList.subList(0, 6);
				} else {
					return userList;
				}
			}
		};
		
		final IModel<List<User>> modelRecommendations = new LoadableDetachableModel<List<User>>() {
			@Override
			protected List<User> load() {
				if(BuitterSession.get().isSignedIn()) {
					return userRepo.whoToFollow(BuitterSession.get().getUser());
				}
				return new ArrayList<User>();
			}
		};
		
		IModel<List<Buit>> modelMyBuits = new LoadableDetachableModel<List<Buit>>() {
			@Override
			protected List<Buit> load() {
				return new ArrayList<Buit>(session.getUser().getMybuits());
			}
		};
		
		IModel<List<Buit>> modelFollowingBuits = new LoadableDetachableModel<List<Buit>>() {
			@Override
			protected List<Buit> load() {
				ArrayList<Buit> buits = new ArrayList<Buit>();
				for(User user: session.getUser().getFollowing()) {
					buits.addAll(user.getMybuits());
				}
				return buits;
			}
		};
		
		notEmptyUserContainer.add(new HomePageListBuitsPanel("listBuitsPanel", modelMyBuits));
		
		notEmptyUserContainer.add(new HomePageListBuitsPanel("listBuitsPanel2", modelFollowingBuits));
		
		emptyUserContainer.add(new ListView<User>("users", modelUsers) {
			@Override
			protected void populateItem(ListItem<User> item) {
				item.add(new Image("userImage", new ProfPicResourceReference(item.getModel().getObject().getPhoto(), item.getModel().getObject().getUsername())));
				item.add(new Label("userUsername", new PropertyModel<String>(item.getModel(), "username")));
				item.add(new Label("userDescription", new PropertyModel<String>(item.getModel(), "description")));
				PageParameters pgParameters = new PageParameters();
				pgParameters.add("username", item.getModelObject().getUsername());
				item.add(new BookmarkablePageLink<Void>("profilePageLink", ProfilePage.class, pgParameters));
			}	
		});
		
		final IModel<List<Hashtag>> trendingTopicModel = new LoadableDetachableModel<List<Hashtag>>() {
			@Override
			protected List<Hashtag> load() {
				Date tdate = new Date(System.currentTimeMillis() - (time * DAY_IN_MS));
				return buitRepo.getHashtagsSinceDate(tdate,trendingQuantity);
			}
		};
		add(new ListView<Hashtag>("hashtags", trendingTopicModel) {
			@Override
			protected void populateItem(ListItem<Hashtag> item) {
				PageParameters pgParameters = new PageParameters();
				pgParameters.add("hashtag", item.getModelObject().getHashtag());
				BookmarkablePageLink<Void> hashtagPageLink = new BookmarkablePageLink<Void>("hashtagPageLink", HashtagPage.class, pgParameters);
				hashtagPageLink.add(new Label("hashtag", new PropertyModel<String>(item.getModel(), "hashtag")));
				hashtagPageLink.add(new Label("hashtagCount", new PropertyModel<String>(item.getModel(), "count")));
				item.add(hashtagPageLink);
			}
		});
		Label emptyListLabel = new Label("trendingEmpty", "There are no hashtags for this period of time.") {
			public boolean isVisible() {
				return trendingTopicModel.getObject().isEmpty();
			};
		};
		
		add(emptyListLabel);
		
		final List<Integer> integers = Arrays.asList(new Integer[] {1,7,30});
		IChoiceRenderer<Integer> renderer = new IChoiceRenderer<Integer>() {
			@Override
			public Object getDisplayValue(Integer key) {
				switch(key) {
					case 1: return "1 day";
					case 7: return "1 week";
					case 30: return "1 month";
					default: throw new IllegalStateException(time + " is not mapped!");
				}
			}

			@Override
			public String getIdValue(Integer key, int index) {
				return String.valueOf(integers.get(index));
			}
		};
		DropDownChoice<Integer> dpdown = new DropDownChoice<Integer>("timeSelect", new PropertyModel<Integer>(this, "time"), integers, renderer) {
			@Override
			protected void onSelectionChanged(final Integer newSelection) {
				super.onSelectionChanged(newSelection);
				time = newSelection;
			}
			@Override
            protected boolean wantOnSelectionChangedNotifications() {
                return true;
            }
		};
		
		Label emptyRecommendationLabel = new Label("recommendationEmpty", "There are no users to follow at this time.") {
			public boolean isVisible() {
				return modelRecommendations.getObject().isEmpty();
			};
		};
		
		whoToFollowContainer.add(emptyRecommendationLabel);
		
		whoToFollowContainer.add(new ListView<User>("recommendedUsers", modelRecommendations) {
			@Override
			protected void populateItem(ListItem<User> item) {
				item.add(new Image("recommendedUserImage", new ProfPicResourceReference(item.getModel().getObject().getPhoto(), item.getModel().getObject().getUsername())));
				PageParameters pgParameters = new PageParameters();
				pgParameters.add("username", item.getModelObject().getUsername());
				BookmarkablePageLink<Void> profilePageLink = new BookmarkablePageLink<Void>("profilePageLink", ProfilePage.class, pgParameters);
				profilePageLink.add(new Label("recommendedUsername", new PropertyModel<String>(item.getModel(), "username")));
				item.add(profilePageLink);
			}
		});
		
		
		
		add(dpdown);
		add(emptyUserContainer);
		add(notEmptyUserContainer);
		add(whoToFollowContainer);
	}
}
