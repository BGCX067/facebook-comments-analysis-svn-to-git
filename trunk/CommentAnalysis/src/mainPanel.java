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
/*		Class Goal: nainPanel: Create the content of the main tab                               	*/
/*                                                                                              */
/************************************************************************************************/

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class mainPanel extends JPanel implements ActionListener, AdjustmentListener, InputMethodListener{
	JPanel panel;
	JPanel checkPanel;
	JPanel barPanel;
	JPanel tagPanel;
	JPanel picPanel;
	
	GridBagLayout gbl;
	
	JButton valid;
	JButton cancel;
	
	JTextField word1;
	JTextField word2;
	JTextField word3;
	JTextField word4;
	JTextField word5;
	JTextField word6;
	JTextField thresholdValue;
	
	JLabel label1;
	JLabel label2;
	JLabel label3;
	JLabel label4;
	JLabel label5;
	JLabel label6;
	JLabel thresholdLabel;
	JLabel picLabel;
		
	JTextArea tagArea;
		
	int threshold;
	
	compareWords compare;
	Vector<WordFrequency> dataBase;
	
	mainPanel(String fileName, Vector<WordFrequency> dB) throws IOException {
		
		
		/* ***DECLARATION******************************/
		/*        
		/* 					Declaration of many items
		/*
		/* *******************************************/
		
		this.dataBase = (Vector<WordFrequency>) dB.clone();
		
		this.panel = new JPanel();
		this.panel.setPreferredSize(new Dimension(250,200));
		
		this.checkPanel = new JPanel();
		this.checkPanel.setPreferredSize(new Dimension(250,50));		
		
		//button1 = new JButton("Button1");
		//button2 = new JButton("Button2");
		this.valid = new JButton("OK");
		this.cancel = new JButton("Cancel");
		
		this.word1 = new JTextField(25);		
		this.word2 = new JTextField(25);
		this.word3 = new JTextField(25);		
		this.word4 = new JTextField(25);
		this.word5 = new JTextField(25);		
		this.word6 = new JTextField(25);
		
		this.label1 = new JLabel("Word1");		
		this.label2 = new JLabel("Word2");
		this.label3 = new JLabel("Word3");
		this.label4 = new JLabel("Word4");
		this.label5 = new JLabel("Word5");
		this.label6 = new JLabel("Word6");
		
		
		/* ***WORDS-PANEL*****************************/
		/*        
		/*  Creation of panel with manual word as input
		/*  in JTextField to describe the picture
		/*
		/*
		/* *******************************************/
		
		this.panel.setBorder(BorderFactory.createTitledBorder(	BorderFactory.createLineBorder(Color.lightGray),
				"Fill Manual Tags",
				TitledBorder.LEFT,
				TitledBorder.TOP,
				new Font("arial",Font.ITALIC,15),
				Color.gray));
		
		this.panel.setLayout(new GridLayout(6,2));		
		this.panel.add(this.label1);
		this.panel.add(this.word1);
		this.panel.add(this.label2);	
		this.panel.add(this.word2);
		this.panel.add(this.label3);
		this.panel.add(this.word3);
		this.panel.add(this.label4);
		this.panel.add(this.word4);
		this.panel.add(this.label5);
		this.panel.add(this.word5);
		this.panel.add(this.label6);
		this.panel.add(this.word6);
		
		
		/* ***CHECK-PANEL*******************************/
		/*        
		/*  Creation of panel to validate the operation
		/*
		/* *********************************************/		
		this.checkPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray),
				"Check your task",
				TitledBorder.LEFT,
				TitledBorder.TOP,
				new Font("arial",Font.ITALIC,15),
				Color.gray));
		
		checkPanel.setLayout(new GridLayout(1,2));
		
		this.checkPanel.add(valid);
		this.checkPanel.add(cancel);
		
		
		/* ***SCROLLBAR & THRESHOLD***************************/
		/*        
		/*  Creation of panel containing a scrollbar to
		/*  dynamically change the threshold value which sets
		/*  which words are included as picture tags
		/* 	and also display the threshold value
		/*
		/* **************************************************/		
		this.barPanel = new JPanel();
		this.barPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray),
				"Adjust Treshold",
				TitledBorder.LEFT,
				TitledBorder.TOP,
				new Font("arial",Font.ITALIC,15),
				Color.gray));
		
		this.threshold = 500;		
		
		//Add an offset of 5, because it stops for unknown reason 5 units before ...
		JScrollBar hbar = new JScrollBar(JScrollBar.HORIZONTAL, threshold, 5, 0, 1005); 
		hbar.setUnitIncrement(1);	
		hbar.setBackground(Color.LIGHT_GRAY);		
		
		this.thresholdValue = new JTextField(5);
		this.thresholdValue.setText(Double.toString((double)this.threshold/1000));
		this.thresholdValue.setHorizontalAlignment(JLabel.CENTER);
		this.thresholdLabel = new JLabel("Threshold");
		
		this.barPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc0 = new GridBagConstraints();		
		gbc0.gridx = 0;
		gbc0.gridy = 0;
		gbc0.gridwidth = 2;
		gbc0.fill = 2;
		gbc0.weightx = 0.0;
		this.barPanel.add(hbar,gbc0);
		//gbc0.fill = 1;
		gbc0.gridwidth = 1;
		gbc0.gridx = 0;
		gbc0.gridy = 1;
		gbc0.weightx = 0.5;
		barPanel.add(thresholdLabel,gbc0);
		gbc0.gridx = 1;
		gbc0.gridy = 1;
		gbc0.weightx = 0.5;
		barPanel.add(thresholdValue,gbc0);
		
		
		/* ***PICTURE*************************************/
		/*        
		/*  Set the picture characteristics and resize if 	
		/*  too big
		/*
		/* ***********************************************/	
		this.picPanel = new JPanel();
		this.picPanel.setLayout(new BorderLayout());
		//Get height of previous panels to re-scale the picture		
		int panelH = panel.getHeight();		
		int checkPanelH = checkPanel.getHeight();
		int totalH = panelH + checkPanelH;
		
		/*Get the name of the .php file so as to have the matching picture
		It means the .php file et .jpg need to have the exac same name*/		 
		fileName = fileName.replaceAll(".php", "");		
		BufferedImage myPictureBefore = ImageIO.read(new File(fileName+".jpg"));	
		
		//Rescale the BuffereImage to the size of other elements*/
		int w = myPictureBefore.getWidth();
		int h = myPictureBefore.getHeight();		
		
		BufferedImage myPictureAfter = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		
		AffineTransform at = new AffineTransform();
		if(w<=400)
			at.scale((double)0.88, (double)0.88);
		else
			at.scale((double)0.5, (double)0.5);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		myPictureAfter = scaleOp.filter(myPictureBefore, myPictureAfter);		
		//Create the picture thanks to the BufferedImage
		this.picLabel = new JLabel(new ImageIcon( myPictureAfter ));
		this.picPanel.add(picLabel);		
		
		
		/* ***TAG AREA*************************************/
		/*        
		/*  Here are present the tags automatically added
		/*  in function of the threshold
		/*
		/* ************************************************/		
		this.tagPanel = new JPanel();
		this.tagPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray),
				"New Tags",
				TitledBorder.LEFT,
				TitledBorder.TOP,
				new Font("arial",Font.ITALIC,15),
				Color.gray));
		
		this.tagArea = new JTextArea(5,20);
		this.tagArea.setText("");
		this.tagArea.setBackground(Color.LIGHT_GRAY);
		this.tagPanel.setLayout(new BorderLayout());
		this.tagPanel.add(this.tagArea);
		
		
		/* ***GRIDBAGLAYOUT********************************/
		/*        
		/*  Add all the previous panel and picture into
		/*  a GridBagLayout to organize them
		/*
		/* ***********************************************/		
		gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gbl);		
		gbc.gridx = 1;
		gbc.gridy = 0;		
		add(panel,gbc);		
		gbc.gridy = 1;
		gbc0.weightx = 0.5;
		add(checkPanel,gbc);
		gbc.ipady = 10; // Scrollbar height
		gbc.gridy = 2;
		gbc.fill = 2; 
		gbc0.weightx = 0.5;
		add(barPanel,gbc);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.ipady = 0;
		gbc.gridx = 0;
		gbc.gridy = 0;		
		gbc.gridheight = 4; // one more than elements to stick all right elements togehter			
		add(picPanel,gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		add(tagPanel,gbc);
		
		
		/* ***LISTENER************************/
		/*        
		/*  Listen the buttons and Scrolbar
		/*
		/* ***********************************/				
		//button1.addActionListener(this);
    //button2.addActionListener(this);
		valid.addActionListener(this);
		cancel.addActionListener(this);	
		hbar.addAdjustmentListener(this);
		thresholdValue.addInputMethodListener(this);
		//model.addChangeListener(changeListener);
		
		//JScrollPane codeScrollerH = new JScrollPane();
 	 	//JScrollBar codeScrollerV = new JScrollBar(JScrollBar.VERTICAL);       
 	  //add(this, codeScrollerH); 
 	  //add(this, codeScrollerV);
	}
	
	public JPanel getPanel(){
		return this.panel;}
	
	public JPanel getCheckPanel(){
		return this.checkPanel;}
	
	public JPanel getbarPanel(){
		return this.barPanel;}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == valid)
		{
			//System.out.println("OK pushed \n");							
			this.tagArea.append(this.word1.getText()+" ");
			this.tagArea.append(this.word2.getText()+" ");
			this.tagArea.append(this.word3.getText()+" ");
			this.tagArea.append(this.word4.getText()+" ");
			this.tagArea.append(this.word5.getText()+" ");
			this.tagArea.append(this.word6.getText()+"\n");
			
			//Store the manual tags into a vector to be manipulated easily
			Vector <String> input = new Vector<String>();
			if(!this.word1.getText().isEmpty()){input.add(this.word1.getText());}
			/*if(!this.word1.getText().isEmpty()){input.add(this.word2.getText());}
			if(!this.word1.getText().isEmpty()){input.add(this.word3.getText());}
			if(!this.word1.getText().isEmpty()){input.add(this.word4.getText());}
			if(!this.word1.getText().isEmpty()){input.add(this.word5.getText());}
			if(!this.word1.getText().isEmpty()){input.add(this.word6.getText());}*/
			
			String word1;
			//Uncomment to help debugging 
			/*word1 = "wjeudg";				
			try {
				this.compare = new compareWords(word1, this.word1.getText());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(word1 +" "+this.word1.getText());
			this.compare.displayValue();*/			
			
			for(int i=0; i<this.dataBase.size(); i++)
			{		
				for(int j=0; j<input.size(); j++)
				{
					word1 = this.dataBase.elementAt(i).getWord();				
					try {
						this.compare = new compareWords(word1, input.elementAt(j));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println(word1 +" "+input.elementAt(j));
					this.compare.displayValue();
					if(this.dataBase.elementAt(i).getValue() < this.compare.getValue())
						this.dataBase.elementAt(i).setValue(this.compare.getValue());
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		else if(e.getSource() == cancel)
			System.out.println("Cancel pushed \n");		
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent evt) {
    Adjustable source = evt.getAdjustable();
    String text;    
    if (!evt.getValueIsAdjusting()) {
    	double value = (double)evt.getValue()/1000 ;
    	
    	this.thresholdValue.setText(Double.toString(value));
    	for(int i=0; i<this.dataBase.size(); i++)
    	{
    		if(this.dataBase.elementAt(i).getValue() >= value)
    		{ 
    			text = this.tagArea.getText().toString();
    			if(!text.contains(this.dataBase.elementAt(i).getWord()) )
    				this.tagArea.append(this.dataBase.elementAt(i).getWord()+";");
    		}
    		else
    		{
    			text = this.tagArea.getText().toString();    			
    			text = text.replace(this.dataBase.elementAt(i).getWord()+";", "");    			
    			this.tagArea.setText("");
    			this.tagArea.setText(text);
    		}
    	}
    }
  }	
	
	public void inputMethodTextChanged(InputMethodEvent event)
	{
		if(event.getSource() == this.thresholdValue)
		System.out.println ("Text changed");
	}

	@Override
	public void caretPositionChanged(InputMethodEvent arg0) {
		InputMethodListener inputMethodListener;
		
		case InputMethodEvent.CARET_POSITION_CHANGED:
	             System.out.println("esgsg");		
	}
}