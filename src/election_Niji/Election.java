package election_Niji;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class Election {

	//git add test

  public static void main(String args[]) throws InputFileException 
  { 
	manipulateFile();  	  
  }

  /**
   * The program guideline
   * @throws InputFileException, an creating exception when the input file
   * is not find. 
   */
  private static void manipulateFile() throws InputFileException 
  {
		ArrayList<String> arraylist = new  ArrayList<String>();
		// Le chemin du fichier d'entr�e
		String pathInputFile = "file.txt"; 
		// Le chemin du fichier de sortie
		String pathOutputFile = "fileOut.txt";
		
		Path p1 = Paths.get("file.txt");

	    try(FileReader fr = new FileReader(pathInputFile);
		         BufferedReader br = new BufferedReader(fr))
	    {	    	
	    	if (!Files.exists(p1)) throw new InputFileException
	    	("Please check that the file \"File\" is present and in the root of the program.");
	    	String line;
	    	StringBuffer sb = new StringBuffer();
	 	    	
	    	while((line = br.readLine()) != null) 
	    	{
	    		sb.append(line);
				String[] sentenceOnArray = line.split(",");	
			    String[] words = placeOnArrayWhithoutSpace(line);
			    translateCode(words);
			    int nbVotes = calculateSum(words);
			    TreeMap<Integer, String> sentenceIncreasing = ascendingSortingVoices(words);     		  
			    arraylist = calculateAndAddVotesPercentage(sentenceOnArray, sentenceIncreasing, nbVotes);
			    
			    //Ajout des r�sultat de l'ArrayList dans un ficher
			    try 
			    {
			        File outFile = new File(pathOutputFile);
			        // Create Writer to write a file.
			        Writer writer = new FileWriter(outFile, true);
			        	        
			        // Create a BufferedWriter 
			        BufferedWriter bufferedWriter = new BufferedWriter(writer); 
			        
			        for (String s : arraylist) 
			        {
			            bufferedWriter.write(s);	            
			        }
			        bufferedWriter.newLine();
			        bufferedWriter.close (); // Ferme le flux
			    } catch (Exception e) 
			    {
			        e.printStackTrace();
			    }
		      }
		    	fr.close();   	    	
	    }
	    catch(Exception e)
	    {
	    	throw new InputFileException("Please check that the file \"File\""
	    			+ " is present and in the root of the project.");
	    	//System.out.println(e.getCause().getMessage());
	    } 	       	    
  }
  /**
   * Calculate the percentage of votes of each political party and add them 
   * in a table with the data already present. 
   * @param String[]sentence
   * 	type of an input value
   * @param TreeMap<Integer, String> sentenceIncreasing
   * 	type of an input value
   * @param int nbVotes
   * 	type of an input value
   * @return ArrayList<String>
   * 	type of the returned value
   */
  private static ArrayList<String> calculateAndAddVotesPercentage
  (String[]sentence, TreeMap<Integer, String> sentenceIncreasing, int nbVotes) 
  {
	  //On ajoute le nom de la circonscription au tableau arrayList
	  ArrayList<String> arraylist = new ArrayList<String>();
	  arraylist.add(0, sentence[0] + " :");

		  for(int i=0; i<sentenceIncreasing.size(); i++)
	      {
	    	  // R�cup�re la cl�
	    	  int key =  (int) sentenceIncreasing.keySet().toArray()[i];
	    	  
	    	  // R�cup�re la valeur
	    	  String value = sentenceIncreasing.get(key);
	    	  
	    	  // Calcul le pourcentage arrondi au centi�me
	    	  double percentage = Double.valueOf(key)*100/nbVotes;
	    	  DecimalFormat df = new DecimalFormat("#.##");
	    	  
	    	  arraylist.add(" " + value + ": ");
        	  arraylist.add(String.valueOf(key) + " votes, ");
        	  arraylist.add(String.valueOf(df.format(percentage)) + "% ;");        	  
	      }
	  return arraylist;
  }
  /**
   * Orders the data in descending order of votes. 
   * @param String[] words
   * @return TreeMap<Integer, String>
   */
  private static TreeMap<Integer, String> ascendingSortingVoices(String[] words) 
  {
	  TreeMap<Integer, String> votesPartiesPairs = new TreeMap<>(Collections.reverseOrder());
	  // on recup�re seulement les nombres
	  for (int i=1; i<words.length; i=i+2) 
	  {
		  votesPartiesPairs.put(Integer.parseInt(words[i]), (words[i+1])); 
	  }
	  return votesPartiesPairs;
  }
  /**
   * Removes the spaces from the line and inserts the results in a String array.
   * @param String nextLine
   * @return String[] words
   */
  private static String[] placeOnArrayWhithoutSpace(String nextLine) 
  {
 	  //On supprime les espaces
	  String strWhithoutSpace = nextLine.replaceAll("\\s", "");
	  //System.out.println(strWhithoutSpace);
  
	  //on s�pare chaque mots de la chaine lorsqu'une virgule est pr�sente
	  String[] words = strWhithoutSpace.split(",");
	  
	  return words;	
  }
  /**
   * Translates the acronyms of political party names into their full titles.
   * @param String[] wordsCode
   */ 
  private static void translateCode(String[] wordsCode) 
  {
	//On r�cup�re seulement les mots 
	  for (int i=2; i<wordsCode.length; i=i+2) 
	  {	  
		  switch (wordsCode[i]) {
	      case "C":
	    	  wordsCode[i] = "Parti conservateur";
	          break;
	      case "L":
	    	  wordsCode[i] = "Parti travailliste";
	          break;
	      case "LD":
	    	  wordsCode[i] = "Lib�raux-d�mocrates";
		       break;
	      case "G":
	    	  wordsCode[i] = "Parti Vert";
	          break;
	      case "Ind.":
	    	  wordsCode[i] = "Ind�pendant";
	          break;
	      case "UKIP":
	    	  wordsCode[i] = "UKIP";
	          break;
	      case "SNP ":
	    	  wordsCode[i] = "SNP";
	          break;
	      default:
	    	  LogFile file = new LogFile(wordsCode[i]);
	    	  file.CreateLogFile();
	    	  System.out.println("error");
	    	  wordsCode[i] = "ERROR";
	    	  System.out.println("Please check the results in the \"fileLog\" file at the root of the project.");  	 
		  }  
	  }   	  
  }
  /**
   * Calculates the total sum of votes. 
   * @param String[] words
   * @return int sum
   */
  private static int calculateSum(String[] words) 
  {
	  int sum = 0;
	  // on recup�re seulement les nombres
	  for (int i=1; i<words.length; i=i+2) 
	  {
		  //On calcule la somme des voix		  
		  try
		  {
	            sum += Integer.parseInt(words[i]);
          }
          catch (NumberFormatException ex)
		  {
	            ex.printStackTrace();
	      }	  
	  }
	  return sum;
  } 
}
