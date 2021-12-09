package realitycheck.gui;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import realitycheck.model.Applicant;
import realitycheck.model.Application;
import realitycheck.model.Category;
import realitycheck.model.Expert;
import realitycheck.model.UserType;
import realitycheck.repo.ApplicantRepo;
import realitycheck.repo.ApplicationRepo;
import realitycheck.repo.ExpertRepo;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.JButton;

public class ViewApplication extends JFrame {

	private JPanel contentPane;

	private ApplicantRepo applicantRepo;

	private ApplicationTableModel model;

	private ExpertRepo expertRepo;

	private ApplicationRepo applicationRepo;
	private JComboBox<String> applicantComboBox;
	private JTable table;
	private JButton approveButton;

	/**
	 * Create the frame.
	 */
	public ViewApplication(ExpertRepo expertRepo, ApplicantRepo applicantRepo, ApplicationRepo applicationRepo) {
		this.applicantRepo = applicantRepo;
		this.applicationRepo = applicationRepo;
		this.model = new ApplicationTableModel();
		this.expertRepo = expertRepo;


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		applicantComboBox = new JComboBox<String>();
		for(Applicant ap : applicantRepo.findAll()) {
			applicantComboBox.addItem(ap.getUserName());
		}


		JLabel applicantLabel = new JLabel("Please select an applicant to view:");

		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		// if there is default value, then update the table
		if(applicantComboBox.getSelectedItem() != null) {
			model.refreshData(applicantComboBox.getSelectedItem().toString());
		}

		// if an item is selected, then update the table
		applicantComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					model.refreshData(applicantComboBox.getSelectedItem().toString());
					table.repaint();
				}
			}
		});

		approveButton = new JButton("Approve");
		// approve a selected application
		approveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if(table.getSelectedColumn() < 0) {
					// if not select, throw a warning
					showMessageDialog(null, "Please select a row before approving.", "Warning", ERROR_MESSAGE);
				} else {
					// if already approved, throw a warning
					if(model.getApplicationAt(table.getSelectedRow()).getApprove()) {
						showMessageDialog(null, "You have already approved this application.", "Warning", ERROR_MESSAGE);
					} else {
						// approve the applciation
						Application application = model.getApplicationAt(table.getSelectedRow());
						application.approve();
						applicationRepo.save(application);

						// if he is already an expert then add the category to his categories
						if(applicantRepo.findByUserName(application.getApplicantName()) == null) {
							Expert ex = expertRepo.findByUserName(application.getApplicantName());
							ex.addCategory(application.getCategory());
							expertRepo.save(ex);
						} else {
							// add the applicant to expert and delete the applicant
							Applicant ap = applicantRepo.findByUserName(application.getApplicantName());
							Expert ex = new Expert(UserType.Expert, ap.getUserName(), ap.getEmail(), ap.getPassword());
							ex.addCategory(application.getCategory());
							expertRepo.save(ex);
							applicantRepo.delete(ap);
						}
					}
				}
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(applicantLabel)
							.addGap(18)
							.addComponent(applicantComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
						.addComponent(approveButton, Alignment.TRAILING))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(applicantLabel)
						.addComponent(applicantComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(approveButton)
					.addContainerGap(15, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	/**
	 * This inner class is used to define the properties of application table
	 *
	 */
	private class ApplicationTableModel extends AbstractTableModel {

	    private String[] columnNames = {"Link", "Email Address"};

	    private List<Application> rows = new ArrayList<>();

	    public ApplicationTableModel() {
	    }

	    public void refreshData(String name) {
	    	this.rows.clear();

	    	for (Application ap: applicationRepo.findByApplicantName(name)) {
	    		this.rows.add(ap);
	    	}
	    }

	    @Override
	    public int getColumnCount() {
	        return columnNames.length;
	    }

	    @Override
	    public int getRowCount() {
	        return rows.size();
	    }

	    @Override
	    public Object getValueAt(int row, int col) {

	    	 Application application = rows.get(row) ;

	    	switch (col) {
	    		case 0: return application.getLink();
	    		case 1: return application.getInstitutionEmail();
	    	}

	    	return null ;

	    }

	    @Override
	    public String getColumnName(int col) {
	        return columnNames[col];
	    }

	    public Application getApplicationAt(int row) {
	    	return rows.get(row);
	    }
	}

}
