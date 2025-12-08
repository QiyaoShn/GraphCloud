package com.yupi.yunpicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yunpicturebackend.model.dto.user.UserQueryRequest;
import com.yupi.yunpicturebackend.model.entity.User;
import com.yupi.yunpicturebackend.model.vo.LoginUserVO;
import com.yupi.yunpicturebackend.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author qwqw
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2025-11-29 16:23:20
 */
public interface UserService extends IService<User> {
    /**
     *
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 获取加密后的密码
     *
     * @param userPassword
     * @return 加密后的密码
     */
    String getEncryptPassword(String userPassword);

    /**
     *
     * @param userAccount
     * @param userPassword
     * @param request
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取当前的登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 脱敏后的用户登录信息
     *
     * @param user 用户
     * @return 脱敏后的用户信息
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏后的用户信息
     *
     * @param user
     * @return
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏后的用户信息列表
     *
     * @param userList
     * @return 脱敏后的用户列表
     */
    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 用户注销（退出登录）
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取查询条件
     * 将不同的Java对象转化成MyBatis-plus需要的查询体、查询类的方法
     *
     * @param userQueryRequest
     * @return
     */
    QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);
}
