package com.xiamu.goods.controller;

import com.xiamu.goods.service.GoodsHtmlService;
import com.xiamu.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("item")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsHtmlService goodsHtmlService;
    @GetMapping("{id}.html")
    public String toItemPage(Model model, @PathVariable("id")Long id){
        Map<String, Object> map = this.goodsService.loadData(id);
        model.addAllAttributes(map);
        this.goodsHtmlService.createHtml(id);
        return "item";
    }
}
