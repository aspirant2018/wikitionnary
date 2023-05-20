import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Mot implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	String contenu;
	HashSet<String> categorie;
	HashSet<String> pronciation;
	HashSet<String> synonymes;
	HashSet<String> antonymes;
	Map<String,String> translations;
	
	
	public static String removeLineBreaks(String text) {
        return text.replaceAll("\\n"," ");
    }
	
	
	public Mot(String pageContenu) {
		
		//les attributs de mot
		
		this.contenu = pageContenu;
		this.setCategorie();
		this.setPronociation();
		this.setSynonymes();
		this.setAntonymes();
		this.setTranslation();
	}
	
	//Méthodes pour extraire les attributs de mot
	public void setPronociation() {
		
		/*
		 * La méthode setPronciation a le but d'extraire
		 *  la transcription phonetique d'un mot
		 */
		
		HashSet<String> pronciation = new HashSet<>();
		
		//expression réguliere pour trouver les prononciations
		String regex="\\{\\{pron\\|(.*?)\\|fr\\}\\}";
		Pattern PatternPro = Pattern.compile(regex);	
		Matcher matcher = PatternPro.matcher(this.contenu);
		
		//Rechereche des correspondances et stockage dans le dictionnaire
		while (matcher.find()) {
			pronciation.add(matcher.group(1));
		}
		
		this.pronciation = pronciation;
	}
	
	public void setCategorie() {
		
		/*
		 * La méthode setCategorie a le but d'extraire
		 *  la categorie d'un mot par ex: Adjectif , Nom , Verbe ..
		 */
		
		HashSet<String> Categorie = new HashSet<>();
		
		//expression réguliere pour trouver tous les catégories
		String regex= "\\{\\{S\\|(.*)\\|fr\\}\\}";
		Pattern catPattern = Pattern.compile(regex);	
		Matcher m = catPattern.matcher(this.contenu);
		while (m.find()) {
			Categorie.add(m.group(1));
		}
		
		//expression réguliere pour une catégorie et la stocké dans une Set
		regex= "\\{\\S\\|(.*)\\|fr\\|num=\\d\\}\\}";
		catPattern = Pattern.compile(regex);
		m = catPattern.matcher(this.contenu);
		while (m.find()) {
			Categorie.add(m.group(1));
		}
		
		this.categorie = Categorie;
	}
	
	public void setSynonymes() {

		/*
		 * La méthode setSynonymes a le but d'extraire
		 *  tous les synonymes d'un mot.
		 */
		
		String allSynonymes = "";
		String text = this.contenu;
		text = removeLineBreaks(text);
		
		//expression réguliere pour trouver tous les synonymes
		String regex ="====\\s\\{\\{S\\|synonymes\\}\\}\\s====(.*?)\\{";
		Pattern allSynPattern = Pattern.compile(regex);
		Matcher m = allSynPattern.matcher(text);
		while (m.find()) {
			allSynonymes = m.group(1);
			}
		
		//expression réguliere pour un synonyme et le stocké dans une Set
		HashSet<String> synonymes = new HashSet<>();
		
		regex ="\\[\\[(.*?)\\]\\]";
		Pattern synPattern = Pattern.compile(regex);
		m = synPattern.matcher(allSynonymes);
		while (m.find()) {
			synonymes.add(m.group(1));
			}
		
		this.synonymes=synonymes;
		}
	
	public void setAntonymes() {
		/*
		 * La méthode setAntonymes a le but d'extraire
		 *  tous les antonymes d'un mot.
		 */
		String allAntonymesText = "";
		String text = this.contenu;
		text = removeLineBreaks(text);
		
		//expression réguliere pour trouver tous les antonymes
		String regex ="====\\s\\{\\{S\\|antonymes\\}\\}\\s====(.*)===\\s\\{\\{S\\|traductions\\}\\}";
		Pattern allAntoPattern = Pattern.compile(regex);
		Matcher m = allAntoPattern.matcher(text);
		while (m.find()) {
			allAntonymesText = m.group(1);
			}
		
		//expression réguliere pour un synonyme et le stocké dans une Set
		HashSet<String> antonymes = new HashSet<>();
		regex ="\\[\\[(.*?)\\]\\]";
		Pattern antoPattern = Pattern.compile(regex);
		m = antoPattern.matcher(allAntonymesText);
		while (m.find()) {
			antonymes.add(m.group(1));			
			}
		
		this.antonymes=antonymes;
		System.out.println(this.antonymes);
		
	}
	
	public void setTranslation() {
		
		/*
		 * La méthode setTranslation a le but d'extraire
		 *  tous les langues associé avec la traduction d'un mot.
		 */
		
		Map<String,String> translations = new HashMap<>();
		
		//expression réguliere pour trouver les traductions
		String regex = "\\{\\{T\\|([^}]+)}}\\s*:\\s*\\{\\{trad-\\|[^}|]+\\|([^}]+)}}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(this.contenu);
		
		//Rechereche des correspondances et stockage dans le dictionnaire
		while (matcher.find()) {
			String language= matcher.group(1);
			String translation = matcher.group(2);
			
			translations.put(language, translation);
			
		}
		
		System.out.println(translations);
		this.translations = translations;
	}
	
	//Méthodes pour afficher les attributs de mot
	public void getPronociation() {
		System.out.println(this.pronciation);
	}
	public void getCategorie() {
		System.out.println(this.categorie);
	}
	public void getSynonymes() {
		System.out.println(this.synonymes);
	}
	public void getAntonymes() {
		System.out.println(this.antonymes);
	}
	public void getTranslation() {
		System.out.println(this.translations);
	}
	
	


	
	
}
