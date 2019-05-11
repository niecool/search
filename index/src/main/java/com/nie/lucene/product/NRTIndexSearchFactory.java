package com.nie.lucene.product;

import com.nie.CustomizedPropertyPlaceholderConfigurer;
import com.nie.lucene.product.buildIndex.IndexWriterFactory;
import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.ControlledRealTimeReopenThread;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NRTCachingDirectory;
import org.elasticsearch.index.Index;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zhaochengye
 * @date 2019-05-11 00:43
 */
public class NRTIndexSearchFactory {
    private Logger LOG = Logger.getLogger(NRTIndexSearchFactory.class);
    private IndexWriter indexWriter;
    private IndexSearcher indexSearcher;
    private long token;//每次writer操作的时候需要设置token值。
    private SearcherManager searcherManager;
    private ControlledRealTimeReopenThread<IndexSearcher> CRTReopenThread;



    public NRTIndexSearchFactory(){
        init();
    }

    public void init(){
        indexWriter = getSingletonIndexWriter();//先创建writer，避免获取reader报错。
        try {
            searcherManager = new SearcherManager(indexWriter, new SearcherFactory());
            CRTReopenThread = new ControlledRealTimeReopenThread<>(indexWriter,searcherManager,5.0,0.25);
            CRTReopenThread.setDaemon(true);
            CRTReopenThread.setName("NrtManger Reopen Thread");
            CRTReopenThread.start();//启动线程NRTManager的reopen线程
        } catch (IOException e) {
            LOG.error(e);
        }
    }

    public IndexWriter getIndexWriter(){
        return getSingletonIndexWriter();
    }
    public IndexSearcher getIndexSearcher(){
        try {
            CRTReopenThread.waitForGeneration(token);
            indexSearcher = searcherManager.acquire();
        } catch (Exception e) {
            LOG.error(e);
        }
        return indexSearcher;
    }


    public void doSearch(){
        //子类实现
    }


    public static class InnerClass{
        private static final IndexWriter instance;

        static {
            IndexWriter instance1 = null;
            try {
                String pathStr = (String) CustomizedPropertyPlaceholderConfigurer.getContextProperty("lucene.path.tencloud");
                Path path = Paths.get(pathStr);
                Directory dir = FSDirectory.open(path);
                IndexWriterConfig config = new IndexWriterConfig();
                config.setUseCompoundFile(false);
                config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
                instance1 = new IndexWriter(dir,config);
            } catch (IOException e) {
                e.printStackTrace();
            }
            instance = instance1;
        }

    }

    public static IndexWriter getSingletonIndexWriter(){
        return NRTIndexSearchFactory.InnerClass.instance;
    }

    public void setToken(long token) {
        this.token = token;
    }

    public SearcherManager getSearcherManager() {
        return searcherManager;
    }
}
