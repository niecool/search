package com.nie.segment.model;

import com.nie.segment.dict.NewDictionary;
import com.nie.segment.config.SegmentConf;
import com.nie.segment.utils.QuickSortSet;
import com.nie.utils.CharacterUtil;

import java.util.*;

/**
 * @author zhaochengye
 * @date 2019-05-05 16:58
 */
public class AnalyzeContext {
    // 字符窜读取缓冲
    private char[] segmentBuff;
    // 字符类型数组
    private int[] charTypes;

    // 记录Reader内已分析的字串总长度
    // 在分多段分析词元时，该变量累计当前的segmentBuff相对于reader起始位置的位移
    private int buffOffset;
    // 当前缓冲区位置指针
    private int cursor;
    // 最近一次读入的,可处理的字串长度
    private int available;
    // 原始分词结果集合，未经歧义处理
    private QuickSortSet candy_set;
    // LexemePath位置索引表
    private Map<Integer, LexemePath> pathMap;
    // 最终分词结果集
    private LinkedList<Lexeme> final_lexemes;

    // 是否使用消歧
    private SegmentConf conf;

    // 表示一次
    private boolean finished = false;

    private NewDictionary dictionary;

    public AnalyzeContext(SegmentConf conf, NewDictionary dictionary) {
        this.conf = conf;
        this.dictionary = dictionary;
        this.candy_set = new QuickSortSet();
        this.pathMap = new HashMap<Integer, LexemePath>();
        this.final_lexemes = new LinkedList<Lexeme>();
    }

    public int getCursor() {
        return this.cursor;
    }

    public char[] getSegmentBuff() {
        return this.segmentBuff;
    }

    public char getCurrentChar() {
        return this.segmentBuff[this.cursor];
    }

    public int getCurrentCharType() {
        return this.charTypes[this.cursor];
    }

    public int getBufferOffset() {
        return this.buffOffset;
    }

    /**
     * 每次只会分一个String的字符串,所以不会有offset的情况。
     */
    public int fillBuffer(String input) {
        int readCount = input.length();
        this.segmentBuff = new char[readCount];
        input.getChars(0, readCount, segmentBuff, 0);
        this.charTypes = new int[readCount];
        for (int i = 0; i < readCount; i++) {
            this.segmentBuff[i] = CharacterUtil.regularize(this.segmentBuff[i]);
            this.charTypes[i] = CharacterUtil.identifyCharType(this.segmentBuff[i]);
        }
        this.available = readCount;
        // 重置当前指针
        this.cursor = 0;
        return readCount;
    }

    /**
     * 初始化buff指针，处理第一个字符
     */
    public void initCursor() {
        this.cursor = 0;
        this.segmentBuff[this.cursor] = CharacterUtil.regularize(this.segmentBuff[this.cursor]);
        this.charTypes[this.cursor] = CharacterUtil.identifyCharType(this.segmentBuff[this.cursor]);
    }

    /**
     * 指针+1 成功返回 true； 指针已经到了buff尾部，不能前进，返回false 并处理当前字符
     */
    public boolean moveCursor() {
        if (this.cursor < this.available - 1) {
            this.cursor++;
            this.segmentBuff[this.cursor] = CharacterUtil.regularize(this.segmentBuff[this.cursor]);
            this.charTypes[this.cursor] = CharacterUtil.identifyCharType(this.segmentBuff[this.cursor]);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前segmentBuff是否已经用完 当前执针cursor移至segmentBuff末端this.available - 1
     *
     * @return
     */
    public boolean isBufferConsumed() {
        return this.cursor == this.available - 1;
    }

    /**
     * 向分词结果集添加词元
     *
     * @param lexeme
     */
    public void addLexeme(Lexeme lexeme) {
        this.candy_set.addLexeme(lexeme);
    }

    /**
     * 添加分词结果路径 路径起始位置 ---> 路径 映射表
     *
     * @param path
     */
    public void addLexemePath(LexemePath path) {
        if (path != null) {
            this.pathMap.put(path.getPathBegin(), path);
        }
    }

    /**
     * 返回原始分词结果
     *
     * @return
     */
    public QuickSortSet getOrgLexemes() {
        return this.candy_set;
    }

    /**
     * 推送分词结果到结果集合 1.从buff头部遍历到this.cursor已处理位置 2.将map中存在的分词结果推入results
     * 3.将map中不存在的CJK字符以单字方式推入results
     */
    public void outputToResult() {
        int index = 0;
        for (; index <= this.cursor;) {
            // 跳过非CJK字符
            if (CharacterUtil.CHAR_USELESS == this.charTypes[index]) {
                index++;
                continue;
            }
            // 从pathMap找出对应index位置的LexemePath
            LexemePath path = this.pathMap.get(index);
            if (path != null) {
                // 输出LexemePath中的lexeme到results集合
                Lexeme lex = path.pollFirst();
                while (lex != null) {
                    this.final_lexemes.add(lex);
                    // 将index移至lexeme后
                    index = lex.getBegin() + lex.getLength();
                    lex = path.pollFirst();
                    if (lex != null) {
                        // 输出path内部，词元间遗漏的单字
                        for (; index < lex.getBegin(); index++) {
                            this.outputSingleCJK(index,this.final_lexemes);
                        }
                    }
                }

                if (path.getBackup() != null) {
                    Lexeme bl = path.getBackup().pollFirst();
                    while (bl != null) {
                        this.final_lexemes.add(bl);
                        // 将index移至lexeme后
                        index = bl.getBegin() + bl.getLength();
                        bl = path.getBackup().pollFirst();
                        if (bl != null) {
                            // 输出path内部，词元间遗漏的单字
                            for (; index < bl.getBegin(); index++) {
                                this.outputSingleCJK(index,this.final_lexemes);
                            }
                        }
                    }
                }
            } else {// pathMap中找不到index对应的LexemePath
                // 单字输出
                this.outputSingleCJK(index,this.final_lexemes);
                index++;
            }
        }
        // 清空当前的Map
        this.pathMap.clear();
        this.finished = true;
    }

    public List<List<Lexeme>> getTwoPathResult() {
        LinkedList<Lexeme> firstList = new LinkedList<Lexeme>();
        LinkedList<Lexeme> secondList = new LinkedList<Lexeme>();
        boolean hasBackUp = false;// 表示是否需要第二个路径。
        int index = 0;
        for (; index <= this.cursor;) {
            // 跳过非CJK字符
            if (CharacterUtil.CHAR_USELESS == this.charTypes[index]) {
                index++;
                continue;
            }
            // 从pathMap找出对应index位置的LexemePath
            LexemePath path = this.pathMap.get(index);
            if (path != null) {
                // 输出LexemePath中的lexeme到results集合
                Lexeme l = path.pollFirst();
                while (l != null) {
                    firstList.add(l);
                    // System.out.println(l.getLexemeTypeString());
                    if ((path.getBackup() == null || hasBackUp)) {
                        secondList.add(l);
                    }
                    // 将index移至lexeme后
                    index = l.getBegin() + l.getLength();
                    l = path.pollFirst();
                    if (l != null) {
                        // 输出path内部，词元间遗漏的单字
                        for (; index < l.getBegin(); index++) {
                            this.outputSingleCJK(index, firstList);
                            if (path.getBackup() == null || hasBackUp) {
                                this.outputSingleCJK(index, secondList);
                            }
                        }
                    }
                }

                if (path.getBackup() != null && (!hasBackUp)) {
                    Lexeme bl = path.getBackup().pollFirst();
                    while (bl != null) {
                        secondList.add(bl);
                        // 将index移至lexeme后
                        index = bl.getBegin() + bl.getLength();
                        bl = path.getBackup().pollFirst();
                        if (bl != null) {
                            // 输出path内部，词元间遗漏的单字
                            for (; index < bl.getBegin(); index++) {
                                outputSingleCJK(index, secondList);
                            }
                        }
                    }
                    hasBackUp = true;
                }
            } else {// pathMap中找不到index对应的LexemePath
                // 单字输出
                outputSingleCJK(index, firstList);
                outputSingleCJK(index, secondList);
                index++;
            }
        }

        List<List<Lexeme>> twoPathList = new ArrayList<List<Lexeme>>();
        List<Lexeme> firstResult = new ArrayList<Lexeme>();
        Lexeme lex = getNextLexeme(firstList);
        while (lex != null) {
            firstResult.add(lex);
            lex = getNextLexeme(firstList);
        }
        // System.out.println("firstResult: "+firstResult.toString());
        twoPathList.add(firstResult);
        if (hasBackUp && firstResult.size() < 5) {
            List<Lexeme> secondResult = new ArrayList<Lexeme>();
            Lexeme secLex = getNextLexeme(secondList);
            while (secLex != null) {
                secondResult.add(secLex);
                secLex = getNextLexeme(secondList);
            }
            // System.out.println("secondResult: "+secondResult.toString());

            twoPathList.add(secondResult);
        }

        return twoPathList;
    }

    /**
     * 返回lexeme
     *
     * 同时处理合并
     *
     * @return
     */
    private Lexeme getNextLexeme(LinkedList<Lexeme> resultList) {
        // 从结果集取出，并移除第一个Lexme
        Lexeme result = resultList.pollFirst();
        while (result != null) {
            // 数量词合并
            this.compoundResult(result, resultList);
            // 不是停止词, 生成lexeme的词元文本,输出
            result.setLexemeText(String.valueOf(segmentBuff, result.getBegin(), result.getLength()));
            break;
        }
        return result;
    }

    /**
     * 对CJK字符进行单字输出
     *
     * @param index
     */
    private void outputSingleCJK(int index,List<Lexeme> list) {
        if (CharacterUtil.CHAR_CHINESE == this.charTypes[index]) {
            Lexeme singleCharLexeme = new Lexeme(this.buffOffset, index, 1, Lexeme.TYPE_CNCHAR);
            list.add(singleCharLexeme);
        } else if (CharacterUtil.CHAR_OTHER_CJK == this.charTypes[index]) {
            Lexeme singleCharLexeme = new Lexeme(this.buffOffset, index, 1, Lexeme.TYPE_OTHER_CJK);
            list.add(singleCharLexeme);
        } else if (CharacterUtil.CHAR_ENGLISH == this.charTypes[index]) {
            Lexeme singleCharLexeme = new Lexeme(this.buffOffset, index, 1, Lexeme.TYPE_ENGLISH);
            list.add(singleCharLexeme);
        } else if (CharacterUtil.CHAR_ARABIC == this.charTypes[index]) {
            Lexeme singleCharLexeme = new Lexeme(this.buffOffset, index, 1, Lexeme.TYPE_ARABIC);
            list.add(singleCharLexeme);
        }
    }

    /**
     * 返回lexeme
     *
     * 同时处理合并
     *
     * @return
     */
    public Lexeme getNextLexeme() {
        // 从结果集取出，并移除第一个Lexme
        Lexeme lex = this.final_lexemes.pollFirst();
        while (lex != null) {
            // 数量词合并
            this.compound(lex);
            // 不是停止词, 生成lexeme的词元文本,输出
            lex.setLexemeText(String.valueOf(segmentBuff, lex.getBegin(), lex.getLength()));
            break;
        }
        return lex;
    }

    private void compound(Lexeme result) {
        compoundResult(result, this.final_lexemes);
    }

    /**
     * 组合词元
     */
    private void compoundResult(Lexeme result, LinkedList<Lexeme> resultList) {
        if (!conf.isUseSmart()) {
            return;
        }
        // 数量词合并处理
        if (!resultList.isEmpty()) {

            if (Lexeme.TYPE_ARABIC == result.getLexemeType()) {
                Lexeme nextLexeme = resultList.peekFirst();
                boolean appendOk = false;
                if (Lexeme.TYPE_COUNT == nextLexeme.getLexemeType()) {
                    // 合并英文数词+中文量词
                    appendOk = result.append(nextLexeme, Lexeme.TYPE_CQUAN);
                }

                // else if(Lexeme.TYPE_ENGLISH == nextLexeme.getLexemeType() &&
                // nextLexeme.getLength() == 1){
                // appendOk = result.append(nextLexeme, Lexeme.TYPE_LETTER);
                // }
                //
                if (appendOk) {
                    // 弹出
                    resultList.pollFirst();
                }
            }

            // 可能存在第二轮合并
            // if (Lexeme.TYPE_CNUM == result.getLexemeType() &&
            // !resultList.isEmpty()) {
            // Lexeme nextLexeme = resultList.peekFirst();
            // boolean appendOk = false;
            // if (Lexeme.TYPE_COUNT == nextLexeme.getLexemeType()) {
            // // 合并中文数词+中文量词
            // appendOk = result.append(nextLexeme, Lexeme.TYPE_CQUAN);
            // }
            // if (appendOk) {
            // // 弹出
            // resultList.pollFirst();
            // }
            // }

            // 促销词合并1
            if (result.getLexemeType() == Lexeme.TYPE_PROMOTION && !resultList.isEmpty()) {
                Lexeme nextLexeme = resultList.peekFirst();
                boolean appendOk = false;
                appendOk = result.appendPromotion(nextLexeme, Lexeme.TYPE_PROMOTION);
                if (appendOk) {
                    resultList.pollFirst();
                }
            }

            // 促销词合并2
            if (result.getLexemeType() == Lexeme.TYPE_PROMOTION && !resultList.isEmpty()) {
                Lexeme nextLexeme = resultList.peekFirst();
                boolean isRemoveLast=false;
                while (nextLexeme != null && nextLexeme.getEndPosition() <= result.getEndPosition()) {
                    nextLexeme = resultList.pollFirst();
                    isRemoveLast=true;
                }
                if(isRemoveLast){
                    resultList.addFirst(nextLexeme);
                }
                boolean appendOk = false;
                appendOk = result.append(nextLexeme, Lexeme.TYPE_PROMOTION);
                if (appendOk) {
                    resultList.pollFirst();
                }
            }

            // 促销词合并3
            if (result.getLexemeType() == Lexeme.TYPE_PROMOTION && !resultList.isEmpty()) {
                Lexeme nextLexeme = resultList.peekFirst();
                boolean isRemoveLast=false;
                while (nextLexeme != null && nextLexeme.getEndPosition() <= result.getEndPosition()) {
                    nextLexeme = resultList.pollFirst();
                    isRemoveLast=true;
                }
                if(isRemoveLast){
                    resultList.addFirst(nextLexeme);
                }
                boolean appendOk = false;
                appendOk = result.append(nextLexeme, Lexeme.TYPE_PROMOTION);
                if (appendOk) {
                    resultList.pollFirst();
                }
            }

        }
    }

    public NewDictionary getDictionary() {
        return dictionary;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public SegmentConf getConf() {
        return conf;
    }

}
