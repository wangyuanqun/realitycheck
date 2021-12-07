package realitycheck;

/**
 * @author Yuanqun Wang
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import realitycheck.gui.RealityCheck;
import realitycheck.model.Applicant;
import realitycheck.model.Expert;
import realitycheck.model.Moderator;
import realitycheck.model.Application;
import realitycheck.model.Volunteer;
import realitycheck.model.Category;
import realitycheck.model.Comment;
import realitycheck.model.Channel;
import realitycheck.model.UserType;
import realitycheck.model.Video;
import realitycheck.repo.ApplicationRepo;
import realitycheck.repo.ChannelRepo;
import realitycheck.repo.CommentRepo;
import realitycheck.repo.VideoRepo;
import realitycheck.repo.ApplicantRepo;
import realitycheck.repo.ExpertRepo;
import realitycheck.repo.ModeratorRepo;
import realitycheck.repo.VolunteerRepo;


@SpringBootApplication
public class App implements InitializingBean {
	
	@Autowired
	private ApplicantRepo applicantRepo;
	
	@Autowired
	private ExpertRepo expertRepo;
	
	@Autowired
	private ModeratorRepo moderatorRepo;
	
	@Autowired
	private VolunteerRepo volunteerRepo;
	
	@Autowired
	private ApplicationRepo applicationRepo;
	
	@Autowired
	private ChannelRepo channelRepo;
	
	@Autowired
	private VideoRepo videoRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	public static void main(String args[]) {
    	SpringApplicationBuilder builder = new SpringApplicationBuilder(App.class);
        builder.headless(false).run(args);
    }
	
	private void createNewIfNotExisted() {
		
		if(channelRepo.count() == 0) {
			channelRepo.save(new Channel("ABC News (Australia)", "https://www.youtube.com/c/NewsOnABC", Arrays.asList(Category.Climate, Category.Covid19, Category.Political, Category.Racial_Prejudice, Category.Religious_Extremism), "Fake News", true, 101));
			channelRepo.save(new Channel("Taylor Swift", "https://www.youtube.com/channel/UCqECaJ8Gagnn7YCbPEzWH6g", Arrays.asList(Category.Religious_Extremism), "Spreads religious extremism", true, 92));
			channelRepo.save(new Channel("Disney", "https://www.youtube.com/user/disneysshows", Arrays.asList(Category.Racial_Prejudice, Category.Political), "Racist", true, 59));
			channelRepo.save(new Channel("Vice", "https://www.youtube.com/user/vice", Arrays.asList(Category.Climate, Category.Political), "Nothing is true", true, 47));
			channelRepo.save(new Channel("National Geographic", "https://www.youtube.com/user/NationalGeographic", Arrays.asList(Category.Climate, Category.Religious_Extremism), "All the documentaries are fake", true, 13));
			channelRepo.save(new Channel("Netflix", "https://www.youtube.com/user/NewOnNetflix", Arrays.asList(Category.Climate, Category.Political, Category.Racial_Prejudice), "The shows are not real", true, 23));

			videoRepo.save(new Video("ABC News (Australia)", "ABC Video 1"));
			videoRepo.save(new Video("ABC News (Australia)", "ABC Video 2"));
			videoRepo.save(new Video("ABC News (Australia)", "ABC Video 3"));
			videoRepo.save(new Video("Taylor Swift", "Taylor Swift Video 1"));
			videoRepo.save(new Video("Taylor Swift", "Taylor Swift Video 2"));
			videoRepo.save(new Video("Disney", "Disney Video 1"));
			videoRepo.save(new Video("Disney", "Disney Video 2"));
			videoRepo.save(new Video("Vice", "Vice Video 1"));
			videoRepo.save(new Video("Vice", "Vice Video 2"));
			videoRepo.save(new Video("National Geographic", "National Geographic Video 1"));
			videoRepo.save(new Video("National Geographic", "National Geographic Video 2"));
			videoRepo.save(new Video("Netflix", "Netflix Video 1"));
			videoRepo.save(new Video("Netflix", "Netflix Video 2"));
		}
		
		
		if(expertRepo.count() == 0) {
			expertRepo.save(new Expert(UserType.Expert, "exp", "exp@exp.com", "exp", Arrays.asList(Category.Racial_Prejudice, Category.Political, Category.Climate, Category.Covid19, Category.Religious_Extremism)));
		}
		
		if(moderatorRepo.count() == 0) {
			moderatorRepo.save(new Moderator(UserType.Moderator, "mod", "mod@mod.com", "mod"));
		}
		
		if(commentRepo.count() == 0) {
			commentRepo.save(new Comment("ABC Video 1", "This is a comment"));
			commentRepo.save(new Comment("ABC Video 2", "This is a comment"));
			commentRepo.save(new Comment("Taylor Swift Video 1", "This is a comment"));
			commentRepo.save(new Comment("Taylor Swift Video 2", "This is a comment"));
			commentRepo.save(new Comment("Disney Video 1", "This is a comment"));
			commentRepo.save(new Comment("Disney Video 2", "This is a comment"));
			commentRepo.save(new Comment("Vice Video 1", "This is a comment"));
			commentRepo.save(new Comment("Vice Video 2", "This is a comment"));
			commentRepo.save(new Comment("National Geographic Video 1", "This is a comment"));
			commentRepo.save(new Comment("National Geographic Video 2", "This is a comment"));
			commentRepo.save(new Comment("Netflix Video 1", "This is a comment"));
			commentRepo.save(new Comment("Netflix Video 2", "This is a comment"));
		}
	}
	
	@Override
    public void afterPropertiesSet() throws Exception {
		
		createNewIfNotExisted();
		
		RealityCheck start = new RealityCheck(applicantRepo, expertRepo, moderatorRepo, volunteerRepo, applicationRepo, channelRepo, videoRepo, commentRepo);
		start.pack();
		start.setVisible(true);
		
    }
}




















