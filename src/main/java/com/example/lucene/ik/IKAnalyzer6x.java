package com.example.lucene.ik;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

public class IKAnalyzer6x extends Analyzer {
    private boolean userSmart;

    public boolean useSmart() {
        return userSmart;
    }

    public void setUserSmart(boolean userSmart) {
        this.userSmart = userSmart;
    }

    public IKAnalyzer6x() {
        //IK分词器Lucene Analyzer接口的实现类,默认细粒度切分算法
        this(true);
    }

    public IKAnalyzer6x(boolean userSmart) {
        super();
        this.userSmart = userSmart;
    }

    @Override
    protected TokenStreamComponents createComponents(String s) {
        Tokenizer tokenizer = new IKTokenizer6x(this.useSmart());
        return new TokenStreamComponents(tokenizer);
    }
}
