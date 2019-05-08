package com.nie.interviews.decorator;

/**
 * @author zhaochengye
 * @date 2019-05-07 11:20
 */
public abstract class Pancake {

    public String desc="我不是一个具体的饼";

    public String getDesc(){
        return desc;
    }

    public abstract double price();

}
