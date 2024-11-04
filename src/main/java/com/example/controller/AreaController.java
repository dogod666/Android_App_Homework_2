package com.example.controller;

/**
 * ClassName: AreaController
 * Package: com.example.controller
 * Description:
 *
 * @Author 谢依雯
 * @Create 2024/10/26 15:21
 */

import com.example.mapper.AreaMapper;
import com.example.pojo.Area;
import com.example.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AreaController {
    @Autowired
    private AreaMapper areaMapper;
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

    @PostMapping ("/data")
    public Result JsonList(@RequestBody Area area, HttpServletRequest request, HttpServletResponse response){
        if(area==null){
            return new Result(0,"输入对象为空");
        }
        String clientIP = getClientIP(request);
        area.setIp(clientIP);
        areaMapper.insert(area);
        return new Result(1,"成功");
    }
    @PostMapping("/datas")
    public Result JsonLists(@RequestBody List<Area> areas, HttpServletRequest request, HttpServletResponse response){
        if(areas==null || areas.isEmpty()){
            return new Result(0,"输入对象为空");
        }
        String clientIP = getClientIP(request);
        for (Area area:areas){
            area.setIp(clientIP);
        }
        areaMapper.inserts(areas);
        return new Result(1,"成功");
    }
    //    添加一下方法级别跨域允许
//    toDO
    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping("/all")
    public Result find(){
        List<Area> areas=areaMapper.find();
        return new Result(1,areas,"成功");
    }

}
