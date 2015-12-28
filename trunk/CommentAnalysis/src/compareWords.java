import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class compareWords{	
	private String URL;
	//Used to find the line containing the comparison value in the source code
	private String wup = "using wup is"; 
	private String endString = ".</p>";
	//If a word is non well writed the page doesn't return any result
	//then the program crashes, but if it works it returns a class "returns" in HTML code
	private String wrongWord = "Results";
	private double value;
	private String line;
	private StringBuffer sourceCode;
	BufferedReader reader;
	
	public compareWords(String word1, String word2) throws Exception{
		//Don't make any operation if words are the same
		if(word1.equalsIgnoreCase(word2))
		{
			this.value = 1.0;
			return;	
		}
		
		URL = "http://marimba.d.umn.edu/cgi-bin/similarity/similarity.cgi?" +
		"word1="+word1+"&senses1=all&" +
		"word2="+word2+"&senses2=all&measure=wup&rootnode=yes";			
		
		this.reader = read();
		
		//Initiate = read the first line to know if content is null or not
		this.line = reader.readLine();
		this.sourceCode = new StringBuffer();		
		
		while(this.line != null)
		{
			this.sourceCode.append(this.line);
			this.line = this.reader.readLine();
		}
		
		//If a word is misspelled do not continue algorithm because it would crash
		//instead return 0
		if(this.sourceCode.indexOf(this.wrongWord) == -1)
		{
			this.value = 0.0;
			System.out.println("saf");
			return;
		}
		
		int positionBeginning = this.sourceCode.indexOf(this.wup);
		int positionEnd = this.sourceCode.indexOf(this.endString);
		int begin = positionBeginning + this.wup.length() +1; 
		
		this.value = Double.valueOf(this.sourceCode.substring(begin, positionEnd));			
	}
	
	public BufferedReader read() throws MalformedURLException, IOException{		
		return new BufferedReader(new InputStreamReader(new URL(this.URL).openStream()));			
	}	
	public String getURL(){return this.URL;}
	public double getValue(){return this.value;}
	public void displayValue(){System.out.println(this.value);}
	public void displayLine(){System.out.println(this.line);}
	public void displaySource() throws IOException{
		while(this.line != null)
		{
			System.out.println(line);
			this.line = this.reader.readLine();
		}
	}
}
