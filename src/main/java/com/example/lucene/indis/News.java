package com.example.lucene.indis;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class News {
    private int id;
    private String title;
    private String content;

    private int replay;

    public News() {
    }
    public News(int id, String title, String content, int replay) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.replay = replay;
    }
}
