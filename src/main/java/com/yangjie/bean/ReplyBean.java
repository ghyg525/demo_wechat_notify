package com.yangjie.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * 微信返回实体
 * @author YangJie 
 * @createTime 2017年11月17日 下午9:19:49
 */
public class ReplyBean {

	/** 开发者微信号 */
	@JacksonXmlProperty(localName="ToUserName")
	private String toUserName;
	/** 发送方帐号（一个OpenID） */
	@JacksonXmlProperty(localName="FromUserName")
	private String fromUserName;
	/** 消息创建时间 （整型） */
	@JacksonXmlProperty(localName="CreateTime")
	private long createTime;
	
	
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
		
}
