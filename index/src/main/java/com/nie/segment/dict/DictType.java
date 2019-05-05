package com.nie.segment.dict;


import com.nie.segment.model.Lexeme;

/**
 * @author zhaochengye
 * @date 2019-04-27 00:25
 * 这个类定义的类型为数据库中表KEYWORD_DICT_TYPE,由于历史原因，定义的类型比较乱,所以需要转换
 * 成词典对应的
 */
public class DictType {
    //1 品类 2 品牌 7 促销词 8 人群 9包装 12材质 13产地 14成份 16修饰词  26颜色

    //4 型号 3 系列  17  口味 19 款式 18 功效 99其他
    public static final int CATEGORY = 1 ; //品类
    public static final int BRAND = 2 ;   //品牌
    public static final int SERIES = 3 ;  //系列
    public static final int XINGHAO = 4 ;  //型号
    public static final int GUIGE = 5 ; //规格
    public static final int COMBINE = 6; //组合
    public static final int PROMOTION = 7; //促销
    public static final int RENQUN = 8 ; //人群
    public static final int BAOZHUANG = 9 ; //包装
    public static final int MERCHANT = 15 ; //商家名
    public static final int CAIZHI = 12; //材质
    public static final int CHANDI = 13; //产地
    public static final int CHENFEN = 14;//成分
    public static final int XIUSHICI = 16; //修饰词
    public static final int KOUWEI = 17; //口味
    public static final int GONGXIAO = 18; //修饰词
    public static final int KUANSHI = 19; //修饰词
    public static final int COLOR = 26; //颜色
    public static final int LIANGCI = 30; //量词

    public static final int TAG = 40; //标签词



    public static final int MODEL = 98;


    public static int getLexemeType(int dictType){
        switch (dictType) {
            case CATEGORY:
                return Lexeme.TYPE_CATEGORY;
            case BRAND:
                return Lexeme.TYPE_BRAND;
            case SERIES:
                return Lexeme.TYPE_SERIES;
            case GUIGE:
                return Lexeme.TYPE_GUIGE;
            case COMBINE:
                return Lexeme.TYPE_COMBINE;
            case PROMOTION:
                return Lexeme.TYPE_PROMOTION;
            case RENQUN:
                return Lexeme.TYPE_RENQUN;
            case BAOZHUANG:
                return Lexeme.TYPE_BAOZHUANG;
            case MERCHANT:
                return Lexeme.TYPE_MERCHANT;
            case MODEL:
                return Lexeme.TYPE_MODEL;
            case CAIZHI:
                return Lexeme.TYPE_CAIZHI;
            case CHANDI:
                return Lexeme.TYPE_CHANDI;
            case CHENFEN:
                return Lexeme.TYPE_CHENFEN;
            case XIUSHICI:
                return Lexeme.TYPE_XIUSHICI;
            case COLOR:
                return Lexeme.TYPE_COLOR;
            case TAG:
                return Lexeme.TYPE_TAG;
            case KOUWEI:
                return Lexeme.TYPE_KOUWEI;
            case GONGXIAO:
                return Lexeme.TYPE_GONGXIAO;
            case KUANSHI:
                return Lexeme.TYPE_KUANSHI;
            case XINGHAO:
                return Lexeme.TYPE_MODEL;
            case LIANGCI:
                return Lexeme.TYPE_LIANGCI;
            default:
                return Lexeme.TYPE_OTHER;
        }
    }
}
