package com.nie.wordTag;

public class Word {
	private String value;
	private int index;
	private int flag;
	private double idf=0.0;
	private boolean selected=false;
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	//1 品类 2 品牌 7 促销词 8 人群 9包装 12材质 13产地 14成份 16修饰词  26颜色
	private double IG=0.0;
	private double tfidf=0.0;
	public double getTfidf() {
		return tfidf;
	}
	public void setTfidf(double tfidf) {
		this.tfidf = tfidf;
	}
	public double getIG() {
		return IG;
	}
	public void setIG(double iG) {
		IG = iG;
	}
	public double getIdf() {
		return idf;
	}
	public void setIdf(double idf) {
		this.idf = idf;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	

}
