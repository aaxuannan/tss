package com.baizhi.controller;


import com.baizhi.entity.Poetry;
import com.baizhi.service.PoertyService;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tss")
public class PoertyCpntroller {

    @Autowired
    private PoertyService poertyService;

    @RequestMapping("/sen")
    public String senrch(int page,int size,String context, String aut, HttpServletRequest http) throws Exception {
        System.out.println(context+"  "+aut);
        ArrayList<ScoreDoc> score = new ArrayList<ScoreDoc>();
        List<Poetry> poetries = poertyService.foundSearch(context,aut,page,size);
        http.setAttribute("scoreDocs",poetries);
        return "showAll";
    }


}
