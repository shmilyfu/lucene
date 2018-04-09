package com.example.lucene.indis;

import com.example.lucene.ik.IKAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class CreateIndex {
    public static void main(String[] args) throws Exception {
        //创建三个新闻对象
        News news1 = new News();
        news1.setId(1);
        news1.setTitle("习近平举行仪式欢迎津巴布韦总统访华并同其举行会谈");
        news1.setContent("国家主席习近平3日下午在人民大会堂北大厅举行仪式欢迎津巴布韦总统埃默森姆南加古瓦对我国进行国事访问");
        news1.setReplay(246);
        News news2 = new News();
        news2.setId(2);
        news2.setTitle("美国发布建议征收中国产品关税清单");
        news2.setContent("华盛顿特区美国贸易代表办公室今天公布了一份从中国进口的产品清单这些清单可能是来自中国的产品这是美国对中国与美国技术和知识产权被迫转让有关的不公平贸易行为做出的回应的一部分受附加关税影响");
        news2.setReplay(356);
        News news3 = new News();
        news3.setId(3);
        news3.setTitle("配钥匙行业曝出大bug已证实");
        news3.setContent("对此成都商报记者向成都市锁具行业协会会长 刘至祥进行了求证");
        news3.setReplay(677);
        //创建IK分词器
        Analyzer analyzer = new IKAnalyzer6x(true);
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        Directory directory = null;
        IndexWriter indexWriter = null;
        //索引目录
        Path indexPath = Paths.get("index");

        //开始时间
        Date start = new Date();
        if (!Files.isReadable(indexPath)) {
            System.out.println("Document directory '" + indexPath.toAbsolutePath() + "'does not exist or is not readable,please check the path");
            System.exit(1);
        }
        directory = FSDirectory.open(indexPath);
        indexWriter = new IndexWriter(directory, iwc);
        //设置新闻id索引并存储
        FieldType idType = new FieldType();
        idType.setIndexOptions(IndexOptions.DOCS);
        idType.setStored(true);
        //设置新闻标题索引文档 词项频率 位移信息和偏移量 存储并词条化
        FieldType titleType = new FieldType();
        titleType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        titleType.setStored(true);
        titleType.setTokenized(true);
        //设置新闻内容
        FieldType contentType = new FieldType();
        contentType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        contentType.setStored(true);
        contentType.setTokenized(true);
        contentType.setStoreTermVectors(true);
        contentType.setStoreTermVectorPositions(true);
        contentType.setStoreTermVectorOffsets(true);
        contentType.setStoreTermVectorPayloads(true);

        Document doc1 = new Document();
        doc1.add(new Field("id", String.valueOf(news1.getId()), idType));
        doc1.add(new Field("title", news1.getTitle(), titleType));
        doc1.add(new Field("content", news1.getContent(), contentType));
        doc1.add(new IntPoint("replay", news1.getReplay()));
        doc1.add(new StoredField("replay_display", news1.getReplay()));

        Document doc2 = new Document();
        doc2.add(new Field("id", String.valueOf(news2.getId()), idType));
        doc2.add(new Field("title", news2.getTitle(), titleType));
        doc2.add(new Field("content", news2.getContent(), contentType));
        doc2.add(new IntPoint("replay", news2.getReplay()));
        doc2.add(new StoredField("replay_display", news2.getReplay()));

        Document doc3 = new Document();
        doc3.add(new Field("id", String.valueOf(news3.getId()), idType));
        doc3.add(new Field("title", news3.getTitle(), titleType));
        doc3.add(new Field("content", news3.getContent(), contentType));
        doc3.add(new IntPoint("replay", news3.getReplay()));
        doc3.add(new StoredField("replay_display", news3.getReplay()));

        indexWriter.addDocument(doc1);
        indexWriter.addDocument(doc2);
        indexWriter.addDocument(doc3);
        indexWriter.commit();
        indexWriter.close();
        Date end = new Date();
        System.out.println("索引文档用时" + (end.getTime() - start.getTime()) + "毫秒");


    }
}
