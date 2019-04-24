package com.nie.monitor.impl;

import com.nie.model.DataChangeMessage;
import com.nie.model.DataServiceRequest;
import com.nie.model.DataServiceResponse;
import com.nie.model.NieMessage;
import com.nie.monitor.Observer;
import com.nie.service.DataServer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhaochengye
 * @date 2019-04-23 20:03
 */
public class RealtimeDataChangeObserver implements Observer {

    private Logger log = Logger.getLogger(RealtimeDataChangeObserver.class);
    private DataChangeMessage message;
    @Resource
    private DataServer dataServer;


    @Override
    public void update(NieMessage nieMessage) {
        if(nieMessage instanceof DataChangeMessage){
            this.message = (DataChangeMessage)nieMessage;
            //OtherOperatorSteps
            System.out.println("RealtimeDataChangeObserver接收到消息，正在处理消息======================================");
            for (Long id : message.getIdList()){
                System.out.println(id);
            }

            if(message.getIdList().isEmpty()) return;

            dataServer = new DataServer();//todo spring 注入
            DataServiceRequest request = new DataServiceRequest();
            request.setProductIds(message.getIdList());
            request.setTimestamp(message.getTimeStamp());
            try {
                DataServiceResponse response = dataServer.buildResponse(request);//执行索引入口
                System.out.println("建立索引结束=============================================================");

            }catch (Exception e){
                log.error(e);
            }

        }

    }
}
