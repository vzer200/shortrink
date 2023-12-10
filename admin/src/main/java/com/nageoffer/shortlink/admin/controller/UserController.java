package com.nageoffer.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.common.convention.result.Results;
import com.nageoffer.shortlink.admin.dto.req.UserLoginReqDto;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDto;
import com.nageoffer.shortlink.admin.dto.req.UserUpdateReqDto;
import com.nageoffer.shortlink.admin.dto.resp.UserActualRespDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDTO;
import com.nageoffer.shortlink.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制管理层
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    @GetMapping("/api/shortlink/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username) {
        return Results.success(userService.getUserByUsername(username));

    }

    /**
     * 根据用户名查询用户无脱敏信息
     *
     * @param username
     * @return
     */
    @GetMapping("/api/shortlink/v1/actual/user/{username}")
    public Result<UserActualRespDTO> getActualUserByUsername(@PathVariable("username") String username) {
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username),UserActualRespDTO.class));

    }

    /**
     * 查询用户名是否存在
     * @param username
     * @return
     */

    @GetMapping("/api/shortlink/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username) {
        return Results.success(userService.hasUsername(username));

    }


    /**
     * 注册用户
     */
    @PostMapping("/api/shortlink/v1/user")
    public Result<Void> register(@RequestBody UserRegisterReqDto requestParam){

        userService.register(requestParam);
        return Results.success();
    }

    /**
     * 修改用户
     * @param requestParam
     * @return
     */

    @PutMapping("/api/shortlink/v1/user")
    public Result<Void> update(@RequestBody UserUpdateReqDto requestParam){
        userService.update(requestParam);
        return Results.success();
    }


    /**
     *用户登录
     * @param requestParam
     * @return
     */
    @PostMapping("/api/shortlink/v1/user/login")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDto requestParam){
        return Results.success(userService.login(requestParam));
    }



    @GetMapping("/api/shortlink/v1/user/check-login")
    public Result<Boolean> checkLogin(@RequestParam("username") String username,
                                      @RequestParam("token") String token){
        return Results.success(userService.checkLogin(username,token));

    }


    @DeleteMapping("/api/shortlink/v1/user/logout")
    public Result<Void> logout(@RequestParam("username") String username,
                                      @RequestParam("token") String token){
        userService.logout(username,token);
        return Results.success();

    }

}
