package com.zsstudying.sys.service;

import com.zsstudying.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2023-03-03
 */
public interface IUserService extends IService<User> {


    Map<String, Object> login(User user);

    Map<String, Object> getUserInfo(String token);

    public void logout(String token);
}
