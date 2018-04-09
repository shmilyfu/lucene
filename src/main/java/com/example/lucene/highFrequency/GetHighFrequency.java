package com.example.lucene.highFrequency;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class GetHighFrequency {
    public static void main(String[] args) throws IOException {
        Directory directory = FSDirectory.open(Paths.get("index"));
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        //通过getTermVector获取content字段的频率
        Terms terms = directoryReader.getTermVector(0, "content");
        TermsEnum termsEnum = terms.iterator();
        Map<String, Integer> map = new HashMap<>();
        BytesRef thisTerms;
        while ((thisTerms = termsEnum.next()) != null) {
            //词项
            String termString = thisTerms.utf8ToString();
            //通过totalTermFreq()方法获取词项频率
            map.put(termString, (int) termsEnum.totalTermFreq());
        }
        //按value排序
        List<Map.Entry<String, Integer>> sortMap = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(sortMap, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        getTopN(sortMap, 10);
    }

    public static void getTopN(List<Map.Entry<String, Integer>> sortMap, int N) {
        for (int i = 0; i < N; i++) {
            System.out.println(sortMap.get(i).getKey() + ":" + sortMap.get(i).getValue());
        }

    }
}
