package com.nie.segment.segmentor;


import com.nie.segment.model.AnalyzeContext;

/**
 * 
 * 叶子分词器接口,实现这个接口的类，进行具体的分词工作
 */
public interface LeafSegmenter {

  /**
   * 从分析器读取下一个可能分解的词元对象
   * @param context 分词算法上下文
   */
  void analyze(AnalyzeContext context);

  /**
   * 重置子分析器状态
   */
  void reset();

}
