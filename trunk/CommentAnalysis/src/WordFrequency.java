import java.util.Vector;

import javax.swing.JTextArea;

/************************************************************************************************/
/*                                                                                              */
/*		Author:   Yann PRIK                                                                     	*/
/*		Activity: -Erasmus Master Student at Høgskolen i Gjøvik - Norway                        	*/
/*				   Media Technology & Information Security                                      			*/
/*                -Student at Engineering School Sup'Galilee (University Paris 13 - Norway      */  
/*				   Network & Telecommunications                                                 			*/
/*    Project:  Extract facebook users' comment from pictures                                   */ 
/*                                                                                              */
/************************************************************************************************/
/*                                                                                              */
/*		Class Goal: WordFrequency: Return the frequency of words included into a String         	*/
/*                                                                                              */
/************************************************************************************************/

public class WordFrequency{
	private String word;
	private int frequency;
	//public String [] name;
	private double value;
	
	public WordFrequency(String word)
	{
		this.word = word;
		this.frequency = 1;
		this.value = 0.0;
	}	
	
	public String getWord(){return this.word;}	
	
	public int getFrequency(){return this.frequency;}	
	
	public double getValue(){return this.value;}
	
	public void setValue(double val){this.value = val;}
	
	public void increaseFrequency(){this.frequency += 1;}	
	//Display one word and frequency in the console
	public void displayWord(){System.out.println(this.word+" = "+this.frequency);} 
	
	//Add a word and frequency in a JTextArea
	//Useful in pannelVisualization.java
	public void displayWord(JTextArea CodeArea){CodeArea.append(this.word+" = "+this.frequency+"\n");}	
}
