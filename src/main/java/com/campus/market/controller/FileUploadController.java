package com.campus.market.controller;

import com.campus.market.dto.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传控制器
 */
@RestController
@RequestMapping("/upload")
public class FileUploadController {

    // 从配置文件读取图片上传目录
    @Value("${file.upload.image-path}")
    private String imageUploadPath;

    /**
     * 上传图片
     */
    @PostMapping("/image")
    public Result<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("请选择要上传的文件");
        }

        // 检查文件类型
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !isImageFile(originalFilename)) {
            return Result.error("只支持上传图片文件（jpg、jpeg、png、gif）");
        }

        // 检查文件大小（最大5MB）
        if (file.getSize() > 5 * 1024 * 1024) {
            return Result.error("文件大小不能超过5MB");
        }

        try {
            // 生成文件名：日期_UUID_原始文件名
            String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
            String fileExtension = getFileExtension(originalFilename);
            String newFileName = dateStr + "_" + uuid + fileExtension;

            // 确保上传目录存在
            File uploadDir = new File(imageUploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 保存文件到指定目录
            Path filePath = Paths.get(imageUploadPath, newFileName);
            Files.write(filePath, file.getBytes());

            // 返回文件访问URL（相对路径，用于前端访问）
            String fileUrl = "/images/" + newFileName;

            Map<String, String> result = new HashMap<>();
            result.put("url", fileUrl);
            result.put("filename", newFileName);

            System.out.println("图片上传成功: " + filePath.toString());
            System.out.println("访问URL: " + fileUrl);

            return Result.success("上传成功", result);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("文件上传失败：" + e.getMessage());
        }
    }

    /**
     * 判断是否为图片文件
     */
    private boolean isImageFile(String filename) {
        String extension = getFileExtension(filename).toLowerCase();
        return extension.equals(".jpg") || extension.equals(".jpeg") ||
               extension.equals(".png") || extension.equals(".gif");
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String filename) {
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return filename.substring(lastIndexOf);
    }
}
