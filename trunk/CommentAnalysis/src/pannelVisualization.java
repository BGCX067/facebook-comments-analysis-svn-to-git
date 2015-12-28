/************************************************************************************************/
/*                                                                                              */
/*		Author:   Yann PRIK                                                                     	*/
/*		Activity: -Erasmus Master Student at Høgskolen i Gjøvik - Norway                        	*/
/*				  		 Media Technology & Information Security                                      	*/
/*              -Student at Engineering School Sup'Galilee (University Paris 13 - Norway        */  
/*				   		Network & Telecommunications                                                		*/
/*    Project:  Extract facebook users' comment from pictures                                   */ 
/*                                                                                              */
/************************************************************************************************/
/*                                                                                              */
/*		Class Goal: CreateWordFrequency: Return the frequency of words included into a String   	*/
/*                                                                                              */
/************************************************************************************************/

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class pannelVisualization extends JFrame implements ActionListener,WindowListener {
	private File file;
	private String pageURL;
	private mainPanel mainpanel;
	
	private JMenuBar  menu;
		private JMenu  menuFile;		
			private JMenuItem openFileMenuItem; 
			private JMenuItem getWordsMenuItem;
			private JMenuItem getSourceMenuItem;
			private JMenuItem saveFileMenuItem;
			private JMenuItem cleanAreaMenuItem;
		
	private JTabbedPane tab;	
			
	//private  JPanel inputPanel;
		
	//private JTextArea codeArea;
  	
	openFile openfile;
	
	//Instantiate CreateFrequency class but dB do not contains the sorted words
	CreateWordFrequency dB;
	//Contains object WordFrequency, so words + frequency and later values
	Vector <WordFrequency> wordDatabase;

  public pannelVisualization() { 
  	super("Word Similarity");  	      
    
  	menu = new JMenuBar();
  	setJMenuBar(menu);
  	
  	menuFile=new JMenu("File");
    menuFile.setMnemonic(KeyEvent.VK_F);
    menu.add(menuFile);  
    
    openFileMenuItem = new JMenuItem("Open File...");
    openFileMenuItem.setActionCommand("Open File");
    menuFile.add(openFileMenuItem);
    openFileMenuItem.addActionListener(this); 
    
    getWordsMenuItem = new JMenuItem("Get Words");
  	getWordsMenuItem.setActionCommand("Get Words");
  	menuFile.add(getWordsMenuItem);
  	getWordsMenuItem.addActionListener(this);
		
  	getSourceMenuItem = new JMenuItem("Get Source");
  	getSourceMenuItem.setActionCommand("Get Source");
  	menuFile.add(getSourceMenuItem);
  	getSourceMenuItem.addActionListener(this); 
  	
  	saveFileMenuItem = new JMenuItem("Save");
  	saveFileMenuItem.setActionCommand("Save");
  	menuFile.add(saveFileMenuItem);
  	saveFileMenuItem.addActionListener(this);
  	
  	cleanAreaMenuItem = new JMenuItem("Clean");
  	cleanAreaMenuItem.setActionCommand("Clean");
  	menuFile.add(cleanAreaMenuItem);
  	cleanAreaMenuItem.addActionListener(this);       
         
    this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    int w = dim.width;
    int h = dim.height;
    this.setSize(w/2, (h-40)/2); // 40px about size of lower taskbar in windows
   // this.setLocation(w/8, h/8);
    this.setVisible(true);    
    addWindowListener(this);  
  	}
	
	public void windowActivated(WindowEvent arg0) {}	
	public void windowClosed(WindowEvent arg0) {}	
	public void windowClosing(WindowEvent windowEvent)
	{
		if((JOptionPane.showConfirmDialog(null,"Are you sure you want to quit?",
				"Quit",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE))==JOptionPane.YES_OPTION)
			System.exit(0);
	}	
	public void windowDeactivated(WindowEvent arg0) {}	
	public void windowDeiconified(WindowEvent arg0) {}	
	public void windowIconified(WindowEvent arg0) {}	
	public void windowOpened(WindowEvent arg0) {}
	
	public void actionPerformed(ActionEvent e) {
    
    if (e.getSource() == getSourceMenuItem) {    	
    	try {
      	readURL websiteURL = new readURL(this.pageURL);     		   		
    		Reader r = websiteURL.getReader();              
        int c;
        while ((c = r.read()) != -1) {
          //codeArea.append(String.valueOf((char) c));
        }
        //codeArea.setCaretPosition(1);
      } catch (Exception ee) {}      
    }
    else if (e.getSource() == openFileMenuItem) {
    	//Select a file
    	openfile = new openFile(this.getContentPane());
    	this.file = openfile.getFile();    	
    	
    	//Create 4 tabs when open a file
    	this.tab = new JTabbedPane();
    	tab.setSize(this.getContentPane().getWidth(),
    							this.getContentPane().getHeight());
    	    	
      this.getContentPane().add(tab);           	
      	
      JTextArea area1 = new JTextArea();
      JTextArea area2 = new JTextArea();   
      JTextArea area3 = new JTextArea();            
          
      ReadFiles readfiles = new ReadFiles(this.file);
   	 	//readfiles.displayLine();   	 	
   	 	this.dB = new CreateWordFrequency(readfiles.comments);
   	 	this.dB.writeDataBase();
   	 	//dB.sort("frequency","");
   	 	this.dB.sort("word","");
   	 	//dB.displayDatabase();
   	 	
   	 	//Created to be transmitted to mainPanel
   	 	this.wordDatabase = new Vector<WordFrequency>();
   	 	this.wordDatabase = (Vector<WordFrequency>) this.dB.getWFDatabase().clone();   	 
   	 	
   	 	//Add main view in area0    	 
   	 	try {
				this.mainpanel = new mainPanel(this.file.getName(), this.wordDatabase);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
   	 	   	 	
   	 	//Add file content in area1
      area1.setText(this.file.getAbsolutePath());      
      //Add comments in area2
      readfiles.displayComments(area2);      
      //Add word and frequency in area3
      this.dB.displayDatabase(area3);     
      
      this.tab.add("Start", mainpanel);      
      this.tab.add("File Content", area1);
      this.tab.add("Comments", area2);
      this.tab.add("Word Frequency", area3);      
    }
    else if (e.getSource() == getWordsMenuItem) {
    	 ReadFiles readfiles = new ReadFiles(this.file);
    	 //readfiles.displayLine();
    	 //readfiles.displayComments();
    	 CreateWordFrequency dB = new CreateWordFrequency(readfiles.comments);
    	 dB.writeDataBase();
    	 //dB.sort("frequency","");
    	 dB.sort("word","");
    	 //dB.displayDatabase();
    }
    else if (e.getSource() == saveFileMenuItem) {
    	  return;  	
    }
    else if (e.getSource() == cleanAreaMenuItem) {
  	  //this.codeArea.setText("");  	
    }
	}	
}