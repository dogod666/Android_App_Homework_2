package com.example.service.imp;

import com.example.mapper.VideoMapper;
import com.example.pojo.WhatRecord;
import com.example.result.Result;
import com.example.service.VideoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName: VidioService
 * Package: com.example.service.imp
 * Description:
 *
 * @Author 谢依雯
 * @Create 2024/11/3 13:42
 */
@Service
public class VideoServiceImp implements VideoService {

    @Autowired
    public VideoMapper videoMapper;
    @Override
    public  Result byId(int id) {
        Result result=new Result(1,videoMapper.byId(id),"成功");
        return result;
    }

    @Override
    public Result byIds(List<Integer> ids) {
        Result result=new Result(1,videoMapper.byIds(ids),"成功");
        return result;
    }

    @Override
    public void watch(HttpServletRequest request, WhatRecord whatrecord) {
        //获取ip
        String clientIP = getClientIP(request);
        whatrecord.setIp(clientIP);
        //记录创建时间
        whatrecord.setCreatedTime(LocalDateTime.now());
    }

    @Override
    public Result find() {
        Result result=new Result(1,videoMapper.find(),"成功");
        return result;
    }

    @Override
    public Result all() {
        Result result=new Result(1,videoMapper.all(),"成功");
        return result;
    }
// toDO 从规范的意义上讲，code最好还是200等等这种
// toDO 下面这种方法，应该写到工具类里面，当然这些都是代码规范，不是强制必须

    //获取ip
    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;}

}
