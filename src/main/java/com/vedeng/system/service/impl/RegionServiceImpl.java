package com.vedeng.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vedeng.authorization.dao.RegionMapper;
import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.vo.RegionVo;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.system.service.RegionService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("regionService")
public class RegionServiceImpl extends BaseServiceimpl implements RegionService {

	@Autowired
	@Qualifier("regionMapper")
	private RegionMapper regionMapper;

	@SuppressWarnings("unchecked")
	@Override
	public List<Region> getRegionByParentId(Integer parentId) {
		List<Region> list = null;
		if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_REGION_LIST + parentId)) {
			String json = JedisUtils.get(dbType + ErpConst.KEY_PREFIX_REGION_LIST + parentId);
			JSONArray jsonArray = JSONArray.fromObject(json);
			list = (List<Region>) JSONArray.toCollection(jsonArray, Region.class);
		} else {
			list = regionMapper.getRegionByParentId(parentId);
			JedisUtils.set(dbType + ErpConst.KEY_PREFIX_REGION_LIST + parentId,
					JsonUtils.convertConllectionToJsonStr(list), ErpConst.ZERO);
		}
		return list;
	}

	@Override
	public Object getRegion(Integer regionId, Integer returnType) {
		if (regionId <= 0) {
			return null;
		}

		List<Region> returnList = new ArrayList<Region>();
		List<String> regionName = new ArrayList<>();
		String returnString = "";

		// 查询当前地区信息
		Region region = null;
		if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_REGION_OBJECT + regionId)) {
			String json = JedisUtils.get(dbType + ErpConst.KEY_PREFIX_REGION_OBJECT + regionId);
			JSONObject jsonObject = JSONObject.fromObject(json);
			region = (Region) JSONObject.toBean(jsonObject, Region.class);
		} else {
			region = regionMapper.getRegionById(regionId);
		}

		if(region != null){
			Integer parentId = region.getParentId();
			returnList.add(region);
			regionName.add(region.getRegionName());
			do {
				Region regionInfo = null;
				if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_REGION_OBJECT + parentId)) {
					String json = JedisUtils.get(dbType + ErpConst.KEY_PREFIX_REGION_OBJECT + parentId);
					JSONObject jsonObject = JSONObject.fromObject(json);
					regionInfo = (Region) JSONObject.toBean(jsonObject, Region.class);
				} else {
					regionInfo = regionMapper.getRegionById(parentId);
				}
				parentId = regionInfo.getParentId();
				returnList.add(regionInfo);
				regionName.add(regionInfo.getRegionName());
			} while (parentId > 0);

			Collections.reverse(returnList);
			Collections.reverse(regionName);

			for (String name : regionName) {
				returnString += name + " ";
			}
		}

		/*
		 * Region province,city; switch(region.getRegionType()){ case 1://省
		 * returnList.add(region); returnString = region.getRegionName(); break; case
		 * 2://市 province = regionMapper.getRegionById(region.getParentId());
		 * returnList.add(province); returnList.add(region); returnString =
		 * province.getRegionName() + " " + region.getRegionName(); break; case 3://区
		 * city = regionMapper.getRegionById(region.getParentId()); province =
		 * regionMapper.getRegionById(city.getParentId()); returnList.add(province);
		 * returnList.add(city); returnList.add(region); returnString =
		 * province.getRegionName() + " " + city.getRegionName() + " " +
		 * region.getRegionName(); break; default:// returnList.add(region);
		 * returnString += region.getRegionName(); break; }
		 */

		if (returnType == 1) {
			return returnList;
		} else {
			return returnString;
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 根据主键查询
	 * 
	 * @param RegionId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年10月13日 上午11:14:20
	 */
	@Override
	public Region getRegionByRegionId(Integer regionId) {
		Region region = null;
		if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_REGION_OBJECT + regionId)) {
			String json = JedisUtils.get(dbType + ErpConst.KEY_PREFIX_REGION_OBJECT + regionId);
			if (json == null || "".equals(json)) {
				return null;
			}
			JSONObject jsonObject = JSONObject.fromObject(json);
			region = (Region) JSONObject.toBean(jsonObject, Region.class);
		} else {
			region = regionMapper.getRegionById(regionId);
			if (region != null) {
				JedisUtils.set(dbType + ErpConst.KEY_PREFIX_REGION_OBJECT + regionId,
						JsonUtils.convertObjectToJsonStr(region), ErpConst.ZERO);
			}
		}
		return region;
	}

	@Override
	public Region getRegion(Region region) {
		return regionMapper.getRegion(region);
	}

	/**
	 * <b>Description:</b><br>
	 * 查询分页
	 * 
	 * @param region
	 * @param page
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年3月16日 上午10:57:06
	 */
	@Override
	public List<Region> getRegionListPage(Region region, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("region", region);
		return regionMapper.getRegionListPage(map);
	}

	/**
	 * <b>Description:</b><br>
	 * 新增保存
	 * 
	 * @param regionVo
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年3月16日 下午1:39:06
	 */
	@Transactional
	@Override
	public int saveRegin(RegionVo regionVo) {
		if (regionVo != null && ObjectUtils.isEmpty(regionVo.getZone())
				&& ObjectUtils.notEmpty(regionVo.getCityName())) {// 添加市
			// 验证当前添加城市是否已存在
			Region region = new Region();
			region.setParentId(regionVo.getProvince());
			region.setRegionName(regionVo.getCityName());
			region.setRegionType(2);
			List<Region> cityList = regionMapper.getRegionByParam(region);
			if (cityList != null && cityList.size() > 0) {
				return -1;// 城市已存在
			}
			int i = regionMapper.insert(region);
			if (i > 0) {
				JedisUtils.del(dbType + ErpConst.KEY_PREFIX_REGION_LIST + regionVo.getProvince());

				return region.getRegionId();
			}
			return 0;

		} else if (regionVo != null && ObjectUtils.notEmpty(regionVo.getZone())) {
			// 验证当前添加区县是否已存在
			Region region = new Region();
			region.setParentId(regionVo.getCity());
			region.setRegionName(regionVo.getZone());
			region.setRegionType(3);
			List<Region> zoneList = regionMapper.getRegionByParam(region);
			if (zoneList != null && zoneList.size() > 0) {
				return -2;// 区县已存在
			}
			int i = regionMapper.insert(region);
			if (i > 0) {
				JedisUtils.del(dbType + ErpConst.KEY_PREFIX_REGION_LIST + regionVo.getCity());
				return region.getRegionId();
			}
			return 0;
		}
		return 0;
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑保存
	 * 
	 * @param regionVo
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年3月16日 下午1:39:06
	 */
	@Transactional
	@Override
	public int saveEditRegin(Region region) {
		int i = regionMapper.update(region);
		if (i > 0) {
			JedisUtils.del(dbType + ErpConst.KEY_PREFIX_REGION_LIST + region.getParentId());
			JedisUtils.del(dbType + ErpConst.KEY_PREFIX_REGION_OBJECT + region.getRegionId());
		}
		return i;
	}

	@Override
	public RegionVo getRegionByArea(String area) {
		String[] addressArr = area.split(" ");

		RegionVo regionVo = new RegionVo();
		if (null != addressArr && null != addressArr[0] && !addressArr[0].equals("")) {
			regionVo.setProvinceName(addressArr[0]);
			if (null != addressArr && null != addressArr[1] && !addressArr[1].equals("")) {
				regionVo.setCityName(addressArr[1]);
				if (null != addressArr && null != addressArr[2] && !addressArr[2].equals("")) {
					regionVo.setZone(addressArr[2]);
				}
			}
		} else {
			return null;
		}

		return regionMapper.getRegionByArea(regionVo);
	}

	@Override
	public String getRegionIdStringByMinRegionId(Integer minRegionId) {
		if (null == minRegionId) {
			return "";
		}
		// 根据当前地区ID查询省市区的ID以,分割
		return regionMapper.getRegionIdStringByMinRegionId(minRegionId);
	}

	@Override
	public List<RegionVo> getCityList(RegionVo regionVo) {
		regionVo.setCityEnd(35);
		List<RegionVo> list = regionMapper.getCityList(regionVo);
		return list;
	}

	@Override
	public String getRegionNameStringByMinRegionIds(String regionIds) {
		String[] regionIdArray = StringUtils.split(regionIds, ",");
		StringBuilder sb = new StringBuilder();
		if (ArrayUtils.isNotEmpty(regionIdArray)) {
			for (String regionId : regionIdArray) {
				Region re = getRegionByRegionId(NumberUtils.toInt(regionId));
				sb.append(re.getRegionName() + " ");
			}
			return sb.toString();
		}
		return "N/A";
	}

}
