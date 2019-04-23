package com.nie.service;

import com.nie.model.DataServiceRequest;
import com.nie.model.DataServiceResponse;

/**
 * @author zhaochengye
 * @date 2019-04-23 18:58
 */
public interface DataService {
    public DataServiceResponse buildResponse(DataServiceRequest req) throws Exception;
}
