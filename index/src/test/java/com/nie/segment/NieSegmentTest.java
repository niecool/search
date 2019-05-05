package com.nie.segment;

import com.nie.segment.dict.NewDictionary;
import com.nie.segment.segmentor.NieSegmenter;

import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-05-05 17:49
 */
public class NieSegmentTest {


    public static void main(String[] args) {
        NewDictionary dic = new NewDictionary(null,true);
        NieSegmenter segmenter = new NieSegmenter(dic);
        List<String> res =  segmenter.segmentStr("我要购买iPhone");
        System.out.println(res.size());
    }
}
