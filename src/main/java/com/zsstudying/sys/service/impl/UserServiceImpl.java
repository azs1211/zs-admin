package com.zsstudying.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zsstudying.common.utils.JwtUtils;
import com.zsstudying.config.MyRedisConfig;
import com.zsstudying.sys.entity.User;
import com.zsstudying.sys.mapper.UserMapper;
import com.zsstudying.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zs
 * @since 2023-03-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Autowired
    @Qualifier(value = "redisTemplate")
    private RedisTemplate template;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;


//    @Override
//    public Map<String, Object> login(User user) {
//        //根据用户名和密码查询
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getUsername, user.getUsername());
//        wrapper.eq(User::getPassword, user.getPassword());
//        User loginUser = userMapper.selectOne(wrapper);
//        //结果不为空，则生成token，并将用户信息存入redis
//        if (loginUser != null) {
//            //暂时用UUID，用jwt替换
//            String key = "user:" + UUID.randomUUID();
//
//            //存入redis
//            loginUser.setPassword(null);
//            template.opsForValue().set(key, loginUser, 30, TimeUnit.MINUTES);
//            //返回数据
//            Map<String, Object> data = new HashMap<>();
//            data.put("token", key);
//            return data;
//        }
//        return null;
//    }

    @Override
    public Map<String, Object> login(User user) {
        //根据用户名查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User loginUser = userMapper.selectOne(wrapper);
        //结果不为空，并且密码与传入的密码匹配，则生成token，并将用户信息存入redis
        if (loginUser != null && passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
            //暂时用UUID，用jwt替换
//        String key = "user:" + UUID.randomUUID();

            //存入redis
            loginUser.setPassword(null);
//        template.opsForValue().set(key, loginUser, 30, TimeUnit.MINUTES);
            //创建jwt
            String token = jwtUtils.createToken(loginUser);

            //返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            return data;
        }
        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        //根据token获取用户信息
//        Object obj = template.opsForValue().get(token);
        User loginUser = null;
        try {
            loginUser = jwtUtils.parseToken(token, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (loginUser != null) {
//            User loginUser = JSON.parseObject(JSON.toJSONString(obj), User.class);
            Map<String, Object> data = new HashMap<>();
            data.put("name", loginUser.getUsername());
            data.put("avatar", loginUser.getAvatar());
            //角色
            List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles", roleList);
            return data;
        }

        return null;
    }

    @Override
    public void logout(String token) {
//        template.delete(token);

    }
}
