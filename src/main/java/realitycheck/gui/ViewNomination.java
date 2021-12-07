package realitycheck.gui;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
/**
 * @author Allan Xie
 */
import static javax.swing.JOptionPane.showMessageDialog;


import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import realitycheck.model.Channel;
import realitycheck.model.Expert;
import realitycheck.model.Category;
import realitycheck.repo.ChannelRepo;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * 
 * @author Allan Xie
 *
 */
public class ViewNomination extends JFrame {
	private Expert expert;
	private ChannelRepo channelRepo;
	
	private ChannelTableModel model;
	
	private JPanel contentPane;
	private JTable table;
	private JButton reviewButton;
	private JLabel categoryLabel;
	private JLabel applicantLabel;
	private JComboBox<String> categoryComboBox;
	private JScrollPane scrollPane;

	
	public ViewNomination(Expert expert, ChannelRepo channelRepo) {
		this.expert = expert;
		this.channelRepo = channelRepo;
		this.model = new ChannelTableModel();
		ViewNomination vn = this;
		
		categoryLabel = new JLabel("Select Category:");
		applicantLabel = new JLabel("Please review the following nominations:");
		scrollPane = new JScrollPane();
		
		//review the selected nomination
		reviewButton = new JButton("Review");
		reviewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//if no nomination is selected, throw a warning
				if(table.getSelectedRowCount() != 1) {	
					showMessageDialog(null, "Please slect a row before approving.", "Warning", ERROR_MESSAGE);
				} 
				//otherwise, open review nomination window and display selected nomination details
				else {
					List<Category> commonCategories = new ArrayList<>(expert.getCategories());
					commonCategories.retainAll(model.getChannelAt(table.getSelectedRow()).getCategories());
					//if expert matches at least one category of selected channel, open new window
					if(commonCategories.size() > 0) {
						ReviewNomination rn = new ReviewNomination(vn, expert, channelRepo, model.getChannelAt(table.getSelectedRow()));
						rn.pack();
						rn.setVisible(true);
					}
					//if not, throw a warning
					else {
						showMessageDialog(null, "This is not your category", "Warning", ERROR_MESSAGE);
					}
				}
			}
		});
		
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		table.repaint();
		
		//add the options in the combo box
		categoryComboBox = new JComboBox();
		categoryComboBox.addItem("All");
		categoryComboBox.addItem("Your Categories");
		for(Category c: Category.values()) {
			categoryComboBox.addItem(c.toString());
		}
		
		//whenever an option is selected, refresh table
		categoryComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Refresh();
			}
		});
		
		Refresh();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Swing Layout
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
		gl_contentPane.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPane.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addComponent(table, GroupLayout.PREFERRED_SIZE, 402, GroupLayout.PREFERRED_SIZE)
							.addComponent(applicantLabel)
							.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
								.addComponent(categoryComboBox, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
								.addGap(178)))
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(374)
						.addComponent(reviewButton))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addContainerGap()
						.addComponent(categoryLabel, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
		gl_contentPane.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPane.createSequentialGroup()
				.addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
					.addComponent(categoryLabel)
					.addComponent(categoryComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(11)
				.addComponent(applicantLabel)
				.addGap(7)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addComponent(table, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addComponent(reviewButton))
		);
		contentPane.setLayout(gl_contentPane);
		
	}
	
	
//class to display table
	private class ChannelTableModel extends AbstractTableModel {
		
	    private String[] columnNames = {"Channel Name", "Category", "Votes"};
	    
	    private ArrayList<Channel> rows = new ArrayList<>();
	    private HashSet<Channel> allRows = new HashSet<>();
	    public ChannelTableModel() {
	    }
	    
	    //look at which category is selected in combo box to decide which channels to display
	    public void refreshData(String selectedItem) {
	    	rows = new ArrayList<>();
	    	allRows = new HashSet<>();
	    	
	    	List<Category> selectedCategories = new ArrayList<>();
	    	
	    	//switch statement so that display channel matches category
	    	switch(selectedItem) {
	    		//display all channels
	    		case "All":
	    			for(Category c: Category.values()) {
	    				selectedCategories.add(c);
	    			}
	    			break;
	    		
	    		//display channels that matches the expert's category
	    		case "Your Categories":
	    			for(Category c: expert.getCategories()) {
	    				selectedCategories.add(c);
	    			}
	    			break;

	    		//display each individual category
	    		case "Climate":
	    			selectedCategories.add(Category.Climate);
	    			break;
	    			
	    		case "Covid19":
	    			selectedCategories.add(Category.Covid19);
	    			break;
	    			
	    		case "Political":
	    			selectedCategories.add(Category.Political);
	    			break;
	    			
	    		case "Racial_Prejudice":
	    			selectedCategories.add(Category.Racial_Prejudice);
	    			break;
	    			
	    		case "Religious_Extremism":
	    			selectedCategories.add(Category.Religious_Extremism);
	    			break;
	    	}
	
	    	//only display channel is channel has not been reviewed and matches selected category
	    	for (Channel nom: channelRepo.findAll()) {
	    		for(int i = 0; i < nom.getCategories().size(); i++) {
	    			for(int j = 0; j < selectedCategories.size(); j++) {
	    				if(nom.getReviewed() == false && nom.getCategories().get(i) == selectedCategories.get(j)) {
	    					allRows.add(nom);
	    				}
	    			}
	    		}
	    	}
	    	
	    	//display by votes in descending order
	    	rows = new ArrayList<>(allRows);
	    	rows.sort(Comparator.comparingInt(Channel::getVotes).reversed());
	    }
	    
	    public int getColumnCount() {
	        return columnNames.length;
	    }

	    public int getRowCount() {
	        return rows.size();
	    }

	    public String getColumnName(int col) {
	        return columnNames[col];
	    }

	    public Object getValueAt(int row, int col) {
	    	
	    	 Channel nomination = rows.get(row) ;
	    	
	    	switch (col) {
	    		case 0: return nomination.getchannelName();
	    		case 1: return nomination.getCategories();
	    		case 2: return nomination.getVotes();
	    	}
	    	
	    	return null ;
	    	
	    }

	    public Class getColumnClass(int col) {
	    	switch (col) {
	    		case 0: return String.class ;
	    		case 1: return String.class ;
	    		case 2: return String.class ;
	    	}
	    	
	    	return null ;
    	}
	    
	    public Channel getChannelAt(int row) {
	    	return rows.get(row);
	    }
	}
	
	//refreshes the table
	public void Refresh() {
		model.refreshData(categoryComboBox.getSelectedItem().toString());
		table.repaint();
		table.clearSelection();
	}
}