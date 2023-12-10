package com.nageoffer.shortlink.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.admin.dao.entity.UserDO;
import com.nageoffer.shortlink.admin.dto.req.UserLoginReqDto;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDto;
import com.nageoffer.shortlink.admin.dto.req.UserUpdateReqDto;
import com.nageoffer.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDTO;

/**
 * 用户接口层
 */
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    UserRespDTO getUserByUsername(String username);


    /**
     * 查询用户名是否存在
     *
     * @param username
     * @return
     */

    Boolean hasUsername(String username);

    /**
     * 用户注册
     *
     * @param requestParam
     */

    void register(UserRegisterReqDto requestParam);


    /**
     * 根据用户名修改用户
     * @param requestParam
     */

    void update(UserUpdateReqDto requestParam);

    /**
     * 用户登录
     * @param requestParam
     * @return
     */

    UserLoginRespDTO login(UserLoginReqDto requestParam);

    /**
     * 检查用户是否登录
     * @param username
     * @param token
     * @return
     */

    Boolean checkLogin(String username, String token);

    void logout(String username, String token);
}
