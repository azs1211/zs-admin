package com.zsstudying.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsstudying.common.vo.Result;
import com.zsstudying.sys.entity.User;
import com.zsstudying.sys.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zs
 * @since 2023-03-03
 */
@Api(tags = "用户接口列表")
@RestController
@RequestMapping("/user")
//@CrossOrigin
public class UserController {

    @Resource
    private IUserService iUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/all")
    public Result<List<User>> getAllUser() {
        List<User> list = iUserService.list();
        return Result.success(list, "查询成功");
    }

    @ApiOperation(value = "用户登录接口")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> data = iUserService.login(user);
        if (data != null) {
            return Result.success(data);
        }
        return Result.fail(20002, "用户名或密码错误");
    }

    @GetMapping("/info")
    public Result<Map<String, Object>> getUserInfo(@RequestParam("token") String token) {
        Map<String, Object> data = iUserService.getUserInfo(token);
        if (data != null) {
            return Result.success(data);
        }
        return Result.fail(20003, "用户登录信息无效，请重新登录。");
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token) {
        iUserService.logout(token);
        return Result.success("注销成功");
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getUserList(@RequestParam(value = "username",required = false) String username,
                                                  @RequestParam(value = "phone",required = false)String phone,
                                                  @RequestParam(value = "pageNo")Long pageNo,
                                                  @RequestParam(value = "pageSize")Long pageSize){

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(username),User::getUsername,username);
        wrapper.eq(StringUtils.hasLength(phone),User::getPhone,phone);
        wrapper.orderByDesc(User::getId);

        Page<User> page = new Page<>(pageNo,pageSize);
        iUserService.page(page, wrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        return Result.success(data);
    }

    @PostMapping
    public Result<?> addUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        iUserService.save(user);
        return Result.success("新增用户成功");
    }
    @PutMapping
    public Result<?> updateUser(@RequestBody User user){
        user.setPassword(null);
        iUserService.updateById(user);
        return Result.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id){
        User user = iUserService.getById(id);
        return Result.success(user);
    }
    @DeleteMapping("/{id}")
    public Result<?> deleteUserById(@PathVariable("id") Integer id){
        iUserService.removeById(id);
        return Result.success("删除用户成功");
    }
}

