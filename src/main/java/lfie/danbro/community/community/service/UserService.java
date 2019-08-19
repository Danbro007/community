package lfie.danbro.community.community.service;


import lfie.danbro.community.community.mapper.UserMapper;
import lfie.danbro.community.community.model.User;
import lfie.danbro.community.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 修改或者增加用户
     *
     * @param user 用户对象
     */
    public void updateOrInsert(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> dbUsers = userMapper.selectByExample(userExample);
        //数据库中找不到用户 添加用户
        if (dbUsers.size() == 0) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            int userId = userMapper.insert(user);
            user.setId(userId);
        } else {
            User dbUser = dbUsers.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria().andAccountIdEqualTo(dbUser.getAccountId());
            userMapper.updateByExampleSelective(updateUser, example);
        }
//        //加载在redis中
//        redisTemplate.opsForValue().set(user.getToken(),user);
//        redisTemplate.expire(user.getToken(),24,TimeUnit.HOURS);

    }

    public User selectUserByAccountId(String accountId) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(accountId);
        return userMapper.selectByExample(userExample).get(0);
    }
}
