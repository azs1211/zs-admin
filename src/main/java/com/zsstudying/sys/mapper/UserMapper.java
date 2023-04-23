package com.zsstudying.sys.mapper;

import com.zsstudying.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zs
 * @since 2023-03-03
 * 处理用户数据的持久层接口
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户id获取用户的职位名称
     *
     * @param userId 用户的id
     * @return 返回一个用户职位数组
     */
    public List<String> getRoleNameByUserId(Integer userId);
}
