package com.yangjie.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * 微信通知实体
 * @author YangJie 
 * @createTime 2017年11月17日 下午9:19:49
 */
public class NotifyMsgBean {

	/** 开发者微信号 */
	@JacksonXmlProperty(localName="ToUserName")
	private String toUserName;
	/** 发送方帐号（一个OpenID） */
	@JacksonXmlProperty(localName="FromUserName")
	private String fromUserName;
	/** 消息创建时间 （整型） */
	@JacksonXmlProperty(localName="CreateTime")
	private int createTime;
	/** 消息类型 {@link MsgTypeEnum} */
	@JacksonXmlProperty(localName="MsgType")
	private MsgTypeEnum msgType;
	
	// 文本消息特有
	/** 文本消息内容 */
	@JacksonXmlProperty(localName="Content")
	private String content;
	/** 消息id，64位整型 */
	@JacksonXmlProperty(localName="MsgId")
	private long msgId;
	
	// 事件消息特有
	/** 事件类型 {@link EventEnum} */
	@JacksonXmlProperty(localName="Event")
	private EventEnum event;
	/** 事件KEY值 */
	@JacksonXmlProperty(localName="EventKey")
	private String eventKey;
	/** 扫描信息 */
	@JacksonXmlProperty(localName="ScanCodeInfo")
	private ScanCodeInfo scanCodeInfo;
	
	/**
	 * 扫码菜单通知参数
	 * @author YangJie 
	 * @createTime 2017年11月17日 下午9:19:43
	 */
	public class ScanCodeInfo{
		/** 扫描类型 {@link ScanTypeEnum}*/
		@JacksonXmlProperty(localName="ScanType")
		private String scanType;
		/** 扫描结果 */
		@JacksonXmlProperty(localName="ScanResult")
		private String scanResult;
		
		public String getScanType() {
			return scanType;
		}
		public void setScanType(String scanType) {
			this.scanType = scanType;
		}
		public String getScanResult() {
			return scanResult;
		}
		public void setScanResult(String scanResult) {
			this.scanResult = scanResult;
		}
		
	}
	
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
	public MsgTypeEnum getMsgType() {
		return msgType;
	}
	public void setMsgType(MsgTypeEnum msgType) {
		this.msgType = msgType;
	}
	public EventEnum getEvent() {
		return event;
	}
	public void setEvent(EventEnum event) {
		this.event = event;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	public int getCreateTime() {
		return createTime;
	}
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getMsgId() {
		return msgId;
	}
	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}
	public ScanCodeInfo getScanCodeInfo() {
		return scanCodeInfo;
	}
	public void setScanCodeInfo(ScanCodeInfo scanCodeInfo) {
		this.scanCodeInfo = scanCodeInfo;
	}
	
}
