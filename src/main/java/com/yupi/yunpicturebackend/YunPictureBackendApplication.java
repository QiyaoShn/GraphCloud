package com.yupi.yunpicturebackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.yupi.yunpicturebackend.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class YunPictureBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(YunPictureBackendApplication.class, args);
    }

}
