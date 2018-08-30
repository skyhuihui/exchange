/**
 * 
 */
package com.zag.core.ali.oss.config;

import java.util.ResourceBundle;

/**
 * oss 属性配置类
 * @author stone
 * @date 2017年8月16日
 * @reviewer 
 */
public interface OssConfig {

	ResourceBundle rb = ResourceBundle.getBundle("oss_config");

	String endpoint = rb.getString("endpoint");

	String accessKeyId = rb.getString("accessKeyId");

	String accessKeySecret = rb.getString("accessKeySecret");

	String defaultBucketName = rb.getString("bucketName");
}
