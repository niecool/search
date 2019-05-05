package com.nie.segment.model;

/**
 * @author zhaochengye
 * @date 2019-04-27 00:10
 */

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

/**
 * IK词元对象
 */
public class Lexeme implements Comparable<Lexeme>, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -320365115130741610L;
    // lexemeType常量
    // 未知
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_CATEGORY = 1; // 品类
    public static final int TYPE_BRAND = 1 << 1; // 品牌
    public static final int TYPE_SERIES = 1 << 2; // 系列
    public static final int TYPE_GUIGE = 1 << 3; // 规格
    public static final int TYPE_COMBINE = 1 << 4; // 组合
    public static final int TYPE_PROMOTION = 1 << 5; // 促销
    public static final int TYPE_TAG = 1 << 8; // 标签词
    public static final int TYPE_MERCHANT = 1 << 26; // 商家名
    public static final int TYPE_RENQUN = 1 << 6; // 人群
    public static final int TYPE_BAOZHUANG = 1 << 7; // 包装
    public static final int TYPE_MODEL = 1 << 9; // 型号词
    public static final int TYPE_CAIZHI = 1 << 10; // 材质
    public static final int TYPE_CHANDI = 1 << 11; // 产地
    public static final int TYPE_CHENFEN = 1 << 12;
    public static final int TYPE_XIUSHICI = 1 << 13; // 修饰
    public static final int TYPE_COLOR = 1 << 14; // 颜色
    public static final int TYPE_KOUWEI = 1 << 27; // 口味
    public static final int TYPE_GONGXIAO = 1 << 28;// 功效
    public static final int TYPE_KUANSHI = 1 << 29; // 款式
    public static final int TYPE_LIANGCI = 1 << 30; // 量词
    // 普通词
    public static final int TYPE_OTHER = 1 << 15;

    public static final int TYPE_SUB_WORD = 1 << 16;
    // 中文量词
    public static final int TYPE_COUNT = 1 << 17;
    // 中文数量词
    public static final int TYPE_CQUAN = 1 << 18;
    // 英文
    public static final int TYPE_ENGLISH = 1 << 19;
    // 数字
    public static final int TYPE_ARABIC = 1 << 20;
    // 英文数字混合
    public static final int TYPE_LETTER = 1 << 21;
    // 中文单字
    public static final int TYPE_CNCHAR = 1 << 22;
    // 日韩文字
    public static final int TYPE_OTHER_CJK = 1 << 23;
    // 中文数词
    public static final int TYPE_CNUM = 1 << 24;
    // 扩展词
    public static final int TYPE_EXTEND = 1 << 25;

    // 词元的起始位移
    private int offset;
    // 词元的相对起始位置
    private int begin;
    // 词元的长度
    private int length;
    // 词元文本
    private String lexemeText;
    // 词元类型
    private int lexemeType;
    // 表示词中符号的个数,主要用于后面的消歧使用。
    private int symbolNum;

    private boolean isChinese = false;

    private boolean isEnglish = false;

    private Map<Integer, String[]> synonymWordsMap;

    private Map<Integer, String[]> extendWordsMap;

    public Lexeme(int offset, int begin, int length, int lexemeType, Map<Integer, String[]> synonymWordsMap,
                  Map<Integer, String[]> extendWordsMap, int symbolNum) {
        this(offset, begin, length, lexemeType, synonymWordsMap, extendWordsMap, symbolNum, false);
    }

    public Lexeme(int offset, int begin, int length, int lexemeType, Map<Integer, String[]> synonymWordsMap,
                  Map<Integer, String[]> extendWordsMap, int symbolNum, boolean isChinese) {
        this.offset = offset;
        this.begin = begin;
        if (length < 0) {
            throw new IllegalArgumentException("length < 0");
        }
        this.length = length;
        this.lexemeType = lexemeType;
        this.symbolNum = symbolNum;
        this.synonymWordsMap = synonymWordsMap;
        this.extendWordsMap = extendWordsMap;
        this.isChinese = isChinese;
    }

    public Lexeme(int offset, int begin, int length, int lexemeType) {
        this(offset, begin, length, lexemeType, null, null, 0);
    }

    /*
     * 判断词元相等算法 起始位置偏移、起始位置、终止位置相同
     *
     * @see java.lang.Object#equals(Object o)
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (this == o) {
            return true;
        }

        if (o instanceof Lexeme) {
            Lexeme other = (Lexeme) o;
            if (this.offset == other.getOffset() && this.begin == other.getBegin()
                    && this.length == other.getLength()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /*
     * 词元哈希编码算法
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int absBegin = getBeginPosition();
        int absEnd = getEndPosition();
        return (absBegin * 37) + (absEnd * 31) + ((absBegin * absEnd) % getLength()) * 11;
    }

    /*
     * 词元在排序集合中的比较算法
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Lexeme other) {
        // 起始位置优先
        if (this.begin < other.getBegin()) {
            return -1;
        } else if (this.begin == other.getBegin()) {
            // 词元长度优先
            if (this.length > other.getLength()) {
                return -1;
            } else if (this.length == other.getLength()) {
                return 0;
            } else {// this.length < other.getLength()
                return 1;
            }

        } else {// this.begin > other.getBegin()
            return 1;
        }
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getBegin() {
        return begin;
    }

    /**
     * 获取词元在文本中的起始位置
     *
     * @return int
     */
    public int getBeginPosition() {
        return offset + begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    /**
     * 获取词元在文本中的结束位置
     *
     * @return int
     */
    public int getEndPosition() {
        return offset + begin + length;
    }

    /**
     * 获取词元的字符长度
     *
     * @return int
     */
    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        if (this.length < 0) {
            throw new IllegalArgumentException("length < 0");
        }
        this.length = length;
    }

    /**
     * 获取词元的文本内容
     *
     * @return String
     */
    public String getLexemeText() {
        if (lexemeText == null) {
            return "";
        }
        return lexemeText;
    }

    public void setLexemeText(String lexemeText) {
        if (lexemeText == null) {
            this.lexemeText = "";
            this.length = 0;
        } else {
            this.lexemeText = lexemeText;
            this.length = lexemeText.length();
        }
    }

    /**
     * 获取词元类型
     *
     * @return int
     */
    public int getLexemeType() {
        return lexemeType;
    }

    /**
     * 获取词元类型标示字符串
     *
     * @return String
     */
    public String getLexemeTypeString() {
        switch (lexemeType) {
            case TYPE_CATEGORY:
                return "CATEGORY";

            case TYPE_BRAND:
                return "BRAND";

            case TYPE_SERIES:
                return "SERIES";

            case TYPE_GUIGE:
                return "GUIGE";

            case TYPE_COMBINE:
                return "COMBINE";

            case TYPE_PROMOTION:
                return "PROMOTION";

            case TYPE_RENQUN:
                return "RENQUN";

            case TYPE_CAIZHI:
                return "CAIZHI";
            case TYPE_CHANDI:
                return "CHANDI";
            case TYPE_CHENFEN:
                return "CHENFEN";
            case TYPE_XIUSHICI:
                return "XIUSHICI";
            case TYPE_COLOR:
                return "COLOR";
            case TYPE_OTHER:
                return "OTHER";
            case TYPE_SUB_WORD:
                return "SUB_WORD";

            case TYPE_ENGLISH:
                return "ENGLISH";

            case TYPE_ARABIC:
                return "ARABIC";

            case TYPE_LETTER:
                return "LETTER";

            case TYPE_CNCHAR:
                return "CN_CHAR";

            case TYPE_OTHER_CJK:
                return "OTHER_CJK";

            case TYPE_COUNT:
                return "COUNT";

            case TYPE_CNUM:
                return "TYPE_CNUM";

            case TYPE_CQUAN:
                return "TYPE_CQUAN";

            case TYPE_EXTEND:
                return "TYPE_EXTEND";
            case TYPE_TAG:
                return "TAG";
            case TYPE_KOUWEI:
                return "KOUWEI";
            case TYPE_GONGXIAO:
                return "GONGXIAO";
            case TYPE_KUANSHI:
                return "KUANSHI";
            case TYPE_UNKNOWN:
                return "UNKNOWN";
            case TYPE_LIANGCI:
                return "LIANGCI";
        }

        StringBuilder builder = new StringBuilder();
        if ((lexemeType & TYPE_CATEGORY) == TYPE_CATEGORY) {
            builder.append("CATEGORY");
        }
        if ((lexemeType & TYPE_CNUM) == TYPE_CNUM) {
            builder.append("CNUM");
        }
        if ((lexemeType & TYPE_TAG) == TYPE_TAG) {
            builder.append("_TAG");
        }
        if ((lexemeType & TYPE_KOUWEI) == TYPE_KOUWEI) {
            builder.append("_KOUWEI");
        }
        if ((lexemeType & TYPE_GONGXIAO) == TYPE_GONGXIAO) {
            builder.append("_GONGXIAO");
        }
        if ((lexemeType & TYPE_KUANSHI) == TYPE_KUANSHI) {
            builder.append("_KUANSHI");
        }
        if ((lexemeType & TYPE_BRAND) == TYPE_BRAND) {
            builder.append("_BRAND");
        }
        if ((lexemeType & TYPE_SERIES) == TYPE_SERIES) {
            builder.append("_SERIES");
        }
        if ((lexemeType & TYPE_GUIGE) == TYPE_GUIGE) {
            builder.append("_GUIGE");
        }
        if ((lexemeType & TYPE_COMBINE) == TYPE_COMBINE) {
            builder.append("_COMBINE");
        }
        if ((lexemeType & TYPE_PROMOTION) == TYPE_PROMOTION) {
            builder.append("_PROMOTION");
        }
        if ((lexemeType & TYPE_RENQUN) == TYPE_RENQUN) {
            builder.append("_RENQUN");
        }
        if ((lexemeType & TYPE_BAOZHUANG) == TYPE_BAOZHUANG) {
            builder.append("_BAOZHUANG");
        }
        if ((lexemeType & TYPE_MODEL) == TYPE_MODEL) {
            builder.append("_MODEL");
        }
        if ((lexemeType & TYPE_MERCHANT) == TYPE_MERCHANT) {
            builder.append("_MERCHANT");
        }
        if ((lexemeType & TYPE_SUB_WORD) == TYPE_SUB_WORD) {
            builder.append("_SUB");
        }
        if ((lexemeType & TYPE_XIUSHICI) == TYPE_XIUSHICI) {
            builder.append("_XIUSHICI");
        }
        if ((lexemeType & TYPE_OTHER) == TYPE_OTHER) {
            builder.append("_OTHER");
        }
        if ((lexemeType & TYPE_LIANGCI) == TYPE_LIANGCI) {
            builder.append("_LIANGCI");
        }

        return builder.toString();
    }

    public void setLexemeType(int lexemeType) {
        this.lexemeType = lexemeType;
    }

    /**
     * 合并两个相邻的词元
     *
     * @param l
     * @param lexemeType
     * @return boolean 词元是否成功合并
     */
    public boolean append(Lexeme l, int lexemeType) {
        if (l != null && this.getEndPosition() == l.getBeginPosition()) {
            this.length += l.getLength();
            this.lexemeType = lexemeType;
            return true;
        } else {
            return false;
        }
    }

    public boolean appendPromotion(Lexeme l, int lexemeType) {
        if (l != null && this.getEndPosition() <= l.getBeginPosition()) {
            this.length = l.getEndPosition() - this.getBeginPosition();
            this.lexemeType = lexemeType;
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     */
    @Override
    public String toString() {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append(this.getBeginPosition()).append("-").append(this.getEndPosition());
        strbuf.append(" : ").append("\"").append(this.lexemeText).append("\"");
        if (synonymWordsMap != null) {
            strbuf.append(" , synset:");
            for (Map.Entry<Integer, String[]> entry : synonymWordsMap.entrySet()) {
                strbuf.append("{" + entry.getKey() + ":" + Arrays.toString(entry.getValue()) + "}");
            }
        }

        if (extendWordsMap != null) {
            strbuf.append(" , extset:");
            for (Map.Entry<Integer, String[]> entry : extendWordsMap.entrySet()) {
                strbuf.append("{" + entry.getKey() + ":" + Arrays.toString(entry.getValue()) + "}");
            }
        }

        strbuf.append(" : ");
        strbuf.append(this.getLexemeTypeString());
        return strbuf.toString();
    }

    public boolean istargetType(int targetType) {
        return (this.lexemeType & targetType) == targetType;
    }

    public boolean isAttrType() {
        // 人群、包装、成分、材质、产地、颜色
        if (((lexemeType & TYPE_RENQUN) == TYPE_RENQUN) || ((lexemeType & TYPE_BAOZHUANG) == TYPE_BAOZHUANG)
                || ((lexemeType & TYPE_CAIZHI) == TYPE_CAIZHI) || ((lexemeType & TYPE_CHANDI) == TYPE_CHANDI)
                || ((lexemeType & TYPE_CHENFEN) == TYPE_CHENFEN) || ((lexemeType & TYPE_COLOR) == TYPE_COLOR)
                || ((lexemeType & TYPE_KOUWEI) == TYPE_KOUWEI) || ((lexemeType & TYPE_KUANSHI) == TYPE_KUANSHI)
                || ((lexemeType & TYPE_GONGXIAO) == TYPE_GONGXIAO)) {
            return true;
        }
        return false;
    }

    public int getSymbolNum() {
        return symbolNum;
    }

    public void setSymbolNum(int symbolNum) {
        this.symbolNum = symbolNum;
    }

    public Map<Integer, String[]> getSynonymWordsMap() {
        return synonymWordsMap;
    }

    public Map<Integer, String[]> getExtendedWordsMap() {
        return extendWordsMap;
    }

    public boolean isChinese() {
        return isChinese;
    }

    public boolean isEnglish() {
        return isEnglish;
    }

    public void setEnglish(boolean isEnglish) {
        this.isEnglish = isEnglish;
    }

    public boolean isNum() {
        // TODO Auto-generated method stub
        if ((this.lexemeType & TYPE_ARABIC) == TYPE_ARABIC || (this.lexemeType & TYPE_CNUM) == TYPE_CNUM) {
            return true;
        }
        return false;
    }
}
