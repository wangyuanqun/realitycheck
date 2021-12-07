package realitycheck.gui;

import static javax.swing.JOptionPane.*;
import static javax.swing.JOptionPane.showMessageDialog;

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
import realitycheck.model.Channel;
import realitycheck.repo.ChannelRepo;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JButton;
/**
 * 
 * @author Allan Xie
 *
 */
public class NominateChannel extends JFrame {
	private ChannelRepo channelRepo;
	
	private JPanel contentPane;
	private JCheckBox climateCheckBox;
	private JCheckBox covidCheckBox;
	private JCheckBox politicalCheckBox;
	private JCheckBox religousCheckBox;
	private JCheckBox racialCheckBox;
	private JLabel reasonLabel;
	private JLabel nameLabel;
	private JLabel linkLabel;
	private JLabel categoryLabel;
	private JEditorPane linkPane;
	private JEditorPane reasonPane;
	private JEditorPane namePane;
	private JButton nominateButton;
	
	public NominateChannel(ChannelRepo channelRepo) {
		this.channelRepo = channelRepo;
		
		setTitle("Nominate Channel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		nameLabel = new JLabel("Channel Name:");
		reasonLabel = new JLabel("Please provide your reason:");
		linkLabel = new JLabel("Channel Link:");
		categoryLabel = new JLabel("Please select corresponding categories:");
		
		climateCheckBox = new JCheckBox("Climate");
		covidCheckBox = new JCheckBox("Covid19");
		politicalCheckBox = new JCheckBox("Political");
		religousCheckBox = new JCheckBox("Religous Extremism");
		racialCheckBox = new JCheckBox("Racial Rrejudice");
		
		List<JCheckBox> listBox = new ArrayList<>(Arrays.asList(climateCheckBox, covidCheckBox, politicalCheckBox, religousCheckBox, racialCheckBox));

		reasonPane = new JEditorPane();
		linkPane = new JEditorPane();
		namePane = new JEditorPane();
		
		//check whether all details are filled out, if so, save the channel to database
		nominateButton = new JButton("Nominate");
		nominateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				List<Category> categories = new ArrayList<>();
				for(JCheckBox box : listBox) {
					if(box.isSelected()) {
						Category category = null;
						//switch statement to get the selected category
						switch(box.getText()) {
							case "Climate":
								category = Category.Climate;
								break;
							case "Covid19":
								category = Category.Covid19;
								break;
							case "Political":
								category = Category.Political;
								break;
							case "Religious Extremism":
								category = Category.Religious_Extremism;
								break;
							case "Racial Rrejudice":
								category = Category.Racial_Prejudice;
								break;
						}
						categories.add(category);
					}
				}
				//validate link field to make sure it is a link to youtube
				final String LINK_REGEX = "www.youtube.com/\\w+";
				//throw warning if any field is empty
				if(namePane.getText().isEmpty()) {
					showMessageDialog(null, "Please fill in the channel name", "Warning", ERROR_MESSAGE);
				} 
				else if(linkPane.getText().isEmpty()){
					showMessageDialog(null, "Please fill in the channel link", "Warning", ERROR_MESSAGE);
				}
				else if(categories.isEmpty()){
					showMessageDialog(null, "Please slect at least one category", "Warning", ERROR_MESSAGE);
				}
				else if(reasonPane.getText().isEmpty()){
					showMessageDialog(null, "Please fill in the reason for nominating", "Warning", ERROR_MESSAGE);
				}
				else if(!linkPane.getText().matches(LINK_REGEX)){
					showMessageDialog(null, "Please provide the correct link", "Warning", ERROR_MESSAGE);
				}
				else {
					//save nomination to database
					channelRepo.save(new Channel(namePane.getText(), linkPane.getText(), categories, reasonPane.getText()));
					showMessageDialog(null, "You have nominated a channel", "Success!", INFORMATION_MESSAGE);
				}
			}
		});

		//Swing Layout
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(105)
								.addComponent(covidCheckBox)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(politicalCheckBox))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(religousCheckBox)
								.addGap(59)
								.addComponent(racialCheckBox)))
						.addComponent(reasonPane, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
						.addComponent(nominateButton, Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(nameLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(namePane, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(linkLabel)
							.addGap(18)
							.addComponent(linkPane, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
						.addComponent(categoryLabel)
						.addComponent(climateCheckBox)
						.addComponent(reasonLabel))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(nameLabel)
						.addComponent(namePane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(linkLabel)
						.addComponent(linkPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(23)
					.addComponent(categoryLabel)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(covidCheckBox)
								.addComponent(politicalCheckBox))
							.addPreferredGap(ComponentPlacement.UNRELATED))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(climateCheckBox)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(religousCheckBox)
						.addComponent(racialCheckBox))
					.addGap(21)
					.addComponent(reasonLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(reasonPane, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(nominateButton)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}


