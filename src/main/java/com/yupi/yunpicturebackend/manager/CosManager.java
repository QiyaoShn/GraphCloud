package com.yupi.yunpicturebackend.manager;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;
import com.yupi.yunpicturebackend.config.CosClientConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

/**
 * 通用的文件上传服务（可复用）
 * 与业务逻辑无关
 */
@Component
public class CosManager {
    @Resource
    private CosClientConfig cosClientConfig;

    @Resource
    private COSClient cosClient;

    /**
     * 上传对象，一个key对应一个文件
     *
     * @param key  唯一键，存到对象存储的哪个位置
     * @param file 文件，本地上传的文件
     * @return
     */
    public PutObjectResult putObject(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key,
                file);
        return cosClient.putObject(putObjectRequest);
    }

    /**
     * 下载对象到后端服务器中，然后再返回给前端。可以通过COSObject对象，拿到文件的流
     * 但是如果本身是公开的图片，后端直接给前端返回一个url即可，不用下面这个方法
     *
     * @param key 唯一键
     * @return
     */
    public COSObject getObject(String key) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(cosClientConfig.getBucket(), key);
        return cosClient.getObject(getObjectRequest);
    }

    /**
     * 上传图片对象（附带图片信息）
     *
     * @param key  唯一键，存到对象存储的哪个位置
     * @param file 文件，本地上传的文件
     * @return
     */
    public PutObjectResult putPictureObject(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key,
                file);
        //对图片进行处理（获取基本信息也被视作一种图片的处理）
        PicOperations picOperations = new PicOperations();
        // “1”表示返回原图信息
        picOperations.setIsPicInfo(1);
        //构造处理参数
        putObjectRequest.setPicOperations(picOperations);
        return cosClient.putObject(putObjectRequest);
    }

}
