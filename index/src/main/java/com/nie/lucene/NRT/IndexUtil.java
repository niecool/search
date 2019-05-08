package com.nie.lucene.NRT;

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

/**
 *
 * openIfChanged
 * @author zhaochengye
 * @date 2019-05-07 17:59
 */
public class IndexUtil {

    IndexReader reader;
    IndexSearcher searcher = null;
    private String[] ids = {"1", "2", "3", "4", "5", "6"};
    //下面是邮件
    private String[] emails = {"aa@qq.com", "bb@sina.edu", "cc@yahu.org", "ss@sina.com", "dd@gmail.com", "ee@163.com"};
    //下面是邮件内容
    private String[] content = {
            "welcom to visited the space,I like football",
            "hello boy, i like someone",
            "come on baby",
            "first blood",
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

    public IndexUtil() {
        try {
//            DirectoryReader reader = new StandardDirectoryReader();
            setDates();//设置日期
            scores.put("qq.com", 2.0f);//如果是"qq.com"结尾的索引则让其权值为2.0，注意：默认是1.0
            scores.put("sina.edu", 1.5f);
            directory = FSDirectory.open(Paths.get("/Users/zhaochengye/Documents/myTest/test"));
            mgr = new SearcherManager(directory,new SearcherFactory());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //创建索引
    public void index(){
        IndexWriter writer = null;
        try {
            writer = new IndexWriter(directory, new IndexWriterConfig(new StandardAnalyzer()));
            //此方法可将索引全部清空
            writer.deleteAll();
            Document document = null;
            for(int i = 0; i < ids.length; i++){
                document = new Document();
                //id需要存储，不需要加权、分词，email也需要存储，但不需要分词，有时候也需要加权
                //对于内容，我们不需要存储和加权，但需要分词。而名字需要存储，不需要分词和加权
                //这里我们先不对整型数据进行索引，后面再说
                document.add(new StringField("id", ids[i], Field.Store.YES));
                document.add(new StringField("email", emails[i], Field.Store.YES));//默认不会被分词
                document.add(new TextField("content", content[i], Field.Store.YES));//TextField默认会被分词器
                document.add(new StringField("name", names[i], Field.Store.YES));

                //为数字添加索引，第三个参数设置为true表示默认索引
                document.add(new LongPoint("attach", attaches[i]));
                //为日期添加索引
                document.add(new LongPoint("date", dates[i].getTime()));


                String et = emails[i].substring(emails[i].lastIndexOf("@") + 1);
                System.out.println(et);
//                //加入权值
//                if(scores.containsKey(et)){
//                    document.(scores.get(et));
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
            if(writer != null){
                try {
                    writer.close();
                } catch (CorruptIndexException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
            writer = new IndexWriter(directory, new IndexWriterConfig());

            //参数可以是一个选项，可以是一个query，也可以是一个term，term是一个精确查找的值
            //这里我们测试此方法之后再次执行搜索方法，发现文档数numDocs还有5个，比之前少了一个，但是maxDoc还是6个
            //在我们的索引目录中发现出现了一个delete的文件。这里的删除就像一个回收站一样，是可以恢复的
            writer.deleteDocuments(new Term("id", "1"));//这里表示删除索引为1的id
            writer.commit();
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
        IndexWriter writer = null;
        try {
            writer = new IndexWriter(directory, new IndexWriterConfig());

            /* lucene并没有提供更新，这里的更新操作其实是如下两个操作的合集
             * 先删除之后再添加，所以不是在之前的位置更新
             * 测试之后我们会发现回收站中有一个索引
             * */
            Document document = new Document();
            document.add(new StringField("id", "11", Field.Store.YES));
            document.add(new StringField("email", emails[0], Field.Store.YES));
            document.add(new TextField("content", content[0], Field.Store.NO));
            document.add(new StringField("name", names[0], Field.Store.YES));
            writer.updateDocument(new Term("id", "1"), document);
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (LockObtainFailedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(writer != null){
                try {
                    writer.close();
                } catch (CorruptIndexException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void search01(){

        try {
            if(reader == null){
                reader = StandardDirectoryReader.open(directory);
            }
            IndexSearcher searcher = new IndexSearcher(reader);
            TermQuery query = new TermQuery(new Term("content", "like"));//搜索内容中含有like的
            TopDocs tds = searcher.search(query, 10);
            for(ScoreDoc sd : tds.scoreDocs){
                Document doc = searcher.doc(sd.doc);
                //这里我们获取权值getBoost()的时候发现都是1.0，这是因为这里是获取的一个document，和原来的没有关系。
                //要想看其权值信息，可以使用luke工具
                //而这里的日期需要我们转换成日期格式
                System.out.println("(" + sd.doc + "权值："+ 1+ ")" + doc.get("name") + "[" + doc.get("email") + "]-->"
                        + doc.get("id") + "-->" + doc.get("attach") + "-->" + doc.get("date"));
            }
//            reader.close();
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

        try {
            mgr.maybeRefresh();//判断是否需要重新打开一个IndexSearcher
            searcher = mgr.acquire();
            TermQuery query = new TermQuery(new Term("content", "like"));//搜索内容中含有like的
            TopDocs tds = searcher.search(query, 10);
            for(ScoreDoc sd : tds.scoreDocs){
                Document doc = searcher.doc(sd.doc);
                System.out.println("(" + sd.doc + "权值："+ 1 + ")" + doc.get("name") + "[" + doc.get("email") + "]-->"
                        + doc.get("id") + "-->" + doc.get("attach") + "-->" + doc.get("date"));
            }
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                mgr.release(searcher);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        IndexUtil util = new IndexUtil();
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