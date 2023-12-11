package com.nageoffer.shortlink.admin.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.admin.common.convention.exception.ClientException;
import com.nageoffer.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.nageoffer.shortlink.admin.dao.entity.UserDO;
import com.nageoffer.shortlink.admin.dao.mapper.UserMapper;
import com.nageoffer.shortlink.admin.dto.req.UserLoginReqDto;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDto;
import com.nageoffer.shortlink.admin.dto.req.UserUpdateReqDto;
import com.nageoffer.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDTO;
import com.nageoffer.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.nageoffer.shortlink.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;
import static com.nageoffer.shortlink.admin.common.enums.UserErrorCodeEnum.USER_NAME_EXIST;
import static com.nageoffer.shortlink.admin.common.enums.UserErrorCodeEnum.USER_SAVE_ERROR;


/**
 * 用户接口实现层
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {


    @Autowired
    private RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    @Autowired
    private RedissonClient redissonClient;

    private final StringRedisTemplate redisTemplate;

    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ClientException(UserErrorCodeEnum.USER_NULL);
        }
        UserRespDTO userRespDTO = new UserRespDTO();
        BeanUtils.copyProperties(userDO, userRespDTO);

        return userRespDTO;
    }

    /**
     * hasUsername方法接收一个字符串类型的参数username，用于检查用户名是否已经被注册过。
     * 它直接调用userRegisterCachePenetrationBloomFilter的contains方法，
     * 传入username作为参数，返回布尔值表示用户名是否存在于布隆过滤器中。
     *
     * @param username
     * @return
     */

    @Override
    public Boolean hasUsername(String username) {
        return !userRegisterCachePenetrationBloomFilter.contains(username);
    }


    /**
     * 注册功能
     * 使用redisson分布式锁 防止恶意请求毫秒级触发大量请求去一个未注册的用户名
     *
     * 如果恶意请求使用未注册不同用户发起大量请求是防不住的
     * 只有通过限流等方案保障系统安全
     *
     * @param requestParam
     */
    @Override
    public void register(UserRegisterReqDto requestParam) {
        if (!hasUsername(requestParam.getUsername())) {
            //当前用户已经存在
            throw new ClientException(USER_NAME_EXIST);
        }
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + requestParam.getUsername());
        try {
            if (lock.tryLock()) {
                //用户名不存在 将用户名存入数据库
                int insert = baseMapper.insert(BeanUtil.toBean(requestParam, UserDO.class));
                if (insert < 1) {
                    throw new ClientException(USER_SAVE_ERROR);
                }
                userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
                return;
            }
            throw new ClientException(USER_NAME_EXIST);

        } finally {
            lock.unlock();
        }


    }

    @Override
    public void update(UserUpdateReqDto requestParam) {

        // TODO 验证当前用户是否为登录用户

        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername());

        baseMapper.update(BeanUtil.toBean(requestParam,UserDO.class),queryWrapper);

    }

    @Override
    public UserLoginRespDTO login(UserLoginReqDto requestParam) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername())
                .eq(UserDO::getPassword, requestParam.getPassword())
                .eq(UserDO::getDelFlag,0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO==null){
             throw new ClientException("用户不存在");
        }

        Boolean hasLogin = redisTemplate.hasKey("login_" + requestParam.getUsername());
        if (hasLogin!=null&&hasLogin){
            throw new ClientException("用户已登录");
        }

        /**
         * Hash
         * key:login_用户名
         * Value:
         * key:token标识
         * val:json字符串（用户信息）
         */

        String token = UUID.randomUUID().toString();
        redisTemplate.opsForHash().put("login_"+requestParam.getUsername(),token, JSON.toJSONString(userDO));
        redisTemplate.expire("login_"+requestParam.getUsername(),30L, TimeUnit.DAYS);
        return new UserLoginRespDTO(token);

    }

    @Override
    public Boolean checkLogin(String username, String token) {
        return redisTemplate.opsForHash().get("login_"+username,token)!=null;
    }

    @Override
    public void logout(String username, String token) {
        if (checkLogin(username,token)){
            //用户已经登录
            redisTemplate.delete("login_"+username);
            return;
        }

        throw new ClientException("用户Token不存在或用户未登录");
    }


}
