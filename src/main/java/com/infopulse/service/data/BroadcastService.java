package com.infopulse.service.data;

import com.infopulse.entity.BroadcastMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BroadcastService {

    private RedisTemplate<String, BroadcastMessage> redisTemplate;

    public BroadcastService(RedisTemplate<String, BroadcastMessage> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveBroadcast(String sender, String messageToSend) {
        BroadcastMessage broadcastMessage = new BroadcastMessage();
        broadcastMessage.setSender(sender);
        broadcastMessage.setMessage(messageToSend);
        redisTemplate.opsForList().leftPush("broadcast", broadcastMessage);
    }

    public List<BroadcastMessage> getAll() {
        return redisTemplate.opsForList().range("broadcast", 0, -1);
    }
}
