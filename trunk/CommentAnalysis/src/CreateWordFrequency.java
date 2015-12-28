/************************************************************************************************/
/*                                                                                              */
/*		Author:   Yann PRIK                                                                     	*/
/*		Activity: -Erasmus Master Student at H�gskolen i Gj�vik - Norway                        	*/
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.swing.JTextArea;

public class CreateWordFrequency{
	String eraseSymbol = "!#!?!";
	Vector<String> comments;
	//Accessible dataBase containing word & frequency of all users' comment
	//included in one file
	Vector<WordFrequency> dataBase = new Vector<WordFrequency>();
	
	WordFrequency wordFrequency;
	
	//Vector comments = vector containing the non sorted users' comments 
	CreateWordFrequency(Vector<String> comments)
	{
		if(comments.isEmpty() == false)
		{
			this.comments = (Vector<String>) comments.clone();			
		}	
	}
		
	//Use the method writeSentence, to write each sentence included in vector comment
	//into the database >> put each word of the String sentence into an array then
	//just a loop sizeof comments vector
	public void writeDataBase()
	{		
		String commentsFiltered;
		for(int i=0; i<this.comments.size(); i++)		
		{			
			commentsFiltered = filter(this.comments.elementAt(i));			
			String [] ArraySentence = commentsFiltered.split(" ");			
			writeSentence(ArraySentence);			
		}		
		
		for(int i=0; i<this.dataBase.size(); i++)
		{
			if(this.dataBase.elementAt(i).getWord().equals(""))
				this.dataBase.remove(this.dataBase.elementAt(i));
		}
	}
	
	//Take an array of word to compare to the database
	//Increase the frequency if word already included, else create a new instance
	//of a WordFrequency object
	public void writeSentence(String[] Array)
	{	
		int addWord = 1;
		if(this.dataBase.isEmpty() == true)
		{	
			//TAKE CARE MAYBE "" could erase a dB containing more objects...
			//NEED to change the symbol
			//SEE the same below
			this.dataBase.add(new WordFrequency(eraseSymbol));
		}		
		for(int i=0; i<Array.length; i++)
		{
			for(int j=0; j<this.dataBase.size(); j++)
			{
				//Word already existing in the dataBase
				//Only need to increase the frequency
				if(Array[i].equalsIgnoreCase(this.dataBase.elementAt(j).getWord()) == true)
				{
					this.dataBase.elementAt(j).increaseFrequency();
					//j = this.dataBase.size();
					addWord = 0;
				}				
			}
			//Word not existing in the dataBase
			//Need to create one new instance	
			if(addWord == 1)
			{
				//TAKE CARE MAYBE "" could erase a dB containing more objects...
				//NEED to change the symbol
				if(this.dataBase.elementAt(0).getWord() == eraseSymbol)
				this.dataBase.clear();
				
				WordFrequency word = new WordFrequency(Array[i]);
				this.dataBase.add(word);
			}
		}				
	}	
	
	//Get the siwe of a sentence included into a string
	//Here useless, used another technique to solve the problem
	/*public int sizeString(String text, String regex) {
		int size;
		
		if(text.isEmpty() == false)
			size = 1;
		else
			size = 0;
		Matcher matcher = Pattern.compile(regex).matcher(text);		
		
		while(matcher.find()) {	
			size += 1;
		}			    
		return size;
	}*/
	
	//Get all the word, and only the words included into the dataBase
	public Vector<String> getWords(Vector<WordFrequency> comments)
	{
		Vector<String> wordVector = new Vector<String>();
		for(int i=0; i<comments.size(); i++)
		{
			wordVector.add(comments.elementAt(i).getWord());
		}
		return wordVector;
	}
	
	//Get all the word, and only the words included into the dataBase
	public ArrayList<String> getArrayWords(Vector<WordFrequency> comments)
	{
		ArrayList<String> wordArray = new ArrayList<String>();
		for(int i=0; i<comments.size(); i++)
		{
			wordArray.add(comments.elementAt(i).getWord());
		}
		return wordArray;
	}
	
	//Display all the WordFrequency objects includd into the dataBase
	public void displayDatabase()
	{
		for(int i=0; i<this.dataBase.size(); i++)
		{			
			this.dataBase.elementAt(i).displayWord();
		}
	}
	
	//Display all the WordFrequency objects included into the dataBase
	//inside a special container JTextArea
	public void displayDatabase(JTextArea codeArea)
	{
		for(int i=0; i<this.dataBase.size(); i++)
		{			
			this.dataBase.elementAt(i).displayWord(codeArea);
		}
	}
	
	public Vector <WordFrequency> getWFDatabase(){return this.dataBase;}
	
	public String filter(String text)
	{		
		String [] regexLetters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n",
				"o","p","q","r","s","t","u","v","w","x","y","z",
				"A","B","C","D","E","F","G","H","I","J","K","L","M","N",
				"O","P","Q","R","S","T","U","V","W","X","Y","Z","-"};
		
		// ’ = ' ;
		String [] regexPonctuation = {"!", "?", ".", "'", ",", "^", ";", "(", ")",
				":","<br/>", "<br />", "</a>", "\\", "/", "<", ">", "\"", "#", "’" };
		String [] regexFacebook = {"data-hovercard=ajaxhovercarduserphpid=",
								   "href=httpwwwfacebookcom","‎a","‎","translatedBody",
								   "0","1","2","3","4","5","6","7","8","9","- "};
		
		for(int i=0; i<regexPonctuation.length; i++)
		{			
			text = text.replace(regexPonctuation[i], " ");
		}
		
		for(int i=0; i<regexFacebook.length; i++)
		{			
			text = text.replace(regexFacebook[i], "");			
		}		
			
		/*for(int i=0; i<regexLetters.length; i++)
		{
			if(text.contains(regexLetters.) == true);			
		}*/
		return text;
	}	
	
	//Sort the database
	public void sort(final String field, List<WordFrequency> dataBase, final String ascDescIndicator) {
	    Collections.sort(dataBase, new Comparator<WordFrequency>() {
	        @Override
	        public int compare(WordFrequency o1, WordFrequency o2) {
	            if(field.equals("word")) {
	                return o1.getWord().toUpperCase().compareTo(o2.getWord().toUpperCase());
	            } 
	            if(field.equals("frequency")) {
	            	if(o1.getFrequency() > o2.getFrequency())
	            		return 1;
	            	else
	            		return -1;
	            }
	            else
	            	return 0;
	        }           
	    });
	    if(ascDescIndicator.equals("desc"))
	    	Collections.reverse(this.dataBase);
	}
	
	//Same method to sort the database with different inputs
	public void sort(final String field, final String ascDescIndicator) {
	    Collections.sort(this.dataBase, new Comparator<WordFrequency>() {
			@Override
	        public int compare(WordFrequency o1, WordFrequency o2) {
	            if(field.equals("word")) {
	                return o1.getWord().toUpperCase().compareTo(o2.getWord().toUpperCase());
	            } 
	            if(field.equals("frequency")) {
	            	if(o1.getFrequency() > o2.getFrequency())
	            		return 1;
	            	else
	            		return -1;
	            }	           
	            else
	            	return 0;
	        }           
	    });
	    //Possibility to reverse the element inside the database
	    if(ascDescIndicator.equals("desc"))
	    	Collections.reverse(this.dataBase);	    	
	}
}
