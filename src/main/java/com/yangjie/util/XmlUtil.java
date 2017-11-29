package com.yangjie.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlUtil {

	private static final XmlMapper xmlMapper = new XmlMapper();
	
	
	/**
	 * 将对象转换成xml字符串
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String toXml(Object object) {
		try {
			return xmlMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * xml字符串转换成对象
	 * @param xml
	 * @param valueType
	 * @return
	 * @throws Exception
	 */
	public static <T> T toObject(String xml, Class<T> valueType) throws Exception {
		try {
			return xmlMapper.readValue(xml, valueType);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * xml转对象(容器对象类型) 
	 * List<Bean> : (xml, List.class, Bean.class)
	 * Map<Bean1, Bean2> : (xml, Map.class, Bean1.class, Bean2.class)
	 * @param xml
	 * @param parametrized 容器类型
	 * @param parameterClasses 实体类型
	 * @return
	 * @throws Exception
	 */
	public static <T> T toObject(String xml, Class<?> parametrized, Class<?>... parameterClasses) {
		try {
			return xmlMapper.readValue(xml, xmlMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
