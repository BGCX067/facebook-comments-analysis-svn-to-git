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

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class readURL{	
	Reader reader;	
	
  public readURL(String URL){  	
    try {
    	URL pageURL = new URL(URL);
      HttpURLConnection urlConnection = (HttpURLConnection) pageURL.openConnection();               
      
      InputStream in = new BufferedInputStream(urlConnection.getInputStream());
      this.reader = new InputStreamReader(in);          
    } 
    catch (Exception ee){
    }
  }
  
  public Reader getReader(){
  	return this.reader;}
 }
