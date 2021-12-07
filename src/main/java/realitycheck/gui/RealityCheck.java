package realitycheck.gui;

/**
 * @author Yuanqun Wang
 */

import static javax.swing.JOptionPane.*;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import realitycheck.model.UserType;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import realitycheck.repo.ApplicantRepo;
import realitycheck.repo.ApplicationRepo;
import realitycheck.repo.ChannelRepo;
import realitycheck.repo.CommentRepo;
import realitycheck.repo.ExpertRepo;
import realitycheck.repo.ModeratorRepo;
import realitycheck.repo.VideoRepo;
import realitycheck.repo.VolunteerRepo;
import realitycheck.model.Applicant;
import realitycheck.model.Expert;
import realitycheck.model.Moderator;
import realitycheck.model.Volunteer;


public class RealityCheck extends JFrame {
	
	// define the format of the email address
	private final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

	private JPanel contentPane;
	private JButton logInBtn;
	private JButton signInBtn;
	private JTextField userNameField;
	private JPasswordField userPasswordField;
	private JLabel userTypeLabel;
	private JComboBox<UserType> userTypeComboBox;

	private ApplicantRepo applicantRepo;
	
	private ApplicationRepo applicationRepo;
	
	private ChannelRepo channelRepo;

	private ExpertRepo expertRepo;

	private ModeratorRepo moderatorRepo;

	private VolunteerRepo volunteerRepo;

	private VideoRepo videoRepo;
	
	private CommentRepo commentRepo;
	
	private JLabel userEmailLabel;
	private JTextField userEmailField;

	/**
	 * Create the frame.
	 */
	public RealityCheck(ApplicantRepo applicantRepo, ExpertRepo expertRepo, ModeratorRepo moderatorRepo, VolunteerRepo volunteerRepo,
			ApplicationRepo applicationRepo, ChannelRepo channelRepo, VideoRepo videoRepo, CommentRepo commentRepo) {
		
		this.applicantRepo = applicantRepo;
		this.expertRepo = expertRepo;
		this.moderatorRepo = moderatorRepo;
		this.volunteerRepo = volunteerRepo;
		this.applicationRepo = applicationRepo;
		this.channelRepo = channelRepo;
		this.videoRepo = videoRepo;
		
		setTitle("Log In");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		JLabel userNameLabel = new JLabel("User Name:");
		
		JLabel userPasswordLabel = new JLabel("User Password:");
		
		userNameField = new JTextField();
		userNameField.setColumns(10);
		
		userPasswordField = new JPasswordField();
		userPasswordField.setColumns(10);
		
		userTypeLabel = new JLabel("User Type:");
		
		userEmailLabel = new JLabel("User Email:");
		
		userEmailField = new JTextField();
		userEmailField.setColumns(10);
		
		signInBtn = new JButton("Sign Up");
		signInBtn.setEnabled(false);
		
		userTypeComboBox = new JComboBox<UserType>(UserType.values());
		userTypeComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					if(userTypeComboBox.getSelectedItem() == UserType.Applicant || userTypeComboBox.getSelectedItem() == UserType.Volunteer) {
						signInBtn.setEnabled(true);
					} else {
						signInBtn.setEnabled(false);
					}
				}
			}
		});
		
		logInBtn = new JButton("Log in");
		
		// if the textFields are empty throw a warning window
		logInBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// if any text field is empty, throw a warning window
				if(checkInputs()) {
					// Warning window
					showMessageDialog(null, "You need to fill in your information to log in.", "Warning", ERROR_MESSAGE);
				} else {
					// check the format of email address
					if(userEmailField.getText().matches(EMAIL_REGEX)) {
						if(!checkUser()) {
							showMessageDialog(null, "User name or password or email is incorrect.", "Warning", ERROR_MESSAGE);
						} else {
							// new window
							UserType type = (UserType) userTypeComboBox.getSelectedItem();
							switch(type) {
								case Moderator:
									ModeratorHome mo = new ModeratorHome(volunteerRepo, moderatorRepo);
									mo.pack();
									mo.setVisible(true);
									break;
									
								case Volunteer:
									VolunteerHome vo = new VolunteerHome(channelRepo, videoRepo, commentRepo);
									vo.pack();
									vo.setVisible(true);
									break;
									
								case Expert:
									ExpertHome ex = new ExpertHome(expertRepo.findByUserName(userNameField.getText()), expertRepo, applicationRepo, applicantRepo, channelRepo);
									ex.pack();
									ex.setVisible(true);
									
									break;
									
								case Applicant:
									ApplicantHome ap = new ApplicantHome(userNameField.getText(), applicationRepo);
									ap.pack();
									ap.setVisible(true);
									
									break;
							}
							
						}
					} else {
						showMessageDialog(null, "Wrong format of email address.", "Warning", ERROR_MESSAGE);
					}
				}
			}
		});
		
		// if the textFields are empty throw a warning window
		signInBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(signInBtn.getModel().isEnabled()) {
					// if any text field is empty, throw a warning window
					if(checkInputs()) {
						// Warning window
						showMessageDialog(null, "You need to fill in your information to sign up.", "Warning", ERROR_MESSAGE);
					} else {
						// check the format of the email address
						if(userEmailField.getText().matches(EMAIL_REGEX)) {
							if(!checkUser()) {
								// new user assigned and new window
								UserType type = (UserType) userTypeComboBox.getSelectedItem();
								switch(type) {
									case Volunteer:
										volunteerRepo.save(new Volunteer(type, userNameField.getText(), userEmailField.getText(), userPasswordField.getText()));
										VolunteerHome vo = new VolunteerHome(channelRepo, videoRepo, commentRepo);
										vo.pack();
										vo.setVisible(true);
										
										break;
									case Applicant:
										applicantRepo.save(new Applicant(type, userNameField.getText(), userEmailField.getText(), userPasswordField.getText()));
										ApplicantHome ap = new ApplicantHome(userNameField.getText(), applicationRepo);
										ap.pack();
										ap.setVisible(true);
										
										break;
								}
								
							} else {
								showMessageDialog(null, "Account already exists.", "Warning", ERROR_MESSAGE);
							}
						} else {
							showMessageDialog(null, "Wrong format of email address.", "Warning", ERROR_MESSAGE);
						}
					}
				}
			}
		});
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(65)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(userEmailLabel)
							.addContainerGap())
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(userPasswordLabel)
								.addContainerGap())
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(userTypeLabel)
									.addContainerGap())
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(logInBtn)
										.addPreferredGap(ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
										.addComponent(signInBtn)
										.addGap(77))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(userNameLabel)
										.addGap(65)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(userNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(userTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(userPasswordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(userEmailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(108)))))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(32)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(userTypeLabel)
						.addComponent(userTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(userNameLabel)
						.addComponent(userNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(userEmailLabel)
						.addComponent(userEmailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(userPasswordLabel)
						.addComponent(userPasswordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(logInBtn)
						.addComponent(signInBtn))
					.addGap(31))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	/**
	 * To check if Inputs are empty.
	 * @return if fields are empty, return true
	 */
	private boolean checkInputs() {
		if(userNameField.getText().isEmpty() || userPasswordField.getText().isEmpty() || userEmailField.getText().isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * To check is account exists in the database.
	 * @return if the account exists, return true, otherwise, return false.
	 */
	private boolean checkUser() {
		UserType type = (UserType) userTypeComboBox.getSelectedItem();
		
		switch(type) {
			case Moderator:
				Moderator moderator = moderatorRepo.findByUserName(userNameField.getText());
				if(moderator != null) {
					if(moderator.getPassword().equals(userPasswordField.getText())) {
						return true;
					}
				}
			case Volunteer:
				Volunteer volunteer = volunteerRepo.findByUserName(userNameField.getText());
				if(volunteer != null) {
					if(volunteer.getPassword().equals(userPasswordField.getText())) {
						return true;
					}
				}
			case Expert:
				Expert expert = expertRepo.findByUserName(userNameField.getText());
				if(expert != null) {
					if(expert.getPassword().equals(userPasswordField.getText())) {
						return true;
					}
				}
			case Applicant:
				Applicant applicant = applicantRepo.findByUserName(userNameField.getText());
				if(applicant != null) {
					if(applicant.getPassword().equals(userPasswordField.getText())) {
						return true;
					}
				}
		}
		
		return false;
	}
}
