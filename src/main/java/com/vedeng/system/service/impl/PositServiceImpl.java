package com.vedeng.system.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.PositionMapper;
import com.vedeng.authorization.model.Position;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.goods.model.Category;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.PositService;

@Service("positService")
public class PositServiceImpl extends BaseServiceimpl implements PositService {
	public static Logger logger = LoggerFactory.getLogger(PositServiceImpl.class);

	@Autowired
	@Qualifier("positionMapper")
	private PositionMapper positionMapper;

	/**
	 * 根据部门获取职位
	 * @param orgId
	 * @return
	 */
	@Override
	public List<Position> getPositByOrgId(Integer orgId) {
		return positionMapper.getPositByOrgId(orgId);
	}

	/**
	 * 分页搜索职位
	 * @param role
	 * @param page
	 * @return
	 */
	@Override
	public List<Position> querylistPage(Position position, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("position", position);
		map.put("page", page);
		return positionMapper.querylistPage(map);
	}

	/**
	 * 根据主键查询对应详细信息 
	 */
	@Override
	public Position getPositionByKey(Position position) {
		return positionMapper.getPositionByKey(position);
	}

	/**
	 * 新增数据保存
	 */
	@Override
	public ResultInfo<?> insert(Position position) {
		int i = positionMapper.vailPosition(position);
		if(i==0){
			int j = positionMapper.insert(position);
			if(j==1){
				return new ResultInfo<>(0,"操作成功");
			}
		}else{
			return new ResultInfo<>(-1,"职位名称重复，请确认");
		}
		return new ResultInfo<>(-1,"操作失败");
	}

	/**
	 * 修改职位信息
	 */
	@Override
	public ResultInfo<?> update(Position position) {
		int i = positionMapper.vailPosition(position);
		if(i==0){
			int j = positionMapper.update(position);
			if(j==1){
				return new ResultInfo<>(0,"操作成功");
			}
		}else{
			return new ResultInfo<>(-1,"职位名称重复，请确认");
		}
		return new ResultInfo<>(-1,"操作失败");
	}

	/**
	 * 查询用户职位
	 */
	@Override
	public List<Position> getPositionByUserId(Integer userId) {
		return positionMapper.getPositionByUserId(userId);
	}

	/**
	 * 部门集合查询职位 
	 */
	@Override
	public List<Position> getPositByOrgIds(List<Integer> orgIds) {
		return positionMapper.getPositByOrgIds(orgIds);
	}

	@Override
	public Integer deletePosit(Position posit) {
		return positionMapper.delete(posit.getPositionId());
	}

	@Override
	public List<SysOptionDefinition> getPositType(Integer id) {
		List<SysOptionDefinition> list = null;
		
		SysOptionDefinition sod = new SysOptionDefinition();
		sod.setScope(id);
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<SysOptionDefinition>>> TypeRef = new TypeReference<ResultInfo<List<SysOptionDefinition>>>() {};
		String url=httpUrl + "sysoptiondefinition/getoptionbyscope.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, sod,clientId,clientKey, TypeRef);
			list = (List<SysOptionDefinition>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}
}
