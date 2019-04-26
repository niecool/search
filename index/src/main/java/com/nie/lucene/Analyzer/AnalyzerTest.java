package com.nie.lucene.Analyzer;

import com.nie.lucene.product.buildIndex.IndexWriterFactory;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.IndexWriter;

import java.io.StringReader;

/**
 * @author zhaochengye
 * @date 2019-04-26 09:26
 */
public class AnalyzerTest {

    public static void main(String[] args) {
        StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
        StringReader reader = new StringReader("据WWDC要推出iPhone6要出了？与iPhone5s土豪金相比怎样呢？@2014巴西世界杯 test中文");
        //获取TokenStream
        TokenStream ts = standardAnalyzer.tokenStream("fieldName",reader);
        try{
        ts.reset();
        CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
        //遍历分词数据
            while (ts.incrementToken()){
                System.out.print(term.toString()+"|");
            }
            ts.end();
            ts.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
