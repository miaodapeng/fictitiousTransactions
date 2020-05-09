package com.vedeng.common.util;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * <b>Description:</b><br> 复写redisTemplate的序列化、反序列化操作
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.common.util
 * <br><b>ClassName:</b> CollectionSerializer
 * <br><b>Date:</b> 2017年5月3日 下午1:38:34
 */
public class CollectionSerializer<T extends Serializable> implements RedisSerializer<T> {
	private CollectionSerializer() {
	}

	public static volatile CollectionSerializer<Serializable> collectionSerializer = null;

	public static CollectionSerializer<Serializable> getInstance() {
		if (collectionSerializer == null) {
			synchronized (CollectionSerializer.class) {
				if (collectionSerializer == null) {
					collectionSerializer = new CollectionSerializer<>();
				}
			}
		}
		return collectionSerializer;
	}

	@Override
	public byte[] serialize(T t) throws SerializationException {

		return SerializationUtils.serialize(t);
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {

		return SerializationUtils.deserialize(bytes);
	}

}
