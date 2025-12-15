package com.yupi.yunpicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yunpicturebackend.model.dto.picture.PictureQueryRequest;
import com.yupi.yunpicturebackend.model.dto.picture.PictureReviewRequest;
import com.yupi.yunpicturebackend.model.dto.picture.PictureUploadByBatchRequest;
import com.yupi.yunpicturebackend.model.dto.picture.PictureUploadRequest;
import com.yupi.yunpicturebackend.model.entity.Picture;
import com.yupi.yunpicturebackend.model.entity.User;
import com.yupi.yunpicturebackend.model.vo.PictureVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qwqw
 * @description 针对表【picture(图片)】的数据库操作Service
 * @createDate 2025-12-05 19:04:08
 */
public interface PictureService extends IService<Picture> {
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

    /**
     * 清理图片文件
     * @param oldPicture
     */
    void clearPictureFile(Picture oldPicture);
}
