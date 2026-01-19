package com.yupi.yupicture.infrastructure.utils;

/**
 * 颜色转换工具类
 */
public class ColorTransformUtils {
    private ColorTransformUtils() {
        //工具类不需要实例化
    }

    /**
     * 获取标准颜色（将数据万象的5位色值转换为6位）
     * @param color
     * @return
     */
    public static String getStandardColor(String color) {
        //每一种rgb色值都有可能只有一个0，要转换为00
        //如果是6位，不用转换，如果是5位，要给第三位后面加个0
        if (color.length() == 7) {
            color = color.substring(0, 4) + "0" + color.substring(4,7);
        }
        return color;
    }
}
