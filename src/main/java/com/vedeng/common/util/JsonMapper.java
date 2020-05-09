package com.vedeng.common.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * <b>Description:</b><br> 自定义 ObjectMapper
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.common.util
 * <br><b>ClassName:</b> JsonMapper
 * <br><b>Date:</b> 2017年4月26日 上午9:08:09
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class JsonMapper extends ObjectMapper{
	 
	private static final long serialVersionUID = -5106109699769842408L;
	
	public JsonMapper() {
		 //忽略位置的属性
		 this.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		 //设置时间格式
		 this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		 //将null 值 转换成空字符串
		 this.getSerializerProvider().setNullValueSerializer(new JsonSerializer() {
			public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider)
					throws IOException, JsonProcessingException {
				jgen.writeString("");
			}
		});
		 
		 //空字符串 反序列时 转换为null
		 this.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
	}
	
}
