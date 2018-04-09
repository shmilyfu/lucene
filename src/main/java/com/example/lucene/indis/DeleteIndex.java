package com.example.lucene.indis;

import com.example.lucene.ik.IKAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteIndex {

    public static void main(String[] args) throws IOException {
        //删除title中含有关键字"美国"的文档
        deleteDoc("title", "美国");
    }

    public static void deleteDoc(String field, String key) throws IOException {
        Analyzer analyzer = new IKAnalyzer6x();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        Path indexdir = Paths.get("index");
        Directory directory;
        directory = FSDirectory.open(indexdir);
        System.out.println(field+key);
        IndexWriter indexWriter = new IndexWriter(directory, iwc);
        indexWriter.deleteDocuments(new Term(field, key));
        indexWriter.commit();
        indexWriter.close();
        System.out.println("删除完成");
    }
}
