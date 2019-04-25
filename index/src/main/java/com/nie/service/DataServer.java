package com.nie.service;

import com.nie.model.DataServiceRequest;
import com.nie.model.DataServiceResponse;
import com.nie.model.ProductIndexable;
import com.nie.service.realtime.RealtimeIndexableBuilder;
import com.nie.utils.NetUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-23 18:57
 */
@Component
public class DataServer implements DataService{
    private Logger log = Logger.getLogger(DataServer.class);

    @Autowired
    private RealtimeIndexableBuilder realtimeIndexableBuilder;

    @Override
    public DataServiceResponse buildResponse(DataServiceRequest req) throws Exception {
        if(req == null){
            log.warn("req is null");
            return new DataServiceResponse();
        }
        List<ProductIndexable> products = null;
        DataServiceResponse dataServiceResponse = new DataServiceResponse();

        products = realtimeIndexableBuilder.buildRouter(req.getProductIds(),req.getSource());
        dataServiceResponse.setDeleteProductCount(realtimeIndexableBuilder.getDeleteProductCount());
        dataServiceResponse.setExtendProductCount(realtimeIndexableBuilder.getExtendProductCount());
        dataServiceResponse.setProductIndexableList(products);
        dataServiceResponse.setDataServiceIp(NetUtil.getLocalIP());

        return dataServiceResponse;
    }

    public RealtimeIndexableBuilder getRealtimeIndexableBuilder() {
        return realtimeIndexableBuilder;
    }

    public void setRealtimeIndexableBuilder(RealtimeIndexableBuilder realtimeIndexableBuilder) {
        this.realtimeIndexableBuilder = realtimeIndexableBuilder;
    }
}
