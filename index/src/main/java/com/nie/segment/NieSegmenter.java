//package com.nie.segment;
//
//import com.nie.segment.utils.Big5Convertor;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author zhaochengye
// * @date 2019-04-27 00:03
// */
//public class NieSegmenter {
//
//    private NewDictionary dictionary;
//
//    private Big5Convertor converter = Big5Convertor.getInstance();
//
//    public NieSegmenter(NewDictionary dictionary) {
//        this.dictionary = dictionary;
//    }
//
//    private String regularizeInput(String input) {
//        if (input == null || input.trim().equals("")) {
//            return null;
//        }
//        input = input.trim().toLowerCase();
//        input = converter.big52gb(input);
//        return input;
//    }
//
//    public List<Lexeme> segment(String input) {
//        List<Lexeme> lexemes = new ArrayList<Lexeme>();
//        List<Lexeme> lexemes2 = new ArrayList<Lexeme>();
//        input = regularizeInput(input);
//        if (input == null) {
//            return lexemes;
//        }
//        SegmentConf conf = new SegmentConf();
//        conf.setChineseSmart(true);
//        conf.setUseSmart(true);
//        CompositeSegmenter segmenter = new CompositeSegmenter(dictionary, arbitrator, input, conf);
//        Lexeme lex = segmenter.next();
//        while (lex != null) {
//            lexemes.add(lex);
//            lex = segmenter.next();
//        }
//        for (Lexeme lex1 : lexemes) {
//            // if(!lex1.getLexemeText().equals(" ")){
//            if (lex1.isNum() && lex1.getLength() == 1) {
//                // System.out.println("is num");
//                String temp = NewDictionary.allNumTransform(lex1.getLexemeText());
//                if (!temp.equals("")) {
//                    Map<Integer, String[]> synonymWordsMap = new HashMap<Integer, String[]>();
//                    synonymWordsMap.put(Lexeme.TYPE_ARABIC, temp.split(","));
//                    Lexeme num = new Lexeme(lex1.getOffset(), lex1.getBegin(), lex1.getLength(), Lexeme.TYPE_ARABIC,
//                            synonymWordsMap, null, 1, lex1.isChinese());
//                    num.setLexemeText(lex1.getLexemeText());
//                    lexemes2.add(num);
//                } else {
//                    lexemes2.add(lex1);
//
//                }
//            } else {
//                lexemes2.add(lex1);
//            }
//            // }
//        }
//
//        // lexemes.addAll(nums);
//        return JudgeWordType(lexemes2);
//    }
//
//
//
//}
