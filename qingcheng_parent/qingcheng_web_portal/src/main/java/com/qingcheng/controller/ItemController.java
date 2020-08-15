package com.qingcheng.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qingcheng.pojo.goods.Goods;
import com.qingcheng.pojo.goods.Sku;
import com.qingcheng.pojo.goods.Spu;
import com.qingcheng.service.goods.CategoryService;
import com.qingcheng.service.goods.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Reference
    private CategoryService categoryService;

    @Reference
    private SpuService spuService;

    @Value("${pagePath}")
    private String pagePath;

    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping("/createPage")
    public void createPage(String spuId) {
        // 获取商品信息
        Goods goods = spuService.findGoodsById(spuId);
        // 获取spu信息
        Spu spu = goods.getSpu();
        // 获取sku列表
        List<Sku> skuList = goods.getSkuList();
        // 获取商品分类
        List<String> categoryList = new ArrayList<>();
        categoryList.add(categoryService.findById(spu.getCategory1Id()).getName());
        categoryList.add(categoryService.findById(spu.getCategory2Id()).getName());
        categoryList.add(categoryService.findById(spu.getCategory3Id()).getName());
        // sku地址
        Map<String, String> urlMap = new HashMap<>();
        for (Sku sku : skuList) {
            if("1".equals(sku.getStatus())) {
                // Map字段排序
                String specJson = JSON.toJSONString(JSON.parseObject(sku.getSpec()), SerializerFeature.MapSortField);
                // key:{"规格":"88片"}
                // 10000000616300.html
                urlMap.put(specJson, sku.getId() + ".html");
            }
        }

        // 批量生成sku页面
        for (Sku sku: skuList) {
            // 1、创建上下文和数据模型
            Context context = new Context();
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("spu", spu);
            dataModel.put("sku", sku);
            dataModel.put("categoryList", categoryList);
            dataModel.put("skuImages", sku.getImages().split(","));
            dataModel.put("spuImages", spu.getImages().split(","));
            // 参数列表
            Map paraItems = JSON.parseObject(spu.getParaItems());
            dataModel.put("paraItems", paraItems);
            // 规格列表
            Map<String, String> specItems = (Map) JSON.parseObject(sku.getSpec());
            dataModel.put("specItems", specItems);
            // 规格选项
            // {"规格":["88片","80片","104片","96片"]}
            // {"规格":{["option":"88片","checked": true},....]}
            Map<String, List> specMap = (Map) JSON.parseObject(spu.getSpecItems());
            for (String key : specMap.keySet()) {
                List<String> list = specMap.get(key);
                List<Map> mapList = new ArrayList<>();
                for (String value : list) {
                    Map map = new HashMap<>();
                    map.put("option", value);
                    map.put("checked", value.equals(specItems.get(key)));
                    Map<String, String> spec = (Map) JSON.parseObject(sku.getSpec()); // 当前sku
                    // {"规格":"88片"}
                    spec.put(key, value);
                    // Map字段排序
                    String specJson = JSON.toJSONString(spec, SerializerFeature.MapSortField);
                    map.put("url", urlMap.get(specJson));
                    mapList.add(map);
                }
                specMap.put(key, mapList);
            }
            dataModel.put("specMap", specMap);

            context.setVariables(dataModel);

            // 2、准备文件
            File dir = new File(pagePath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File dest = new File(dir, sku.getId() + ".html");

            ///3、生成页面
            try {
                PrintWriter writer = new PrintWriter(dest, "UTF-8");
                templateEngine.process("item", context, writer);
                System.out.println("生成页面：" + sku.getId() + ".html");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }
}
