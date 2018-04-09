package com.example.lucene.ik;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;

public class IKTokenizer6x extends Tokenizer {
    //IK分词器实现
    private IKSegmenter ikSegmenter;
    //词元文本属性
    private final CharTermAttribute charTermAttribute;
    //词元位移属性
    private final OffsetAttribute offsetAttribute;
    //词元分类属性
    private final TypeAttribute typeAttribute;

    //最后一个词元的结束位置
    private int endPosition;

    public IKTokenizer6x(boolean useSmart) {
        super();
        offsetAttribute = addAttribute(OffsetAttribute.class);
        charTermAttribute = addAttribute(CharTermAttribute.class);
        typeAttribute = addAttribute(TypeAttribute.class);
        ikSegmenter = new IKSegmenter(input, useSmart);
    }

    @Override
    public boolean incrementToken() throws IOException {
        //清除所有词元属性
        clearAttributes();
        Lexeme nextLexeme = ikSegmenter.next();
        //将lexeme转成attr
        if (nextLexeme != null) {
            //设置词元文本
            charTermAttribute.append(nextLexeme.getLexemeText());
            //设置词元长度
            charTermAttribute.setLength(nextLexeme.getLength());
            //设置词元位移
            offsetAttribute.setOffset(nextLexeme.getBeginPosition(), nextLexeme.getEndPosition());
            //记录分词的最后位置
            endPosition = nextLexeme.getEndPosition();
            //记录词元分类
            typeAttribute.setType(nextLexeme.getLexemeText());
            return true;
        }
        return false;
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        ikSegmenter.reset(input);
    }

    @Override
    public void end() throws IOException {
        int finalOffset = correctOffset(this.endPosition);
        offsetAttribute.setOffset(finalOffset, finalOffset);
    }
}
