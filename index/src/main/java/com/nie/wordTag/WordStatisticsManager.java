package com.nie.wordTag;

import com.nie.segment.model.Lexeme;
import com.nie.segment.dict.NewDictionary;
import com.nie.segment.segmentor.NieSegmenter;
import com.nie.segment.utils.Big5Convertor;

import java.io.*;
import java.util.*;


public class WordStatisticsManager {
	static private NieSegmenter ws=null;
	private HashMap<String,Word> wordMap=new HashMap<String,Word>();
	private List<String> texts;
	public WordStatisticsManager() throws IOException, Exception {
		NewDictionary dict = new NewDictionary(null,true);
		ws=new NieSegmenter(dict);
	}
	static Comparator<Word> comparatorIDF = new Comparator<Word>(){
		   @Override
		public int compare(Word s1, Word s2) {  
			   return s1.getTfidf()<s2.getTfidf()?1:0;  
		   }  
		  };  
		  
	public void initIDF(String inputFile) throws FileNotFoundException {
		List<String> texts=Tools.readTXTToList(inputFile);
		for(String text:texts){
			String[] temp=text.split("#");
			Word word=new Word();
			word.setIdf(Double.parseDouble(temp[1]));
			word.setValue(temp[0]);
			wordMap.put(temp[0],word);
		}
	}
	public List<Lexeme> dataTransform(String keyword, List<Lexeme> words){
		List<Lexeme> result=new ArrayList<Lexeme>();
		boolean flag=true;
		for(Lexeme word:words){
			if(!keyword.equals(word.getLexemeText())){
				if(flag){
					word.setLexemeText(word.getLexemeText()+"_s");
					result.add(word);
				}
				else{
					word.setLexemeText(word.getLexemeText()+"_e");
					result.add(word);
				}
			}
			else{
				flag=false;
			}
		}
		return result;
	}
	public void prepareData(List<String> texts, String keyword) throws FileNotFoundException {
		List<String> tlist=new ArrayList<String>();
		this.texts=texts; 
		for(String text:this.texts){
			List<Lexeme> res=dataTransform(keyword,ws.segment(text));
			StringBuffer sb=new StringBuffer();
			for(Lexeme w1:res){
				sb.append(w1.getLexemeText()).append(" ");
				if((w1.getLexemeText().length()>3&&!w1.getLexemeTypeString().contains("BRAND")||w1.getLexemeTypeString().contains("CATEGORY"))){
				if(!w1.getLexemeTypeString().contains("ARABIC")&& 
						!w1.getLexemeTypeString().contains("ENGLISH")
						&&!w1.getLexemeTypeString().contains("GUIGE")
						&&!w1.getLexemeTypeString().contains("TYPE_CQUAN")
						&&!w1.getLexemeTypeString().contains("PROMOTION")){
					if(!wordMap.containsKey(w1.getLexemeText())){
						Word w=new Word();
						w.setValue(w1.getLexemeText());
						wordMap.put(w1.getLexemeText(), w);
					}
				}

			}
			}
			tlist.add(sb.toString());
		}
		this.texts=tlist;
	}
	public void prepareDataForWordNet(String inputFile) throws FileNotFoundException {
		texts=Tools.readTXTToList(inputFile);
		for(String text:texts){
			String[] temp=text.split("#");
			//segText(temp[0]);
			String[] words=temp[0].split("\\|");
			for(String w1:words){
				if(w1.length()>1){
				//int tag=lf.getWordTag(w1);
				//if(tag!=-1){
					if(!wordMap.containsKey(w1)){
						Word w=new Word();
						w.setValue(w1);
						wordMap.put(w1, w);
					}
				//}

			}
			}
		}
	}
	public void prepareDataDir(String inputDir) throws IOException {
		File dir = new File(inputDir);
	    File[] files = dir.listFiles();
		BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("d:/word.txt")));

	    for(File path:files){
	    	List<String> texts=Tools.readTXTToList(inputDir+path.getName());
			for(String text:texts){
				String[] words=text.split("\\|");
				for(String w1:words){
					if(w1.length()>1){
//						int tag=lf.getWordTag(w1);
//						if(tag==2){
//							w1="brand";
//						}
					if(!wordMap.containsKey(w1)){
					
						wr.write(w1+"\n");
						Word w=new Word();
						w.setValue(w1);
						wordMap.put(w1, w);
					}
				}}
			}
			texts.clear();
	    }
	}
	public void calculateIDFDir(String dirPath) throws FileNotFoundException {
		File dir = new File(dirPath);
	    File[] files = dir.listFiles();
	    int allsize=files.length;
	    int i=0;
	    System.out.println("word size:"+wordMap.size());
		for(Map.Entry<String, Word> map:wordMap.entrySet()){
			Word word=map.getValue();
			String wStr=word.getValue();
			int num=0;
			for(File file:files){
				List<String> catetexts=Tools.readTXTToList(dirPath+file.getName());
				for(String t:catetexts){
					if(t.contains(wStr)){
						num++;
						break;
					}
				}
				catetexts.clear();
			}

			word.setIdf(Math.log(((double)allsize/(double)num)));
			wordMap.put(word.getValue(),word);
			i++;
			if(i%1000==0){
				System.out.println(i+" words idf finished");
			}
		}

	}
	public void calculateTFIDFDir(String dirPath) throws IOException {
		File dir = new File(dirPath);
	    File[] files = dir.listFiles();
	    int allsize=files.length;
	    int i=0;
	    for(File file:files){
	    	System.out.println(i++);
			String out="d:/data/cate_tfidf/"+file.getName();
			BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out)));

	    	List<Word> wordList=new ArrayList<Word>();
	    	HashSet<String> set=new HashSet<String>();
	    	List<String> texts=Tools.readTXTToList(dirPath+file.getName());
	    	for(String t:texts){
	    		set.add(t);
	    	}
	    	for(String word:set){
	    		Word res=wordMap.get(word);
	    		double tf=calculateTF(word,texts);
	    		res.setTfidf(tf*res.getTfidf());
	    		wordList.add(res);
	    	}
			Collections.sort(wordList,comparatorIDF);
		    for(Word word:wordList){
		    	wr.write(word.getValue()+"#"+word.getTfidf()+"\n");
		    //	System.out.println(word.getValue()+"#"+word.getIdf());
		    }
		    wr.close();
		    texts.clear();
	    }


		

	}
	public void calculateIDF(){
		for(Map.Entry<String, Word> map:wordMap.entrySet()){
			Word word=map.getValue();
			String wStr=word.getValue();
			int num=0;
			Big5Convertor converter = Big5Convertor.getInstance();
			for(String t:this.texts){
				t = converter.big52gb(t);

				if(t.toLowerCase().contains(wStr)){
					num++;
				}
			}
			if(num==0){
				word.setIdf(0.0);
			}
			else{
				word.setIdf(Math.log(((double)this.texts.size()/(double)num)));
			}
//			if(word.getValue().equals("东方海洋")){
//				System.out.println("mao: "+word.getIdf()+"#"+num+"#"); 
//			}
			wordMap.put(word.getValue(),word);
		}

	}
	public void calculateIDF(int otherNum){
		for(Map.Entry<String, Word> map:wordMap.entrySet()){
			Word word=map.getValue();
			String wStr=word.getValue();
			int num=0;
			//Big5Convertor converter = Big5Convertor.getInstance();
			for(String t:this.texts){
			//	t = converter.big52gb(t);
				if(t.contains(wStr)){
					num++;
				}
			}
//			if("170g".equals(word.getValue())){
//				System.out.println(word.getValue());
//			}
			if(num==0){
				word.setIdf(0.0);
			}
			else{
				word.setIdf(Math.log(((double)(otherNum)/(double)num)));
			}
//			if(word.getValue().equals("东方海洋")){
//				System.out.println("mao: "+word.getIdf()+"#"+num+"#"); 
//			}
			wordMap.put(word.getValue(),word);
		}

	}
	public double calculateTF(String word, String[] words){
		//String[] tArr=words.split("\\|");
		int num=0;
		for(String t:words){
			if(word.equals(t)){
			num++;	
			}
		}
		return (double)num/words.length;
	}
	public double calculateTF(String word, List<String> textlist){
		//String[] tArr=words.split("\\|");
		int num=0;
		int allnum=0;
		for(String text:textlist){
			String[] tArr=text.split("\\|");
			for(String t:tArr){
				allnum++;
				if(word.equals(t)){
				num++;	
				}
			}
		}
		return (double)num/allnum;
	}
	public double calculateTFIDF(String word, String text){
		String[] tArr=text.split("\\|");
		int num=0;
		for(String t:tArr){
			if(word.equals(t)){
				num++;	
			}			
		}
		Word w=wordMap.get(word);
		if(w!=null){
			return ((double)num/tArr.length)*w.getIdf();
		}
		else{
			return 0;
		}
	}
	public void calculateWordForDocTFIDF(String outp) throws IOException {
		String alltext="";
		for(String text:texts){
			alltext +="|"+text;
		}
		//	System.out.println(text);
			List<Word> result=new ArrayList<Word>();
			HashSet<String> wordSet=new HashSet<String>();
			//String[] temp=text.split("#");
			String[] words=alltext.split("\\|");
			BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outp)));

			for(String w:words){
				wordSet.add(w);
			}
			for(String keyword:wordSet){
				//double tf=calculateTF(keyword,words);
				//System.out.println(keyword);
				Word res=wordMap.get(keyword);
				if(res!=null){
					//res.setTfidf(tf*res.getIdf());
					res.setTfidf(res.getIdf());
					result.add(res);
				}
			}
			Collections.sort(result,comparatorIDF);
		    for(Word word:result){
		    	wr.write(word.getValue()+"#"+word.getTfidf()+"\n");
		    //	System.out.println(word.getValue()+"#"+word.getIdf());
		    }
		    wr.close();
		
	}
	public void clear(){
		wordMap.clear();
		texts.clear();
	}

	public static void writeFile(String output, int i, int flag, HashMap<String,Word> wordMap) throws IOException {
		// TODO Auto-generated method stub
		List<Map.Entry<String,Word>> mappingList = new ArrayList<Map.Entry<String,Word>>(wordMap.entrySet());
		if(i==0){
		   Collections.sort(mappingList, new Comparator<Map.Entry<String,Word>>(){
		   @Override
		public int compare(Map.Entry<String,Word> mapping1, Map.Entry<String,Word> mapping2){
		    return mapping1.getValue().getIdf()>mapping2.getValue().getIdf()?1:0; 
		   } 
		  });  
		BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
		for(Map.Entry<String,Word> mapping:mappingList){
			//if("170g".equals(mapping.getKey())){
				//System.out.println("mapping.getKey(): "+mapping.getKey());
		//	}
			wr.write(flag+"#"+mapping.getKey()+"#"+mapping.getValue().getIdf()+"\n");
		}
		wr.close();
		}
		else if(i==1){
			 Collections.sort(mappingList, new Comparator<Map.Entry<String,Word>>(){
				   @Override
				public int compare(Map.Entry<String,Word> mapping1, Map.Entry<String,Word> mapping2){
				    return mapping1.getValue().getTfidf()>mapping2.getValue().getTfidf()?1:0; 
				   } 
				  });  
				BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
				for(Map.Entry<String,Word> mapping:mappingList){
					wr.write(mapping.getKey()+"#"+mapping.getValue().getTfidf()+"\n");
				}
		}
	
	
	}
	public void writeFile(String output, int i, int flag) throws IOException {
		// TODO Auto-generated method stub
		List<Map.Entry<String,Word>> mappingList = new ArrayList<Map.Entry<String,Word>>(wordMap.entrySet());
		if(i==0){
		   Collections.sort(mappingList, new Comparator<Map.Entry<String,Word>>(){
		   @Override
		public int compare(Map.Entry<String,Word> mapping1, Map.Entry<String,Word> mapping2){
		    return mapping1.getValue().getIdf()>mapping2.getValue().getIdf()?1:0; 
		   } 
		  });  
		BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output,true)));
		for(Map.Entry<String,Word> mapping:mappingList){
			//if("170g".equals(mapping.getKey())){
				//System.out.println("mapping.getKey(): "+mapping.getKey());
		//	}
			wr.write(flag+"#"+mapping.getKey()+"#"+mapping.getValue().getIdf()+"\n");
		}
		wr.close();
		}
		else if(i==1){
			 Collections.sort(mappingList, new Comparator<Map.Entry<String,Word>>(){
				   @Override
				public int compare(Map.Entry<String,Word> mapping1, Map.Entry<String,Word> mapping2){
				    return mapping1.getValue().getTfidf()>mapping2.getValue().getTfidf()?1:0; 
				   } 
				  });  
				BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
				for(Map.Entry<String,Word> mapping:mappingList){
					wr.write(mapping.getKey()+"#"+mapping.getValue().getTfidf()+"\n");
				}
		}
	
	
	}
	public HashMap<String, Word> getWordMap() {
		// TODO Auto-generated method stub
		return this.wordMap;
	}

}
