package com.eatwhat.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;

@Data
@AllArgsConstructor
@Slf4j
public class QiniuOssUtil {
    private String accessKey;
    private String secretKey;
    private String bucket;

    /**
     * 文件上传
     */
    public String upload(byte[] bytes, String objectName) {
        // 创建配置
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;

        // 创建上传管理器
        UploadManager uploadManager = new UploadManager(cfg);

        // 创建认证信息
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
            Response response = uploadManager.put(byteInputStream, objectName, upToken, null, null);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("文件上传到：{}", putRet.key);

            // 文件访问路径规则
            /* StringBuilder stringBuilder = new StringBuilder("http://senwiy2am.hn-bkt.clouddn.com/");
            stringBuilder
                    .append(bucketName)
                    .append(".")
                    .append(region)
                    .append("/")
                    .append(putRet.key); */

            return "http://sghwqeoqr.hn-bkt.clouddn.com/" + putRet.key;

        } catch (QiniuException ex) {
            log.error("上传文件出错: {}", ex.response.toString());
            return null;
        } catch (Exception e) {
            log.error("编码错误: {}", e.getMessage());
            return null;
        }
    }
}