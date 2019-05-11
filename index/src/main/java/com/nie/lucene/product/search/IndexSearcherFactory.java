package com.nie.lucene.product.search;

import com.nie.CustomizedPropertyPlaceholderConfigurer;
import com.nie.lucene.product.buildIndex.IndexWriterFactory;
import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.StandardDirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class IndexSearcherFactory {
    private static Logger log = Logger.getLogger(IndexSearcherFactory.class);
//    private IndexReader indexReader;
    private IndexSearcher indexSearcher;

    static class InnerClass{
        private static IndexReader getIndexReader() {
            IndexReader indexReader = null;
            try {
           String pathStr = (String)CustomizedPropertyPlaceholderConfigurer.getContextProperty("lucene.path.tencloud");
           Path path = Paths.get(pathStr);
//                Path path = Paths.get("/Users/zhaochengye/Documents/myTest");
                try {
                    if(!Files.exists(path)){
                        Files.createDirectory(path);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Directory dir = FSDirectory.open(path);

                indexReader = StandardDirectoryReader.open(dir);
            } catch (IOException e) {
                log.error(e);
            }
            return indexReader;
        }
    }




    public IndexSearcher getIndexSearcher(){
        IndexReader indexReader = InnerClass.getIndexReader();
        indexSearcher = new IndexSearcher(indexReader);
        return indexSearcher;
    }

    public static void main(String[] args) {
        try {
            if(!Files.exists(Paths.get("/tmp/luceneTest"))){
                Files.createFile(Paths.get("/tmp/luceneTest"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
