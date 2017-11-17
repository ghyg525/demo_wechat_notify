package com.yangjie.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yangjie.controller.NotifyController;
import com.yangjie.util.XmlUtil;

@Service
public class NotifyService {
	
	private Logger logger = LoggerFactory.getLogger(NotifyController.class);
	
	@Value("${wechat.token}")
	private String token;
	
	
	/**
	 * 验证签名
	 * 开发者通过检验signature对请求进行校验。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败。加密/校验流程如下：
	 * 1）将token、timestamp、nonce三个参数进行字典序排序
	 * 2）将三个参数字符串拼接成一个字符串进行sha1加密
	 * 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public boolean check(String signature, String timestamp, String nonce) {
		List<String> list = Arrays.asList(token, timestamp, nonce);
		Collections.sort(list); // 排序
		String str = "";
		for(String s : list) {
			str += s; // 连接字符串
		}
		return signature.equals(DigestUtils.sha1Hex(str));
	}
	
	
	/**
	 * 处理消息通知
	 * @param msg
	 * @return
	 */
	public String dispose(String msg) {
		try { // 此处暂时用map进行参数解析, 后面有时间转成bean
			Map<String, String> msgMap = XmlUtil.toObject(msg, HashMap.class, Map.class, String.class, String.class);
			logger.info("收到推送消息：{}", msgMap);
			String msgType = msgMap.get("MsgType");
			if ("event".equals(msgType)) {
				return disposeEvent(msgMap); // 处理事件消息
			}else if ("text".equals(msgType)) {
				return disposeText(msgMap); // 处理文本消息
			}else {
				return packTextReply(msgMap, "木有处理");
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
	private String disposeEvent(Map<String, String> msgMap) {
		if ("subscribe".equals(msgMap.get("Event"))) { // 关注事件
			return packTextReply(msgMap, "恭喜你找到组织了!");
		}
		if ("CLICK".equals(msgMap.get("Event"))) { // 自定义菜单事件
			if ("点击".equals(msgMap.get("EventKey"))) {
				return packTextReply(msgMap, "哒哒哒");
			}
			if ("图片".equals(msgMap.get("EventKey"))) {
				return packImageReply(msgMap, "");
			}
		}
		return packTextReply(msgMap, "事件消息木处理");
	}
	
	/**
	 * 处理文本消息
	 * @param msgMap
	 * @return
	 */
	private String disposeText(Map<String, String> msgMap) {
		String content = "文本消息木有处理";
		return packTextReply(msgMap, content);
	}

	/**
	 * 封装文本回复内容
	 * @param msgMap
	 * @param content
	 * @return
	 */
	private String packTextReply(Map<String, String> msgMap, String content) {
		Map<String, String> replyMap = new HashMap<String, String>();
		replyMap.put("MsgType", "text"); // 消息类型
		replyMap.put("Content", content); // 消息内容
		return packReply(msgMap, replyMap);
	}
	
	/**
	 * 封装图片回复内容
	 * @param msgMap
	 * @return
	 */
	private String packImageReply(Map<String, String> msgMap, String imageId) {
		Map<String, String> replyMap = new HashMap<String, String>();
		replyMap.put("MsgType", "image"); // 消息类型
		replyMap.put("Image", "<MediaId><![CDATA["+imageId+"]]></MediaId>"); // 图片id，图片需要通过接口上传
		return packReply(msgMap, replyMap);
	}
	
	/**
	 * 封装回复内容
	 * 
	 * @param msgMap
	 * @param replyMap
	 * @return 
	 */
	private String packReply(Map<String, String> msgMap, Map<String, String> replyMap) {
		replyMap.put("ToUserName", msgMap.get("FromUserName"));
		replyMap.put("FromUserName", msgMap.get("ToUserName"));
		replyMap.put("CreateTime", String.valueOf(System.currentTimeMillis()));
		logger.info("回复消息内容：{}", replyMap);
		return mapToXml(replyMap);
	}
	
	
	
	/**
	 * 获取xml格式参数字符串
	 * @param paramMap
	 * @return
	 */
	private String mapToXml(Map<String, String> paramMap) {
		StringBuilder xmlBuilder = new StringBuilder("<xml>");
		for (String key : paramMap.keySet()) {
			if (key != null && paramMap.get(key)!=null && !paramMap.get(key).isEmpty()) {
				xmlBuilder.append("<").append(key).append(">").append(paramMap.get(key))
					.append("</").append(key).append(">");
			}
		}
		xmlBuilder.append("</xml>");
		return xmlBuilder.toString();
	}

}
