package com.yangjie.bean;

/**
 * 事件类型
 * @author YangJie 
 * @createTime 2017年11月17日 下午9:23:55
 */
public enum EventEnum {

	/** 订阅 */
	subscribe, 
	/** 取消订阅 */
	unsubscribe,
	
	/** 自定义菜单事件 */
	/** 拉取消息 */
	CLICK,
	/** 跳转链接 */
	VIEW,
	/** 扫码推事件 */
	scancode_push,
	/** 扫码等消息 */
	scancode_waitmsg,
	/** 拍照发图 */
	pic_sysphoto,
	/** 拍照或者相册发图 */
	pic_photo_or_album,
	/** 微信相册发图器 */
	pic_weixin,
	/** 地理位置选择器 */
	location_select,
	
	;
	
}
