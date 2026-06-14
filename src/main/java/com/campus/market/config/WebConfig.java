package com.campus.market.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * Web配置类 - 配置静态资源映射
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 从配置文件读取图片上传目录
    @Value("${file.upload.image-path}")
    private String imageUploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 确保路径格式正确
        String path = imageUploadPath;
        if (!path.endsWith("/") && !path.endsWith("\\")) {
            path = path + File.separator;
        }

        // 将 /images/** 路径映射到配置的图片目录
        // 例如：访问 /images/20260114_abc123.jpg 会映射到 E:/images/20260114_abc123.jpg
        String locationPath = "file:" + path.replace("\\", "/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations(locationPath);

        System.out.println("静态资源映射配置成功：");
        System.out.println("  URL路径: /images/**");
        System.out.println("  文件路径: " + imageUploadPath);
        System.out.println("  映射路径: " + locationPath);
    }
}
