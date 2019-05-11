package com.nie.lucene.product.search;

import com.nie.lucene.product.NRTIndexSearchFactory;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;

import java.io.IOException;


public class ProductSearcher {
    private Logger log = Logger.getLogger(ProductSearcher.class);
    private IndexSearcher indexSearcher;

    public void searchIndex(){
//        indexSearcher = new IndexSearcherFactory().getIndexSearcher();
        NRTIndexSearchFactory factory =  new NRTIndexSearchFactory();
        indexSearcher = factory.getIndexSearcher();
        try {
            TopDocs topDocs = indexSearcher.search(buildQuery(),50);
            System.out.println(topDocs.totalHits);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            for (ScoreDoc scoreDoc : scoreDocs) {
                Document doc = indexSearcher.doc(scoreDoc.doc);
                log.info("doc信息：" + "docId=" + scoreDoc.doc + "id=" + doc.get("id") + "productCname="
                        + doc.get("product_cname") + "productEname=" + doc.get("product_ename") + "productDescription="
                        + doc.get("product_description"));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                factory.getSearcherManager().release(indexSearcher);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Query buildQuery(){
        Term term = new Term("id","9063662");
        Query query = new TermQuery(term);
        return query;
    }
}
