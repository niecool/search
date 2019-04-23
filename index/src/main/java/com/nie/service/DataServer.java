package com.nie.service;

import com.nie.model.DataServiceRequest;
import com.nie.model.DataServiceResponse;
import com.nie.model.ProductIndexable;
import com.nie.service.realtime.RealtimeIndexableBuilder;
import com.nie.utils.NetUtil;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-23 18:57
 */
public class DataServer implements DataService{
    private Logger log = Logger.getLogger(DataServer.class);

    @Override
    public DataServiceResponse buildResponse(DataServiceRequest req) throws Exception {
        if(req == null){
            log.warn("req is null");
            return new DataServiceResponse();
        }
        List<ProductIndexable> products = null;
        DataServiceResponse dataServiceResponse = new DataServiceResponse();

        RealtimeIndexableBuilder realtimeIndexableBuilder = new RealtimeIndexableBuilder();
        products = realtimeIndexableBuilder.buildRouter(req.getProductIds(),req.getSource());
        dataServiceResponse.setDeleteProductCount(realtimeIndexableBuilder.getDeleteProductCount());
        dataServiceResponse.setExtendProductCount(realtimeIndexableBuilder.getExtendProductCount());
        dataServiceResponse.setProductIndexableList(products);
        dataServiceResponse.setDataServiceIp(NetUtil.getLocalIP());

        return null;
    }
}
