package com.nie.segment;


/**
 *
 * 配置管理类接口
 * @author zhaochengye
 * @date 2019-04-27 00:24
 */
public class Configuration {
    public static final String DEFAULT_DIC_MAIN = "com/yhd/segment/dic/segment.dic";
    public static final String DEFAULT_DIC_QUANTIFIER = "com/yhd/segment/dic/quantifier.dic";
    public static final String DICT_NAME = "segment.dic";
    public static final String DEFAULT_MODEL = "com/yhd/segment/dic/tag_model.dic";
    private String mainPath;
    private boolean useDefalult = false;
    public Configuration() {

    }

    public Configuration(String dictPath){

    }

    public String getMainPath() {
        return mainPath;
    }
    public void setMainPath(String mainPath) {
        this.mainPath = mainPath;
    }
    public boolean isUseDefalult() {
        return useDefalult;
    }
    public void setUseDefalult(boolean useDefalult) {
        this.useDefalult = useDefalult;
    }


}
