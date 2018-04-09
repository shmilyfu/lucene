package com.example.lucene.ik;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.StringReader;

/**
 * lucene自带smartCn分词器与IK比较
 */
public class IkVsSmartCn {
    private static String str1 = "厉害了我的哥,你这么牛逼咋不上天?";
    private static String str2 = "并将其解压到你想安装的目录，并配置 Windows";
    private static String str3 = "习近平举行仪式欢迎津巴布韦总统访华并同其举行会谈";

    public static void main(String[] args) throws Exception {
        /*Analyzer analyzer = null;
//        System.out.println("句子1" + str1);
        analyzer = new SmartChineseAnalyzer();
        printAnalyzer(analyzer, str1);
        analyzer = new IKAnalyzer6x();
        printAnalyzer(analyzer, str1);
        analyzer = new SmartChineseAnalyzer();
        printAnalyzer(analyzer, str2);
        analyzer = new IKAnalyzer6x(true);
        printAnalyzer(analyzer, str2);*/
        IKAnalyzer6x ikAnalyzer6x = new IKAnalyzer6x();
        printAnalyzer(ikAnalyzer6x,str3);
         ikAnalyzer6x = new IKAnalyzer6x();
        printAnalyzer(ikAnalyzer6x,str1);
    }

    public static void printAnalyzer(Analyzer analyzer, String str) throws Exception {
        StringReader stringReader = new StringReader(str);
        TokenStream tokenStream = analyzer.tokenStream(str, stringReader);
        tokenStream.reset();
        CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            System.out.print(attribute.toString() + "|");
        }
        System.out.println();
        System.out.println("-------------------------");
    }

}
