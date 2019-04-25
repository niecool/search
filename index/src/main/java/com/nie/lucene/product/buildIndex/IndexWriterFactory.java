package com.nie.lucene.product.buildIndex;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author zhaochengye
 * @date 2019-04-25 14:58
 */
public class IndexWriterFactory {

    public static class InnerClass{
        private static final IndexWriter instance;

        static {
            IndexWriter instance1 = null;
            try {
                Directory dir = FSDirectory.open(Paths.get("/Users/zhaochengye/Documents/myTest"));
                IndexWriterConfig config = new IndexWriterConfig();
                config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
                instance1 = new IndexWriter(dir,config);
            } catch (IOException e) {
                e.printStackTrace();
            }
            instance = instance1;
        }

    }

    public static IndexWriter getSingletonIndexWriter(){
        return InnerClass.instance;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    IndexWriterFactory indexWriterFactory = new IndexWriterFactory();
                    IndexWriter indexWriter = indexWriterFactory.getSingletonIndexWriter();
                    System.out.println(indexWriter);
                    try {
                        indexWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

}
