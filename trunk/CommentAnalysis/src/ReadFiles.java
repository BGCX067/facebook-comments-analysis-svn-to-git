/************************************************************************************************/
/*                                                                                              */
/*		Author:   Yann PRIK                                                                       */
/*		Activity: -Erasmus Master Student at Høgskolen i Gjøvik - Norway                          */
/*				   		Media Technology & Information Security                                      		*/
/*              -Student at Engineering School Sup'Galilee (University Paris 13 - Norway      	*/  
/*				  		Network & Telecommunications                                                 		*/
/*    Project:  Extract facebook users' comment from pictures                                   */ 
/*                                                                                              */
/************************************************************************************************/
/*                                                                                              */
/*		Class Goal: ReadFiles: Read facebook nameFiles and save them in Vector                        */
/*                                                                                              */
/*		Available Methods: -findLine                                                              */
/* 						   -stringOccur                                                        					  */
/* 						   -regexOccur                                                          					*/
/* 						   -regexPosition                                                       					*/
/* 						   -findComment                                                         					*/
/* 						   -displayComments                                                     					*/
/*                                                                                              */
/************************************************************************************************/

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;

public class ReadFiles {
	//Name of the file used
	String nameFile;
	
	//Vectors containing the positions of "commentBody" and </span> tags
	Vector <Integer> positionBeginning = new Vector<Integer>();
	Vector <Integer> positionEnd = new Vector<Integer>();
	
	//Vector containing all the comments
	Vector <String> comments = new Vector<String>();
	
	//detect which nameFile is being used
	int first = 0;
	
	//String allowing us to find the specific line corresponding to all the user's comments
	String wordLine;		
	//String allowing us to find each beginning comment posted
	String tagBeginning;
	//String allowing us to find each end comment posted
	String tagEnd;
	
	//Line containing the comments
	String line;
	
	public ReadFiles(){}
	
	public ReadFiles(File file)
	{	
		//return the path of the file
		this.nameFile = file.getName();
		//Check if nameFile used comes from facebook, flickr etc...		
		checkWebsite(this.nameFile);	
		
		//Line containing the comments		
		try{
		this.line = findLine(this.wordLine, nameFile);
		}
		catch(NullPointerException e){
			System.out.println("ReadFiles.java in ReadFiles class :\n"+this.wordLine+" is not found in "+nameFile);
			System.exit(0);
		}
		
		//Finding the position of the "commentBody" and </span> tags = finding each comment position
		this.positionBeginning = regexPosition(this.line, this.tagBeginning);
		this.positionEnd = regexPosition(this.line, this.tagEnd, this.positionBeginning);
				
		//Adding all the comments the the comments Vector
		this.comments = getComment(this.positionBeginning, this.positionEnd, this.line);	
	}
	
	public String findLine(String word, String nameFile) throws NullPointerException
	{
		//int count = 0;	
		int stop = 0;		
		String lineTemp = null;	
				 
		try
		{			
			FileInputStream fstream = new FileInputStream(nameFile);		  
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br2 = new BufferedReader(new InputStreamReader(in));						
						
			while (stop == 0)   
			{					
				lineTemp = br2.readLine();
				String [] Array= null;
				//When findLine is used to check the nature of the nameFile
				if(this.first == 0)
				{
					Array = lineTemp.split(" ");
					this.first = 1;
				}
				//When finLine is used to find facebook line containing all the comments
				else
					Array = lineTemp.split(">");						
				 
				for(String str:Array)
				{
					if(str.equalsIgnoreCase(word))	
					{
						//count =  count + 1;	
						stop = 1;
						break;
					}
				}							
			}			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}		
		
		return lineTemp;
	}
	
	public static final int stringOccur(String text, String string) {
		return regexOccur(text, Pattern.quote(string));
	}
		 
	public static final int regexOccur(String text, String regex) {		
		Matcher matcher = Pattern.compile(regex).matcher(text);		 
		int occur = 0;
		while(matcher.find()) {		    	
		    occur ++;		     
		}
		return occur;
	}
		 
	public static final Vector<Integer> regexPosition(String text, String regex) {
		
		Matcher matcher = Pattern.compile(regex).matcher(text);
		Vector <Integer> position = new Vector<Integer>();
		//First search of the redex mus start at position 0, so very important to initiate it
		position.add(0);
		while(matcher.find()) {	
			int i = 0;
			//Add each position of the same regex to the position vector
			//It needs for each different loop to begin after the previous matching search
			position.add(i,text.indexOf(regex, position.elementAt(i)+regex.length()));
			i++;
		}
		//Eliminate the last element of the position Vector which is 0
		//Also Eliminate the last found element not useful because not corresponding to a comment
		position.remove(position.size()-1);
		//position.remove(0);			    
		return position;
	}
		 
	public static final Vector<Integer> regexPosition(String text, String regex, Vector<Integer> referencePosition) {
		Vector <Integer> position = new Vector<Integer>();
		//First search of the redex mus start at position 0, so very important to initiate it
		position.add(0);
		for(int i=0; i<referencePosition.size(); i++)	
		{			    	
			//Add each position of the same regex to the position vector
			//It needs for each different loop to begin after the previous matching search
			position.add(i,text.indexOf(regex, referencePosition.elementAt(i)));			       			    
		}
		//Eliminate the last element of the position Vector which is 0			    
	    position.remove(position.size()-1);			   
		return position;
	}
		 
	public String findComment(int positionBeginning, int positionEnd, String text)
	{				
	 String comment = (String) text.subSequence(positionBeginning, positionEnd);			      
	 System.out.println(comment);
	 return comment;
	}
		 
	public Vector<String> getComment(Vector<Integer> positionBeginning, Vector<Integer> positionEnd, String text){			
		Vector<String> coms = new Vector<String>();
	    
		for(int i=1; i<=positionBeginning.size(); i++){
			//+2 corresponding to the characters "> after commentBody
			coms.add((String) text.subSequence(positionBeginning.elementAt(positionBeginning.size()-i)+
					tagBeginning.length()+2,positionEnd.elementAt(positionEnd.size()-i)));}			
		return coms;			 
	}
		 
	public void displayComments(){
		for(int i=0; i<this.positionBeginning.size(); i++)
		System.out.println(this.comments.elementAt(i)+"\n");}
	
	public void displayComments(JTextArea codeArea){
		for(int i=0; i<this.positionBeginning.size(); i++)
		codeArea.append(this.comments.elementAt(i)+"\n");}
	
	public int sizeString(String text, String regex){		
		int size =0;
		Matcher matcher = Pattern.compile(regex).matcher(text);		
		
		while(matcher.find()) {	
			size += 1;
		}			    
		return size;
	}
	
	public void displayLine(){
		System.out.println(this.line);}
	
	public void displayLine(JTextArea codeArea){
		codeArea.append(line+"\n");}
	
	//Check the if the nameFile used comes from facebook, flickr etc...
	public void checkWebsite(String nameFile){
		String facebookWordLine = " <span data-jsid=\"text\" class=\"commentBody\"";
		String facebookTagBeginning = "commentBody";		
		String facebookTagEnd = "<span";		
		String facebookLine = null;
				
		try{
			if(findLine("id=\"facebook\"", nameFile).contains("id=\"facebook\"") == true){	
				this.wordLine = facebookWordLine;
				this.tagBeginning = facebookTagBeginning;
				this.tagEnd = facebookTagEnd;		
			}
		}catch(NullPointerException e){
			System.out.println("ReadFiles.java in checkWebsites :\nYour nameFile "+nameFile+" is not a valid nameFile");
			System.exit(0);}
	}	
}
