package com.nie.utils;

/**
 * @author zhaochengye
 * @date 2019-04-27 00:20
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * 字符集识别工具类
 */
public class CharacterUtil {

    public static final int CHAR_USELESS = 0;

    public static final int CHAR_ARABIC = 0X00000001;

    public static final int CHAR_ENGLISH = 0X00000002;

    public static final int CHAR_CHINESE = 0X00000004;

    public static final int CHAR_OTHER_CJK = 0X00000008;

    public static final int CHAR_SYMBOL = 0X00000010;

    public static final int CHAR_BLANK = 0X00000011;

    public static final int CHAR_NUMCONNECTOR = 0X00000013;


    // 链接符号
    private static final char[] Letter_Connector = new char[] { '#', '&', '+', '-', '.', '@', '_','’', 'π'};

    // 数字符号
    private static final char[] Num_Connector = new char[] {'.'};

    private static final Set<Character> letterConnectorSet = new HashSet<Character>();

    static{
        for (char c : Letter_Connector) {
            letterConnectorSet.add(c);
        }
    }

    /**
     * 识别字符类型
     * @param input
     * @return int CharacterUtil定义的字符类型常量
     */
    public static int identifyCharType(char input) {
        if (input >= '0' && input <= '9') {
            return CHAR_ARABIC;

        } else if ((input >= 'a' && input <= 'z') || (input >= 'A' && input <= 'Z')) {
            return CHAR_ENGLISH;

        } else {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(input);
            if(input==' '){
                return CHAR_BLANK;
            }
            if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) {
                // 目前已知的中文字符UTF-8集合
                return CHAR_CHINESE;

            } else if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS // 全角数字字符和日韩字符
                    // 韩文字符集
                    || ub == Character.UnicodeBlock.HANGUL_SYLLABLES
                    || ub == Character.UnicodeBlock.HANGUL_JAMO
                    || ub == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO
                    // 日文字符集
                    || ub == Character.UnicodeBlock.HIRAGANA // 平假名
                    || ub == Character.UnicodeBlock.KATAKANA // 片假名
                    || ub == Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS) {
                return CHAR_OTHER_CJK;

            }else if(letterConnectorSet.contains(input)){
                return CHAR_SYMBOL;
            }
//      else if(Num_Connector[0]==input){
//    	  return CHAR_NUMCONNECTOR;
//      }

        }
        // 其他的不做处理的字符
        return CHAR_USELESS;
    }

    /**
     * 进行字符规格化（全角转半角，大写转小写处理）
     * @param input
     * @return char
     */
    public static char regularize(char input) {
        if (input == 12288) {
            input = (char) 32;

        } else if (input > 65280 && input < 65375) {
            input = (char) (input - 65248);

        } else if (input >= 'A' && input <= 'Z') {
            input += 32;
        }

        return input;
    }


    /**
     * 判断死否是英文和数字
     * @param charType
     * @return
     */
    public static boolean isLatinChar(int charType){
        return CHAR_ENGLISH == charType || CHAR_ARABIC == charType;
    }

    /**
     * 判断是否是字母连接符号
     * @param input
     * @return
     */
    public static  boolean isLetterConnector(char input) {
        return letterConnectorSet.contains(input);
    }

    /**
     * 判断是否是数字连接符号
     * @param input
     * @return
     */
    public static boolean isNumConnector(char input) {
        int index = Arrays.binarySearch(Num_Connector, input);
        return index >= 0;
    }
}
