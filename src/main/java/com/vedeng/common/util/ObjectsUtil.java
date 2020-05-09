/**
 * <b>Description: 用于泛型对象的处理工具类</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName ObjectsUtil.java
 * <br><b>Date: 2018年5月2日 上午9:59:37 </b> 
 *
 */
package com.vedeng.common.util;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;


public class ObjectsUtil
{
	public static Logger logger = LoggerFactory.getLogger(ObjectsUtil.class);

	/**
	 * 
	 * <b>Description: 将该对象中String类型的属性根据escapeType=1转义或者escapeType=2还原</b><br> 该对象的String属性须有set或get方法
	 * @param t   该对象
	 * @param escapeType 转义类型: <br>1 将字符串转义 <br> 2 将转义后的字符串还原
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年5月2日 上午11:07:14 </b>
	 */
	public static <T> T resetStringValueByEscapeType(T t, int escapeType) 
	{
		if(null == t)
			return null;
		
		Class<?> cl = t.getClass();
		
		Field[] fs = cl.getDeclaredFields();
		for(Field f : fs)
		{
			// 针对Stirng 
			if(String.class.equals(f.getType()))
			{		
				String value = getFieldValueByFieldName(f, t);
				if(null == value)
					continue;
				// 将获取的String类型值转义
				if(1 == escapeType)
				{					
					setFieldValueByFieldName(t, f, null, CodeEscapeUtil.escapeString(value));
				}
				// 将转义后的字符串还原
				else if(2 == escapeType)
				{
					setFieldValueByFieldName(t, f, null, CodeEscapeUtil.restoreString(value));
				}
			}
		}
			
		return t;
	}
	
	/**
	 * 
	 * <b>Description: 通过属性名称获取该属性的值</b><br> 
	 * @param field  该对象的属性Field
	 * @param o      该对象
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年5月2日 上午10:58:35 </b>
	 */
	public static String getFieldValueByFieldName(Field field, Object o)
	{
		if(null == field || null == o)
			return null;
		try
		{
			// 设置对象的访问权限，保证对private的属性的访问
			field.setAccessible(true);
			return (String) field.get(o);
		}
		catch (Exception e)
		{
			return null;
		}

	}
	
	/**
	 * 
	 * <b>Description: 通过属性名称设置该属性的值</b><br> 
	 * @param o         该对象
	 * @param field		该对象的属性Field
	 * @param fieldName 该对象属性名称
	 * @param value		设置该对象属性的值
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年5月2日 上午10:59:48 </b>
	 */
	public static void setFieldValueByFieldName(Object o, Field field, String fieldName, String value)
	{
		if(null == o)
			return;
		try
		{
			
			if(null == field)
			{
				if(null == fieldName || fieldName.trim().length() == 0)
					return;
				// 获取obj类的字节文件对象
				Class<?> c = o.getClass();
				// 获取该类的成员变量
				field = c.getDeclaredField(fieldName);
				
			}
			// 取消语言访问检查
			field.setAccessible(true);
			// 给变量赋值
			field.set(o, value);
		}
		catch(Exception e)
		{
			logger.error(Contant.ERROR_MSG, e);
		}
		
	}
	
//	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
//	{
//		Goods gs = new Goods();
//		String value = "~!@#$%^&*()_+$%^&*()_%^&*(--";
//		gs.setGoodsName(value);
//		System.out.println("old1 = " + gs.getGoodsName());
//		resetStringValueByEscapeType(gs, 1);
//		System.out.println("new = "+ gs.getGoodsName());
//		resetStringValueByEscapeType(gs, 2);
//		String va = gs.getGoodsName();
//		System.out.println("old2 = "+ gs.getGoodsName());
//		System.out.println("old1:old2 = " + value.equals(va));
//	}
}

