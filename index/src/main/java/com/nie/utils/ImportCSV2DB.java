package com.nie.utils;

import com.nie.DaoManager.KeywordDictDaoManager;
import com.nie.model.pojo.KeywordDict;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author zhaochengye
 * @date 2019-04-26 15:29
 */
@Component
public class ImportCSV2DB {

    private static Logger LOG = Logger.getLogger(ImportCSV2DB.class);
    @Resource
    private KeywordDictDaoManager keywordDictDaoManager;

    /**
     *
     */
    public void insertOneRecord(){
        File file = new File("/Users/zhaochengye/Documents/gitProject2/search/segments/src/sql/searchUTF8.txt");
        List<KeywordDict> list = ImportCSV2DB.importCsv(file);
        KeywordDict keywordDict = list.get(0);
        int a = keywordDictDaoManager.insert(keywordDict);
    }

    /**
     *
     */
    public void testStream(){
        File file = new File("/Users/zhaochengye/Documents/gitProject2/search/segments/src/sql/searchUTF8.txt");
        List<KeywordDict> list = ImportCSV2DB.importCsv(file);
        long start = System.currentTimeMillis();
        System.out.println(start);


        System.out.println(Runtime.getRuntime().freeMemory());
        list.addAll(list);
        list.addAll(list);
        list.addAll(list);
        list.addAll(list);
        list.addAll(list);
        list.addAll(list);
        list.addAll(list);
        list.addAll(list);
        System.out.println(list.size());
        long process = System.currentTimeMillis();
        System.out.println(process - start);
        List<Future> futures = new ArrayList<>();
//        if(list.size()>100000){
//            ExecutorService service = Executors.newFixedThreadPool(8);
//            for (int i = 0; i < list.size()/100000 + 1; i++) {
//                final int l = i;
//                Future<Boolean> future = service.submit(new Callable<Boolean>() {
//                    @Override
//                    public Boolean call() {
//                        System.out.println("========================================================================"+l);
//                        try {
//                           haha(list.subList(l * 100000, (l + 1) * 100000 > list.size() ? list.size() : (l + 1) * 100000));
//                           return true;
//                        }catch (Exception e){
//                            LOG.error(e);;
//                            return null;
////                            System.exit(0);//错误就终止，不能漏掉
//                        }
//                    }
//                });
//                futures.add(future);
//            }
//        }else{
            haha(list);
//        }


for (Future<Boolean> future: futures){
    try {
        Boolean su = future.get();

    } catch (InterruptedException e) {
        LOG.error(e);;
    } catch (ExecutionException e) {
        LOG.error(e);;
    }
}




        System.out.println(Runtime.getRuntime().freeMemory());
        System.out.println(System.currentTimeMillis()-process+"==============+++++++++++++++++++++===============");
        System.out.println(list.size());
    }

    /**
     *
     * asd  asdnie
     *
     * ....
     *
     * asd asdnie + nie
     * asd asdnienie
     *
     */
    public void haha(List<KeywordDict> list){
//        for (int i = 0; i < list.size(); i++) {
//            KeywordDict keywordDicts = list.get(i);
//            keywordDicts.setKeyword(keywordDicts.getKeyword()+keywordDicts.getLastUpdateName());
////            if(i%100000==0){
////                System.out.println(i);
////            }
//        }
//        list = list.subList(0,1);
        list.parallelStream().map(x -> x.combine()).collect(Collectors.toList());
//        list.stream().forEach(KeywordDict);
    }

    public void insertAll(){
        File file = new File("/Users/zhaochengye/Documents/gitProject2/search/segments/src/sql/searchUTF8.txt");
        List<KeywordDict> list = ImportCSV2DB.importCsv(file);
        if(CollectionUtils.isEmpty(list)) return;
        System.out.println(list.size()+"zcy");
        ExecutorService pool = Executors.newFixedThreadPool(16);
        if(list.size()>1000){
            for (int i = 0; i < list.size()/1000 + 1; i++) {
                final int l = i;
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("========================================================================"+l);
                        try {
                            keywordDictDaoManager.batchInsert(list.subList(l * 1000, (l + 1) * 1000 > list.size() ? list.size() : (l + 1) * 1000));
                        }catch (Exception e){
                            LOG.error(e);;
                            System.exit(0);//错误就终止，不能漏掉
                        }
                    }
                });
            }
        }else{
             keywordDictDaoManager.batchInsert(list);
        }

    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 123; i++) {
            list.add(i);
        }
        for (int i = 0; i < list.size()/10+1; i++) {
            List<Integer> list1 = list.subList(i * 10, (i + 1) * 10 > list.size() ? list.size() : (i + 1) * 10);
            System.out.println(list1.size());
        }
    }

    public static List<KeywordDict> importCsv(File file){
        List<KeywordDict> dataList=new ArrayList<KeywordDict>();

        BufferedReader br=null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = "";
            while ((line = br.readLine()) != null) {
                try {
                    KeywordDict k = buildKeywordDict(line);
                    dataList.add(k);
                }catch (Exception e){
                    //继续添加
                }

            }
        }catch (Exception e) {
            LOG.error(e);;
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    LOG.error(e);;
                }
            }
        }

        return dataList;
    }

    /**
     *
     */
    /**
     *
     */
    public static KeywordDict buildKeywordDict(String line) throws Exception{
        KeywordDict k = new KeywordDict();
        String[] arr = line.split("\t");
        k.setKeyword(arr[0].equals("null")?null : arr[0]);
        k.setSynonym(arr[1].equals("null")?null : arr[1]);
        k.setExtented(arr[2].equals("null")?null : arr[2]);
        k.setType(arr[3].equals("null")?null : Integer.parseInt(arr[3].trim()));

        k.setLastUpdateId(new BigDecimal(1));
        k.setLastUpdateName("nie");
        k.setLastUpdateTime(new Date());
        k.setIsCheck(1);
        k.setDataFrom("5");//数据来源，1:淘宝导入，2:一号店导入，3:用户录入，4.用户导入,5.初次倒入
        return k;
    }


}
