import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class openFile {
	private JFileChooser fileChooser;
	private File file;
	private JTabbedPane tab;
	private JTextArea codeArea;
	int check = 0;
	
	openFile(Container content){  	
		fileChooser = new JFileChooser();			
		
    if (this.fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
    	file = this.fileChooser.getSelectedFile(); 
    	check = 1;
      
    }else {    	
    	this.codeArea = new JTextArea();    	
    	content.add(this.codeArea);    	
    	this.codeArea.setText("Open command cancelled by user.");    	    	    	    	    	
    	}
	}	
	
	public File getFile(){
		if(check == 1)
			return this.file;
		else
			return null;}
}
