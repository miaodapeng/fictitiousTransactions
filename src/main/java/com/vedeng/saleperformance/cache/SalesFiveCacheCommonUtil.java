
package com.vedeng.saleperformance.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * <b>Description: 销售五行缓存类</b><br>按照天的时间范围来设置 
 * <b>Author: Franlin</b> 
 * @fileName SalesFiveCacheCommonUtil.java
 * <br><b>Date: 2018年6月14日 上午9:31:26 </b> 
 *
 */
public class SalesFiveCacheCommonUtil
{
	/**
	 * 销售个人五行缓存通用Map
	 * key: 
	 */
	public static Map<String, Object> cacheMap = new ConcurrentHashMap<String, Object>();
	
	/**
	 * 
	 * <b>Description: 添加</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月30日 13:58
	 */
	public static void put(String key, Object data)
	{
		cacheMap.put(key, data);
	}
	
	/**
	 * 
	 * <b>Description: 获取</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月30日 13:58
	 */
	public static Object get(String key)
	{
		return cacheMap.get(key);
	}
	
	/**
	 * 
	 * <b>Description: 清空缓存map</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月30日 13:58
	 */
	public static void clear()
	{
		cacheMap.clear();
	}
	
	
}

