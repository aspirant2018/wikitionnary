import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import javax.xml.stream.XMLStreamException;
import org.apache.commons.cli.*;


public class wikitionnaire {
	
	
	
	


	public static void main(String[] args) throws IOException, XMLStreamException, ParseException {
		
		/*
		 * creating the options that we will use later to interact with the user
		 */
		
		Options options = new Options();
		options.addOption("w","what",true,"the type of information that you want");
		options.addOption("f","for",true,"the information about this specific word ");
		
		CommandLineParser parser= new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		
		
		
		/*
		 * if serialized files are not in the folder : create them!!
		 */
		
		String folderPath = "C:\\Users\\Raymond\\eclipse-workspace\\TP4\\src\\motSerialisé";
		
			if(!folderContainsSerializedFiles(folderPath)) {
				
				System.out.println("creating files...........");
				Extractor Extractor  = new Extractor();
				Extractor.extractePage();
				System.out.println("done!!!!");
				
			}else {
				System.out.println("files already exist");
			}
			/* serialized files are now in the folder */
			
			
			
			/* 
			 * Now !! i will get the word from the arguments:
			 */
			
			if((cmd.hasOption("w"))&&(cmd.hasOption("f"))){
				
				String word = cmd.getOptionValue("f");
				String whatInfo = cmd.getOptionValue("w");
				System.out.println("the word that you are looking for is "+word);
				System.out.println("the information you are looking for is "+whatInfo);
				
				// 
				String fileName = word+".ser";

				if (isFileInFolder(folderPath,fileName)) {
					System.out.println("the file "+fileName +" in the folder.....");
					System.out.println("Starting the desirialization process of the object......");
					String filePath = folderPath + "\\" + fileName;
					Object deserializedWord = deserializeFile(filePath);
					
					if (whatInfo.equals("pos")){
						((Mot) deserializedWord).getCategorie();
					}
					if (whatInfo.equals("pron")) {
						((Mot) deserializedWord).getPronociation();
					}
					if (whatInfo.equals("syn")) {
						((Mot) deserializedWord).getSynonymes();
					}
					if (whatInfo.equals("ant")) {
						((Mot) deserializedWord).setAntonymes();
						}
					if (whatInfo.equals("tran")) {
						((Mot) deserializedWord).setTranslation();
						}
					
				}else {
					System.out.println(fileName+" not in the folder !!! ");}
					
			}else {
				System.out.println("please put valide arguments !!!");
			}
							
			
	} // end of the method

	
	private static Object deserializeFile(String filePath) {
	    try {
	        FileInputStream fileIn = new FileInputStream(filePath);
	        ObjectInputStream in = new ObjectInputStream(fileIn);

	        Object object = in.readObject(); // Deserialize the object
	        in.close();
	        fileIn.close();

	        return object;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return null;
	}



		private static boolean isFileInFolder(String folderPath, String fileName) {
		// TODO Auto-generated method stub
			File folder = new File(folderPath);
		    
		    if (folder.exists() && folder.isDirectory()) {
		        File[] files = folder.listFiles();
		        
		        if (files != null) {
		            for (File file : files) {
		                if (file.isFile() && file.getName().equals(fileName)) {
		                    return true;
		                }
		            }
		        }
		    }
		    
		    return false;
	}


		//verifié si le dossier motSerialisé contient des fichier .ser:
	
		private static boolean folderContainsSerializedFiles(String folderPath) {
			    File folder = new File(folderPath);
			    
			    if (folder.exists() && folder.isDirectory()) {
			        File[] files = folder.listFiles();
			        
			        if (files != null) {
			            for (File file : files) {
			                if (file.isFile() && file.getName().endsWith(".ser")) {
			                    return true;
			                }
			            }
			        }
			    }
			    return false;
			}
} //end of the class
				
				
				
				
				
				
				
				
					
					
					
				
				
			

