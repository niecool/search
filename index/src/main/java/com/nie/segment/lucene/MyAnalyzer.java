package com.nie.segment.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

/**
 * @author zhaochengye
 * @date 2019-05-06 00:14
 */
public class MyAnalyzer extends Analyzer {
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer _IKTokenizer = new MyTokenizer();
        return new TokenStreamComponents(_IKTokenizer);
    }
}
