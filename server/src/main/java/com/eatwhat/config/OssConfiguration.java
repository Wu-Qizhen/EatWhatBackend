package com.eatwhat.config;

import com.eatwhat.properties.QiniuOssProperties;
import com.eatwhat.utils.QiniuOssUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public QiniuOssUtil qiniuOssUtil(QiniuOssProperties qiniuOssProperties) {
        return new QiniuOssUtil(
                qiniuOssProperties.getAccessKey(),
                qiniuOssProperties.getSecretKey(),
                qiniuOssProperties.getBucket());
    }
}