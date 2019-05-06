package com.nie.segment.segmentor;

import com.nie.segment.model.Lexeme;
import com.nie.segment.dict.NewDictionary;
import com.nie.segment.config.SegmentConf;
import com.nie.segment.arbitrator.NieArbitrator;
import com.nie.segment.utils.Big5Convertor;
import com.nie.segment.wordTag.WordTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaochengye
 * @date 2019-04-27 00:03
 */

public class NieSegmenter {

    private NewDictionary dictionary;

    private NieArbitrator arbitrator = new NieArbitrator();

    private Big5Convertor converter = Big5Convertor.getInstance();

    public NieSegmenter(NewDictionary dictionary) {
        this.dictionary = dictionary;
    }

    private String regularizeInput(String input) {
        if (input == null || input.trim().equals("")) {
            return null;
        }
        input = input.trim().toLowerCase();
        input = converter.big52gb(input);
        return input;
    }

    public List<Lexeme> segment(String input) {
        List<Lexeme> lexemes = new ArrayList<Lexeme>();
        List<Lexeme> lexemes2 = new ArrayList<Lexeme>();
        input = regularizeInput(input);
        if (input == null) {
            return lexemes;
        }
        SegmentConf conf = new SegmentConf();
        conf.setChineseSmart(true);
        conf.setUseSmart(true);
        CompositeSegmenter segmenter = new CompositeSegmenter(dictionary, arbitrator, input, conf);
        Lexeme lex = segmenter.next();
        while (lex != null) {
            lexemes.add(lex);
            lex = segmenter.next();
        }
        for (Lexeme lex1 : lexemes) {
            // if(!lex1.getLexemeText().equals(" ")){
            if (lex1.isNum() && lex1.getLength() == 1) {
                // System.out.println("is num");
                String temp = NewDictionary.allNumTransform(lex1.getLexemeText());
                if (!temp.equals("")) {
                    Map<Integer, String[]> synonymWordsMap = new HashMap<Integer, String[]>();
                    synonymWordsMap.put(Lexeme.TYPE_ARABIC, temp.split(","));
                    Lexeme num = new Lexeme(lex1.getOffset(), lex1.getBegin(), lex1.getLength(), Lexeme.TYPE_ARABIC,
                            synonymWordsMap, null, 1, lex1.isChinese());
                    num.setLexemeText(lex1.getLexemeText());
                    lexemes2.add(num);
                } else {
                    lexemes2.add(lex1);

                }
            } else {
                lexemes2.add(lex1);
            }
            // }
        }

        // lexemes.addAll(nums);
        return JudgeWordType(lexemes2);
    }

    private List<Lexeme> JudgeWordType(List<Lexeme> lexemes) {
        List<Lexeme> results = new ArrayList<Lexeme>();
        WordTag wt = WordTag.getInstance();
        for (Lexeme word : lexemes) {
            word = wt.judgeWordType(word, lexemes);
            if (!word.getLexemeText().equals(" ")) {
                results.add(word);
            }
        }
        return results;
    }

    public List<String> segmentStr(String input) {
        return lexemesToStrs(segment(input));
    }
    private List<String> lexemesToStrs(List<Lexeme> lexemes) {
        List<String> list = new ArrayList<String>();
        for (Lexeme lexeme : lexemes) {
            list.add(lexeme.getLexemeText());
        }
        return list;
    }





    /**
     * 最细粒度切分,只要词典中的词都可以分出来。
     *
     * @param input
     * @return
     */
    public List<Lexeme> complexSegment(String input) {
        List<Lexeme> lexemes = new ArrayList<Lexeme>();
        input = regularizeInput(input);
        if (input == null) {
            return lexemes;
        }
        SegmentConf conf = new SegmentConf();
        CompositeSegmenter segmenter = new CompositeSegmenter(dictionary, arbitrator, input, conf);
        Lexeme lex = segmenter.next();
        while (lex != null) {
            lexemes.add(lex);
            lex = segmenter.next();
        }
        return JudgeWordType(lexemes);
    }

    /**
     * 最细粒度切分,只要词典中的词都可以分出来。
     *
     * @param input
     * @return
     */
    public List<String> complexSegmentStr(String input) {
        return lexemesToStrs(complexSegment(input));
    }

    /**
     * 最优分词
     *
     * @param input
     * @return
     */
    public List<Lexeme> optimalSegment(String input) {
        List<Lexeme> lexemes = new ArrayList<Lexeme>();
        input = regularizeInput(input);
        if (input == null) {
            return lexemes;
        }
        SegmentConf conf = new SegmentConf();
        conf.setUseSmart(true);
        conf.setOptimal(true);
        CompositeSegmenter segmenter = new CompositeSegmenter(dictionary, arbitrator, input, conf);
        Lexeme lex = segmenter.next();
        while (lex != null) {
            lexemes.add(lex);
            lex = segmenter.next();
        }
        return lexemes;
    }

    public List<String> optimalSegmentStr(String input) {
        return lexemesToStrs(optimalSegment(input));
    }


    /**
     * 双路径分词,切出最好的两条路径,有进行逻辑判断
     *
     * @param input
     * @return
     */
    public List<List<Lexeme>> twoOptimalSegment(String input) {
        List<List<Lexeme>> resultList = new ArrayList<List<Lexeme>>();
        input = regularizeInput(input);
        if (input == null) {
            return resultList;
        }
        SegmentConf conf = new SegmentConf();
        conf.setUseSmart(true);
        conf.setChineseSmart(true);
        conf.setWordLimit(true);
        if (input.length() > 15) {
            conf.setOptimal(true);
        }
        // 如果全部是数字
        if (input.matches("[0-9]+")) {
            List<Lexeme> list = new ArrayList<Lexeme>();
            Lexeme lex = matchWord(input);
            if (lex != null) {
                list.add(lex);
            } else {
                Lexeme temp = new Lexeme(0, 0, 0, 0);
                temp.setLexemeText(input);
                temp.setLexemeType(Lexeme.TYPE_ARABIC);
                list.add(temp);
            }

            resultList.add(list);
            return resultList;
        }
        CompositeSegmenter segmenter = new CompositeSegmenter(dictionary, arbitrator, input, conf);
        List<List<Lexeme>> twoPathList = segmenter.getSegmentTwoPath();
        return twoPathList;
    }

    /**
     * 双路径分词,切出最好的两条路径,有进行逻辑判断
     *
     * @param input
     * @return
     */
    public List<List<String>> twoOptimalSegmentStr(String input) {
        return lex2Str(twoOptimalSegment(input));
    }

    private List<List<String>> lex2Str(List<List<Lexeme>> segmentTwoPath) {
        List<List<String>> resultList = new ArrayList<List<String>>();
        for (List<Lexeme> path : segmentTwoPath) {
            List<String> pathList = new ArrayList<String>();
            for (Lexeme lex : path) {
                if (!lex.getLexemeText().equals(" ")) {
                    pathList.add(lex.getLexemeText());
                }
            }
            resultList.add(pathList);
        }
        return resultList;
    }

    /**
     * 根据词典匹配输入对应Lexeme,如果没有返回null
     */
    public Lexeme matchWord(String input) {
        if(input == null || input.trim().equals("")){
            return null;
        }
        return dictionary.matchWord(input);
    }

}
