package com.example.nearbydemo.controller;

import com.example.nearbydemo.service.GeoService;
import com.example.nearbydemo.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeoController {

    @Autowired
    private GeoService geoService;

    /**
     * 上传位置
     *
     * @param userId 用户id
     * @param x      经度
     * @param y      纬度
     */
    @GetMapping("save")
    public void save(String userId, String x, String y) {
        Point point = new Point(Double.valueOf(x), Double.valueOf(y));
        geoService.save(point, userId);
    }

    /**
     * 查询附近的人
     *
     * @param x 经度
     * @param y 纬度
     * @return
     */
    @GetMapping("near")
    public Object near(String x, String y) {
        Point point = new Point(Double.valueOf(x), Double.valueOf(y));
        return geoService.near(point);
    }

}
