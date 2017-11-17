package com.yangjie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yangjie.service.NotifyService;

@RestController
@RequestMapping("/notify")
public class NotifyController {
	
	@Autowired
	private NotifyService notifyService;  
	
	
	/**
	 * 初始验证
	 * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @param echostr 随机字符串
	 * @return
	 */
	@GetMapping
	public String notify(String signature, String timestamp, String nonce, String echostr){
		if (signature!=null && timestamp!=null && nonce!=null && notifyService.check(signature, timestamp, nonce)) {
			return echostr; // 验证通过原样返回echostr
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
		return notifyService.dispose(msg);
	}
	
}
