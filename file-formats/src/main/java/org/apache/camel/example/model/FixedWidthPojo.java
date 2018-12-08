package org.apache.camel.example.model;

public class FixedWidthPojo {

	private String firstWord;

	private String secondWord;
    
	public String getFirstWord() {
		return firstWord;
	}
	public void setFirstWord(String firstWord) {
		this.firstWord = firstWord;
	}
	public String getSecondWord() {
		return secondWord;
	}
	public void setSecondWord(String secondWord) {
		this.secondWord = secondWord;
	}
	@Override
	public String toString() {
		return "FixedWidthPojo [firstWord=" + firstWord + ", secondWord=" + secondWord + "]";
	}

}
