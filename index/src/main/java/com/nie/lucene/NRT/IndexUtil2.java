package com.nie.lucene.NRT;

/**
 * @author zhaochengye
 * @date 2019-05-07 21:47
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

public class IndexUtil2 {
    SearcherManager searcherManager;
    ControlledRealTimeReopenThread<IndexSearcher> CRTReopenThread;
    private long _reopenToken;      // index update/delete methods returned token
    private String[] ids = {"1", "2", "3", "4", "5", "6"};
    //下面是邮件
    private String[] emails = {"aa@qq.com", "bb@sina.edu", "cc@yahu.org", "ss@sina.com", "dd@gmail.com", "ee@163.com"};
    //下面是邮件内容
    private String[] content = {
            "welcom to visited the space,I like football",
            "hello boy, i like someone",
            "come on baby like",
            "first blood like",
            "I like football,I like football",
            "my girlfriend is so beatiful, every body like game"
    };
    private int[] attaches = {2,5,6,5,8,4};//附件数量
    //发件人名字
    private String[] names = {"Tom", "Jack", "goudan", "alibaba", "jerry", "kitty"};
    //邮件的日期
    private Date[] dates = null;

    private Directory directory = null;
    private Map<String, Float> scores = new HashMap<String, Float>();//新建一个Map，用来存储权值
    /*private static IndexReader reader = null;//声明一个IndexReader的属性*/
    private SearcherManager mgr = null;
//    private NRTManager nrtMgr = null;
    private IndexWriter writer;

    public IndexUtil2() {
        try {
            setDates();//设置日期
            scores.put("qq.com", 2.0f);//如果是"qq.com"结尾的索引则让其权值为2.0，注意：默认是1.0
            scores.put("sina.edu", 1.5f);
            directory = FSDirectory.open(Paths.get("/Users/zhaochengye/Documents/myTest/test"));
            writer = new IndexWriter(directory, new IndexWriterConfig(new StandardAnalyzer()));
            searcherManager = new SearcherManager(writer, new SearcherFactory());
            CRTReopenThread = new ControlledRealTimeReopenThread<>(
                    writer,searcherManager,5.0,1);//重新打开的时候间隔最大为5.0，最小为0.025，0.025为25秒

//            NRTManagerReopenThread reopen = new NRTManagerReopenThread(nrtMgr, 5.0, 0.025);
            CRTReopenThread.setDaemon(true);
            CRTReopenThread.setName("NrtManger Reopen Thread");
            CRTReopenThread.start();//启动线程NRTManager的reopen线程
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //创建索引
    public void index(){
        try {
//            writer = new IndexWriter(directory, new IndexWriterConfig(new StandardAnalyzer()));
            //此方法可将索引全部清空
            writer.deleteAll();
            Document document = null;
            for(int i = 0; i < ids.length; i++){
                document = new Document();
                //id需要存储，不需要加权、分词，email也需要存储，但不需要分词，有时候也需要加权
                //对于内容，我们不需要存储和加权，但需要分词。而名字需要存储，不需要分词和加权
                //这里我们先不对整型数据进行索引，后面再说
                document.add(new StringField("id", ids[i], Field.Store.YES));
                document.add(new StringField("email", emails[i], Field.Store.YES));
                document.add(new TextField("content", content[i], Field.Store.YES));
                document.add(new StringField("name", names[i], Field.Store.YES));

                //为数字添加索引，第三个参数设置为true表示默认索引
                document.add(new LongPoint("attach", attaches[i]));
                //为日期添加索引
                document.add(new LongPoint("date", dates[i].getTime()));


                String et = emails[i].substring(emails[i].lastIndexOf("@") + 1);
                System.out.println(et);
                //加入权值
//                if(scores.containsKey(et)){
//                    document.setBoost(scores.get(et));
//                }else{
//                    document.setBoost(0.5f);
//                }
                writer.addDocument(document);
            }
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (LockObtainFailedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
//            if(writer != null){
//                try {
//                    writer.close();
//                } catch (CorruptIndexException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

    //查询
    public void query(){
        try {
            IndexReader reader = StandardDirectoryReader.open(directory);
            //maxDoc 和 numDocs()方法的区别：maxDoc（）返回索引中删除和未被删除的文档总数，
            //后者返回索引中未被删除的文档总数，通过reader可以有效获取文档的数量
            System.out.println("numDocs: " + reader.numDocs());
            System.out.println("maxDocs: " + reader.maxDoc());
            //查看被删除的索引
            System.out.println("deleteDocs : " + reader.numDeletedDocs());
            //记得用完之后关闭
            reader.close();

        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //删除索引
    public void delete(){
        IndexWriter writer = null;
        try {
            writer.deleteDocuments(new Term("id", "1"));//这里表示删除索引为1的id
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (LockObtainFailedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //更新索引
    public void update(){
        try {
            /* lucene并没有提供更新，这里的更新操作其实是如下两个操作的合集
             * 先删除之后再添加，所以不是在之前的位置更新
             * 测试之后我们会发现回收站中有一个索引
             * */
            Document document = new Document();
            document.add(new StringField("id", "11", Field.Store.YES));
            document.add(new StringField("email", emails[0], Field.Store.YES));
            document.add(new TextField("content", content[0], Field.Store.YES));
            document.add(new StringField("name", names[0], Field.Store.YES));
            _reopenToken = writer.updateDocument(new Term("id", "1"), document);
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (LockObtainFailedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search01(){
        IndexReader reader;
        try {
            reader = StandardDirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            TermQuery query = new TermQuery(new Term("content", "like"));//搜索内容中含有like的
            TopDocs tds = searcher.search(query, 10);
            for(ScoreDoc sd : tds.scoreDocs){
                Document doc = searcher.doc(sd.doc);
                //这里我们获取权值getBoost()的时候发现都是1.0，这是因为这里是获取的一个document，和原来的没有关系。
                //要想看其权值信息，可以使用luke工具
                //而这里的日期需要我们转换成日期格式
                System.out.println("(" + sd.doc + "权值："+ 1 + ")" + doc.get("name") + "[" + doc.get("email") + "]-->"
                        + doc.get("id") + "-->" + doc.get("attach") + "-->" + doc.get("date"));
            }
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //设置日期
    private void setDates(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dates = new Date[ids.length];
            dates[0] = sdf.parse("2015-02-15");
            dates[1] = sdf.parse("2015-03-01");
            dates[2] = sdf.parse("2015-05-18");
            dates[3] = sdf.parse("2015-09-05");
            dates[4] = sdf.parse("2015-12-15");
            dates[5] = sdf.parse("2015-08-29");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void search02(){
        IndexSearcher searcher = null;
        try {
//            mgr.maybeReopen();//判断是否需要重新打开一个IndexSearcher
            try {
                CRTReopenThread.waitForGeneration(_reopenToken);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            searcher = searcherManager.acquire();
//            searcher = mgr.acquire();
            TermQuery query = new TermQuery(new Term("content", "like"));//搜索内容中含有like的
            TopDocs tds = searcher.search(query, 10);
            for(ScoreDoc sd : tds.scoreDocs){
                Document doc = searcher.doc(sd.doc);
                System.out.println(doc.get("id") + "-->" + doc.get("name") + "[" + doc.get("email") + "]-->"
                        + doc.get("attach") + "-->" + doc.get("date"));
            }
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                searcherManager.release(searcher);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void commit(){
        try {
            writer.commit();
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        IndexUtil2 util = new IndexUtil2();
        util.index();
        System.out.println("=============");
        util.search01();
        System.out.println("======111=======");
        util.search02();
        System.out.println("=======222======");
        util.update();
        util.search01();
        System.out.println("=======333======");
        util.search02();
    }
}
