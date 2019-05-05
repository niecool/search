package com.nie.segment.config;

public class SegmentConf {
	private boolean useSmart = false;        //是否使用智能分词
    private boolean optimal = false;        //使用最优的分词

    private boolean chineseSmart = false;   //中文采用消歧。
    private boolean wordLimit = false;  //是否对词对应个数限制

    public boolean isUseSmart() {
        return useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }


    public boolean isOptimal() {
        return optimal;
    }

    public void setOptimal(boolean optimal) {
        this.optimal = optimal;
    }

    public boolean isChineseSmart() {
        return chineseSmart;
    }

    public void setChineseSmart(boolean chineseSmart) {
        this.chineseSmart = chineseSmart;
    }

    public boolean isWordLimit() {
        return wordLimit;
    }

    public void setWordLimit(boolean wordLimit) {
        this.wordLimit = wordLimit;
    }
}
