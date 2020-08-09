package com.qingcheng.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qingcheng.entity.PageResult;
import com.qingcheng.entity.Result;
import com.qingcheng.pojo.goods.Album;
import com.qingcheng.service.goods.AlbumService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Reference
    private AlbumService albumService;

    @GetMapping("/findAll")
    public List<Album> findAll(){
        return albumService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Album> findPage(int page, int size){
        return albumService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Album> findList(@RequestBody Map<String,Object> searchMap){
        return albumService.findList(searchMap);
    }

    @PostMapping("/findPage")
    public PageResult<Album> findPage(@RequestBody Map<String,Object> searchMap,int page, int size){
        return  albumService.findPage(searchMap,page,size);
    }

    @GetMapping("/findById")
    public Album findById(Long id){
        return albumService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody Album album){
        albumService.add(album);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Album album){
        albumService.update(album);
        return new Result();
    }

    /**
     * 新增多个图片
     * @param album
     * @return
     */
    @PostMapping("/addImages")
    public Result addImages(@RequestBody Album album){
        Album album1 = albumService.findById(album.getId());
        // 合并集合
        JSONArray items1 = JSON.parseArray(album1.getImageItems());  // 原来的图片
        JSONArray items2 = JSON.parseArray(album.getImageItems());  // 新增的图片
        items1.addAll(items2);
        // 更新
        album1.setImageItems(items1.toJSONString());
        albumService.update(album1);
        return new Result();
    }

    /**
     * 删除图片
     * @param id
     * @param uid
     * @return
     */
    @GetMapping("/deleteImage")
    public Result deleteImage(Long id, Long uid){
        Album album = albumService.findById(id);
        JSONArray items = JSON.parseArray(album.getImageItems());
        // 迭代，找出要删除的图片，进行删除
        Iterator<Object> iterator = items.iterator();
        while(iterator.hasNext()){
            JSONObject obj = (JSONObject) iterator.next();
            if(obj.get("uid").equals(uid)){
                iterator.remove();
                break;
            }
        }
        // 更新
        album.setImageItems(items.toJSONString());
        albumService.update(album);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(Long id){
        albumService.delete(id);
        return new Result();
    }

}
