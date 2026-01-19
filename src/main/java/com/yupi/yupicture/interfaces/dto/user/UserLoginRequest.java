package com.yupi.yupicture.interfaces.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -1816058463936779838L;
    /**
     * 账号
     */
    private String useAccount;
    /**
     * 密码
     */
    private String userPassword;
}
