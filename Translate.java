import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Translate {
	
	String toTranslate;
	String langCode;
	String suppUrl;
	URL mainUrl;
	
	
	public Translate(String l){
		langCode = l;
		suppUrl = "translate?text=" + toTranslate + "&to=" + langCode;
		try{
			mainUrl = new URL("http://www.transltr.org/api/");
		}catch(Exception e){
			e.printStackTrace();
		}
	} 
	
	public void changeWord(String changeWordTo){
		toTranslate = changeWordTo;
		suppUrl = "translate?text=" + toTranslate + "&to=" + langCode;
	}
	
	public void changeLang(String changeLangTo){
		langCode = changeLangTo;
		suppUrl = "translate?text=" + toTranslate + "&to=" + langCode;
	}
	
	public String getJson() throws Exception{
		URL getLanguages = new URL(mainUrl, suppUrl);
		URLConnection read = getLanguages.openConnection();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(read.getInputStream()));
		String inputLine = in.readLine();
		in.close();
		
		return inputLine;
	}
	
	public String[] parseJson() throws Exception{
		String p = getJson();
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(p);
		
		String origLang = (String) obj.get("from");
		String toLang = (String) obj.get("to");
		String origText = (String) obj.get("text");
		String transText = (String) obj.get("translationText");
		
		String[] parsedArr = {origLang, toLang, origText, transText};
		
		return parsedArr;
	}
	
	public String translate(String word) throws Exception{
		changeWord(word);
		String gtlAns = parseJson()[3];
		String[] wordList = gtlAns.split("\\W+");
		return wordList[0];
	}
	
	public String getOrigLang() throws Exception{ 
		return parseJson()[0];
	}
	
	public String getTranslateLang() throws Exception{
		return parseJson()[1];
	}

}
