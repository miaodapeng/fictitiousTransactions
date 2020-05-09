package com.vedeng.common.validator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.shiro.SpringContextHolder;
import com.vedeng.common.util.Encode;
import com.vedeng.system.service.UserService;

import net.sf.json.JSONArray;

public class CustomSqlistAuthorityImpl  {
	public static final Logger log = LoggerFactory.getLogger(CustomSqlistAuthorityImpl.class);
	private static Map<String, Set<String>> map = Maps.newConcurrentMap();

	/**
	 * 刷新单个人的权限
	 * 
	 * @param key
	 *            用户id+用户名
	 * @param value
	 *            权限
	 */
	public static void clear(String key, Set<String> value) {
		JSONArray jsonArray = JSONArray.fromObject(value);
		HashSet<String> permissionListLocal = new HashSet<String>();
		for (int i = 0; i < jsonArray.size(); i++) {
			String singlePerm = jsonArray.getString(i);
			permissionListLocal.add(singlePerm);
		}
		map.put(key, permissionListLocal);
	}

	// 权限验证
	public boolean hasRoleBySqlId(Map map, String c) {
		try {
			return true;
//			String iduserName = map.get("cook-userName").toString();
//			String decodeIdName = Encode.decode(iduserName);
//			String iduserNames[] = StringUtils.split(decodeIdName, "#");
//			if (iduserNames.length < 2) {
//				return false;
//			}
//			long start = System.currentTimeMillis();
//			UserService userservice = SpringContextHolder.getBean(UserService.class);
//			HashSet<String> permissionListLocal = new HashSet<String>();
//			try {
//				if (!map.containsKey(decodeIdName)) {
//					JSONArray jsonArray = JSONArray.fromObject(JedisUtils.get(userservice.getRedisDbType()
//							+ ErpConst.KEY_PREFIX_USER_PERMISSIONS + iduserNames[1] + iduserNames[0]));
//					log.info("从redis获取权限验证花费时间：" + (System.currentTimeMillis() - start));
//					for (int i = 0; i < jsonArray.size(); i++) {
//						String singlePerm = jsonArray.getString(i);
//						permissionListLocal.add(singlePerm);
//					}
//					map.put(decodeIdName, permissionListLocal);
//				}
//			} catch (Exception e) {
//				log.error("从redis里面获取权限失败，默认使用上一次成功的权限", e);
//			}
//			String uri = map.get("uri").toString();
//			log.info("权限验证花费时间：" + (System.currentTimeMillis() - start));
//			if (permissionListLocal.contains(uri)) {
//				return true;
//			}
//			log.error("该用户无权限" + uri);
//			return false;
		} catch (Exception e) {
			log.error("权限验证失败：", e);
			return false;
		}
	}

}
