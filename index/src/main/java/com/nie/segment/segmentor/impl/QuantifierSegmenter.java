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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 
 * 中文数量词子分词器
 */
public class QuantifierSegmenter implements LeafSegmenter {
	// 中文数词
	private static String Chn_Num = "一二两三四五六七八九十零壹贰叁肆伍陆柒捌玖拾百千万亿拾佰仟萬億兆卅廿";// Cnum
	private static Set<Character> ChnNumberChars = new HashSet<Character>();

	static {
		char[] ca = Chn_Num.toCharArray();
		for (char nChar : ca) {
			ChnNumberChars.add(nChar);
		}
	}

	/*
	 * 词元的开始位置， 同时作为子分词器状态标识 当start > -1 时，标识当前的分词器正在处理字符
	 */
	private int nStart;

	/*
	 * 记录词元结束位置 end记录的是在词元中最后一个出现的合理的数词结束
	 */
	private int nEnd;

	// 待处理的量词hit队列
	private List<Hit> countHits;

	public QuantifierSegmenter() {
		nStart = -1;
		nEnd = -1;
		this.countHits = new LinkedList<Hit>();
	}

	/**
	 * 分词
	 */
	@Override
	public void analyze(AnalyzeContext context) {
		// 处理中文数词
		this.processCNumber(context);
		// 处理量词
		this.doMeasureWords(context);
	}

	/**
	 * 重置子分词器状态
	 */
	@Override
	public void reset() {
		nStart = -1;
		nEnd = -1;
		countHits.clear();
	}

	/**
	 * 处理数词
	 */
	private void processCNumber(AnalyzeContext context) {
		if (nStart == -1 && nEnd == -1) {// 初始状态
			if ((CharacterUtil.CHAR_CHINESE == context.getCurrentCharType()
					&& ChnNumberChars.contains(context.getCurrentChar()))) {
				// 记录数词的起始、结束位置
				// System.out.println("piztion: "+context.getCursor());
				nStart = context.getCursor();
				nEnd = context.getCursor();
			}
		} else {// 正在处理状态
			if ((CharacterUtil.CHAR_CHINESE == context.getCurrentCharType()
					&& ChnNumberChars.contains(context.getCurrentChar()))) {
				// 记录数词的结束位置
				nEnd = context.getCursor();
			} else {
				// 输出数词
				// System.out.println("piztion end: "+context.getCursor());

				this.outputNumLexeme(context);
				// 重置头尾指针
				nStart = -1;
				nEnd = -1;
			}
		}

		// 缓冲区已经用完，还有尚未输出的数词
		if (context.isBufferConsumed()) {
			if (nStart != -1 && nEnd != -1) {
				// 输出数词
				outputNumLexeme(context);
				// 重置头尾指针
				nStart = -1;
				nEnd = -1;
			}
		}
	}

	/**
	 * 处理量词
	 * 
	 * @param context
	 */
	private void doMeasureWords(AnalyzeContext context) {
		// 判断是否需要启动量词扫描
		if (!this.needCountScan(context)) {
			return;
		}

		if (CharacterUtil.CHAR_CHINESE == context.getCurrentCharType()
				|| CharacterUtil.CHAR_ENGLISH == context.getCurrentCharType()) {
			NewDictionary dict = context.getDictionary();
			// 优先处理countHits中的hit
			if (!this.countHits.isEmpty()) {
				// 处理词段队列
				Hit[] tmpArray = this.countHits.toArray(new Hit[this.countHits.size()]);
				for (Hit hit : tmpArray) {
					hit = dict.matchWithHit(context.getSegmentBuff(), context.getCursor(), hit);
					if (hit.isMatch()) {
						// 输出当前的词
						Lexeme newLexeme = new Lexeme(context.getBufferOffset(), hit.getBegin(),
								context.getCursor() - hit.getBegin() + 1, Lexeme.TYPE_COUNT);
						context.addLexeme(newLexeme);

						if (!hit.isPrefix()) {// 不是词前缀，hit不需要继续匹配，移除
							this.countHits.remove(hit);
						}

					} else if (hit.isUnmatch()) {
						// hit不是词，移除
						this.countHits.remove(hit);
					}
				}
			}

			// *********************************
			// 对当前指针位置的字符进行单字匹配
			Hit singleCharHit = dict.matchInQuantifierDict(context.getSegmentBuff(), context.getCursor(), 1);
			if (singleCharHit.isMatch()) {// 首字成量词词
				// 输出当前的词
				Lexeme newLexeme = new Lexeme(context.getBufferOffset(), context.getCursor(), 1, Lexeme.TYPE_COUNT);
				context.addLexeme(newLexeme);

				// 同时也是词前缀
				if (singleCharHit.isPrefix()) {
					// 前缀匹配则放入hit列表
					this.countHits.add(singleCharHit);
				}
			} else if (singleCharHit.isPrefix()) {// 首字为量词前缀
				// 前缀匹配则放入hit列表
				this.countHits.add(singleCharHit);
			}

		} else {
			// 输入的不是中文字符
			// 清空未成形的量词
			this.countHits.clear();
		}

		// 缓冲区数据已经读完，还有尚未输出的量词
		if (context.isBufferConsumed()) {
			// 清空未成形的量词
			this.countHits.clear();
		}
	}

	/**
	 * 判断是否需要扫描量词
	 * 
	 * @return
	 */
	private boolean needCountScan(AnalyzeContext context) {
		if ((nStart != -1 && nEnd != -1) || !countHits.isEmpty()) {
			// 正在处理中文数词,或者正在处理量词
			return true;
		} else {
			// 找到一个相邻的数词
			if (!context.getOrgLexemes().isEmpty()) {
				Lexeme l = context.getOrgLexemes().peekLast();
				if (Lexeme.TYPE_CNUM == l.getLexemeType() || Lexeme.TYPE_ARABIC == l.getLexemeType()) {
					if (l.getBegin() + l.getLength() == context.getCursor()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 添加数词词元到结果集
	 * 
	 * @param context
	 */
	private void outputNumLexeme(AnalyzeContext context) {
		if (nStart > -1 && nEnd > -1) {
			// 输出数词
			Lexeme newLexeme = new Lexeme(context.getBufferOffset(), nStart, nEnd - nStart + 1, Lexeme.TYPE_CNUM);
			// System.out.println("piztion res:
			// "+newLexeme.istargetType(Lexeme.TYPE_OTHER));

			context.addLexeme(newLexeme);

		}
	}

}
