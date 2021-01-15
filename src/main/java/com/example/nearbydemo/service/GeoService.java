package com.example.nearbydemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 我们不生产BUG，我们只是BUG的搬运工
 * @create 2021-01-15 2:02
 */
@Service
public class GeoService {
    private static final String GEO_KEY = "user_geo";

    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 上传位置
     *
     * @param point  用户位置
     * @param userId 用户id
     */
    public void save(Point point, String userId) {
        redisTemplate.opsForGeo().add(GEO_KEY, new RedisGeoCommands.GeoLocation(userId, point));
    }

    /**
     * 附近的人
     *
     * @param point 当前用户位置
     * @return
     */
    public GeoResults<RedisGeoCommands.GeoLocation> near(Point point) {
        // 半径3000米
        Distance distance = new Distance(3000, RedisGeoCommands.DistanceUnit.METERS);
        Circle circle = new Circle(point, distance);
        // 查看附近的几个人，这里设置查看5人
        // RedisGeoCommands.GeoRadiusCommandArgs geoRadiusCommandArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().limit(5);
        // 如果无限制，去除.limit(5)
        RedisGeoCommands.GeoRadiusCommandArgs geoRadiusCommandArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending();
        GeoResults<RedisGeoCommands.GeoLocation> geoLocationGeoResult = redisTemplate.opsForGeo().radius(GEO_KEY, circle, geoRadiusCommandArgs);
        // 获取list
        List<GeoResult<RedisGeoCommands.GeoLocation>> list = geoLocationGeoResult.getContent();
        System.out.println(list);
        // list中一个元素
        GeoResult<RedisGeoCommands.GeoLocation> geoLocationGeoResult1 = list.get(0);
        // 该元素地理系统
        System.out.println(geoLocationGeoResult1.getContent());
        // 该元素距离信息
        System.out.println(geoLocationGeoResult1.getDistance());
        return geoLocationGeoResult;
    }
}
