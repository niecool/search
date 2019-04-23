package com.nie.monitor;

import com.nie.model.DataChangeMessage;
import com.nie.model.NieMessage;

/**
 * @author zhaochengye
 * @date 2019-04-23 19:50
 */
public interface Observer {

    public void update(NieMessage message);
}
