package com.zag.service;

/**
 * 动态系统配置,从zookeeper加载,如果加载失败,应该有默认配置
 * 搬运自原系统,抽离自SystemConfigMonitor.SignConfig
 * @author stone
 * @since 2017年8月4日
 * @usage 
 * @reviewer
 */
public class SystemConfig {
	public final static String ZNODE = "/zag/config/system";
	
	public SystemConfig(){}
	/**
	 * 是否开启签名
	 */
	private Boolean openSign = true;

	public Boolean getOpenSign() {
		return openSign;
	}

	public void setOpenSign(Boolean openSign) {
		this.openSign = openSign;
	}
	
	
	
	
}
