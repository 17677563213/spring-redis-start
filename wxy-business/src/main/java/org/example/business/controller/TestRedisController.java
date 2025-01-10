package org.example.business.controller;

import lombok.RequiredArgsConstructor;
import org.example.common.redis.util.RedisUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test/redis")
@RequiredArgsConstructor
public class TestRedisController {

    private final RedisUtil redisUtil;

    @PostMapping("/set")
    public String setValue(@RequestParam String key, @RequestParam String value) {
        boolean result = redisUtil.set(key, value);
        return result ? "Success" : "Failed";
    }

    @GetMapping("/get/{key}")
    public Object getValue(@PathVariable String key) {
        return redisUtil.get(key);
    }
}
