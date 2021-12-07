package realitycheck.gui;

import static javax.swing.JOptionPane.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import realitycheck.model.Application;
import realitycheck.model.Category;
import realitycheck.repo.ApplicationRepo;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
/**
 * 
 * @author Yuanqun Wang
 *
 */
public class ApplicationHome extends JDialog {
	
	// define the format of the email address
	private final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

	private final JPanel contentPanel = new JPanel();
	
	private ApplicationRepo applicationRepo;
	
	private String applicantName;
	
	private JButton categoryBtn;
	private JTextField emailField;
	private JTextField linkField;
	
	/**
	 * Create the dialog.
	 */
	public ApplicationHome(JButton categoryBtn, String applicantName, ApplicationRepo applicationRepo) {
		this.applicantName = applicantName;
		this.applicationRepo = applicationRepo;
		this.categoryBtn = categoryBtn;
		
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel categoryLabel = new JLabel("You are applying for " + this.categoryBtn.getText() + ":");
		JLabel linkLabel = new JLabel("A link that describes your relevant expertise:");
		linkField = new JTextField();
		linkField.setColumns(10);
		JLabel emailLabel = new JLabel("An Email to contact the institution:");
		emailField = new JTextField();
		emailField.setColumns(10);
		
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(categoryLabel))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(35)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(emailLabel)
								.addComponent(linkLabel)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(62)
							.addComponent(linkField, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(emailField, GroupLayout.PREFERRED_SIZE, 266, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(111, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(categoryLabel)
					.addGap(45)
					.addComponent(linkLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(linkField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(emailLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(emailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(48, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				// check if all inputs are filled
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// check if information is filled
						
						if(linkField.getText().isEmpty() || emailField.getText().isEmpty()) {
							showMessageDialog(null, "You need to fill in your information to finish.", "Warning", ERROR_MESSAGE);
						} else if(!(emailField.getText().matches(EMAIL_REGEX))) {
							showMessageDialog(null, "Wrong format of email address.", "Warning", ERROR_MESSAGE);
						} else {
							
							Category category = null;
							switch(categoryBtn.getText()) {
								case "Climate":
									category = Category.Climate;
									break;
								case "Covid19":
									category = Category.Covid19;
									break;
								case "Political":
									category = Category.Political;
									break;
								case "ReligiousExtremism":
									category = Category.Religious_Extremism;
									break;
								case "RacialRrejudice":
									category = Category.Racial_Prejudice;
									break;
							}
							
							applicationRepo.save(new Application(applicantName, category, linkField.getText(), emailField.getText()));
							dispose();
							categoryBtn.setEnabled(false);
							
							showMessageDialog(null, "You successfully created an application", "Message", INFORMATION_MESSAGE);
						}
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// enable the apply Button again
						categoryBtn.setEnabled(true);
						
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

}
