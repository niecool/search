package com.nie.wordTag;

import java.util.HashMap;

public class Feature {
	private int label;
	public int getLabel() {
		return label;
	}
	public void setLabel(int label) {
		this.label = label;
	}
	private HashMap<String, Double> words=new HashMap<String, Double>();
	public void add(String word, double idf){
		this.words.put(word, idf);
	}
	public double getScore(String word){
		if(this.words.containsKey(word)){
			return this.words.get(word);
		}
		else{
			return 0.0;
		}
	}
	public boolean isFeature(String word){
		if(this.words.containsKey(word)){
			return true;
		}
		else{
			return false;
		}
	}

}
