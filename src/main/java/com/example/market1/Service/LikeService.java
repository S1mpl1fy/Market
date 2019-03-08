package com.example.market1.Service;

import com.example.market1.Utils.JedisAdapter;
import com.example.market1.Utils.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    GoodsService goodsService;

    @Autowired
    UserService userService;

    @Autowired
    JedisAdapter jedisAdapter;

    public int getLikeStatus(int userId, int entityType, int entityId){
        String likeKey = RedisKeyUtils.getLikeKey(entityId, entityType);
        String dislikeKey = RedisKeyUtils.getDisLikeKey(entityId, entityType);
        if(jedisAdapter.sismember(likeKey, String.valueOf(userId))){
            return 1;
        }
        if(jedisAdapter.sismember(dislikeKey, String.valueOf(userId))){
            return -1;
        }
        return 0;
    }

    public long like(int userId, int entityType, int entityId){
        String likeKey = RedisKeyUtils.getLikeKey(entityId, entityType);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));
        String dislikeKey = RedisKeyUtils.getDisLikeKey(entityId, entityType);
        jedisAdapter.srem(dislikeKey, String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }

    public long dislike(int userId, int entityType, int entityId){
        String dislikeKey = RedisKeyUtils.getDisLikeKey(entityId, entityType);
        jedisAdapter.srem(dislikeKey, String.valueOf(userId));
        String likeKey = RedisKeyUtils.getLikeKey(entityId, entityType);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));
        return jedisAdapter.scard(dislikeKey);
    }

}
