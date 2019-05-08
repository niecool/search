package com.nie.interviews.decorator;

/**
 * @author zhaochengye
 * @date 2019-05-07 11:22
 */
public class TornCake extends Pancake {

    public TornCake(){
        desc = "手抓饼";
    }
    @Override
    public double price() {
        return 4;
    }
}
