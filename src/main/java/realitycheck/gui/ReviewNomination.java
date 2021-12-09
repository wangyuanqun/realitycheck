package realitycheck.gui;

import static javax.swing.JOptionPane.*;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import realitycheck.model.Category;
import realitycheck.model.Channel;
import realitycheck.model.Expert;
import realitycheck.repo.ChannelRepo;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class ReviewNomination extends JFrame {

	private JPanel contentPane;
	private JEditorPane nominatePane;
	private JEditorPane rejectPane;
	private JEditorPane linkPane;
	private JButton rejectButton;
	private JButton approveButton;

	private ChannelRepo channelRepo;
	private Channel channel;
	private Expert expert;
	private JButton copyButton;



	public ReviewNomination(ViewNomination vn, Expert expert, ChannelRepo channelRepo, Channel channel) {
		this.expert = expert;
		this.channelRepo = channelRepo;
		this.channel = channel;
		setTitle("View Nomination");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel channelLabel = new JLabel("Please select a channel to view:");
		JLabel reasonLabel = new JLabel("Please provide a reason if you reject the nomination:");
		JLabel linkLabel = new JLabel("Channel Link:");

		nominatePane = new JEditorPane();
		nominatePane.setEditable(false);
		nominatePane.setText(channel.getNominateReason());
		linkPane = new JEditorPane();
		linkPane.setText(channel.getLink());
		linkPane.setEditable(false);

		//if reject channel, the details are saved into the database and user is notified
		rejectButton = new JButton("Reject");
		rejectButton.setEnabled(false);
		rejectButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(rejectButton.getModel().isEnabled()) {
					channel.setNominateReason(rejectPane.getText());
					channel.verify(false);
					channel.review();
					channelRepo.save(channel);

					showMessageDialog(null, "Successfully reject the nomination.", "Message", INFORMATION_MESSAGE);

					//View Nomination page is refreshed to show update, this window is then closed
					vn.Refresh();
					setVisible(false);
					dispose();
				}
			}
		});

		//if approve channel, the details are saved into the database and user is notified
		approveButton = new JButton("Approve");
		approveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(approveButton.getModel().isEnabled()) {
					channel.verify(true);
					channel.review();
					channelRepo.save(channel);

					showMessageDialog(null, "Successfully approve the nomination.", "Message", INFORMATION_MESSAGE);

					//View Nomination page is refreshed to show update, this window is then closed
					vn.Refresh();
					setVisible(false);
					dispose();
				}
			}
		});

		//a button that copies the link to the clipboard
		copyButton = new JButton("Copy Link");
		copyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Toolkit.getDefaultToolkit()
		        .getSystemClipboard()
		        .setContents(
		                new StringSelection(channel.getLink()),
		                null
		        );
			}
		});

		//listens to reject pane to ensure that reason is provided if reject, and if reason is provided, prevent them from approving
		rejectPane = new JEditorPane();
		rejectPane.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				rejectButton.setEnabled(true);
				approveButton.setEnabled(false);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(rejectPane.getText().isEmpty()) {
					rejectButton.setEnabled(false);
					approveButton.setEnabled(true);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});

		//Swing Layout
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(rejectPane, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rejectButton, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 259, Short.MAX_VALUE)
							.addComponent(approveButton)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(nominatePane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(linkPane)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(copyButton)))
							.addContainerGap())))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(linkLabel, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(319, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(channelLabel)
					.addContainerGap(261, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(reasonLabel)
					.addContainerGap(160, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(12)
					.addComponent(linkLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(copyButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(linkPane, Alignment.TRAILING))
					.addGap(8)
					.addComponent(channelLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(nominatePane, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(reasonLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rejectPane, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(rejectButton)
						.addComponent(approveButton)))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
