package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import app.Main;
import dndEntities.CharacterSheet;

public class AppFrame extends JFrame implements ActionListener
{
	
	private JButton randomCharacter;
	private JLabel title, featureLabel;
	private textPanel smallerPanel1;
	private JTextArea featureList;
	private JComboBox<String> levelSelect;
	private String text = "Program is able to generate a limited amount of characters from a pool of two classes, which "
			+ "each have two archtypes it can select from. (Cleric and Rogue)"
			+ "\nOnly Race available is Aasimar with 10 subraces."
			+ "\nBackgrounds from A-S are available as long as the background does not grant extra spells."
			+ "\nFeats not available yet and only level can be manually selected at the moment."
			+ "\nFirst Build be patient. -_-";
	private String [] levels = {"Random", "1", "2", "3", "4", "5", "6", "7", "8", 
			"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
	
	public AppFrame() 
	{
		this.setSize(700,700); 
		this.setTitle("Random Dnd Sheet Application: Build 1 - 7/13/2022"); 
		this.setLayout(new BorderLayout());
		
		title = new JLabel("Random Dnd Sheet App", SwingConstants.CENTER);
		title.setFont(new Font("MV Boli", Font.PLAIN, 40));
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		smallerPanel1 = new textPanel();
		
		JPanel smallerPanel2 = new JPanel();
		smallerPanel2.setLayout(new GridLayout(4, 1, 0, 0));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2, 0, 0));
		JLabel buttonLabel = new JLabel("Generate Random Character:");
		buttonLabel.setFont(new Font("MV Boli", Font.PLAIN, 20));
		randomCharacter = new JButton("Click");
		randomCharacter.addActionListener(this);
		buttonPanel.add(buttonLabel);
		buttonPanel.add(randomCharacter);
		
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new GridLayout(1, 2, 0, 0));
		JLabel listLabel = new JLabel("Level of Character:");
		listLabel.setFont(new Font("MV Boli", Font.PLAIN, 20));
		levelSelect = new JComboBox<>();
		for (String lv: levels) {
			levelSelect.addItem(lv);
		}
		listPanel.add(listLabel);
		listPanel.add(levelSelect);
		
		smallerPanel2.add(new JLabel());
		smallerPanel2.add(listPanel);
		smallerPanel2.add(buttonPanel);
		smallerPanel2.add(new JLabel());
		
		centerPanel.add(smallerPanel1);
		centerPanel.add(smallerPanel2);
		
		JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(50, 50));
		JLabel label2 = new JLabel();
		label2.setPreferredSize(new Dimension(50, 50));
		JLabel label3 = new JLabel();
		label3.setPreferredSize(new Dimension(50, 100));
		
		this.add(BorderLayout.NORTH, title);
		this.add(BorderLayout.CENTER, centerPanel);
		this.add(BorderLayout.WEST, label);
		this.add(BorderLayout.EAST, label2);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == randomCharacter) 
		{
			smallerPanel1.setText("Creating new Sheet, this may take a moment.");
			String levelSelected = (String) levelSelect.getSelectedItem();
			
			JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			chooser.setDialogTitle("Save Character Sheet As"); 
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
			chooser.setAcceptAllFileFilterUsed(false);
			
			int fileSelected = chooser.showSaveDialog(null);
			
			if (fileSelected == JFileChooser.APPROVE_OPTION) {
				File selectedFile = chooser.getSelectedFile();
				
				if (!(selectedFile.getAbsolutePath().endsWith(".pdf")))
					selectedFile = new File(selectedFile.getAbsolutePath() + ".pdf");
				
				boolean canSave = true;
				
				if(selectedFile.exists()) 
				{
					int reply = JOptionPane.showConfirmDialog(null, 
							selectedFile.getName() + " already exists, are you sure you want to overwrite it?", 
									"Overwrite File?", JOptionPane.YES_NO_OPTION);
					
					if (reply == JOptionPane.NO_OPTION) {
						canSave = false;
					}
				}
				
				if (canSave) {
					
					boolean created = false;
					try {
						do {
							CharacterSheet character = Main.createCharacter(levelSelected);
							created = Main.createCharacterSheetFile(selectedFile.getAbsolutePath(), character);
					
							if (created == false) {
								smallerPanel1.setText("Could not create File. Please try again.");
								break;
							}
							else {
								smallerPanel1.setText("Sheet created.");
							}
						} while (created == false);
					}
					catch (Exception exception) 
					{
						StringWriter sw = new StringWriter();
						exception.printStackTrace(new PrintWriter(sw));
						String exceptionString = sw.toString();
						smallerPanel1.setText("Oopsies, please try again.");
						JOptionPane.showMessageDialog(null,"Uh oh an exception has occured-! " +
								"Please try restarting the program or try again.\n" +
								exceptionString, "Exception Type: " + exception.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			else {
				smallerPanel1.setText(text);
			}
		}
	}
	
	public class textPanel extends JPanel
	{
		JTextArea featureList;
		JLabel featureLabel;
		
		public textPanel() 
		{
			this.setLayout(new GridLayout(2, 1, 0, 0));
			
			this.featureLabel = new JLabel("Features Available", SwingConstants.CENTER);
			featureLabel.setFont(new Font("MV Boli", Font.PLAIN, 30));
			
			featureList = new JTextArea();
			featureList.setFont(new Font("MV Boli", Font.PLAIN, 13));
			featureList.setText(text);
			featureList.setBackground(Color.white);
			featureList.setLineWrap(true);
			featureList.setWrapStyleWord(true);
			
			this.add(featureLabel);
			this.add(featureList);
		}
		
		public void setText(String message) 
		{
			featureList.setText(message);
			repaint();
		}
		
		@Override
		public void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			draw(g);
		}
		
		public void draw(Graphics g) 
		{
			featureList.setText(featureList.getText());
			featureList.setFont(new Font("MV Boli", Font.PLAIN, 13));
			featureList.setBackground(Color.white);
			featureList.setLineWrap(true);
			featureList.setWrapStyleWord(true);
		}
	}
	

}
