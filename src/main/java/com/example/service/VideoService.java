package com.example.service;

import com.example.mapper.VideoMapper;
import com.example.pojo.WhatRecord;
import com.example.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: VidioService
 * Package: com.example.service
 * Description:
 *
 * @Author 谢依雯
 * @Create 2024/11/3 13:42
 */

public interface VideoService {
    public Result byId(int id);
    public Result byIds(List<Integer> ids);
    public void watch(HttpServletRequest request,WhatRecord whatRecord);
    public Result find();

    Result all();
}
