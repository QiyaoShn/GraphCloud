package com.yupi.yupicture.domain.space.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.yupicture.domain.space.entity.SpaceUser;
import com.yupi.yupicture.interfaces.dto.spaceuser.SpaceUserQueryRequest;

/**
* @author qwqw
* @description 针对表【space_user(空间用户关联)】的数据库操作Service
* @createDate 2025-12-29 20:53:05
*/
public interface SpaceUserDomainService{

    /**
     * 获取查询对象
     *
     * @param spaceUserQueryRequest
     * @return
     */
    QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryRequest spaceUserQueryRequest);


}
