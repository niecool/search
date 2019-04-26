package com.nie.lucene.product.search;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.StandardDirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;


public class IndexSearcherFactory {
    private Logger log = Logger.getLogger(IndexSearcherFactory.class);
//    private IndexReader indexReader;
    private IndexSearcher indexSearcher;

    private IndexReader getIndexReader() {
        IndexReader indexReader = null;
        try {
           Directory dir = FSDirectory.open(Paths.get("D:\\testLucene"));
           indexReader = StandardDirectoryReader.open(dir);
        } catch (IOException e) {
            log.error(e);
        }
        return indexReader;
    }

    public IndexSearcher getIndexSearcher(){
        IndexReader indexReader = getIndexReader();
        indexSearcher = new IndexSearcher(indexReader);
        return indexSearcher;
    }
}
