package com.nie.wordTag;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

	static public List<String> readTXTToList(String filename) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		List<String> result=new ArrayList<String>();
		String line="";
		try {
			while((line=br.readLine())!=null){
				line=line.replace("\r\n", "");
				line=line.replace("\n", "");
				result.add(new String(line));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	static public HashSet<String> readTXTToSet(String filename) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		HashSet<String> result=new HashSet<String>();
		String line="";
		try {
			while((line=br.readLine())!=null){
				line=line.replace("\r\n", "");
				line=line.replace("\n", "");
				result.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			br.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	static public String extractSpecificationInfo(String text){
		Pattern pattern = Pattern.compile("href=\"(.+?)\"");
		Matcher matcher = pattern.matcher("<a href=\"index.html\">主页</a><a href=\"index.html\">主页</a>");
		while(matcher.find()) {   
		  System.out.println(matcher.group(1));
		 
		}  
		return "";
	}
	public static List<String> readTXTToList(InputStream filename) throws FileNotFoundException, UnsupportedEncodingException {
		BufferedReader br = new BufferedReader(new InputStreamReader(filename,"UTF-8"));
		List<String> result=new ArrayList<String>();
		String line="";
		try {
			while((line=br.readLine())!=null){
				line=line.replace("\r\n", "");
				line=line.replace("\n", "");
				result.add(line);
			}
		} catch (IOException e) {
		} 
		try {
			br.close();
		} catch (IOException e) {
		}
		return result;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tools.extractSpecificationInfo("");
	}

}
