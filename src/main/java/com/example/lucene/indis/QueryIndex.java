package com.example.lucene.indis;

import com.example.lucene.ik.IKAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class QueryIndex {
    public static void main(String[] args) throws Exception {
        String field = "title";
        Path path = Paths.get("index");
        Directory directory = FSDirectory.open(path);
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
        Analyzer analyzer = new IKAnalyzer6x();
        QueryParser queryParser = new QueryParser(field, analyzer);
        queryParser.setDefaultOperator(QueryParser.Operator.AND);
        Query query = queryParser.parse("津巴");
        System.out.println("Query:"+query.toString());
        //取前10条
        TopDocs topDocs = indexSearcher.search(query, 10);
        for (ScoreDoc sd : topDocs.scoreDocs){
            Document doc = indexSearcher.doc(sd.doc);
            System.out.println("DocumentId:"+sd.doc);
            System.out.println("id:"+doc.get("id"));
            System.out.println("title:"+doc.get("title"));
            System.out.println("文档评分:"+sd.score);
        }
        directory.close();
        directoryReader.close();


    }
}
