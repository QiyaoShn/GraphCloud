package com.yupi.yunpicturebackend.manager.upload;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.persistence.ImageInfo;
import com.yupi.yunpicturebackend.config.CosClientConfig;
import com.yupi.yunpicturebackend.exception.BusinessException;
import com.yupi.yunpicturebackend.exception.ErrorCode;
import com.yupi.yunpicturebackend.manager.CosManager;
import com.yupi.yunpicturebackend.model.dto.file.UploadPictureResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;

/**
 * 设计模式之  ”模板方法“ （重复流程的复用）
 * 图片上传模板（有两个图片上传方法 url和文件上传，抽象其中的公共流程部分）
 */
@Service
@Slf4j
public abstract class PictureUploadTemplate {
    @Resource
    private CosClientConfig cosClientConfig;

    @Resource
    private CosManager cosManager;

    /**
     * 上传图片
     *
     * @param inputSource    文件
     * @param uploadPathPrefix 上传路径前缀
     * @return
     *
     */
    public UploadPictureResult uploadPicture(Object inputSource, String uploadPathPrefix) {
        //1. 检验图片
        validPicture(inputSource);
        //2. 图片上传地址
        String uuid = RandomUtil.randomString(16);
        String originalFilename = getOriginalFilename(inputSource);
        //自己拼接文件上传路径，而不是使用原始文件名称，可以增强安全性
        String uploadFileName = String.format("%s_%s.%s", DateUtil.formatDate(new Date()), uuid,
                FileUtil.getSuffix(originalFilename));
        String uploadPath = String.format("/%s/%s", uploadPathPrefix, uploadFileName);
        //解析结果并返回
        File file = null;
        try {
            //3. 创建临时文件，获取文件到服务器
            file = File.createTempFile(uploadPath, null);
            //处理文件来源
            processFile(inputSource, file);
            //4. 上传图片到对象存储
            PutObjectResult putObjectResult = cosManager.putPictureObject(uploadPath, file);
            //5. 获取图片信息对象，封装返回结果
            ImageInfo imageInfo = putObjectResult.getCiUploadResult().getOriginalInfo().getImageInfo();
            return buildResult(uploadPath, originalFilename, file,imageInfo);
        } catch (Exception e) {
            log.error("图片上传到对象存储失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            //6. 临时文件清理
            this.deleteTempFile(file);
        }
    }


    /**
     * 处理输入源并生成本地临时文件
     * @param inputSource
     * @param file
     * @throws Exception
     */
    protected abstract void processFile(Object inputSource, File file) throws Exception;

    /**
     * 获取输入源的原始文件名
     * @param inputSource
     * @return
     */
    protected abstract String getOriginalFilename(Object inputSource);

    /**
     * 校验输入源（本地文件 或 URL）
     * @param inputSource
     */
    protected abstract void validPicture(Object inputSource);

    /**
     * 封装返回结果
     * @param uploadPath
     * @param originalFilename
     * @param file
     * @param imageInfo 对象存储返回的图片信息
     * @return
     */
    private UploadPictureResult buildResult(String uploadPath, String originalFilename, File file, ImageInfo imageInfo) {
        //计算图像宽高
        int picWidth = imageInfo.getWidth();
        int picHeight = imageInfo.getHeight();
        double picScale = NumberUtil.round(picWidth * 1.0 / picHeight, 2).doubleValue();
        //封装返回结果
        UploadPictureResult uploadPictureResult = new UploadPictureResult();
        //图像上传的绝对地址
        uploadPictureResult.setUrl(cosClientConfig.getHost() + "/" + uploadPath);
        uploadPictureResult.setPicName(FileUtil.mainName(originalFilename));
        uploadPictureResult.setPicSize(FileUtil.size(file));
        uploadPictureResult.setPicWidth(picWidth);
        uploadPictureResult.setPicHeight(picHeight);
        uploadPictureResult.setPicScale(picScale);
        uploadPictureResult.setPicFormat(imageInfo.getFormat());
        //返回可访问的地址
        return uploadPictureResult;
    }

    /**
     * 清理临时文件
     *
     * @param file
     */
    public void deleteTempFile(File file) {
        if (file == null) {
            return;
        }
        //删除临时文件
        boolean deleteResult = file.delete();
        if (!deleteResult) {
            log.error("file delete error, filepath = {}", file.getAbsolutePath());
        }
    }

    
}
