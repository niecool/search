package com.nie.controller.service;

import com.nie.DaoManager.ProductDaoManager;
import com.nie.controller.model.ProductRequest;
import com.nie.controller.model.ProductResponse;
import com.nie.controller.model.SearchContext;
import com.nie.lucene.product.search.IndexSearcherFactory;
import com.nie.model.pojo.Product;
import com.nie.segment.segmentor.NieSegmenter;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaochengye
 * @date 2019-05-06 16:24
 */
@Component
public class SearchService {
    Logger LOG = Logger.getLogger(SearchService.class);
    private IndexSearcher indexSearcher = new IndexSearcherFactory().getIndexSearcher();
    @Resource
    private ProductDaoManager productDaoManager;
    @Autowired
    private NieSegmenter nieSegmenter;

    /**
     *
     */
    public List<ProductResponse> getProducts(SearchContext searchContext){
        List<ProductResponse> responses = new ArrayList<>();
        try {
            buildQuery(searchContext);
            TopDocs topDocs = indexSearcher.search(searchContext.getQuery(),50);//todo 个数需要可配置
            if(topDocs.totalHits<1) return responses;
            List<Long> ids = new ArrayList();
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document doc = indexSearcher.doc(scoreDoc.doc);
                ids.add(Long.parseLong(doc.get("id")));
                LOG.info("doc信息：" + "docId=" + scoreDoc.doc + "id=" + doc.get("id") + "productCname="
                        + doc.get("product_cname") + "productEname=" + doc.get("product_ename") + "productDescription="
                        + doc.get("product_description"));

            }
            responses = convert2Products(ids);
        } catch (IOException e) {
            e.printStackTrace();
            return responses;
        }
        return responses;

    }

    private void buildQuery(SearchContext searchContext) {
        BooleanQuery.Builder  builder=new BooleanQuery.Builder();
        String keyWord = searchContext.getProductRequest().getKeyWord();
        List<String> noScoreWords = nieSegmenter.segmentStr(keyWord);
        for (String word : noScoreWords){
            Query query = new TermQuery( new Term("no_score_words",word));
            builder.add(query,BooleanClause.Occur.MUST);
        }
        BooleanQuery booleanQuery = builder.build();
        searchContext.setQuery(booleanQuery);
        searchContext.setSegments(noScoreWords);//提供给高亮
    }

    private List<ProductResponse> convert2Products(List<Long> ids) {
        List<ProductResponse> responses = new ArrayList<>();
        if(!CollectionUtils.isEmpty(ids)){
            List<Product> productList = productDaoManager.queryProductByIds(ids);
            responses = productList.stream().map(SearchService::buildResponseFunction).collect(Collectors.toList());
        }
        return responses;
    }

    /**
     *构建结果函数
     */
    public static ProductResponse buildResponseFunction(Product product){
        ProductResponse response = new ProductResponse();
        BeanUtils.copyProperties(product,response);
        return response;
    }










//    public static String query(String question) {
//        List<String> engines = new ArrayList<String>() {{
//            add("http://www.google.com/?q=");
//            add("http://duckduckgo.com/?q=");
//            add("http://www.bing.com/search?q=");
//        }};
//        // get element as soon as it is available
//        Optional<String> result = engines.stream().parallel().map((base) -> {
//            String url = base + question;
//            // open connection and fetch the result
//            return WS.url(url).get();
//        }).findAny();
//        return result.get();
//    }


}
