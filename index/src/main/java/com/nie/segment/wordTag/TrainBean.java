package com.nie.segment.wordTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrainBean {

	private HashMap<Integer, List<String>> corpus=new HashMap<Integer, List<String>>();
	private String keyword;
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public HashMap<Integer, List<String>> getCorpus() {
		return corpus;
	}
	public void addCorpus(int flag, String text){
		if(corpus.containsKey(flag)){
			List<String> list=corpus.get(flag);
			list.add(text);
		}
		else{
			List<String> list=new ArrayList<String>();
			list.add(text);
			corpus.put(flag, list);
		}
		
	}
	public List<String> getCorpus(int flag){
		return corpus.get(flag);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
