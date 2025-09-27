package com.eatwhat.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "eatwhat.qiniuoss")
@Data
public class QiniuOssProperties {

    private String accessKey;
    private String secretKey;
    private String bucket;

}