package com.nie.lucene;

import org.apache.lucene.index.SegmentInfos;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;


/**
 * @author zhaochengye
 * @date 2019-05-09 00:13
 */
public class SegmentsTest {

    public static void main(String[] args) throws IOException {
        Directory dir = FSDirectory.open(Paths.get("/Users/zhaochengye/Documents/myTest"));
        SegmentInfos segmentInfos =  SegmentInfos.readLatestCommit(dir);
        System.out.println(segmentInfos.counter);
    }
}
