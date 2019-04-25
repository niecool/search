package com.nie.lucene.demo;

import com.nie.lucene.product.buildIndex.IndexWriterFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;

import java.io.IOException;

public class Helloword {

    public static void main(String[] args) {
        try {
            IndexWriter writer = new IndexWriterFactory().getSingletonIndexWriter();

            Field field1 = new StringField("fileName","HELLO word world word haha.", Field.Store.YES );
            Field field2 = new StringField("fileContent","Directory dir = FSDirectory.open(Paths.get", Field.Store.NO);
            Document document = new Document();
            document.add(field1);
            document.add(field2);
            writer.addDocument(document);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
