package com.yangjie.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 微信返回文本实体
 * @author YangJie 
 * @createTime 2017年11月17日 下午9:19:49
 */
@JacksonXmlRootElement(localName="xml")
public class ReplyTextBean extends ReplyBean{

	/** 消息类型 */
	@JacksonXmlProperty(localName="MsgType")
	private MsgTypeEnum msgType;
	/** 消息内容 */
	@JacksonXmlCData
	@JacksonXmlProperty(localName="Content")
	private String content;
	
	public MsgTypeEnum getMsgType() {
		return msgType;
	}
	public void setMsgType(MsgTypeEnum msgType) {
		this.msgType = msgType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
