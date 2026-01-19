package com.yupi.yupicture.application.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yupicture.domain.user.entity.User;
import com.yupi.yupicture.infrastructure.common.DeleteRequest;
import com.yupi.yupicture.interfaces.dto.user.UserLoginRequest;
import com.yupi.yupicture.interfaces.dto.user.UserQueryRequest;
import com.yupi.yupicture.interfaces.dto.user.UserRegisterRequest;
import com.yupi.yupicture.interfaces.vo.user.LoginUserVO;
import com.yupi.yupicture.interfaces.vo.user.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * @author qwqw
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2025-11-29 16:23:20
 */
public interface UserApplicationService{

    long userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     * @param userLoginRequest
     * @return
     */
    LoginUserVO userLogin(UserLoginRequest userLoginRequest,  HttpServletRequest request);
    /**
     * 获取当前的登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    String getEncryptPassword(String userPassword);

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


    long addUser(User user);

    User getUserById(long id);

    UserVO getUserVOById(long id);

    boolean deleteUser(DeleteRequest deleteRequest);

    void updateUser(User user);

    Page<UserVO> listUserVOByPage(UserQueryRequest userQueryRequest);

    List<User> listByIds(Set<Long> userIdSet);

    long saveUser(User userEntity);
}
