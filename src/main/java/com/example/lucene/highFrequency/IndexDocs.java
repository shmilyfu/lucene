package com.example.lucene.highFrequency;

import com.example.lucene.ik.IKAnalyzer6x;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

public class IndexDocs {
    public static void main(String[] args) throws Exception {
        File file = new File("testfile/news.txt");
        String text = textToString(file);
        System.out.println(text);
        IKAnalyzer6x ikAnalyzer = new IKAnalyzer6x();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(ikAnalyzer);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        //索引的存储位置
        Directory directory = null;
        //索引的增删改由indexWriter 创建
        IndexWriter indexWriter = null;
        directory = FSDirectory.open(Paths.get("index"));
        indexWriter = new IndexWriter(directory, indexWriterConfig);
        //新建fieldType 用于指定字段索引的信息
        FieldType type = new FieldType();
        //索引时保存文档,词项频率,位置信息,偏移信息
        type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        //原始字符串全部保存在索引中
        type.setStored(true);
        //存储词项量
        type.setStoreTermVectors(true);
        //词条化
        type.setTokenized(true);
        Document document = new Document();
        Field field = new Field("content", text, type);
        document.add(field);
        indexWriter.addDocument(document);
        //提交
        indexWriter.commit();
        //关闭资源操作
        indexWriter.close();
        directory.close();

    }

    public static String textToString(File file) throws Exception {
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            result.append(System.lineSeparator() + str);
        }
        bufferedReader.close();
        return result.toString();
    }
}
