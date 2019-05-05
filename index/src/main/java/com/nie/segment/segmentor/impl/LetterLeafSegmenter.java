/**
 *
 */
package com.nie.segment.segmentor.impl;


import com.nie.segment.model.Hit;
import com.nie.segment.model.Lexeme;
import com.nie.segment.dict.NewDictionary;
import com.nie.segment.model.AnalyzeContext;
import com.nie.segment.segmentor.LeafSegmenter;
import com.nie.utils.CharacterUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LetterLeafSegmenter implements LeafSegmenter {

    private int englishStart;
    private int englishEnd;

    private int arabicStart;
    private int arabicEnd;

    private int start;
    private int end;


    // 待处理的分词hit队列
    private Set<Integer> typeSet = new HashSet<Integer>();
    private List<Hit> penddingHits = new ArrayList<Hit>();
    private static char ENGLISTH_SYMBOL = '&';

    public LetterLeafSegmenter() {
        reset();
    }

    @Override
    public void analyze(AnalyzeContext context) {
        this.englishAnalyze(context);
        this.processEnglishLetter(context);
        this.processArabicLetter(context);
        //处理混合字母(这个要放最后处理，可以通过QuickSortSet排除重复)
        // this.processMixLetter(context);
    }

    @Override
    public void reset() {
        this.start = -1;
        this.end = -1;
        this.englishStart = -1;
        this.englishEnd = -1;
        this.arabicStart = -1;
        this.arabicEnd = -1;
        this.penddingHits.clear();
        this.typeSet.clear();
    }

    public void englishAnalyze(AnalyzeContext context) {
        if (CharacterUtil.CHAR_ENGLISH == context.getCurrentCharType() ||
                CharacterUtil.CHAR_ARABIC == context.getCurrentCharType() ||
                CharacterUtil.CHAR_SYMBOL == context.getCurrentCharType() ||
                CharacterUtil.CHAR_BLANK == context.getCurrentCharType()) {
            NewDictionary dict = context.getDictionary();

            if (!this.penddingHits.isEmpty()) {
                //处理词段队列
                Hit[] tmpArray = this.penddingHits.toArray(new Hit[this.penddingHits.size()]);
                for (Hit hit : tmpArray) {
                    hit = dict.matchWithHit(context.getSegmentBuff(), context.getCursor(), hit);
                    if (hit.isMatch()) {
                        // 输出当前的词
                        Lexeme newLexeme = new Lexeme(context.getBufferOffset(), hit.getBegin(),
                                hit.getEnd() - hit.getBegin() + 1, hit.getMatchedDictSegment().getWordType(),
                                hit.getMatchedDictSegment().getSynonymWordsMap(), hit.getMatchedDictSegment().getExtendedWordsMap(), hit.getMatchedDictSegment().getSymbolNum());
                        //   newLexeme.setEnglish(true);
                        //  if(typeSet.contains(hit.getMatchedDictSegment().getCharType())){


                        //}
                        if (!newLexeme.istargetType(Lexeme.TYPE_BRAND)) {
                            context.addLexeme(newLexeme);
                            //   System.out.println(" if add"+newLexeme.getBegin()+"-"+newLexeme.getLength()+"#"+newLexeme.getLexemeTypeString());
                            typeSet.add(hit.getMatchedDictSegment().getWordType());
                        } else {
                            if (!typeSet.contains(newLexeme.getLexemeType())) {
                                //      System.out.println("brand add"+newLexeme.getBegin()+"-"+newLexeme.getLength()+"#"+newLexeme.getLexemeTypeString());
                                context.addLexeme(newLexeme);
                                typeSet.add(hit.getMatchedDictSegment().getWordType());
                            }
                        }

                        if (!hit.isPrefix()) {// 不是词前缀，hit不需要继续匹配，移除
                            this.penddingHits.remove(hit);
                        }
                    } else if (hit.isUnmatch()) {
                        // hit不是词，移除
                        this.penddingHits.remove(hit);
                    }
                }
            }

            // *********************************
            // 再对当前指针位置的字符进行单字匹配
            Hit singleCharHit = dict.matchInMainDict(context.getSegmentBuff(), context.getCursor(), 1);
            if (singleCharHit.isMatch()) {// 首字成词
                // 输出当前的词
                Lexeme newLexeme = new Lexeme(context.getBufferOffset(), context.getCursor(), 1, singleCharHit.getMatchedDictSegment().getWordType(), singleCharHit.getMatchedDictSegment().getSynonymWordsMap(), singleCharHit.getMatchedDictSegment().getExtendedWordsMap(), singleCharHit.getMatchedDictSegment().getSymbolNum());
                context.addLexeme(newLexeme);

                // 同时也是词前缀
                if (singleCharHit.isPrefix()) {
                    // 前缀匹配则放入hit列表
                    this.penddingHits.add(singleCharHit);
                }
            } else if (singleCharHit.isPrefix()) {// 首字为词前缀
                // 前缀匹配则放入hit列表
                this.penddingHits.add(singleCharHit);
            }
        } else {
            // 遇到CHAR_USELESS字符
            // 清空队列
            this.penddingHits.clear();
        }

        // 判断缓冲区是否已经读完
        if (context.isBufferConsumed()) {
            // 清空队列
            this.penddingHits.clear();
        }
    }


    /**
     * 处理纯英文字母输出
     *
     * @param context
     * @return
     */
    private boolean processEnglishLetter(AnalyzeContext context) {
        boolean needLock = false;
        NewDictionary dict = context.getDictionary();
        if (this.englishStart == -1) {// 当前的分词器尚未开始处理英文字符
            if (CharacterUtil.CHAR_ENGLISH == context.getCurrentCharType()) {

                // 记录起始指针的位置,标明分词器进入处理状态
                this.englishStart = context.getCursor();
                this.englishEnd = this.englishStart;
            }
        } else {// 当前的分词器正在处理英文字符
            if (CharacterUtil.CHAR_ENGLISH == context.getCurrentCharType()) {
                // 记录当前指针位置为结束位置
                this.englishEnd = context.getCursor();
            }
//	      else if(CharacterUtil.CHAR_SYMBOL == context.getCurrentCharType()){
//	    	  //this.englishEnd = context.getCursor();
//	      }
            else {
                // 遇到非English字符,输出词元
                Hit singleCharHit = dict.matchInMainDict(context.getSegmentBuff(), this.englishStart, this.englishEnd - this.englishStart + 1);
                if (singleCharHit.isMatch()) {// 首字成词
                    // 输出当前的词
                    Lexeme newLexeme = new Lexeme(context.getBufferOffset(), singleCharHit.getBegin(),
                            singleCharHit.getEnd() - singleCharHit.getBegin() + 1, singleCharHit.getMatchedDictSegment().getWordType(),
                            singleCharHit.getMatchedDictSegment().getSynonymWordsMap(), singleCharHit.getMatchedDictSegment().getExtendedWordsMap(), singleCharHit.getMatchedDictSegment().getSymbolNum());
                    //    Lexeme newLexeme = new Lexeme(context.getBufferOffset(), this.englishStart, this.englishEnd - this.englishStart + 1,singleCharHit.getMatchedDictSegment().getCharType());
                    context.addLexeme(newLexeme);
                } else {
                    Lexeme newLexeme = new Lexeme(context.getBufferOffset(), this.englishStart,
                            this.englishEnd - this.englishStart + 1, Lexeme.TYPE_ENGLISH);
                    context.addLexeme(newLexeme);
                }
                this.englishStart = -1;
                this.englishEnd = -1;
            }
        }

        // 判断缓冲区是否已经读完
        if (context.isBufferConsumed()) {
            if (this.englishStart != -1 && this.englishEnd != -1) {
                Hit singleCharHit = dict.matchInMainDict(context.getSegmentBuff(), this.englishStart, this.englishEnd - this.englishStart + 1);
                if (singleCharHit.isMatch()) {// 首字成词
                    // 输出当前的词
                    Lexeme newLexeme = new Lexeme(context.getBufferOffset(), singleCharHit.getBegin(),
                            singleCharHit.getEnd() - singleCharHit.getBegin() + 1, singleCharHit.getMatchedDictSegment().getWordType(),
                            singleCharHit.getMatchedDictSegment().getSynonymWordsMap(), singleCharHit.getMatchedDictSegment().getExtendedWordsMap(), singleCharHit.getMatchedDictSegment().getSymbolNum());
                    context.addLexeme(newLexeme);
                } else {
                    Lexeme newLexeme = new Lexeme(context.getBufferOffset(), this.englishStart,
                            this.englishEnd - this.englishStart + 1, Lexeme.TYPE_ENGLISH);
                    context.addLexeme(newLexeme);
                }
                // 缓冲以读完，输出词元
//	        Lexeme newLexeme = new Lexeme(context.getBufferOffset(), this.englishStart, this.englishEnd
//	            - this.englishStart + 1, Lexeme.TYPE_ENGLISH);
                //   System.out.println("S");
                //context.addLexeme(newLexeme);
                this.englishStart = -1;
                this.englishEnd = -1;
            }
        }

        // 判断是否锁定缓冲区
        if (this.englishStart == -1 && this.englishEnd == -1) {
            // 对缓冲区解锁
            needLock = false;
        } else {
            needLock = true;
        }
        return needLock;
    }


    /**
     * 处理阿拉伯数字输出
     *
     * @param context
     * @return
     */
    private boolean processArabicLetter(AnalyzeContext context) {
        boolean needLock = false;

        if (this.arabicStart == -1) {// 当前的分词器尚未开始处理数字字符
            if (CharacterUtil.CHAR_ARABIC == context.getCurrentCharType()) {
                // 记录起始指针的位置,标明分词器进入处理状态
                this.arabicStart = context.getCursor();
                this.arabicEnd = this.arabicStart;
            }
        } else {// 当前的分词器正在处理数字字符
            if (CharacterUtil.CHAR_ARABIC == context.getCurrentCharType()) {
                // 记录当前指针位置为结束位置
                this.arabicEnd = context.getCursor();
            } else if (
                //	CharacterUtil.CHAR_USELESS == context.getCurrentCharType()&&
                    CharacterUtil.isNumConnector(context.getCurrentChar())) {
                //System.out.println("else if is num "+context.getCurrentCharType());
                // 不输出数字，但不标记结束
            } else {
                // //遇到非Arabic字符,输出词元
                Lexeme newLexeme = new Lexeme(context.getBufferOffset(), this.arabicStart, this.arabicEnd
                        - this.arabicStart + 1, Lexeme.TYPE_ARABIC);
                context.addLexeme(newLexeme);
                this.arabicStart = -1;
                this.arabicEnd = -1;
            }
        }

        // 判断缓冲区是否已经读完
        if (context.isBufferConsumed()) {
            if (this.arabicStart != -1 && this.arabicEnd != -1) {
                // 生成已切分的词元
                Lexeme newLexeme = new Lexeme(context.getBufferOffset(), this.arabicStart, this.arabicEnd
                        - this.arabicStart + 1, Lexeme.TYPE_ARABIC);
                context.addLexeme(newLexeme);
                this.arabicStart = -1;
                this.arabicEnd = -1;
            }
        }

        // 判断是否锁定缓冲区
        if (this.arabicStart == -1 && this.arabicEnd == -1) {
            // 对缓冲区解锁
            needLock = false;
        } else {
            needLock = true;
        }
        return needLock;
    }
}
