package realitycheck.gui;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import realitycheck.model.Moderator;
import realitycheck.model.UserType;
import realitycheck.model.Volunteer;
import realitycheck.repo.ApplicantRepo;
import realitycheck.repo.ModeratorRepo;
import realitycheck.repo.VolunteerRepo;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ModeratorHome extends JFrame {

	private JPanel contentPane;

	private VolunteerRepo volunteerRepo;
	private JComboBox<String> volunteerComboBox;
	private JButton promoteButton;
	private ModeratorRepo moderatorRepo;
	private JButton banButton;

	/**
	 * Create the frame.
	 */
	public ModeratorHome(VolunteerRepo volunteerRepo, ModeratorRepo moderatorRepo) {
		this.volunteerRepo = volunteerRepo;
		this.moderatorRepo = moderatorRepo;

		setTitle("Moderator Home");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 325, 297);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel volunteerLabel = new JLabel("Please select a volunteer to promote or ban:");
		volunteerComboBox = new JComboBox<String>();
		for(Volunteer vo : volunteerRepo.findAll()) {
			volunteerComboBox.addItem(vo.getUserName());
		}

		promoteButton = new JButton("Promote");
		//add volunteer to moderator and delete him from volunteer
		promoteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Volunteer vo = volunteerRepo.findByUserName(volunteerComboBox.getSelectedItem().toString());
				moderatorRepo.save(new Moderator(UserType.Moderator, vo.getUserName(), vo.getEmail(), vo.getPassword()));
				volunteerRepo.delete(vo);
				volunteerComboBox.removeAllItems();
				for(Volunteer vl : volunteerRepo.findAll()) {
					volunteerComboBox.addItem(vl.getUserName());
				}
				showMessageDialog(null, "Successfully promoted an account", "Message", INFORMATION_MESSAGE);
			}
		});

		banButton = new JButton("Ban");
		banButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Volunteer vo = volunteerRepo.findByUserName(volunteerComboBox.getSelectedItem().toString());
				if(vo != null) {
					volunteerRepo.delete(vo);
				}
				for(Volunteer vl : volunteerRepo.findAll()) {
					volunteerComboBox.addItem(vl.getUserName());
				}
				showMessageDialog(null, "Successfully banned an account", "Message", INFORMATION_MESSAGE);
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(19)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(volunteerComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(volunteerLabel)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(98)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(promoteButton)
								.addComponent(banButton))))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(19)
					.addComponent(volunteerLabel)
					.addGap(18)
					.addComponent(volunteerComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(promoteButton)
					.addGap(18)
					.addComponent(banButton)
					.addContainerGap(97, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
