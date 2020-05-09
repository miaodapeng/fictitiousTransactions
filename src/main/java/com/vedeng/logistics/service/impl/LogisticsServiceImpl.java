package com.vedeng.logistics.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.model.User;
import com.vedeng.common.cache.CacheUtils;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.logistics.dao.LogisticsMapper;
import com.vedeng.logistics.model.Logistics;
import com.vedeng.logistics.service.LogisticsService;

@Service("logisticsService")
public class LogisticsServiceImpl extends BaseServiceimpl implements LogisticsService {
    public static Logger logger = LoggerFactory.getLogger(LogisticsServiceImpl.class);

	@Autowired
	@Qualifier("logisticsMapper")
	private LogisticsMapper logisticsMapper;
	

    @SuppressWarnings("unchecked")
    @Override
    public List<Logistics> getLogisticsList(Integer companyId) {
        List<Logistics> lList = null;
        List<Logistics> logisticsList = new ArrayList<>();
        // if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_LOGISTICS_LIST+companyId)){
        // JSONArray jsonaArray =
        // JSONArray.fromObject(JedisUtils.get(dbType+ErpConst.KEY_PREFIX_LOGISTICS_LIST+companyId));
        // lList = (List<Logistics>) JSONArray.toCollection(jsonaArray, Logistics.class);
        if (CacheUtils.exists("logisticsList" + companyId)) {
            lList = CacheUtils.get("logisticsList" + companyId);
            for (Logistics l : lList) {
                if (l.getIsEnable() != 0) {
                    logisticsList.add(l);
                }
            }
        }
        else {
            // 调用接口补充快递单信息
            String url = httpUrl + "logistics/express/getlogisticsinfo.htm";

            // 定义反序列化 数据格式

            final TypeReference<ResultInfo<List<Logistics>>> TypeRef =
                    new TypeReference<ResultInfo<List<Logistics>>>() {};
            try {
                ResultInfo<?> result =
                        (ResultInfo<?>) HttpClientUtils.post(url, companyId, clientId, clientKey, TypeRef);
                logisticsList = (List<Logistics>) result.getData();
                // addLogisticsListToRedis(logisticsList,companyId);
                addLogisticsListToEhcache(logisticsList, companyId);
            }
            catch (IOException e) {
                logger.error(Contant.ERROR_MSG, e);
                return null;
            }
        }
        return logisticsList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Logistics> getAllLogisticsList(Integer companyId) {
        List<Logistics> logisticsList = new ArrayList<>();
        // if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_LOGISTICS_LIST+companyId)){
        // JSONArray jsonaArray =
        // JSONArray.fromObject(JedisUtils.get(dbType+ErpConst.KEY_PREFIX_LOGISTICS_LIST+companyId));
        // logisticsList = (List<Logistics>) JSONArray.toCollection(jsonaArray, Logistics.class);
        if (CacheUtils.exists("logisticsList" + companyId)) {
            logisticsList = CacheUtils.get("logisticsList" + companyId);
        }
        else {
            // 调用接口补充快递单信息
            String url = httpUrl + "logistics/express/getlogisticsinfo.htm";

            // 定义反序列化 数据格式

            final TypeReference<ResultInfo<List<Logistics>>> TypeRef =
                    new TypeReference<ResultInfo<List<Logistics>>>() {};
            try {
                ResultInfo<?> result =
                        (ResultInfo<?>) HttpClientUtils.post(url, companyId, clientId, clientKey, TypeRef);
                logisticsList = (List<Logistics>) result.getData();
                // addLogisticsListToRedis(logisticsList,companyId);
                addLogisticsListToEhcache(logisticsList, companyId);
            }
            catch (IOException e) {
                logger.error(Contant.ERROR_MSG, e);
                return null;
            }
        }
        return logisticsList;
    }

    /**
     * <b>Description:</b><br>
     * 将查询到的物流公司信息保存到ehcache
     * 
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年7月27日 下午3:38:25
     */
    private void addLogisticsListToEhcache(List<Logistics> logisticsList, Integer companyId) {
        if (logisticsList != null && logisticsList.size() > 0) {
            // 前置key加所属公司的id
            CacheUtils.put("logisticsList" + companyId, logisticsList);
        }

    }

    /**
     * <b>Description:</b><br>
     * 将查询到的物流公司信息保存到redis
     * 
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年7月27日 下午3:38:25
     */
    private void addLogisticsListToRedis(List<Logistics> logisticsList, Integer companyId) {
        if (logisticsList != null && logisticsList.size() > 0) {
            // 前置key加所属公司的id
            if (!JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_LOGISTICS_LIST + companyId)) {
                JedisUtils.set(dbType + ErpConst.KEY_PREFIX_LOGISTICS_LIST + companyId,
                        JsonUtils.convertConllectionToJsonStr(logisticsList), ErpConst.ZERO);
            }
        }

    }

    /**
     * <b>Description:</b><br>
     * 保存新增或编辑的快递公司
     * 
     * @param user
     * @param logistics
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 上午10:55:02
     */
    @Override
    public ResultInfo<?> saveOrUpdateLogistice(User user, Logistics logistics) {
        if (logistics == null) {
            return new ResultInfo<>();
        }
        if (ObjectUtils.isEmpty(logistics.getLogisticsId())) {
            logistics.setAddTime(DateUtil.sysTimeMillis());
            logistics.setCreator(user.getUserId());
            logistics.setIsEnable(1);
            logistics.setModTime(DateUtil.sysTimeMillis());
            logistics.setUpdater(user.getUserId());
        }
        logistics.setCompanyId(user.getCompanyId());
        logistics.setModTime(DateUtil.sysTimeMillis());
        logistics.setUpdater(user.getUserId());
        // redis获取现有的公司快递列表
        List<Logistics> logisticsList = null;
        // if(JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_LOGISTICS_LIST + user.getCompanyId())){
        // JSONArray jsonaArray = JSONArray.fromObject(JedisUtils.get(dbType+ErpConst.KEY_PREFIX_LOGISTICS_LIST +
        // user.getCompanyId()));
        // logisticsList = (List<Logistics>) JSONArray.toCollection(jsonaArray, Logistics.class);
        // }
        if (CacheUtils.exists("logisticsList" + user.getCompanyId())) {
            logisticsList = CacheUtils.get("logisticsList" + user.getCompanyId());
        }
        if (ObjectUtils.isEmpty(logistics.getLogisticsId()) && logisticsList != null && logisticsList.size() > 0) {
            for (Logistics log : logisticsList) {
                if (log.getName().equals(logistics.getName())) {
                    return new ResultInfo<>(-1, "快递公司名称不允许重复！");
                }
            }
        }
        String url = httpUrl + ErpConst.SAVE_LOGISTICE;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, logistics, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                // JedisUtils.del(dbType + ErpConst.KEY_PREFIX_LOGISTICS_LIST + user.getCompanyId());
                if (CacheUtils.exists("logisticsList" + user.getCompanyId())) {
                    CacheUtils.remove("logisticsList" + user.getCompanyId());
                }
            }
            return result;
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    /**
     * <b>Description:</b><br>
     * 保存默认的快递公司
     * 
     * @param user
     * @param logistics
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 上午10:55:02
     */
    @Override
    public ResultInfo<?> saveSetDefaultLogistics(User user, Logistics logistics) {
        logistics.setUpdater(user.getUserId());
        logistics.setModTime(DateUtil.sysTimeMillis());;
        logistics.setCompanyId(user.getCompanyId());
        String url = httpUrl + ErpConst.SAVE_SET_DEFAULT_LOGISTICE;
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, logistics, clientId, clientKey, TypeRef);
            if (result != null && result.getCode() == 0) {
                if (CacheUtils.exists("logisticsList" + user.getCompanyId())) {
                    CacheUtils.remove("logisticsList" + user.getCompanyId());
                }
                // JedisUtils.del(dbType + ErpConst.KEY_PREFIX_LOGISTICS_LIST + user.getCompanyId());
            }
            return result;
        }
        catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            return null;
        }
    }

    /**
     * 根据参数查询快递公司
     */
	@Override
	public List<Logistics> getLogisticsListByParam(Map<String, Object> logisticsParam) {
		
		return logisticsMapper.getLogisticsListByParam(logisticsParam);
	}

	/**
	 * 根据快递公司id和省份id查询快递费用
	 */
	@Override
	public BigDecimal getFreeByParam(Map<String, Object> regionParamMap) {
		
		return logisticsMapper.getFreeByParam(regionParamMap);
	}

    @Override public Logistics getLogisticsById(Integer logisticsId) {
        return logisticsMapper.getLogisticsById(logisticsId);
    }

    @Override
    public String getLogisticsCode(String name) {
        return logisticsMapper.getLogisticsCode(name);
    }

}
