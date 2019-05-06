package com.nie.segment.wordTag;


import com.nie.segment.config.Configuration;
import com.nie.segment.dict.DictType;
import com.nie.segment.dict.NewDictionary;
import com.nie.segment.model.Lexeme;
import com.nie.segment.segmentor.NieSegmenter;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;

public class WordTag {
	private static WordTag tag=null;
	private Set<String> phoneBrand=new HashSet<String>();
	private Set<String> phoneAccessories=new HashSet<String>();
	public HashMap<String, List<Feature>> wordModel= new HashMap<String, List<Feature>>();
	public String note="";
	private void WordTag(){
		
	}
	public static WordTag getInstance(){
		if(tag==null){
			synchronized(WordTag.class) {
			//	long s=System.currentTimeMillis();
				if(tag==null){
					tag=new WordTag();
					try {
						tag.init();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				else{
					return tag;
				}
			//	System.out.println("tag cost: "+(System.currentTimeMillis()-s));
			}
		}
		return tag;
	}
	public Lexeme judgeWordType(Lexeme word, List<Lexeme> words){
		//DictType dt=new DictType();
		String keyword=word.getLexemeText();
		int delete_id=0;
		if(isPhoneBrand(keyword)){
			for(Lexeme l:words){
				if(isPhoneAccessories(l.getLexemeText())){
					word.setLexemeType(Lexeme.TYPE_XIUSHICI);
					return word;
				}
			}
		}
		List<Lexeme> transData=dataTransform(keyword,words);
		if(this.wordModel.containsKey(keyword)){
			//System.out.println(word.getLexemeText());
			List<Feature> features=this.wordModel.get(keyword);
			//System.out.println("features: "+features.size());
			double score[]={0.0,0.0};
			for(Lexeme w:transData){
				for(int i=0;i<features.size();i++){
					Feature temp=features.get(i);
					String faetureKey=w.getLexemeText();
					if(temp.isFeature(faetureKey)){
					//	note+="#"+w.getLexemeText()+":"+temp.getScore(w.getLexemeText())+":"+temp.getLabel();
						score[i] += temp.getScore(faetureKey);
					}
				}
			}
			int type=0;
			// System.out.println(score[0]+"#"+score[1]);
			if(score[0]>score[1]){
				type= DictType.getLexemeType(features.get(0).getLabel());
			}
			else if(score[0]==score[1]){
				type=Lexeme.TYPE_CATEGORY;
			}
			else{
				type=DictType.getLexemeType(features.get(1).getLabel());
			}
			//note+="##"+score[0]+"##"+score[1];
			
			if(word.getSynonymWordsMap()!=null){
			//System.out.println(word.getLexemeText());
				for(Entry<Integer, String[]> map:word.getSynonymWordsMap().entrySet()){
					int stype=map.getKey();
					if(DictType.getLexemeType(stype)!=type){
						delete_id=stype;
						//word.getSynonymWordsMap().remove(stype);
					}
				}
				if(delete_id!=0){
					word.getSynonymWordsMap().remove(delete_id);
				}
			}
			
			//if(word.getSynonymWordsMap().containsKey(key))
			word.setLexemeType(type);
			//System.out.println(note);
			return word;
		}
		else{
			return word;
		}
	}

	private boolean isPhoneAccessories(String l) {
		if(this.phoneAccessories.contains(l)){
			return true;
		}
		else{
			return false;
		}
	}
	private boolean isPhoneBrand(String word) {
		if(this.phoneBrand.contains(word)){
			return true;
		}
		else{
			return false;
		}
	}
	public void init() throws FileNotFoundException, UnsupportedEncodingException {
	 	//System.out.println(this.getClass().getClassLoader().getSystemResource(Configuration.DEFAULT_MODEL).getPath());
		InputStream input =this.getClass().getClassLoader()
		.getResourceAsStream(Configuration.DEFAULT_MODEL);//"com/yhd/segment/wordtag/tag_model.dic"
		InputStream brand =this.getClass().getClassLoader()
		.getResourceAsStream("dic/phone_brandV1.0.dic");
		InputStream access =this.getClass().getClassLoader()
		.getResourceAsStream("dic/phone_accessoriesV1.0.dic");
		List<String> texts=Tools.readTXTToList(input);
		for(String text:texts){
			List<Feature> features=new ArrayList<Feature>();
			String[] d=text.split("\t");
			if(d.length==3){
				Feature f1=getFeatures(d[1]);
				Feature f2=getFeatures(d[2]);
				features.add(f1);
				features.add(f2);
				wordModel.put(d[0],features);
			}
		}
		List<String> brands=Tools.readTXTToList(brand);
		for(String text:brands){
			phoneBrand.add(text);
		}
		List<String> peijian=Tools.readTXTToList(access);
		for(String text:peijian){
			phoneAccessories.add(text);
		}
		//System.out.println("model size:"+wordModel.size());
		//ws=new WordSegmentation("./models/");
	}	
	private Feature getFeatures(String text) {
		Feature result=new Feature();
		String[] d=text.split("#");
		//System.out.println("d[0]: "+d[0]);
	//	System.out.println(d[1]);
		result.setLabel(Integer.parseInt(d[0]));
		String[] words=d[1].split(",");
		for(String word:words){
			String[] node=word.split(":");
			result.add(node[0], Double.parseDouble(node[1]));
		}
		return result;
	}
	public List<Lexeme> dataTransform(String keyword, List<Lexeme> words){
		List<Lexeme> result=new ArrayList<Lexeme>();
		boolean flag=true;
		for(Lexeme word:words){
			Lexeme temp=new Lexeme(0,0,0,0);
			if(!keyword.equals(word.getLexemeText())){
				if(flag){
					temp.setLexemeText(word.getLexemeText()+"_s");
					result.add(temp);
				}
				else{
					temp.setLexemeText(word.getLexemeText()+"_e");
					result.add(temp);
				}
			}
			else{
				flag=false;
			}
		}
		return result;
	}
	public void trainModel() throws Exception {
		NewDictionary dict = new NewDictionary(null,true);
		NieSegmenter segment = new NieSegmenter(dict);
		String input="d:/data/j_train.txt";//t_test //jlc_sort.txt
		String output="d:/data/model_j.txt";
		String o1="d:/data/t_test_result1.txt";
		String o2="d:/data/t_test_result2.txt";
	//	List<String> lexes = segment.segmentStr(str);
		List<String> texts=Tools.readTXTToList(input);
		BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
		HashMap<String,TrainBean> map=new HashMap<String,TrainBean>();
		int n=0;
		for(String text:texts){
			
			String d[]=text.split("\t");
			if(d.length>1){
			TrainBean tb=null;
			String key=d[0].split(",")[0];
			//System.out.println("d[2]: "+d[2]);
				if(map.containsKey(key)){ 
					tb=map.get(key);
				}
				else{
					tb=new TrainBean();
				}
				for(String train:d[1].split("#")){
					tb.addCorpus(Integer.parseInt(d[2]),train);
				}
				if(!map.containsKey(key)){
					map.put(key, tb);
				}
			}
			else{
				//System.out.println(text);
			}
		}
		//System.out.println("map size: "+map..size());

		for(Entry<String,TrainBean> m:map.entrySet()){
			System.out.println(n++);
			HashSet<String> deleteWord=new HashSet<String>();
			TrainBean tb=m.getValue(); 
			int flag1=0,flag2=0;
			int num1=0;
			//System.out.println("m size: "+tb.getCorpus().size());
			HashMap<Integer, List<String>> c=tb.getCorpus();
			if(c.size()>1){
				List<HashMap<String,Word>> maplist=new ArrayList<HashMap<String,Word>>();
				int i=0;
				for(Entry<Integer, List<String>> entry:c.entrySet()){
					num1 +=entry.getValue().size();
				}
				for(Entry<Integer, List<String>> entry:c.entrySet()){
					WordStatisticsManager wm = new WordStatisticsManager();
					List<String> list=entry.getValue();
					//System.out.println("key: "+entry.getKey());
					wm.prepareData(list,m.getKey());
					if(i==0){
						flag1=entry.getKey();
					}
					else {
						flag2=entry.getKey();
					}
					//flag1=entry.getKey();
					wm.calculateIDF(num1);
					i++;
					maplist.add(wm.getWordMap());
				//	wm.writeFile(output, 0,entry.getKey());
					//wm.clear();
				}
				HashMap<String,Word> m1=maplist.get(0);
				HashMap<String,Word> m2=maplist.get(1);
				DecimalFormat format=new DecimalFormat("#.00");
				StringBuffer sb1=new StringBuffer();
				sb1.append(flag1).append("#");
				int s=0;
				for(Entry<String,Word> node:m1.entrySet()){
					if(s==0){
						sb1.append(node.getKey()).append(":").append(format.format(node.getValue().getIdf())); 
					}
					else{
						sb1.append(",").append(node.getKey()).append(":").append(format.format(node.getValue().getIdf()));
					}
					s++;
				}
				StringBuffer sb2=new StringBuffer();
				sb2.append(flag2).append("#");
				s=0;
				for(Entry<String,Word> node:m2.entrySet()){
					if(s==0){
						sb2.append(node.getKey()).append(":").append(format.format(node.getValue().getIdf())); 
					}
					else{
						sb2.append(",").append(node.getKey()).append(":").append(format.format(node.getValue().getIdf()));
					}
					s++;
				}
				wr.write(m.getKey()+"\t"+sb1.toString()+"\t"+sb2.toString()+"\n");
		}
		}
		wr.close();
	}
	public static void main(String[] args) throws IOException, Exception {
		// TODO Auto-generated method stub
		WordTag wt=new WordTag();
		wt.init();
		Lexeme l=new Lexeme(0,0,0,0);
		//l.setLexemeText("电源");
		//System.out.println(wt.isPhoneAccessories(l.getLexemeText()));
		wt.trainModel();
	}

}
