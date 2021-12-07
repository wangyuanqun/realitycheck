package realitycheck.gui;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import realitycheck.model.Comment;
import realitycheck.model.Queue;
import realitycheck.model.Video;
import realitycheck.repo.CommentRepo;
import realitycheck.repo.VideoRepo;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
/**
 * 
 * @author Yuesong Duan
 *
 */
public class ViewVideo extends JFrame {

	private JPanel contentPane;
	
	private VideoRepo videoRepo;
	private CommentRepo commentRepo;
	private JLabel selectLabel;
	private JComboBox<String> videoComboBox;
	private JButton upVoteButton;
	private JButton downVoteButton;
	private JLabel commentLabel;
	private JComboBox<String> commentComboBox;
	private JEditorPane commentPane;
	private String channelName;

	/**
	 * Create the frame.
	 */
	public ViewVideo(String channelName, VideoRepo videoRepo, CommentRepo commentRepo) {
		
		this.videoRepo = videoRepo;
		this.commentRepo = commentRepo;
		this.channelName = channelName;
		
		setTitle("View Video");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 305, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		selectLabel = new JLabel("Please select a video:");
		
		videoComboBox = new JComboBox<>();
		for(Video vi : videoRepo.findAll()) {
			if(vi.getChannelName().equals(channelName)) {
				videoComboBox.addItem(vi.getVideoName());
			}
		}
		
		upVoteButton = new JButton("Up Vote");
		upVoteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(videoComboBox.getSelectedItem() == null) {
					showMessageDialog(null, "No video selected", "Warning", ERROR_MESSAGE);
				} else {
					Video vi = videoRepo.findByVideoName(videoComboBox.getSelectedItem().toString());
					vi.upVotes();
					if(vi.getVotes() > 99 && vi.getQueue() == Queue.Pending) {
						vi.setQueue(Queue.Active);
					}
					videoRepo.save(vi);
				}
			}
		});
		
		downVoteButton = new JButton("Down Vote");
		downVoteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(videoComboBox.getSelectedItem() == null) {
					showMessageDialog(null, "No video selected", "Warning", ERROR_MESSAGE);
				} else {
					Video vi = videoRepo.findByVideoName(videoComboBox.getSelectedItem().toString());
					vi.downVotes();
					videoRepo.save(vi);
				}
			}
		});
		
		
		
		commentLabel = new JLabel("Please select a comment to view:");
		
		commentPane = new JEditorPane();
		
		commentComboBox = new JComboBox<>();
		for(Comment co : commentRepo.findAll()) {
			commentComboBox.addItem(co.getContent());
		}
		commentComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					Comment co = commentRepo.findByContent(commentComboBox.getSelectedItem().toString());
					commentPane.setText(co.getContent());
					commentPane.setEditable(false);
				}
			}
		});
		
		JLabel suggestLabel = new JLabel("You can also suggest a comment yourself:");
		
		JEditorPane newCommentPane = new JEditorPane();
		
		JButton okButton = new JButton("OK");
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(newCommentPane.getText() == null) {
					showMessageDialog(null, "No comment suggested, you can close the window to quit instead.", "Warning", ERROR_MESSAGE);
				} else {
					Comment co = new Comment(videoComboBox.getSelectedItem().toString(), newCommentPane.getText());
					commentRepo.save(co);
					showMessageDialog(null, "Successfully suggested a comment.", "Message", INFORMATION_MESSAGE);
				}
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(okButton)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(newCommentPane, Alignment.LEADING)
							.addComponent(commentPane, Alignment.LEADING)
							.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(selectLabel)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(6)
										.addComponent(upVoteButton)))
								.addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(6)
										.addComponent(downVoteButton))
									.addComponent(videoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addComponent(commentLabel)
								.addGap(18)
								.addComponent(commentComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addComponent(suggestLabel, Alignment.LEADING)))
					.addContainerGap(113, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(selectLabel)
						.addComponent(videoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(upVoteButton)
						.addComponent(downVoteButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(commentLabel)
						.addComponent(commentComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(commentPane, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(suggestLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(newCommentPane, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(okButton)
					.addContainerGap(71, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
