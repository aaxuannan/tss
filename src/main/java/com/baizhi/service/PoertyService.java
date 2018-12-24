package com.baizhi.service;

import com.baizhi.entity.Poetry;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface PoertyService {

    public List<Poetry> queryAll();

    public void foundIndex()throws IOException;

    public List<Poetry> foundSearch(String context, String aut,int page,int size) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException, InvalidTokenOffsetsException, Exception;
}
