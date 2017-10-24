package com.thinkive.lottery.service;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

public interface ILotteryService {

    public Map<String,Object> lottery(RedisTemplate redisTemplate, String activityId) throws Exception;
}
