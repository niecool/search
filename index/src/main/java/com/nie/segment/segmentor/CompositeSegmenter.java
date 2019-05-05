package com.nie.segment.segmentor;

import com.nie.segment.model.Lexeme;
import com.nie.segment.dict.NewDictionary;
import com.nie.segment.config.SegmentConf;
import com.nie.segment.arbitrator.NieArbitrator;
import com.nie.segment.model.AnalyzeContext;
import com.nie.segment.segmentor.impl.CHNLeafSegmenter;
import com.nie.segment.segmentor.impl.LetterLeafSegmenter;
import com.nie.segment.segmentor.impl.MixWordSegmenter;
import com.nie.segment.segmentor.impl.QuantifierSegmenter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 组合的分词器
 *
 * @author zhaochengye
 * @date 2019-04-28 13:42
 */
public class CompositeSegmenter {
    // 字符串
    private String input;
    // 分词器上下文
    private AnalyzeContext context;
    // 分词处理器列表
    private List<LeafSegmenter> segmenters;
    // 分词歧义裁决器
    private NieArbitrator arbitrator;

    /**
     * @param input 非智能分词：细粒度输出所有可能的切分结果 智能分词： 合并数词和量词，对分词结果进行歧义判断
     */
    public CompositeSegmenter(NewDictionary dict, NieArbitrator arbitrator, String input, SegmentConf conf) {
        this.input = input;
        // 初始化分词上下文
        this.context = new AnalyzeContext(conf, dict);
        // 加载子分词器
        this.segmenters = this.loadSegmenters(conf);
        // 加载歧义裁决器
        this.arbitrator = arbitrator;
    }

    /**
     * 初始化词典，加载子分词器实现
     *
     * @return List<ISegmenter>
     */
    private List<LeafSegmenter> loadSegmenters(SegmentConf conf) {
        List<LeafSegmenter> segmenters = new ArrayList<LeafSegmenter>(4);
        segmenters.add(new QuantifierSegmenter());
        segmenters.add(new CHNLeafSegmenter());
        segmenters.add(new LetterLeafSegmenter());
        segmenters.add(new MixWordSegmenter());
//        segmenters.add(new NumericSegmenter());
//        segmenters.add(new EnglishSegmenter());
        return segmenters;
    }

    /**
     * 分词，获取下一个词元
     *
     * @return Lexeme 词元对象
     */
    public synchronized Lexeme next() {
        Lexeme lex = null;
        while ((lex = context.getNextLexeme()) == null) {
            if (context.isFinished()) {
                return lex;
            }
            int available = context.fillBuffer(this.input);
            if (available > 0) {
                // 初始化指针
                context.initCursor();
                do {
                    // 遍历子分词器
                    for (LeafSegmenter segmenter : segmenters) {
                        segmenter.analyze(context);
                    }
                    // 向前移动指针
                } while (context.moveCursor());
                // 重置子分词器，为下轮循环进行初始化
                for (LeafSegmenter segmenter : segmenters) {
                    segmenter.reset();
                }
            }
            if (context.getOrgLexemes().size() >= 1) {
                // 对分词进行歧义处理
                this.arbitrator.process(context);
            }
            // 将分词结果输出到结果集，并处理未切分的单个CJK字符
            context.outputToResult();
        }
        return lex;
    }

    public synchronized List<List<Lexeme>> getSegmentTwoPath() {
        int available = context.fillBuffer(this.input);
        if (available > 0) {
            // 初始化指针
            context.initCursor();
            do {
                // 遍历子分词器
                for (LeafSegmenter segmenter : segmenters) {
                    segmenter.analyze(context);
                }
                // 向前移动指针
            } while (context.moveCursor());
            // 重置子分词器，为下轮循环进行初始化
            for (LeafSegmenter segmenter : segmenters) {
                segmenter.reset();
            }
        }
        // 对分词进行歧义处理
        this.arbitrator.process(context);
        return context.getTwoPathResult();
    }


}
