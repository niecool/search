package com.nie.segment;

import com.nie.utils.CharacterUtil;
import com.nie.utils.StringUtil;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * @author zhaochengye
 * @date 2019-04-27 00:04
 */
public class NewDictionary {
    private static final Logger LOGGER = Logger.getLogger(NewDictionary.class);

    public static final String DEFAULT_DIC_MAIN = "com/yhd/segment/dic/segment.dic";
    public static final String DEFAULT_DIC_QUANTIFIER = "com/yhd/segment/dic/quantifier.dic";

    public static final String DEFAULT_BIG5TOCN_DICT = "com/yhd/segment/dic/big2cn.dic";
    public static final String DEFAULT_DUOYINZI_DICT = "com/yhd/segment/dic/duoYinZi_ZuCi.dic";
    public static final String DEFAULT_PINGYIN = "com/yhd/segment/dic/pinyin.dic";

    public static final String DICT_NAME = "segment.dic";
    public static final int MIN_DICT_COUNT = 200000;
    private static Map<Character, String> ChnNumberMap = new HashMap<Character, String>();
    private static Map<Character, String> NumberChnMap = new HashMap<Character, String>();

    static {
        ChnNumberMap.put('一', "1");
        ChnNumberMap.put('十', "1");
        ChnNumberMap.put('二', "2");
        ChnNumberMap.put('三', "3");
        ChnNumberMap.put('四', "4");
        ChnNumberMap.put('五', "5");
        ChnNumberMap.put('六', "6");
        ChnNumberMap.put('七', "7");
        ChnNumberMap.put('八', "8");
        ChnNumberMap.put('九', "9");
        ChnNumberMap.put('零', "0");
        NumberChnMap.put('0', "零");
        NumberChnMap.put('1', "一");
        NumberChnMap.put('2', "二");
        NumberChnMap.put('3', "三");
        NumberChnMap.put('4', "四");
        NumberChnMap.put('5', "五");
        NumberChnMap.put('6', "六");
        NumberChnMap.put('7', "七");
        NumberChnMap.put('8', "八");
        NumberChnMap.put('9', "九");
    }

    /*
     * 主词典对象
     */
    private DictSegment _MainDict;

    /*
     * 量词词典
     */
    private DictSegment _QuantifierDict;

    public static ArrayList<String> _SeriesDict = new ArrayList<String>();

    private final static String SEPARATOR = "<>";

    private final static Set<Character> WORD_SEPARATOR_SET = new HashSet<Character>();

    private String mainPath;

    private String dictPath;

    private int dictCount;

    static {
        WORD_SEPARATOR_SET.add(' ');
        WORD_SEPARATOR_SET.add('&');
    }

    public NewDictionary(String dictPath, boolean isUseDefault) {
        if (dictPath != null) {
            if (dictPath.endsWith("/")) {
                this.mainPath = dictPath + DICT_NAME;
            } else {
                this.mainPath = dictPath + "/" + DICT_NAME;
            }
            this.dictPath = dictPath;
        }
        this.loadMainDict(mainPath, isUseDefault);
        this.loadQuantifierDict();
    }

    public static String getSegmentPaht(String dictPath) {
        String segmentPath = DICT_NAME;
        if (dictPath != null) {
            if (dictPath.endsWith("/")) {
                segmentPath = dictPath + DICT_NAME;
            } else {
                segmentPath = dictPath + "/" + DICT_NAME;
            }
        }
        return segmentPath;
    }

    /**
     * 加载新词条
     *
     * @param word 单词
     *            Collection<String>词条列表
     */
    public void addWord(String word, int charType) {
//		if (word != null) {
        if (word != null) {
            // 批量加载词条到主内存词典中
            this._MainDict.fillSegment(word.trim().toLowerCase().toCharArray(), charType);
        }
//		}
    }

    /**
     * 批量移除（屏蔽）词条
     *
     * @param words
     */
    public void disableWords(Collection<String> words) {
        if (words != null) {
            for (String word : words) {
                if (word != null) {
                    // 批量屏蔽词条
                    this._MainDict.disableSegment(word.trim().toLowerCase().toCharArray());
                }
            }
        }
    }

    /**
     * 检索匹配主词典
     *
     * @param charArray
     * @return Hit 匹配结果描述
     */
    public Hit matchInMainDict(char[] charArray) {
        return this._MainDict.match(charArray);
    }

    /**
     * 检索匹配主词典
     *
     * @param charArray
     * @param begin
     * @param length
     * @return Hit 匹配结果描述
     */
    public Hit matchInMainDict(char[] charArray, int begin, int length) {
        return this._MainDict.match(charArray, begin, length);
    }

    /**
     * 检索匹配量词词典
     *
     * @param charArray
     * @param begin
     * @param length
     * @return Hit 匹配结果描述
     */
    public Hit matchInQuantifierDict(char[] charArray, int begin, int length) {
        return this._QuantifierDict.match(charArray, begin, length);
    }

    /**
     * 从已匹配的Hit中直接取出DictSegment，继续向下匹配
     *
     * @param charArray
     * @param currentIndex
     * @param matchedHit
     * @return Hit
     */
    public Hit matchWithHit(char[] charArray, int currentIndex, Hit matchedHit) {
        DictSegment ds = matchedHit.getMatchedDictSegment();
        return ds.match(charArray, currentIndex, 1, matchedHit);
    }

    /**
     * 加载主词典,设置isUseDefault后，如果dictPath不存在就使用默认的。
     */
    private void loadMainDict(String dictPath, boolean isUseDefault) {
        int dictCount = 0;
        // 建立一个主词典实例
        _MainDict = new DictSegment((char) 0);
        InputStream is = null;
        try {
            if (dictPath != null) {
                File dictFile = new File(dictPath);
                if (!dictFile.exists()) {
                    throw new RuntimeException("the path " + dictPath + " is not exists.");
                }
                is = new FileInputStream(dictFile);
            }
            if (is == null) {
                if (isUseDefault) {
                    is = this.getClass().getClassLoader().getResourceAsStream(Configuration.DEFAULT_DIC_MAIN);
                } else {
                    throw new RuntimeException("Main Dictionary not found!!!");
                }
            }

            List<PenddingDict> penddingSynonymList = new ArrayList<PenddingDict>();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] arrays = line.split(SEPARATOR);
                if (arrays.length < 2) {
                    // error, continue
                    continue;
                }

                String word = null;
                int type = 0;
                String[] synonymWords = null;
                String[] extendWords = null;
                String synonymTemp = "";
                // String kuozhanTemp = "";
                word = arrays[0];
                try {
                    type = DictType.getLexemeType(Integer.parseInt(arrays[1]));
                } catch (Exception e) {
                    // error, continue
                    continue;
                }
/**
 String otherSy = "";
 if (word.matches("[a-zA-Z0-9 ]+")) {
 if (word.contains(" ")) {
 otherSy = word.replace(" ", "");
 }
 }

 if (!otherSy.equals("")) {
 synonymTemp = otherSy;
 }
 **/
                if (arrays.length >= 3 && !StringUtil.isEmpty(arrays[2])) {
                    if (!synonymTemp.equals("")) {
                        synonymTemp = synonymTemp + "," + arrays[2];
                    } else {
                        synonymTemp = arrays[2];
                    }
                }
                String numString = numTransform(word);
                if (numString != null && !StringUtil.isEmpty(numString)) {
                    if (synonymTemp.equals("")) {
                        synonymTemp = numString;
                    } else {
                        synonymTemp += "," + numString;
                    }
                }
                if (!synonymTemp.isEmpty()) {
                    synonymWords = synonymTemp.split(",");

                }
                if (arrays.length == 4 && (!StringUtil.isEmpty(arrays[3]))) {
                    // kuozhanTemp = arrays[3];
                    extendWords = arrays[3].split(",");
                }
                // if(word.equals("oppo r7")){
                // System.out.println("synonymTemp同义词：
                // "+synonymTemp+":"+synonymWords[0]);
                // }
                fillWordtoDict(word, type, synonymWords, extendWords);
                if (synonymWords != null) {
                    for (int i = 0; i < synonymWords.length; i++) {
                        String[] synonym_set = Arrays.copyOf(synonymWords, synonymWords.length);
                        String target = synonym_set[i];
                        synonym_set[i] = word;
                        fillWordtoDict(target, type, synonym_set, extendWords);
                    }
                }
                if (extendWords != null) {
                    for (int i = 0; i < extendWords.length; i++) {
                        fillWordtoDict(extendWords[i], type, null, null);
                    }
                }
                /**
                 * // 同义词关系双向映射 String text = word; String synonym1 =
                 * synonymTemp; if (!synonymTemp.equals("")) { if
                 * (synonym1.contains(",")) { String toks[] =
                 * synonym1.split(","); for (int i = 0; i < toks.length; i++) {
                 * String textTemp = text; text = toks[i]; toks[i] = textTemp;
                 * // synonym1=synonym1.replace(sw, textTemp);
                 * fillWordtoDict(text, type, toks, extendWords); } } else {
                 * String textTemp = text; text = synonym1; synonym1 = textTemp;
                 * fillWordtoDict(text, type, synonym1.split(","), extendWords);
                 * } } // 扩展词加入词典 String segword = word; String kuozhan =
                 * kuozhanTemp; if (!kuozhan.equals("") && kuozhan != null) { if
                 * (kuozhan.contains(",")) { String d[] = kuozhan.split(",");
                 * for (int i = 0; i < d.length; i++) { String textTemp =
                 * segword; segword = d[i]; d[i] = textTemp; //
                 * kuozhan=kuozhan.replace(sw, textTemp);
                 * fillWordtoDict(segword, type, null, null); } } else { String
                 * textTemp = segword; segword = kuozhan; kuozhan = textTemp;
                 * fillWordtoDict(segword, type, null, null); } }
                 **/
                // 加载同义词,先放到pendding中,然后等词典加载完，在处理
                if (synonymWords != null) {
                    for (String synonym : synonymWords) {
                        PenddingDict dict = new PenddingDict();
                        dict.charType = type;
                        String[] tmpSynonyms = null;
                        // 品牌和型号词对应的同义词是双向匹配的。
                        if (type == Lexeme.TYPE_BRAND || type == Lexeme.TYPE_MODEL || type == Lexeme.TYPE_SERIES) {
                            Set<String> synSet = new LinkedHashSet<String>();
                            synSet.add(word);
                            for (String inner : synonymWords) {
                                if (!inner.equals(synonym)) {
                                    synSet.add(inner);
                                }
                            }
                            tmpSynonyms = new String[synSet.size()];
                            int i = 0;
                            for (String synWord : synSet) {
                                tmpSynonyms[i] = synWord;
                                i++;
                            }
                            dict.synonyms = tmpSynonyms;
                        } else {
                            tmpSynonyms = new String[] { word };
                        }
                        dict.word = synonym;
                        penddingSynonymList.add(dict);
                    }
                }
                dictCount++;
            }

            // 词典加载完后，再处理pendding的同义词。
            if (penddingSynonymList.size() > 0) {
                for (PenddingDict dict : penddingSynonymList) {

                    Hit hit = _MainDict.match(dict.word.toCharArray());
                    // 如果hit命中,表示已经有词
                    if (hit.isMatch()) {
                        if (hit.getMatchedDictSegment().getSynonymWordsMap() == null) {
                            hit.getMatchedDictSegment().setSynonymWordsMap(new HashMap<Integer, String[]>());
                            hit.getMatchedDictSegment().getSynonymWordsMap().put(dict.charType, dict.synonyms);
                        }
                    } else {
                        fillWordtoDict(dict.word.toLowerCase().trim(), dict.charType, dict.synonyms, null);
                    }
                }
            }
        } catch (IOException ioe) {
            LOGGER.error("Main Dictionary loading exception.");
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
        this.dictCount = dictCount;
    }

    public static String numTransform(String word) {
        String ret = null;
        if (word.matches(".*?([一二三四五六七八九零]+).*?")) {
            StringBuffer buf = new StringBuffer();
            for (char ch : word.toCharArray()) {
                if (ChnNumberMap.containsKey(ch)) {
                    buf.append(ChnNumberMap.get(ch));
                } else {
                    buf.append(ch);
                }
            }
            ret = buf.toString();
        }
        return ret;
    }

    public static String allNumTransform(String word) {
        StringBuffer sb = new StringBuffer();
        if (word.matches("[一二三四五六七八九零十]+")) {
            // System.out.println(word);
            for (char ch : word.toCharArray()) {
                if (ChnNumberMap.containsKey(ch)) {
                    sb.append(ChnNumberMap.get(ch));
                } else {
                    sb.append(ch);
                }
            }
            return sb.toString();
        }
        if (word.matches("[0-9]+")) {
            // System.out.println(word);
            for (char ch : word.toCharArray()) {
                if (NumberChnMap.containsKey(ch)) {
                    sb.append(NumberChnMap.get(ch));
                } else {
                    sb.append(ch);
                }
            }
            return sb.toString();
        }
        return sb.toString();
    }

    private void fillWordtoDict(String word, int type, String[] synonymWords, String[] extendWords) {
        int symbolNum = 0;
        // 将中文和英文混合在一起的分开

        char[] wordArray = word.toLowerCase().trim().toCharArray();
        boolean firstIsChineseType = CharacterUtil.identifyCharType(wordArray[0]) == CharacterUtil.CHAR_CHINESE;
        boolean lastIsChineseCharType = CharacterUtil
                .identifyCharType(wordArray[wordArray.length - 1]) == CharacterUtil.CHAR_CHINESE;
        if (firstIsChineseType != lastIsChineseCharType) {
            int typeIndex = 1;
            for (int i = 1; i < wordArray.length - 1; i++) {
                if ((CharacterUtil
                        .identifyCharType(wordArray[i]) == CharacterUtil.CHAR_CHINESE) == firstIsChineseType) {
                    typeIndex++;
                } else {
                    break;
                }
            }

            for (int i = typeIndex; i < wordArray.length - 1; i++) {
                if ((CharacterUtil
                        .identifyCharType(wordArray[i]) == CharacterUtil.CHAR_CHINESE) != lastIsChineseCharType) {
                    typeIndex = 1;
                    break;
                }
            }
            if (typeIndex > 1) {
                int subType = Lexeme.TYPE_SUB_WORD;
                String[] tmpSynonymWords = null;
                String[] tmpExtendWords = null;
                if (type == Lexeme.TYPE_BRAND) {
                    subType = type;
                    tmpSynonymWords = synonymWords;
                    tmpExtendWords = extendWords;
                }
                if (typeIndex > 3) {
                    _MainDict.fillSegment(Arrays.copyOfRange(wordArray, 0, typeIndex), subType, tmpSynonymWords,
                            tmpExtendWords);
                }
                for (int i = typeIndex; i < wordArray.length; i++) {
                    if (WORD_SEPARATOR_SET.contains(wordArray[i])) {
                        typeIndex++;
                    }
                }
                if (typeIndex < wordArray.length - 3) {
                    _MainDict.fillSegment(Arrays.copyOfRange(wordArray, typeIndex, wordArray.length), subType,
                            tmpSynonymWords, tmpExtendWords);
                }
            }
        } else {
            // 空格,&为分隔符,分开加到词典中
            int wordBegin = 0;
            int length = 0;
            for (int i = 0; i < wordArray.length; i++) {
                if (WORD_SEPARATOR_SET.contains(wordArray[i])) {
                    if (length > 2) {
                        _MainDict.fillSegment(Arrays.copyOfRange(wordArray, wordBegin, wordBegin + length),
                                Lexeme.TYPE_SUB_WORD);
                    }
                    length = 0;
                    wordBegin = i + 1;
                    symbolNum++;
                } else {
                    length++;
                }
                if (i == wordArray.length - 1) {
                    if (wordBegin > 0 && length > 2) {
                        _MainDict.fillSegment(Arrays.copyOfRange(wordArray, wordBegin, wordBegin + length),
                                Lexeme.TYPE_SUB_WORD);
                    }
                }
            }
            if (symbolNum > 0) {
                String[] tmpSynonym = new String[] { word };
                String tmpWord = word.toLowerCase().trim();
                // 去掉所有symbol字符加入到词典里面
                for (Character symbol : WORD_SEPARATOR_SET) {
                    tmpWord = tmpWord.replaceAll(symbol.toString(), "");
                }
                _MainDict.fillSegment(tmpWord.toCharArray(), type, tmpSynonym, extendWords);
            }
        }

        _MainDict.fillSegment(word.toLowerCase().trim().toCharArray(), type, synonymWords, extendWords, symbolNum);
    }

    /**
     * 加载量词词典
     */
    private void loadQuantifierDict() {
        // 建立一个量词典实例
        _QuantifierDict = new DictSegment((char) 0);
        // 读取量词词典文件
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(Configuration.DEFAULT_DIC_QUANTIFIER);
        if (is == null) {
            throw new RuntimeException("Quantifier Dictionary not found!!!");
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
            String theWord = null;
            do {
                theWord = br.readLine();
                if (theWord != null && !"".equals(theWord.trim())) {
                    _QuantifierDict.fillSegment(theWord.trim().toLowerCase().toCharArray(), Lexeme.TYPE_UNKNOWN);
                }
            } while (theWord != null);

        } catch (IOException ioe) {
            LOGGER.error("Quantifier Dictionary loading exception.");
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    /**
     * 根据词典匹配输入对应Lexeme,如果没有返回null
     */
    public Lexeme matchWord(String input) {
        if (input == null || input.trim().equals("")) {
            return null;
        }
        Lexeme word = null;
        char[] input_array = input.trim().toLowerCase().toCharArray();
        final Hit hit = this.matchInMainDict(input_array);
        if (hit.isMatch()) {
            final DictSegment seg = hit.getMatchedDictSegment();
            word = new Lexeme(0, hit.getBegin(), input.length(), seg.getWordType(), seg.getSynonymWordsMap(),
                    seg.getExtendedWordsMap(), seg.getSymbolNum());
            word.setLexemeText(input);
        }
        return word;
    }

    public String getMainPath() {
        return mainPath;
    }

    public String getDictPath() {
        return dictPath;
    }

    public int getDictCount() {
        return dictCount;
    }

    class PenddingDict {
        String word;
        int charType;
        String[] synonyms;
    }

}
