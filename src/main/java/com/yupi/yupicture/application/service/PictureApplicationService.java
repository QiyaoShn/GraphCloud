package com.yupi.yupicture.application.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yupicture.infrastructure.api.aliyunai.model.CreateOutPaintingTaskResponse;
import com.yupi.yupicture.domain.picture.entity.Picture;
import com.yupi.yupicture.domain.user.entity.User;
import com.yupi.yupicture.interfaces.vo.picture.PictureVO;
import com.yupi.yupicture.interfaces.dto.picture.*;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author qwqw
 * @description 针对表【picture(图片)】的数据库操作Service
 * @createDate 2025-12-05 19:04:08
 */
public interface PictureApplicationService extends IService<Picture> {
    /**
     * 上传图片
     *
     * @param inputSource          前端传来的上传的文件
     * @param pictureUploadRequest 请求体
     * @param loginUser            当前用户，用于判断权限
     * @return
     */
    PictureVO uploadPicture(Object inputSource, PictureUploadRequest pictureUploadRequest, User loginUser);

    /**
     * 获取单个图片封装
     *
     * @param picture
     * @param request
     * @return
     */
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    /**
     * 分页获取图片封装（获取多条图片封装）
     *
     * @param picturePage
     * @param request
     * @return
     */
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    /**
     * 校验图片，用于跟新和修改图片时进行判断
     *
     * @param picture
     */
    void validPicture(Picture picture);

    /**
     * 获取查询对象
     *
     * @param pictureQueryRequest
     * @return
     */
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    /**
     * 图片审核功能
     *
     * @param pictureReviewRequest
     * @param loginUser            当前登录的用户
     */
    void doPictureReview(PictureReviewRequest pictureReviewRequest, User loginUser);


    /**
     * 填充审核参数
     *
     * @param picture
     * @param loginUser
     */
    void fillReviewParams(Picture picture, User loginUser);

    /**
     * 批量抓取和创建图片
     * @param pictureUploadByBatchRequest
     * @param loginUser
     * @return 成功创建的图片数
     */
    Integer uploadPictureByBatch(PictureUploadByBatchRequest pictureUploadByBatchRequest, User loginUser);


    //打上这个注解就是异步执行，删除记录成功即返回给前端，然后慢慢删除数据库中的文件
    @Async
    void clearPictureFile(Picture oldPicture);

    /**
     * 删除图片
     * @param pictureId
     * @param loginUser
     */
    void deletePicture(long pictureId, User loginUser);

    /**
     * 编辑图片
     * @param picture
     * @param loginUser
     */
    void editPicture(Picture picture, User loginUser);

    /**
     * 校验空间图片的权限
     * @param loginUser
     * @param picture
     */
    void checkPictureAuth(User loginUser, Picture picture);

    /**
     * 根据颜色搜索图片（用户只能在自己的私人空间中颜色搜图）
     * @param spaceId
     * @param picColor
     * @param loginUser
     * @return
     */
    List<PictureVO> searchPictureByColor(Long spaceId, String picColor, User loginUser);

    /**
     * 批量编辑图片
     *
     * @param pictureEditByBatchRequest
     * @param loginUser
     */
    void editPictureByBatch(PictureEditByBatchRequest pictureEditByBatchRequest, User loginUser);

    /**
     * 创建扩图任务
     * @param createPictureOutPaintingTaskRequest
     * @param loginUser
     * @return
     */
    CreateOutPaintingTaskResponse createPictureOutPaintingTask(CreatePictureOutPaintingTaskRequest createPictureOutPaintingTaskRequest, User loginUser);
}
