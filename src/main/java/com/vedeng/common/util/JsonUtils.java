package com.vedeng.common.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <b>Description:</b><br> json 对象转换工具类
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.common.util
 * <br><b>ClassName:</b> JsonUtils
 * <br><b>Date:</b> 2017年4月26日 上午9:07:10
 */
public class JsonUtils {
	private static ObjectMapper objectMapper;

	static {
		objectMapper = new JsonMapper();
		SimpleSerializers serializers = new SimpleSerializers();
		serializers.addSerializer(new LongToStringSerializer(Long.class));
		objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withAdditionalSerializers(serializers));
	}
	

	/**
	 * <b>Description:</b><br> 对象转换成json
	 * @param obj
	 * @return
	 * @throws IOException
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月26日 上午9:07:20
	 */
	public static String translateToJson(Object obj) throws IOException {
		return objectMapper.writeValueAsString(obj);
	}
    

	/**
	 * <b>Description:</b><br> json 转换成对象
	 * @param json
	 * @param clazz
	 * @return
	 * @throws IOException
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月26日 上午9:07:31
	 */
	public static <T> T readValue(String json, Class<T> clazz) throws IOException {
		return objectMapper.readValue(json, clazz);
	}

	
	/**
	 * <b>Description:</b><br> json 转换成对象
	 * @param json
	 * @param type
	 * @return
	 * @throws IOException
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月26日 上午9:07:43
	 */
	public static <T> T readValueByType(String json, TypeReference<?> type) throws IOException {
		return objectMapper.readValue(json, type);
	}


	/**
	 * <b>Description:</b><br> 获取json 的属性值
	 * @param json
	 * @param name
	 * @return
	 * @throws IOException
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月26日 上午9:07:54
	 */
	public static String readValueByName(String json, String name) throws IOException {
		Map<?, ?> map = objectMapper.readValue(json, Map.class);
		return (String) map.get(name);
	}
	
	@SuppressWarnings("serial")
	private static class LongToStringSerializer extends StdSerializer<Long> {

	    
		protected LongToStringSerializer(Class<Long> t) {
			super(t);
		}

		@Override
		public void serialize(Long value, JsonGenerator jgen, SerializerProvider provider)
				throws IOException, JsonProcessingException {
			jgen.writeString(value.toString());
		}

	}
	
	/**
	 * <b>Description:</b><br> list转字符串
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月11日 上午10:01:39
	 */
	public static String convertConllectionToJsonStr(Collection<?> con){
		JSONArray jsonArray=JSONArray.fromObject(con);
		return jsonArray.toString();
	}
	
	/**
	 * <b>Description:</b><br> 对象转字符串
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月11日 上午10:01:39
	 */
	public static String convertObjectToJsonStr(Object obj){
		JSONObject json=JSONObject.fromObject(obj);
		return json.toString();
	}
	
//	public static List<?> convertJsonStrToList(String jsonStr){
//		JSONArray jsonArray=JSONArray.fromObject(jsonStr);
//		//List<?> list=JSONArray.toCollection(jsonArray, ?.c)
//		return
//	}
	
	
}
