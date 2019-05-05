package com.nie.segment.segmentor.impl;

import com.nie.segment.model.Hit;
import com.nie.segment.model.Lexeme;
import com.nie.segment.dict.NewDictionary;
import com.nie.segment.model.AnalyzeContext;
import com.nie.segment.segmentor.LeafSegmenter;
import com.nie.utils.CharacterUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yipeng
 */
public class MixWordSegmenter implements LeafSegmenter {
	// 待处理的分词hit队列
	private Hit maxHit;
	int offset;
	Set<Integer> typeSet;
	Lexeme outLexeme;
	boolean isFirst = true; // 第一个必须是非符号

	public MixWordSegmenter() {
		offset = -1;
		typeSet = new HashSet<Integer>();
	}

	@Override
	public void analyze(AnalyzeContext context) {
		// 如果不是CHAR_USELESS,或者是letterConnector
		if (CharacterUtil.CHAR_USELESS != context.getCurrentCharType()) {
			if (isFirst) {
				if (CharacterUtil.CHAR_SYMBOL == context.getCurrentCharType()
						|| CharacterUtil.CHAR_BLANK == context.getCurrentCharType()) {
					return;
				} else {
					isFirst = false;
				}
			}
			NewDictionary dict = context.getDictionary();
			typeSet.add(context.getCurrentCharType());
			if (offset == -1) {
				maxHit = dict.matchInMainDict(context.getSegmentBuff(), context.getCursor(), 1);
				offset = context.getCursor();
			} else {
				if (maxHit.getMatchedDictSegment() != null) {
					maxHit = dict.matchWithHit(context.getSegmentBuff(), context.getCursor(), maxHit);
				}
				if (maxHit.isMatch() && typeSet.size() > 1) {
					// 输出当前的词
					outLexeme = new Lexeme(context.getBufferOffset(), maxHit.getBegin(),
							context.getCursor() - maxHit.getBegin() + 1, maxHit.getMatchedDictSegment().getWordType(),
							maxHit.getMatchedDictSegment().getSynonymWordsMap(),
							maxHit.getMatchedDictSegment().getExtendedWordsMap(),
							maxHit.getMatchedDictSegment().getSymbolNum());
				} else if (maxHit.isUnmatch() && (!context.isBufferConsumed())) {
					this.typeSet.clear();
					if (context.getCurrentCharType() == CharacterUtil.CHAR_SYMBOL) {
						isFirst = true;
						offset=-1;
						return;
					}
					typeSet.add(context.getCurrentCharType());
					maxHit = dict.matchInMainDict(context.getSegmentBuff(), context.getCursor(), 1);
					offset = context.getCursor();
					if (outLexeme != null) {
						// System.out.println("MixSeg:"+outLexeme.getBeginPosition()+"-"+outLexeme.getEndPosition());

						context.addLexeme(outLexeme);
						outLexeme = null;
					}
				}
			}
		} else {
			// 遇到CHAR_USELESS字符
			// 清空队列
			if (outLexeme != null) {
				// System.out.println("MixSeg:"+outLexeme.getBeginPosition()+"-"+outLexeme.getEndPosition());
				context.addLexeme(outLexeme);
				outLexeme = null;
			}
			this.reset();
		}

		// 判断缓冲区是否已经读完
		if (context.isBufferConsumed()) {
			if (outLexeme != null) {
				context.addLexeme(outLexeme);
				outLexeme = null;
			}
			// 清空队列
			this.reset();
		}
	}

	@Override
	public void reset() {
		// 清空队列
		this.typeSet.clear();
		this.offset = -1;
		isFirst = true;
	}

}
