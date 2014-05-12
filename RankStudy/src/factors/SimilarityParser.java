package factors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import network.KeyWordSimNetwork;

import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;

public class SimilarityParser {

	private List<String> keywords1;
	private List<String> keywords2;
	KeyWordComputer kwc;

	
	public SimilarityParser(int count){
		kwc = new KeyWordComputer(count);
	}
	
	private void loadKeyWords(String str1, String str2){
		keywords1 = new ArrayList<String>();
		keywords2 = new ArrayList<String>();
		
		Collection<Keyword> result1 = kwc.computeArticleTfidf(str1);
		Collection<Keyword> result2 = kwc.computeArticleTfidf(str2);
		
		Iterator<Keyword> it1 = result1.iterator();
		Iterator<Keyword> it2 = result2.iterator();
		while(it1.hasNext()){
			keywords1.add(((Keyword)it1.next()).getName());
		}
		while(it2.hasNext()){
			keywords2.add(((Keyword)it2.next()).getName());
		}
	}
	
	public double calcSimilarity(String str1, String str2){
		loadKeyWords(str1, str2);
		
		KeyWordSimNetwork simNetwork = new KeyWordSimNetwork();
		String selectedKeys1 = listToString(keywords1);
		String selectedKeys2 = listToString(keywords2);
		
		if((selectedKeys1 != null) && (selectedKeys2 != null) && (!selectedKeys1.isEmpty()) && (!selectedKeys2.isEmpty()))
			return simNetwork.getSimilarity(listToString(keywords1), listToString(keywords2)) / 100;
		else{
			System.out.println("Cannot extract keywords");
			return 0.0f;
		}
	}
	
	private String listToString(List<String> keywords){
		String original = keywords.toString();
		
		original = original.replace(" ", "");
		original = original.replace("[", "");
		original = original.replace("]", "");
		return original;
	}
}
