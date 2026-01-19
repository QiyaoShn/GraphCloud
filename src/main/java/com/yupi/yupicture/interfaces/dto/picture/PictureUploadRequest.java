package com.yupi.yupicture.interfaces.dto.picture;

import lombok.Data;

import java.io.Serializable;

@Data
public class PictureUploadRequest implements Serializable {


    /**
     * 图片id（用于修改已上传的图片）
     */
    private Long id;

    /**
     * 空间 id
     */
    private Long spaceId;


    /**
     * 文件地址
     */
    private String fileUrl;

    /**
     * 图片名称
     */
    private String picName;

    private static final long serialVersionUID = 1L;

}
