package com.nie.interviews;

import com.nie.model.pojo.KeywordDict;
import org.junit.Test;

import java.util.Random;

/**
 *
 *
 * @author zhaochengye
 * @date 2019-04-29 11:32
 */
public class test {


    /**
     *
     */
    @Test
    public void test(){
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
        KeywordDict keywordDict = new KeywordDict();
//        IConvert<Integer, KeywordDict> converter = KeywordDict::new;
    }

    @FunctionalInterface
    interface IConvert<D,F>{
        /**
         *
         */
        public D convert(F f);
    }

    @FunctionalInterface
    interface IConvert2{
        /**
         *
         */
        public void convert();
    }



//    class B implements IConvert<String,Integer>{
//
//
//        @Override
//        public String convert(Integer integer) {
//            return Integer.toString(integer);
//        }
//    }


    @Test
    /**
     *
     */
    public void test2(){

        IConvert<String, String> convert = Something::startsWith;
        String converted = convert.convert("123");

        Something something = new Something();
        IConvert<String, String> convert2 = something::combine;
        String converted2 = convert2.convert("123");

        IConvert2 convert3 = Something::new;
        convert3.convert();//执行构造函数
//        System.out.println(convert3.convert() instanceof Something);

//
//// object methods
//        Something something = new Something();
//        IConvert<String, String> converter1 = something::endWith;
//        String converted1 = converter1.convert("Java");

// constructor methods
//        IConvert<String, Something> convert2 = Something::new;
//        Something something3 = convert2.convert("constructors");

    }
}
