package com.nageoffer.shortlink.admin.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录返回参数响应
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRespDTO {


    private String token;


}
