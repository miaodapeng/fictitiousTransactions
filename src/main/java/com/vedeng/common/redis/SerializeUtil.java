package com.vedeng.common.redis;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列表与反序列化，自定义对象添加到缓存中时使用
 * 
 * @author Administrator
 *
 */
public class SerializeUtil {
	public static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

	public static byte[] serialize(Object object) {
		if (object == null) {
			throw new NullPointerException("Can't serialize null");
		}
		byte[] bytes = null;
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
//			oos.close();
//			baos.close();
			bytes = baos.toByteArray();
		} catch (Exception e) {
			throw new IllegalArgumentException("Non-serializable object", e);
		} finally {
			try {
				if (oos != null)
					oos.close();
				if (baos != null)
					baos.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return bytes;
	}

	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		} finally {
			try {
				if (ois != null)
					ois.close();
				if (bais != null)
					bais.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
}
