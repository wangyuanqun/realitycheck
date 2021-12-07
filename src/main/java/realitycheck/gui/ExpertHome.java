package realitycheck.gui;

/**
 * @author Yuanqun Wang -> View and approve applicant
 * @author Allan Xie -> verify/reject channels
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import realitycheck.model.Expert;
import realitycheck.repo.ApplicantRepo;
import realitycheck.repo.ApplicationRepo;
import realitycheck.repo.ChannelRepo;
import realitycheck.repo.ExpertRepo;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;

public class ExpertHome extends JFrame {

	private JPanel contentPane;
	
	private ApplicantRepo applicantRepo;
	
	private ApplicationRepo applicationRepo;
	
	private ChannelRepo channelRepo;
	
	private ExpertRepo expertRepo;
	
	private Expert expert;

	/**
	 * Create the frame.
	 */
	public ExpertHome(Expert expert, ExpertRepo expertRepo, ApplicationRepo applicationRepo, ApplicantRepo applicantRepo, ChannelRepo channelRepo) {
		this.expert = expert;
		this.applicantRepo = applicantRepo;
		this.applicationRepo = applicationRepo;
		this.channelRepo = channelRepo;
		this.expertRepo = expertRepo;
		
		
		setTitle("ExpertHome");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 324, 182);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		// Yuanqun Wang
		JButton viewApplicationButton = new JButton("View Applications");
		viewApplicationButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// create a new window
				ViewApplication va = new ViewApplication(expertRepo, applicantRepo, applicationRepo);
				va.pack();
				va.setVisible(true);
			}
		});
		
		// Allan Xie
		JButton viewNominationButton = new JButton("View Nominations");
		viewNominationButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// create a new window
				ViewNomination vn = new ViewNomination(expert, channelRepo);
				vn.pack();
				vn.setVisible(true);
			}
		});
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(76)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(viewNominationButton)
						.addComponent(viewApplicationButton))
					.addContainerGap(80, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(viewApplicationButton)
					.addGap(18)
					.addComponent(viewNominationButton)
					.addContainerGap(62, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
