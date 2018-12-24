package com.baizhi.service;


import com.baizhi.dao.PoetryDAO;
import com.baizhi.entity.Poetry;
import com.baizhi.service.PoertyService;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;
import org.testng.annotations.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class PoetryServiceImpl implements PoertyService {

    @Autowired
    private PoetryDAO poetryDAO;


    @Autowired
    private PoertyService poertyService;

    @Override
    public List<Poetry> queryAll() {
        return poetryDAO.findAll();
    }



    public List<Poetry> foundSearch(String context,String aut,int page,int size) throws Exception{
        System.out.println(context+"  "+aut);
        FSDirectory fsDirectory = FSDirectory.open(Paths.get("D:\\index\\08"));
        IndexReader indexReader = DirectoryReader.open(fsDirectory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        // 查询解析器对象  作用 解析查询表达式  域名:条件
        // 参数一: 域名(默认域)
        QueryParser queryParser = new QueryParser("content", new IKAnalyzer());

        Query query = null;

        query = queryParser.parse(context);

        // 声明请求参数信息
        //当前页数
        int nowPage = page;
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

        // 总记录数
        int count = topDocs.totalHits;

        // 创建高亮器对象
        Scorer scorer = new QueryScorer(query);
        // 默认高亮样式 加粗
        // 使用自定义的高亮样式
        Formatter formatter = new SimpleHTMLFormatter("<span style=\"color:red\">","</span>");
        Highlighter highlighter = new Highlighter(formatter,scorer);


        ArrayList<Poetry> poetries = new ArrayList<>();
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docID = scoreDoc.doc;
            Document document = indexReader.document(docID);

            String highlighterBestFragment = highlighter.getBestFragment(new StandardAnalyzer(), "content", document.get("content"));

            Poetry poetry = new Poetry();
            if(highlighterBestFragment!=null){
                poetry.setContent(highlighterBestFragment);
            }
            poetry.setAuthor(document.get("author"));
            poetry.setTitle(document.get("title"));
            poetries.add(poetry);
        }
        indexReader.close();
        return poetries;
    }

    /*
       创建索引
    */
    public void foundIndex()throws IOException {
        FSDirectory fsDirectory = FSDirectory.open(Paths.get("D:\\index\\08"));
        IndexWriter indexWriter = new IndexWriter(fsDirectory, new IndexWriterConfig(new IKAnalyzer()));
        List<Poetry> poetries = poertyService.queryAll();
        Document document = null;
        for (Poetry poetry : poetries) {
            document = new Document();
            document.add(new IntField("id", poetry.getId(), Field.Store.YES));
            document.add(new StringField("author", poetry.getPoet().getName(), Field.Store.YES));
            document.add(new TextField("title", poetry.getTitle(), Field.Store.YES));
            document.add(new TextField("content",poetry.getContent(), Field.Store.YES));
            indexWriter.addDocument(document);
        }
        indexWriter.commit();
        indexWriter.close();
    }

}

