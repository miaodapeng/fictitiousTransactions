/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.vedeng.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.common.constants.Contant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对象操作工具类, 继承org.apache.commons.lang3.ObjectUtils类
 * @author ThinkGem
 * @version 2014-6-29
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {
	public static Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

	/**
	 * 注解到对象复制，只复制能匹配上的方法。
	 * @param annotation
	 * @param object
	 */
	public static void annotationToObject(Object annotation, Object object){
		if (annotation != null){
			Class<?> annotationClass = annotation.getClass();
			Class<?> objectClass = object.getClass();
			for (Method m : objectClass.getMethods()){
				if (StringUtils.startsWith(m.getName(), "set")){
					try {
						String s = StringUtils.uncapitalize(StringUtils.substring(m.getName(), 3));
						Object obj = annotationClass.getMethod(s).invoke(annotation);
						if (obj != null && !"".equals(obj.toString())){
							if (object == null){
								object = objectClass.newInstance();
							}
							m.invoke(object, obj);
						}
					} catch (Exception e) {
						// 忽略所有设置失败方法
					}
				}
			}
		}
	}
	
	/**
	 * 序列化对象
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			if (object != null){
				baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(object);
				return baos.toByteArray();
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	/**
	 * 反序列化对象
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			if (bytes != null && bytes.length > 0){
				bais = new ByteArrayInputStream(bytes);
				ObjectInputStream ois = new ObjectInputStream(bais);
				return ois.readObject();
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}
	 /** 
     * 序列化 list 集合 
     *  
     * @param list 
     * @return 
     */  
    public static byte[] serializeList(List<?> list) {  
  
        if (list==null||list.size()==0) {  
            return null;  
        }  
        ObjectOutputStream oos = null;  
        ByteArrayOutputStream baos = null;  
        byte[] bytes = null;  
        try {  
            baos = new ByteArrayOutputStream();  
            oos = new ObjectOutputStream(baos);  
            for (Object obj : list) {  
                oos.writeObject(obj);  
            }  
            bytes = baos.toByteArray();  
        } catch (Exception e) {  
            logger.error(Contant.ERROR_MSG, e);
        } finally {  
            close(oos);  
            close(baos);  
        }  
        return bytes;  
    }  
  
    /** 
     * 反序列化 list 集合 
     *  
     * @param lb 
     * @return 
     */  
    public static List<?> unserializeList(byte[] bytes) {  
        if (bytes == null) {  
            return null;  
        }  
  
        List<Object> list = new ArrayList<Object>();  
        ByteArrayInputStream bais = null;  
        ObjectInputStream ois = null;  
        try {  
            // 反序列化  
            bais = new ByteArrayInputStream(bytes);  
            ois = new ObjectInputStream(bais);  
            while (bais.available() > 0) {  
                Object obj = (Object) ois.readObject();  
                if (obj == null) {  
                    break;  
                }  
                list.add(obj);  
            }  
        } catch (Exception e) {  
            logger.error(Contant.ERROR_MSG, e);
        } finally {  
            close(bais);  
            close(ois);  
        }  
        return list;  
    }  
    
    /** 
     * 关闭io流对象 
     *  
     * @param closeable 
     */  
    public static void close(Closeable closeable) {  
        if (closeable != null) {  
            try {  
                closeable.close();  
            } catch (Exception e) {  
                logger.error(Contant.ERROR_MSG, e);
            }  
        }  
    }
    
    /**
     * <b>Description:</b><br> 判断整形数据不为null也不为0
     * @param num
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年8月10日 下午3:40:23
     */
    public static boolean notEmpty(Integer num){
    	if(num != null && num != 0){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /**
     * <b>Description:</b><br> 判断字符串不为null也不为""
     * @param num
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年8月10日 下午3:40:23
     */
    public static boolean notEmpty(String obj){
    	if(obj != null && !"".equals(obj)){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /**
     * <b>Description:</b><br> 判断为空
     * @param num
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年8月10日 下午3:42:05
     */
    public static boolean isEmpty(Integer num){
    	if(num == null || num == 0){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /**
     * <b>Description:</b><br> 判断为空
     * @param num
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年8月10日 下午3:42:05
     */
    public static boolean isEmpty(String obj){
    	if(obj == null || "".equals(obj)){
    		return true;
    	}else{
    		return false;
    	}
    }
}
