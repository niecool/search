package com.nie.lucene.product.buildIndex;

import com.nie.model.ProductIndexable;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-25 15:31
 */
public class ProductIndexer {

    IndexWriter indexWriter = IndexWriterFactory.getSingletonIndexWriter();
    List<Document> documents = new ArrayList<Document>();

    /**
     *写索引到文件
     */
    public void writeIndex(List<ProductIndexable> productIndexables){
        try {
            init(productIndexables);
            indexWriter.addDocuments(documents);
            indexWriter.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
//            try {
////                indexWriter.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    private void buildDocuments(List<ProductIndexable> productIndexables){

        for(ProductIndexable p : productIndexables){
            Document document = new Document();
            document.add(new StringField("id", p.getId(), Field.Store.YES));//name, value, store.type StringField不会进行分词，会将整个串存储在索引中，比如(订单号,身份证号等)
            document.add(new StringField("product_cname",p.getProductCname() == null ? "" : p.getProductCname(), Field.Store.YES));
            document.add(new StringField("product_ename", p.getProductEname() == null ? "" : p.getProductEname(), Field.Store.YES));
            document.add(new StringField("category_id", p.getCategoryId() == null ? "" : p.getCategoryId(), Field.Store.NO));//不需要展示全部

            String[] noScoreWords = p.getNoScoreWords();
            if(noScoreWords != null && noScoreWords.length>0){
                for (int i = 0; i < noScoreWords.length; i++) {
                    document.add(new StringField("no_score_words", noScoreWords[i], Field.Store.NO));
                }
            }
            documents.add(document);
        }
    }

    private void init(List<ProductIndexable> productIndexables){
        buildDocuments(productIndexables);
    }

}
