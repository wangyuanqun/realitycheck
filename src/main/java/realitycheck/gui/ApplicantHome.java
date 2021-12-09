package realitycheck.gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import realitycheck.model.Category;
import realitycheck.repo.ApplicantRepo;
import realitycheck.repo.ApplicationRepo;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ApplicantHome extends JFrame {

	private JPanel contentPane;
	private String applicantName;
	private ApplicationRepo applicationRepo;
	private JButton climateButton;
	private JButton covidButton;
	private JButton politicalButton;
	private JButton religiousButton;
	private JButton racialButton;

	/**
	 * Create the frame.
	 */
	public ApplicantHome(String applicantName, ApplicationRepo applicationRepo) {
		this.applicantName = applicantName;
		this.applicationRepo = applicationRepo;

		setTitle("Applicant Home");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 535, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel categoryLabel = new JLabel("Please select the categories that you want to apply for:");

		climateButton = new JButton("Climate");

		covidButton = new JButton("Covid19");

		politicalButton = new JButton("Political");

		religiousButton = new JButton("ReligiousExtremism");

		racialButton = new JButton("RacialRrejudice");

		//add listeners to all the buttons
		List<JButton> btnlist = new ArrayList<>(Arrays.asList(climateButton, covidButton, politicalButton, religiousButton, racialButton));

		for(JButton btn : btnlist) {
			btn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// create a new window to collect the information of application

					ApplicationHome ah = new ApplicationHome(btn, applicantName, applicationRepo);
					ah.pack();
					ah.setVisible(true);

					btn.setEnabled(false);
				}
			});
		}

		// close the window
		JButton closeButton = new JButton("Close");
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(climateButton)
						.addComponent(religiousButton))
					.addGap(84)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(racialButton)
							.addGap(206))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(closeButton)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(covidButton)
									.addPreferredGap(ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
									.addComponent(politicalButton)))
							.addGap(52))))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(categoryLabel)
					.addContainerGap(197, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(categoryLabel)
					.addGap(28)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(climateButton)
						.addComponent(covidButton)
						.addComponent(politicalButton))
					.addGap(40)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(religiousButton)
						.addComponent(racialButton))
					.addGap(112)
					.addComponent(closeButton)
					.addContainerGap(51, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
