package com.nie.controller;

import com.alibaba.fastjson.JSON;
import com.nie.controller.model.ProductRequest;
import com.nie.controller.model.ProductResponse;
import com.nie.controller.model.Result;
import com.nie.controller.model.SearchContext;
import com.nie.controller.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-05-06 16:11
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "getProducts",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getProducts(ProductRequest productRequest){
        Result<List<ProductResponse>> result = new Result();
        try {
            SearchContext searchContext = new SearchContext();
            String keyword = productRequest.getKeyWord();
            keyword = URLDecoder.decode(keyword, "UTF-8");
            productRequest.setKeyWord(keyword);
            searchContext.setProductRequest(productRequest);
            List<ProductResponse> responses = searchService.getProducts(searchContext);
            result.setSuccess(true);
            if(!CollectionUtils.isEmpty(responses)){
                result.setCode(200);
                result.setData(responses);
                result.setSegments(searchContext.getSegments());
                result.setMsg("查询成功");
            }else {
                result.setCode(201);
                result.setMsg("查询成功，没有商品");
            }

        }catch (Exception e){
            result.setCode(500);
            result.setMsg(e.toString());
        }finally {
            return JSON.toJSONString(result);
        }
    }


}
