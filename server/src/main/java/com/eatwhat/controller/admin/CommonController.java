package com.eatwhat.controller.admin;

import com.eatwhat.constant.MessageConstant;
import com.eatwhat.result.Result;
import com.eatwhat.utils.QiniuOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 通用控制器
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {
    @Autowired
    private QiniuOssUtil qiniuOssUtil;

    /**
     * 文件上传
     */
    @RequestMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = null;
            if (originalFilename != null) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String objectName = UUID.randomUUID() + extension;

            String filePath = qiniuOssUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败：{}", e.toString());
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}