package com.nie.segment.utils;

import com.nie.segment.dict.NewDictionary;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 1.默认是根据map结构获取最大映射简体匹配。
 *2.然后为了提高性能，依次对输入做首字母简体匹配。
 *
 * @author zhaochengye
 * @date 2019-04-28 09:43
 */
public class Big5Convertor {

    private static Logger log = Logger.getLogger(Big5Convertor.class);

    private static final String EMPTY = "";

    private static final char SPLITER = ',';

    /**
     * 转换表
     */
    private Map<String, String> map;

    /**
     * 首字检查是否需要转换，提高效率
     */
    private Map<Character, String> check;

    /**
     * 最大词长
     */
    private int maxWordLength;

    private static Big5Convertor instance = new Big5Convertor(true);

    public Big5Convertor(boolean isInit) {
        init();
    }

    private Big5Convertor() {
    }

    public static Big5Convertor getInstance() {
        return instance;
    }

    public void init() {
        // long time = System.currentTimeMillis();
        InputStream stream = NewDictionary.class.getClassLoader()
                .getResourceAsStream(NewDictionary.DEFAULT_BIG5TOCN_DICT);
        BufferedReader br = null;
        map = new HashMap<String, String>();
        check = new HashMap<Character, String>();
        maxWordLength = 0;
        try {
            br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String in = null;
            do {
                in = br.readLine();
                if (in == null) {
                    break;
                }
                in = in.trim();
                if (in.startsWith("//") || in.length() == 0) {
                    continue;
                }
                final int psp = in.indexOf(SPLITER);
                if (psp > 0 && !in.contains("?")) {
                    String big = in.substring(0, psp);
                    String cn = in.substring(psp + 1);
                    if (big.length() > maxWordLength) {
                        maxWordLength = big.length();
                    }
                    map.put(big, cn);
                    if (big.length() > 1) {
                        check.put(big.charAt(0), EMPTY);
                    } else {
                        check.put(big.charAt(0), cn);
                    }
                }
            } while (in != null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    public String big52gb(String sentence) {
        int pHead = 0;
        int pTail = maxWordLength;
        StringBuilder ret = new StringBuilder();
        while (pHead < sentence.length()) {
            final char ck = sentence.charAt(pHead);

            if (!check.containsKey(ck)) {
                ret.append(ck);
                pTail = (++pHead) + maxWordLength;
                continue;
            }
            final String cn = check.get(ck);
            if (!cn.equals(EMPTY)) {
                ret.append(cn);
                pTail = (++pHead) + maxWordLength;
                continue;
            }

            if (pTail > sentence.length()) {
                pTail = sentence.length();
            }
            int l = pTail - pHead;
            boolean fMatched = false;
            for (; l > 0; l--) {
                String test = sentence.substring(pHead, pHead + l);
                if (map.containsKey(test)) {
                    ret.append(map.get(test));
                    fMatched = true;
                    break;
                }
            }
            if (fMatched) {
                pHead += l;
            } else {
                ret.append(sentence.charAt(pHead));
                pHead++;
            }
            pTail = pHead + maxWordLength;
        }
        String res = ret.toString();
        // if(res.contains("优+")){
        // res=res.replace("优+", "优加");
        // }
        if (res.contains("可口可乐")) {
            res = res.replace("可口可乐", "可口可乐 可乐 ");
        }
        if (res.contains("°")) {
            res = res.replace("°", "度");
        }
        // if(res.contains("\t")){
        // res=res.replace("\t", "");
        // }
        return res;
    }

    /**
     * 此方法封装了繁简转换函数以及全半角转换功能
     */
    public String complexToSimple(String sentence) {
        return big52gb(sentence);
    }

    public Map<String, String> getMap() {
        return map;
    }

    public static void main(String[] args) {
        String result = Big5Convertor.getInstance().complexToSimple("space");
        System.out.println(result);
    }

}
