package com.vedeng.common.cache;

import com.vedeng.common.cache.ehcache.CacheHelper;

/**
 * 缓存工具类
 */
public class CacheUtils {
	private static CacheHelper helper = null;

	/**
	 *
	 * @param cacheName
	 * @return
	 */
	private static CacheHelper getCacheHelper() {
		if (helper == null) {
			helper = CacheHelper.getCache("erpCache");
		}
		return helper;
	}

	public static boolean exists(Object key) {
		return getCacheHelper().exists(key);
	}

	public static <T> T get(Object key) {
		return getCacheHelper().get(key);
	}

	public static boolean put(Object key, Object value) {
		return getCacheHelper().put(key, value);
	}

	public static boolean remove(Object key) {
		return getCacheHelper().remove(key);
	}

	public static long getMemoryStoreSize(Object key){
	    return getCacheHelper().getMemoryStoreSize();
	}

}
