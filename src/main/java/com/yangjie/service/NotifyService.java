package com.yangjie.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yangjie.bean.MsgTypeEnum;
import com.yangjie.bean.NotifyAuthBean;
import com.yangjie.bean.NotifyMsgBean;
import com.yangjie.bean.ReplyBean;
import com.yangjie.bean.ReplyTextBean;
import com.yangjie.util.JsonUtil;
import com.yangjie.util.XmlUtil;

/**
 * 微信公众号消息
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1445241432
 * @author YangJie 
 * @createTime 2017年11月29日 下午3:29:32
 */
@Service
public class NotifyService {
	
	private Logger logger = LoggerFactory.getLogger(NotifyService.class);
	
	@Value("${wechat.token}")
	private String token;
	
	
	/**
	 * 验证签名
	 * 开发者通过检验signature对请求进行校验。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败。加密/校验流程如下：
	 * 1）将token、timestamp、nonce三个参数进行字典序排序
	 * 2）将三个参数字符串拼接成一个字符串进行sha1加密
	 * 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421135319
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public boolean check(NotifyAuthBean authBean) {
		List<String> list = Arrays.asList(token, authBean.getTimestamp(), authBean.getNonce());
		Collections.sort(list); // 排序
		String str = "";
		for(String s : list) {
			str += s; // 连接字符串
		}
		return authBean.getSignature().equals(DigestUtils.sha1Hex(str));
	}
	
	
	/**
	 * 处理消息通知
	 * @param msg
	 * @return
	 */
	public String dispose(String msg) {
		try {
			NotifyMsgBean msgBean = XmlUtil.toObject(msg, NotifyMsgBean.class);
			logger.info("解析消息：{}", JsonUtil.toJson(msgBean));
			switch (msgBean.getMsgType()) {
			case event: // 事件消息
				return disposeEvent(msgBean); // 处理事件消息
			case text: // 文本消息
				return disposeText(msgBean); // 处理文本消息
			default:
				return packTextReply(msgBean, "木有处理");
			}
		} catch (Exception e) {
			logger.error("处理出现异常: ", e);
		}
		return "success";
	}
	
	/**
	 * 处理事件消息
	 * @param msgMap
	 * @return
	 */
	private String disposeEvent(NotifyMsgBean msgBean) {
		switch (msgBean.getEvent()) {
		case subscribe: // 关注事件
			packTextReply(msgBean, "恭喜你找到组织了!");
		case CLICK: // 自定义菜单事件
			// 此处的key是创建菜单时设置的
			if ("key".equals(msgBean.getEventKey())) {
				return packTextReply(msgBean, "哒哒哒");
			}
		default:
			return packTextReply(msgBean, "事件消息木处理");
		}
	}
	
	/**
	 * 处理文本消息
	 * @param msgMap
	 * @return
	 */
	private String disposeText(NotifyMsgBean msgBean) {
		String content = "文本消息木有处理";
		return packTextReply(msgBean, content);
	}

	/**
	 * 封装文本回复内容
	 * @param msgMap
	 * @param content
	 * @return
	 */
	private String packTextReply(NotifyMsgBean msgBean, String content) {
		ReplyTextBean replyTextBean = new ReplyTextBean();
		replyTextBean.setMsgType(MsgTypeEnum.text);
		replyTextBean.setContent(content);
		return packReply(msgBean, replyTextBean);
	}
	
	/**
	 * 封装回复内容
	 * 
	 * @param msgMap
	 * @param replyMap
	 * @return 
	 */
	private String packReply(NotifyMsgBean msgBean, ReplyBean replyBean) {
		replyBean.setToUserName(msgBean.getFromUserName());
		replyBean.setFromUserName(msgBean.getToUserName());
		replyBean.setCreateTime(System.currentTimeMillis());
		logger.info("回复消息：{}", JsonUtil.toJson(replyBean));
		return XmlUtil.toXml(replyBean);
	}
	
	
}
