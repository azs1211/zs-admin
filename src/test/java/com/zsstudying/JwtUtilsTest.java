package com.zsstudying;

import com.zsstudying.common.utils.JwtUtils;
import com.zsstudying.sys.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author ZiShao
 * @create 2023-04-08 19:59
 **/
@SpringBootTest
public class JwtUtilsTest {
    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void testCreateJwt(){
        User user = new User();
        user.setUsername("zhangsan");
        user.setPhone("16699998888");
        String token = jwtUtils.createToken(user);
        System.out.println(token);
    }

    @Test
    public void testParseToken(){
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiZjQ2NGQ5NS05ODZkLTRhY2ItOTE1NC0zYjhjNDQxMzQzMjkiLCJzdWIiOiJ7XCJwaG9uZVwiOlwiMTY2OTk5OTg4ODhcIixcInVzZXJuYW1lXCI6XCJ6aGFuZ3NhblwifSIsImlzcyI6InN5c3RlbSIsImlhdCI6MTY4MDk1NTU5NCwiZXhwIjoxNjgwOTU3Mzk0fQ.SXGKYBe01ShSlPrmvqXCLbpF__2lHDysfiPKFqe3BI4";
        User user = jwtUtils.parseToken(jwt, User.class);
        System.out.println(user);
    }
}
