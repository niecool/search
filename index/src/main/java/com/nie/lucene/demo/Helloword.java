package com.nie.lucene.demo;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableFieldType;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Helloword {

    public static void main(String[] args) {
        try {
            Directory dir = FSDirectory.open(Paths.get("d:\\testLucene\\"));
            IndexWriterConfig config = new IndexWriterConfig();
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            IndexWriter writer = new IndexWriter(dir, config);

            Field field1 = new StringField("fileName","HELLO word world word haha.", Field.Store.YES );
            Document document = new Document();
            document.add(field1);
            writer.addDocument(document);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
