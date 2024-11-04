package com.example.controller;

import com.example.pojo.WhatRecord;
import com.example.result.Result;
import com.example.service.VideoService;
import com.example.service.imp.VideoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: VidioController
 * Package: com.example.controller
 * Description:
 *
 * @Author 谢依雯
 * @Create 2024/11/3 13:41
 */

// toDO 其实建议这里再多加一个mapping映射，就相当于是来划分不同controller的一个根路劲
@RestController
public class VideoController {
    @Autowired
    private VideoService videoService;

    @GetMapping("/video/{id}")
    public  Result byId(@PathVariable int id){
        return videoService.byId(id);
    }

//    这个就post
    @PostMapping("/videos")
    public Result byIds(List<Integer> ids){
        return videoService.byIds(ids);
    }

    @GetMapping("/videos/all")
    public Result all(){
        return videoService.all();
    }

    //查找视频观看时间,数据传入视频id，观看时间即可
    @PostMapping("/watch")
    public void watch(WhatRecord whatRecord){

    }
    //查找视频信息
    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping("/find")
    public Result find(){
        return videoService.find();
    }

}
