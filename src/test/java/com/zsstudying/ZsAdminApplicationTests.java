package com.zsstudying;

import com.zsstudying.sys.entity.User;
import com.zsstudying.sys.mapper.UserMapper;
import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class ZsAdminApplicationTests {

    // 在使用@Autowired注解的时候，默认required=true,表示注入的时候bean必须存在，否则注入失败。
//    @Autowired(required = false)
    @Resource
    private UserMapper userMapper;

    @Test
    void testUserMapper() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

}
