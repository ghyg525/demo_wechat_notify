package com.yangjie.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yangjie.bean.NotifyAuthBean;
import com.yangjie.service.NotifyService;
import com.yangjie.util.JsonUtil;

@RestController
@RequestMapping("/notify")
public class NotifyController {

	private Logger logger = LoggerFactory.getLogger(NotifyController.class);
	
	@Autowired
	private NotifyService notifyService;  
	
	
	/**
	 * 初始验证
	 * @param authBean 
	 * @return
	 */
	@GetMapping
	public String notify(NotifyAuthBean authBean){
		logger.info("收到验证消息: {}", JsonUtil.toJson(authBean));
		if (Objects.nonNull(authBean.getSignature()) && Objects.nonNull(authBean.getTimestamp()) 
				&& Objects.nonNull(authBean.getNonce()) && notifyService.check(authBean)) {
			return authBean.getEchostr(); // 验证通过原样返回echostr
		}
		return null;
	}
	
	/**
	 * 处理微信消息通知
	 * @param msg
	 * @return
	 */
	@PostMapping
	public String notify(@RequestBody String msg){
		logger.info("收到通知消息: {}", msg);
		return notifyService.dispose(msg);
	}
	
}
