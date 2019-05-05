package com.nie.segment.arbitrator;

import com.nie.segment.model.Lexeme;
import com.nie.segment.config.SegmentConf;
import com.nie.segment.model.AnalyzeContext;
import com.nie.segment.model.LexemePath;
import com.nie.segment.utils.QuickSortSet;

import java.util.Stack;
import java.util.TreeSet;

/**
 * @author zhaochengye
 * @date 2019-05-05 17:06
 */
public class NieArbitrator {

    /**
     * 分词歧义处理
     * @param orgLexemes
     * @param useSmart
     */
    public void process(AnalyzeContext context) {
        QuickSortSet orgLexemes = context.getOrgLexemes();
        Lexeme orgLexeme = orgLexemes.pollFirst();

        LexemePath crossPath = new LexemePath();
        while (orgLexeme != null) {
            if (!crossPath.addCrossLexeme(orgLexeme)) {
                // 找到与crossPath不相交的下一个crossPath
                if (crossPath.size() == 1 || !context.getConf().isUseSmart()) {
                    // crossPath没有歧义 或者 不做歧义处理
                    // 直接输出当前crossPath
                    //	System.out.println(crossPath);
                    context.addLexemePath(crossPath);
                } else {
                    // 对当前的crossPath进行歧义处理
                    QuickSortSet.Cell headCell = crossPath.getHead();
                    LexemePath judgeResult = this.judge(context.getSegmentBuff(),headCell, crossPath.getPathLength(),context.getConf());
                    // 输出歧义处理结果judgeResult
                    context.addLexemePath(judgeResult);
                }

                // 把orgLexeme加入新的crossPath中
                crossPath = new LexemePath();
                crossPath.addCrossLexeme(orgLexeme);
                // System.out.println(crossPath);
            }
            orgLexeme = orgLexemes.pollFirst();
        }

        // 处理最后的path
        if (crossPath.size() == 1 || !context.getConf().isUseSmart()) {
            // crossPath没有歧义 或者 不做歧义处理
            // 直接输出当前crossPath
            context.addLexemePath(crossPath);
        } else {
            // 对当前的crossPath进行歧义处理
            QuickSortSet.Cell headCell = crossPath.getHead();
            LexemePath judgeResult = this.judge(context.getSegmentBuff(),headCell, crossPath.getPathLength(),context.getConf());
            // 输出歧义处理结果judgeResult
            context.addLexemePath(judgeResult);
        }
    }

    /**
     * 歧义识别
     * @param lexemeCell 歧义路径链表头
     * @param fullTextLength 歧义路径文本长度
     * @param option 候选结果路径
     * @return
     */
    private LexemePath judge(char[] segmentBuff, QuickSortSet.Cell lexemeCell, int fullTextLength, SegmentConf conf) {
        // 候选路径集合
        TreeSet<LexemePath> pathOptions = new TreeSet<LexemePath>();
        // 候选结果路径
        LexemePath option = new LexemePath();

        // 对crossPath进行一次遍历,同时返回本次遍历中有冲突的Lexeme栈
        Stack<QuickSortSet.Cell> lexemeStack = this.forwardPath(lexemeCell, option);

        // 当前词元链并非最理想的，加入候选路径集合
        pathOptions.add(option.copy());

        // 存在歧义词，处理
        QuickSortSet.Cell c = null;
        while (!lexemeStack.isEmpty()) {
            c = lexemeStack.pop();
            // 回滚词元链
            this.backPath(c.getLexeme(), option);
            // 从歧义词位置开始，递归，生成可选方案
            this.forwardPath(c, option);
            pathOptions.add(option.copy());
        }

        LexemePath first = pathOptions.first();
        //取出最优直接返回first
        if(conf.isOptimal()){
            return first;
        }else if(conf.isChineseSmart() && first.size() == 1 && first.peekFirst().isChinese()){
            return first;
        }else if(conf.isWordLimit() && first.size() == 1 && first.peekFirst().getLength() < 4){
            return first;
        }else{
            //表示没有分出词的一串字符串,其长度与first相同的为最优
            if(first.size() == 1 && first.peekFirst().getLexemeType() == Lexeme.TYPE_ENGLISH){
                for(LexemePath path : pathOptions){
                    if(path != first){
                        if(path.getPayloadLength() == first.getPayloadLength()){
                            path.setBackup(first);
                            return path;
                        }
                    }
                }
            }

            if(first.size() == 1 && first.peekFirst().isEnglish() && first.peekFirst().getLength() < 7){
                return first;
            }
            LexemePath backPath = null;
            for(LexemePath path : pathOptions){
                if(path != first){
                    //当最优路径没有连接符号的时候,取PayloadLength相同的第二路径
                    if(first.getPayloadLength() == path.getPayloadLength()+first.peekFirst().getSymbolNum()){
                        backPath = path;
                        break;
                    }
                }
            }
            first.setBackup(backPath);
        }
        // 返回集合中的最优方案
        return first;
    }

    /**
     * 向前遍历，添加词元，构造一个无歧义词元组合
     * @param LexemePath path
     * @return
     */
    private Stack<QuickSortSet.Cell> forwardPath(QuickSortSet.Cell lexemeCell, LexemePath option) {
        // 发生冲突的Lexeme栈
        Stack<QuickSortSet.Cell> conflictStack = new Stack<QuickSortSet.Cell>();
        QuickSortSet.Cell c = lexemeCell;
        // 迭代遍历Lexeme链表
        while (c != null && c.getLexeme() != null) {
            if (!option.addNotCrossLexeme(c.getLexeme())) {
                // 词元交叉，添加失败则加入lexemeStack栈
                conflictStack.push(c);
            }
            c = c.getNext();
        }
        return conflictStack;
    }

    /**
     * 回滚词元链，直到它能够接受指定的词元
     * @param lexeme
     * @param l
     */
    private void backPath(Lexeme l, LexemePath option) {
        while (option.checkCross(l)) {
            option.removeTail();
        }
    }


}
