package model;

public class Keyword {
	private int length;
	private String keyWords;

	public Keyword(int length, String keyWords) {
		super();
		this.length = length;
		this.keyWords = keyWords;
	}

	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	
	
}
