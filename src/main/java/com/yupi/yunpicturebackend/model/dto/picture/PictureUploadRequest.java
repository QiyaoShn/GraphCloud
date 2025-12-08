package com.yupi.yunpicturebackend.model.dto.picture;

import lombok.Data;

import java.io.Serializable;

@Data
public class PictureUploadRequest implements Serializable {


    /**
     * 图片id（用于修改已上传的图片）
     */
    private Long id;

    private static final long serialVersionUID = 1L;

}
