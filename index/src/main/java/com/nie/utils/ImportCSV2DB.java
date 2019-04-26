package com.nie.utils;

import com.nie.DaoManager.KeywordDictDaoManager;
import com.nie.model.pojo.KeywordDict;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-26 15:29
 */
@Component
public class ImportCSV2DB {

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

    public void insertAll(){
        File file = new File("/Users/zhaochengye/Documents/gitProject2/search/segments/src/sql/searchUTF8.txt");
        List<KeywordDict> list = ImportCSV2DB.importCsv(file);
        if(CollectionUtils.isEmpty(list)) return;
        if(list.size()>100){
            for (int i = 0; i < list.size() / 100; i++) {
                keywordDictDaoManager.batchInsert(list.subList(i*100,(i+1)*100 > list.size() ? list.size() : (i+1)*100));
            }
        }else{
             keywordDictDaoManager.batchInsert(list);
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
            e.printStackTrace();
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
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
