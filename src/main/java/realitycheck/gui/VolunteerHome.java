package realitycheck.gui;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import realitycheck.model.Channel;
import realitycheck.repo.ChannelRepo;
import realitycheck.repo.CommentRepo;
import realitycheck.repo.VideoRepo;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JComboBox;
/**
 * 
 * @author Sichen Zhao
 *
 */
public class VolunteerHome extends JFrame {

	private JPanel contentPane;
	private JButton nominateButton;
	private JButton viewButton;
	private JComboBox<String> channelComboBox;
	
	private ChannelRepo channelRepo;
	private VideoRepo videoRepo;
	private CommentRepo commentRepo;
	private JButton voteButton;

	/**
	 * Create the frame.
	 */
	public VolunteerHome(ChannelRepo channelRepo, VideoRepo videoRepo, CommentRepo commentRepo) {
		// nominate channel, vote video, set comments
		this.channelRepo = channelRepo;
		this.videoRepo = videoRepo;
		this.commentRepo = commentRepo;
		
		setTitle("Volunteer Home");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 384, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		nominateButton = new JButton("Nominate a Channel");
		nominateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// new window for channel
				NominateChannel no = new NominateChannel(channelRepo);
				no.pack();
				no.setVisible(true);
			}
		});
		
		viewButton = new JButton("View Videos");
		
		channelComboBox = new JComboBox<>();
		
		JLabel selectChannelLabel = new JLabel("Please select a channel to view videos or vote channel:");
		// only verified channel can be viewed
		for(Channel ch : channelRepo.findByisVerified(true)) {
			channelComboBox.addItem(ch.getchannelName());
		}
		
		viewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(channelComboBox.getSelectedItem() == null) {
					showMessageDialog(null, "No channel selected.", "Warning", ERROR_MESSAGE);
				} else {
					// new window for videos
					ViewVideo vv = new ViewVideo(channelComboBox.getSelectedItem().toString(), videoRepo, commentRepo);
					vv.pack();
					vv.setVisible(true);
					
				}
			}
		});
		
		voteButton = new JButton("Vote Channel");
		voteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(channelComboBox.getSelectedItem() == null) {
					showMessageDialog(null, "No channel selected.", "Warning", ERROR_MESSAGE);
				} else {
					// add votes
					Channel ch = channelRepo.findByChannelName(channelComboBox.getSelectedItem().toString());
					ch.addVotes();
					channelRepo.save(ch);
					showMessageDialog(null, "You successfully voted for the channel.", "Message", INFORMATION_MESSAGE);
				}
			}
		});
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addComponent(selectChannelLabel))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(16)
								.addComponent(channelComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(viewButton))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addComponent(voteButton)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(99)
							.addComponent(nominateButton)))
					.addContainerGap(25, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(27)
					.addComponent(nominateButton)
					.addGap(39)
					.addComponent(selectChannelLabel)
					.addGap(32)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(viewButton)
						.addComponent(channelComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(voteButton)
					.addContainerGap(55, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
