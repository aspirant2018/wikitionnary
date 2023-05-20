import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import javax.xml.stream.XMLStreamException;
import wiktionary_extractor.*;



public class Extractor {
	
	int tailleDeDictionnaire;
		
	public Extractor(){
		this.tailleDeDictionnaire=0;
	}
	
	public void extractePage() throws IOException, XMLStreamException {
		String path = "C:\\Users\\Raymond\\eclipse-workspace\\TP4\\src\\test.xml";
		InputStream stream = new FileInputStream(path);
		PageIterator iterator = new PageIterator(stream);
		String frenchLanguage = "{{langue|fr}}";
		while (iterator.hasNext()) {
			Page page = iterator.next();
			if (page.content.contains(frenchLanguage)){
				this.tailleDeDictionnaire+=1;
				String PageContenu = page.content;
				//la création et la serialisation d'un mot
				Mot mot = new Mot(PageContenu);
				serializeObjetMot(mot,page.title);
			}
		}
	}


	private void serializeObjetMot(Mot mot,String pageTitle) {
		
		String path = "C:\\Users\\Raymond\\eclipse-workspace\\TP4\\src\\motSerialisé\\"+pageTitle+".ser";
		
		try {
			
            FileOutputStream fileOut = new FileOutputStream(path); // Output file path
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            
            out.writeObject(mot); // Serialize the Mot object
            out.close();
            fileOut.close();

            System.out.println("Mot object has been serialized.");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
}
