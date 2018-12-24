package com.baizhi;

import com.baizhi.dao.PoetryDAO;
import com.baizhi.entity.Poetry;
import com.baizhi.service.PoertyService;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TssApplicationTests {

    @Autowired
    private PoetryDAO poetryDAO;

    @Autowired
    private PoertyService poertyService;

    @Test
    public void contextLoads() throws IOException, ParseException, InvalidTokenOffsetsException {
        FSDirectory fsDirectory = FSDirectory.open(Paths.get("D:\\index\\08"));
        IndexReader indexReader = DirectoryReader.open(fsDirectory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        // 查询解析器对象  作用 解析查询表达式  域名:条件
        // 参数一: 域名(默认域)
        QueryParser queryParser = new QueryParser("content", new IKAnalyzer());

        Query query = null;

        query = queryParser.parse("床前明月光");
// 声明请求参数信息
        //当前页数
        int nowPage = 2;
        //每页显示数据
        int pageSize = 5;

        // 分页数据
        TopDocs topDocs = null;
        if (nowPage == 1 || nowPage < 1){
            // 假如说: 查第一页 每页2条
            topDocs = indexSearcher.search(query, pageSize);
        } else if( nowPage > 1){
            // 假如说: 不是第一页 必须先获取上一页的最后一条记录的ScoreDoc
            topDocs = indexSearcher.search(query, (nowPage-1) * pageSize);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            ScoreDoc sd = scoreDocs[scoreDocs.length-1];
            // 参数一: 当前页的上一页的最后的文档的ScoreDoc对象
            topDocs = indexSearcher.searchAfter(sd,query,pageSize);
        }

        Scorer scorer = new QueryScorer(query);
        Formatter formatter = new SimpleHTMLFormatter("<span style=\"color:red\">","</span>");
        Highlighter highlighter = new Highlighter(formatter,scorer);


        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docID = scoreDoc.doc;
            Document document = indexReader.document(docID);

            System.out.println(document.get("id") + " | " + document.get("title") + " | "+document.get("content"));
        }
        indexReader.close();
    }

}



