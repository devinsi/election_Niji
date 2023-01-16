package election_Niji;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;

public class LogFile {
	
	// Le chemin du fichier journal
	String pathlog = "fileLog.txt";
	ArrayList<String> arraylist = new  ArrayList<String>();
	String codeParti;

	public LogFile(String code) {
		this.codeParti = code;
	}



	public void CreateLogFile()
	{
		// Création du fichier journal avec les erreurs
	    try 
	    {
	        File logFile = new File(pathlog);
	        // Create Writer to write a file.
	        Writer writer = new FileWriter(logFile, true);
	        	        
	        // Create a BufferedWriter 
	        BufferedWriter bufferedWriter = new BufferedWriter(writer); 
	        
	        
	            bufferedWriter.write("An error on the code format of one "
	            		+ "of the parties is dragged into the source file. "
	            		+ "Please check the entries. The entrie " + "'"+codeParti+"'" 
	            		+ " do not exist");	            
	        
	        bufferedWriter.newLine();
	        bufferedWriter.close (); // Ferme le flux
	    } catch (Exception e) 
	    {
	        e.printStackTrace();
	    }		
	}
	
	
}
