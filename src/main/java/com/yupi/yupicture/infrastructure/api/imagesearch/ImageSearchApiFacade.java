package com.yupi.yupicture.infrastructure.api.imagesearch;

import com.yupi.yupicture.infrastructure.api.imagesearch.model.ImageSearchResult;
import com.yupi.yupicture.infrastructure.api.imagesearch.sub.GetImageFirstUrlApi;
import com.yupi.yupicture.infrastructure.api.imagesearch.sub.GetImageListApi;
import com.yupi.yupicture.infrastructure.api.imagesearch.sub.GetImagePageUrlApi;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ImageSearchApiFacade {

    /**
     * 搜索图片
     *
     * @param imageUrl
     * @return
     */
    public static List<ImageSearchResult> searchImage(String imageUrl) {
        String imagePageUrl = GetImagePageUrlApi.getImagePageUrl(imageUrl);
        String imageFirstUrl = GetImageFirstUrlApi.getImageFirstUrl(imagePageUrl);
        List<ImageSearchResult> imageList = GetImageListApi.getImageList(imageFirstUrl);
        return imageList;
    }

    public static void main(String[] args) {
        // 测试以图搜图功能
        String imageUrl = "https://yun-picture-1389707977.cos.ap-chengdu.myqcloud.com/public/1994729270370308098/2025-12-15_8iLIaBkVrfFnGuK6.webp";
        List<ImageSearchResult> resultList = searchImage(imageUrl);
        System.out.println("结果列表" + resultList);
    }
}

