package com.nie.segment;

import com.nie.segment.model.Lexeme;
import com.nie.segment.segmentor.CompositeSegmenter;
import org.apache.lucene.analysis.Tokenizer;

import java.io.IOException;

/**
 * @author zhaochengye
 * @date 2019-05-06 00:19
 */
public class MyTokenizer extends Tokenizer {

    @Override
    public boolean incrementToken() throws IOException {
//        //清除所有的词元属性
//        clearAttributes();
//        Lexeme nextLexeme = CompositeSegmenter.next();
//        if(nextLexeme != null){
//            //将Lexeme转成Attributes
//            //设置词元文本
//            termAtt.append(nextLexeme.getLexemeText());
//            //设置词元长度
//            termAtt.setLength(nextLexeme.getLength());
//            //设置词元位移
//            offsetAtt.setOffset(nextLexeme.getBeginPosition(), nextLexeme.getEndPosition());
//            //记录分词的最后位置
//            endPosition = nextLexeme.getEndPosition();
//            //记录词元分类
//            typeAtt.setType(nextLexeme.getLexemeTypeString());
//            //返会true告知还有下个词元
//            return true;
//        }
        //返会false告知词元输出完毕
        return false;
    }
}
