package com.example.lucene.indis;

import com.example.lucene.ik.IKAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UpdateIndex {
    public static void main(String[] args) throws IOException {
        Analyzer analyzer = new IKAnalyzer6x(true);
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        Path indexPath = Paths.get("index");
        Directory directory;
        directory = FSDirectory.open(indexPath);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        Document document = new Document();
        document.add(new TextField("id", "4", Field.Store.YES));
        document.add(new TextField("title", "北京大学开学迎来4380名新生", Field.Store.YES));
        document.add(new TextField("content", "昨天,北京大学迎来4380名来自全国各地及数十个国家的本科新生", Field.Store.YES));
        indexWriter.updateDocument(new Term("title", "证实"), document);
        indexWriter.commit();
        indexWriter.close();
    }
}
