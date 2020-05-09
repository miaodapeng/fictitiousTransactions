package com.vedeng.homepage.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.report.dao.CommonReportMapper;
import com.vedeng.authorization.dao.OrganizationMapper;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.vo.OrganizationVo;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.homepage.dao.SalesGoalSettingMapper;
import com.vedeng.homepage.model.SalesGoalSetting;
import com.vedeng.homepage.model.vo.AfterSalesDataVo;
import com.vedeng.homepage.model.vo.ChildrenEchartsDataVo;
import com.vedeng.homepage.model.vo.EchartsDataVo;
import com.vedeng.homepage.model.vo.SaleEngineerDataVo;
import com.vedeng.homepage.service.MyHomePageService;
import com.vedeng.logistics.model.Logistics;
import com.vedeng.logistics.service.LogisticsService;
import com.vedeng.order.model.vo.BussinessChanceVo;
import com.vedeng.order.model.vo.QuoteorderVo;
import com.vedeng.system.dao.AddressMapper;
import com.vedeng.system.dao.ParamsConfigMapper;
import com.vedeng.system.dao.ParamsConfigValueMapper;
import com.vedeng.system.model.Address;
import com.vedeng.system.model.ParamsConfig;
import com.vedeng.system.model.ParamsConfigValue;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.vo.AddressVo;
import com.vedeng.system.model.vo.ParamsConfigVo;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.RegionService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.dao.CommunicateRecordMapper;
import com.vedeng.trader.dao.RTraderJUserMapper;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.vo.CommunicateRecordVo;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.service.TraderCustomerService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service("myHomePageService")
public class MyHomePageServiceImpl extends BaseServiceimpl implements MyHomePageService {
	public static Logger logger = LoggerFactory.getLogger(MyHomePageServiceImpl.class);

	@Resource
	private OrganizationMapper organizationMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private SalesGoalSettingMapper salesGoalSettingMapper;
	
	@Resource
	private CommunicateRecordMapper communicateRecordMapper;
	
	@Resource
	private RTraderJUserMapper rTraderJUserMapper;
	
	@Resource
	private TraderCustomerService traderCustomerService;
	
	@Resource
	private ParamsConfigMapper paramsConfigMapper;
	
	@Resource
	private ParamsConfigValueMapper paramsConfigValueMapper;
	
	@Resource
	private AddressMapper addressMapper;
	@Autowired
	@Qualifier("orgService")
	private OrgService orgService;
	
	@Resource
	private RegionService regionService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private LogisticsService logisticsService;
	
	@Resource
	private CommonReportMapper commonReportMapper;

	/**
	 * <b>Description:</b><br> 获取当前人员的下一级部门目标数据
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月22日 下午2:59:25
	 */
	@Override
	public List<OrganizationVo> getOrganizationVoList(User user) {
		Organization org = new Organization();
		org.setCompanyId(user.getCompanyId());
		org.setOrgId(user.getOrgId());
		//当前人员部门信息
		org = organizationMapper.getByOrg(org);
		if(ObjectUtils.notEmpty(user.getPositLevel()) && ObjectUtils.notEmpty(user.getPositType()) 
				&& user.getPositType() == 310 ){
			if(user.getPositLevel() == 441 || user.getPositLevel() == 442){
				//下一级所有部门
				Organization orga = new Organization();
				orga.setParentId(org.getOrgId());
				orga.setCompanyId(user.getCompanyId());
				orga.setType(user.getPositType());
				orga.setLevel(org.getLevel()+1);
				List<OrganizationVo> orgaList = organizationMapper.getOrganizationVoList(orga);
				if(orgaList != null && orgaList.size() > 0){
					for (OrganizationVo or : orgaList) {
						List<User> users = userMapper.getUserListByOrgId(or.getOrgId());
						if(users != null && users.size() > 0){
							or.setOrgLeader(users.get(0).getUsername());//部门负责人
							or.setOrgLeaderId(users.get(0).getUserId());
							//部门的全年目标数据
							SalesGoalSetting salesGoalSetting = new SalesGoalSetting();
							salesGoalSetting.setCompanyId(user.getCompanyId());
							salesGoalSetting.setOrgId(or.getOrgId());
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
							Date date = new Date();
							try {
								salesGoalSetting.setYear(sdf.parse(sdf.format(date)));
							} catch (ParseException e) {
								logger.error(Contant.ERROR_MSG, e);
							}
							or.setSalesGoalSettingList(salesGoalSettingMapper.getSalesGoalSettingList(salesGoalSetting));
						}
					}
				}
				return orgaList;
			}else if(user.getPositLevel() == 444){
				List<OrganizationVo> orgaList = new ArrayList<>();
				List<User> userList = userMapper.getUserListByOrgId(user.getOrgId());
				for (User us : userList) {
					OrganizationVo or = new OrganizationVo();
					or.setOrgId(user.getOrgId());
					or.setOrgLeader(us.getUsername());//销售人员
					or.setOrgLeaderId(us.getUserId());
					//销售人员的全年目标数据
					SalesGoalSetting salesGoalSetting = new SalesGoalSetting();
					salesGoalSetting.setCompanyId(user.getCompanyId());
					salesGoalSetting.setOrgId(user.getOrgId());
					salesGoalSetting.setUserId(us.getUserId());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
					Date date = new Date();
					try {
						salesGoalSetting.setYear(sdf.parse(sdf.format(date)));
					} catch (ParseException e) {
						logger.error(Contant.ERROR_MSG, e);
					}
					or.setSalesGoalSettingList(salesGoalSettingMapper.getSalesGoalSettingList(salesGoalSetting));
					orgaList.add(or);
				}
				return orgaList;
			}
			
		}
		return null;
		
	}
	/**
	 * <b>Description:</b><br> 保存设置的销售目标
	 * @param sgslist
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月24日 上午10:08:10
	 */
	@Transactional
	@Override
	public int saveOrUpdateConfigSaleGoal(List<SalesGoalSetting> sgslist) {
		if(sgslist != null && sgslist.size() > 0){
			int res = 0;
			for (SalesGoalSetting sgs : sgslist) {
				if(ObjectUtils.isEmpty(sgs.getSalesGoalSettingId())){
					res = salesGoalSettingMapper.insertSelective(sgs);
					if(res == 0){
						return 0;
					}
				}else{
					res = salesGoalSettingMapper.updateByPrimaryKeySelective(sgs);
					if(res == 0){
						return 0;
					}
				}
			}
			return res;
		}
		return 0;
	}
	
	/**
	 * <b>Description:</b><br> 查询月份的合计
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月24日 下午2:27:02
	 */
	@Override
	public List<BigDecimal> getTotalMonthList(Map<String, Object> map) {
		return salesGoalSettingMapper.getTotalMonthList(map);
	}
	
	/**
	 * <b>Description:</b><br> 获取当前人员的下一级部门id集合
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月22日 下午2:59:25
	 */
	@Override
	public List<Integer> getOrgIdList(User user) {
		Organization org = new Organization();
		org.setCompanyId(user.getCompanyId());
		org.setOrgId(user.getOrgId());
		//当前人员部门信息
		org = organizationMapper.getByOrg(org);
		//下一级所有部门
		Organization orga = new Organization();
		orga.setParentId(org.getOrgId());
		orga.setCompanyId(user.getCompanyId());
		orga.setType(user.getPositType());
		orga.setLevel(org.getLevel()+1);
		List<Integer> orgaList = organizationMapper.getOrgIdsList(orga);
		if(orgaList == null || orgaList.size() == 0){
			orgaList = new ArrayList<>();
			orgaList.add(-1);
		}
		return orgaList;
	}
	
	/**
	 * <b>Description:</b><br> 查询完成，同比，环比数据
	 * @param echartsDataVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月24日 下午5:03:24
	 */
	@Override
	public EchartsDataVo getEchartsDataVo(User user) {
		EchartsDataVo echartsDataVo = new EchartsDataVo();
		echartsDataVo.setCompanyId(user.getCompanyId());
		echartsDataVo.setPositLevel(user.getPositLevel());
		echartsDataVo.setPositType(user.getPositType());

		if(ObjectUtils.notEmpty(user.getPositType()) && user.getPositType() == 310){//销售
			if( ObjectUtils.notEmpty(user.getPositLevel()) && (user.getPositLevel() == 441 || user.getPositLevel() == 442)){
				//所有部门
				//List<Integer> orgIds = organizationMapper.getAllOrgIdList(user.getOrgId());
				List<Integer> orgIds = orgService.getOrgIds(user.getOrgId(), user.getCompanyId());
				echartsDataVo.setOrgIdList(orgIds);
				//下一级所有部门
				List<Integer> nextOrgIdlist = getOrgIdList(user);
				Map<Integer, List<Integer>> map = new HashMap<>();
				if(nextOrgIdlist != null && nextOrgIdlist.size() > 0){
					for (int i = 0; i < nextOrgIdlist.size(); i++) {
						List<Integer> orgIdList = orgService.getOrgIds(nextOrgIdlist.get(i), user.getCompanyId());
						//List<Integer> orgIdList = organizationMapper.getAllOrgIdList(nextOrgIdlist.get(i));
						map.put(nextOrgIdlist.get(i), orgIdList);
					}
				}
				echartsDataVo.setOrgMap(map);
				//获取省份客户数
				List<Region> list = regionService.getRegionByParentId(1);
				List<Integer> areaIdList = new ArrayList<>();
				for (Region reg : list) {
					areaIdList.add(reg.getRegionId());
				}
				echartsDataVo.setAreaIdList(areaIdList);
			}else if(ObjectUtils.notEmpty(user.getPositLevel()) && user.getPositLevel() == 444){
				echartsDataVo.setOrgId(user.getOrgId());
				List<Integer> userIdList = userMapper.getUserIdListByOrgId(user.getOrgId());
				echartsDataVo.setUserIdList(userIdList);
			}
		}else if(ObjectUtils.notEmpty(user.getPositType()) && user.getPositType() == 312){//售后

		}else if(ObjectUtils.notEmpty(user.getPositType()) && user.getPositType() == 313){//物流
			
		}

		
		String url = httpUrl + ErpConst.GET_ECHARTSVO;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, echartsDataVo, clientId, clientKey, TypeRef2);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			JSONObject json = JSONObject.fromObject(result.getData());
			echartsDataVo = JsonUtils.readValue(json.toString(), EchartsDataVo.class);
			if(ObjectUtils.notEmpty(user.getPositType()) && user.getPositType() == 310){//销售
				if(ObjectUtils.notEmpty(user.getPositLevel()) && user.getPositLevel()==441){
					//分解mongodb的数据满足饼状图的数据显示
					if(echartsDataVo.getSaleMoneyMap() != null && !echartsDataVo.getSaleMoneyMap().isEmpty()){
						List<String> nextDeptList = new ArrayList<>();
						List<BigDecimal> deptSaleMoney = new ArrayList<>();
						List<Long> deptSaleorder = new ArrayList<>();
						Iterator iter = echartsDataVo.getSaleMoneyMap().entrySet().iterator();
						while (iter.hasNext()) {
							Map.Entry entry = (Map.Entry) iter.next();
							Integer orgId = (Integer) entry.getKey();
							nextDeptList.add(organizationMapper.selectByPrimaryKey(orgId).getOrgName());
							deptSaleMoney.add(new BigDecimal(entry.getValue().toString()));
							Iterator itera = echartsDataVo.getSaleorderSumMap().entrySet().iterator();
							while(itera.hasNext()){
								Map.Entry en = (Map.Entry) itera.next();
								if(orgId.equals(en.getKey())){
									deptSaleorder.add(Long.valueOf(en.getValue().toString()));
								}
							}
						}
						echartsDataVo.setDeptSaleMoney(deptSaleMoney);
						echartsDataVo.setNextDeptList(nextDeptList);
						echartsDataVo.setDeptSaleorder(deptSaleorder);
					}
					//获取省份客户数
					List<Region> list = regionService.getRegionByParentId(1);
					if(list != null && list.size() > 0){
						List<String> proNameList = new ArrayList<>();//省份名称
						List<Integer> proCustomerList = new ArrayList<>();//客户数
						Integer min = 10000;
						Integer max = 0;
						Iterator iter = echartsDataVo.getCustomerMap().entrySet().iterator();
						while(iter.hasNext()){
							Map.Entry en = (Map.Entry) iter.next();
							for (Region reg : list) {
								if(reg.getRegionId().equals(en.getKey())){
									proNameList.add(reg.getRegionName());
									Integer num = Integer.valueOf(en.getValue().toString());
									proCustomerList.add(num);
									if(min > num){
										min = num;
									}
									if(num > max){
										max = num;
									}
								}
							}
						}
						echartsDataVo.setMax(max);
						echartsDataVo.setMin(min);
						echartsDataVo.setProCustomerList(proCustomerList);
						echartsDataVo.setProNameList(proNameList);
					}
				}
				echartsDataVo.setOrgMap(null);
				echartsDataVo.setSaleMoneyMap(null);
				echartsDataVo.setSaleorderSumMap(null);
				echartsDataVo.setCustomerMap(null);
			}else if(ObjectUtils.notEmpty(user.getPositType()) && user.getPositType() == 312){//售后
				List<SysOptionDefinition> sales = getSysOptionDefinitionList(535);//销售售后类型
				Map<Integer, Integer> salesMap = echartsDataVo.getSaleAftersaleMap();
				List<String> salesNames = new ArrayList<>();
				List<Integer> salesNums = new ArrayList<>();
				for (SysOptionDefinition sys : sales) {
					if(!salesMap.isEmpty() && salesMap.containsKey(sys.getSysOptionDefinitionId()) && ObjectUtils.isEmpty(sys.getRelatedField())){
						salesNames.add(sys.getTitle());
						salesNums.add(salesMap.get(sys.getSysOptionDefinitionId()));
					}else if(ObjectUtils.isEmpty(sys.getRelatedField())){
						salesNames.add(sys.getTitle());
						salesNums.add(0);
					}
				}
				echartsDataVo.setSalesNames(salesNames);
				echartsDataVo.setSalesNums(salesNums);
				List<SysOptionDefinition> thirds = getSysOptionDefinitionList(537);//第三方售后类型
				Map<Integer, Integer> thirdAftersaleMap = echartsDataVo.getThirdAftersaleMap();
				List<String> thirdNames = new ArrayList<>();
				List<Integer> thirdNums = new ArrayList<>();
				for (SysOptionDefinition sys : thirds) {
					if(!thirdAftersaleMap.isEmpty() && thirdAftersaleMap.containsKey(sys.getSysOptionDefinitionId()) && ObjectUtils.isEmpty(sys.getRelatedField())){
						thirdNames.add(sys.getTitle());
						thirdNums.add(thirdAftersaleMap.get(sys.getSysOptionDefinitionId()));
					}else if(ObjectUtils.isEmpty(sys.getRelatedField())){
						thirdNames.add(sys.getTitle());
						thirdNums.add(0);
					}
				}
				echartsDataVo.setThirdNames(thirdNames);
				echartsDataVo.setThirdNums(thirdNums);
				echartsDataVo.setSaleAftersaleMap(null);
				echartsDataVo.setThirdAftersaleMap(null);
			}else if(ObjectUtils.notEmpty(user.getPositType()) && user.getPositType() == 313){//物流
				List<ChildrenEchartsDataVo> logisticsFreightList = new ArrayList<>();//快递运费堆叠柱状图数据
				
				List<ChildrenEchartsDataVo> logisticsPiecesList = new ArrayList<>();//快递件数堆叠柱状图数据

				List<Logistics> logisticsList = logisticsService.getLogisticsList(user.getCompanyId());
				//本月运费支出占比
				Map<Integer, BigDecimal> freightChargesMap = echartsDataVo.getFreightChargesMap();
				//快递运费
				Map<Integer,Map<Integer, BigDecimal>> logisticsFreightMap = echartsDataVo.getLogisticsFreightMap();
				//快递件数
				Map<Integer,Map<Integer, Integer>> logisticsPiecesMap = echartsDataVo.getLogisticsPiecesMap();
				List<String> logisticsNames = new ArrayList<>();
				List<BigDecimal> logisticsAmount = new ArrayList<>();
				for (Logistics logistics : logisticsList) {
					if(!freightChargesMap.isEmpty() && freightChargesMap.containsKey(logistics.getLogisticsId())){
						logisticsNames.add(logistics.getName());
						logisticsAmount.add(freightChargesMap.get(logistics.getLogisticsId()));
					}else {
						logisticsNames.add(logistics.getName());
						logisticsAmount.add(new BigDecimal(0));
					}
					
					ChildrenEchartsDataVo freight = new ChildrenEchartsDataVo();
					ChildrenEchartsDataVo pieces = new ChildrenEchartsDataVo();
					freight.setName(logistics.getName());
					pieces.setName(logistics.getName());
					List<String> freightList = new ArrayList<>();
					List<String> piecesList = new ArrayList<>();
					for (int i = 1; i < 13; i++) {
						if(logisticsFreightMap.containsKey(logistics.getLogisticsId())){
							Map<Integer, BigDecimal> freightMap = logisticsFreightMap.get(logistics.getLogisticsId());
							if(freightMap.containsKey(i)){
								freightList.add(freightMap.get(i).toString());
							}else{
								freightList.add("0");
							}
						}else{
							freightList.add("0");
						}
						
						if(logisticsPiecesMap.containsKey(logistics.getLogisticsId())){
							Map<Integer, Integer> piecesMap = logisticsPiecesMap.get(logistics.getLogisticsId());
							if(piecesMap.containsKey(i)){
								piecesList.add(piecesMap.get(i).toString());
							}else{
								piecesList.add("0");
							}
						}else{
							piecesList.add("0");
						}
					}
					freight.setChildrenList(freightList);
					pieces.setChildrenList(piecesList);
					
					logisticsFreightList.add(freight);
					logisticsPiecesList.add(pieces);
				}
				echartsDataVo.setLogisticsFreightList(logisticsFreightList);
				echartsDataVo.setLogisticsPiecesList(logisticsPiecesList);
				
				echartsDataVo.setLogisticsNames(logisticsNames);
				echartsDataVo.setLogisticsAmount(logisticsAmount);
				
				List<SysOptionDefinition> list = getSysOptionDefinitionList(495);
				//本月业务发货占比
				Map<Integer, Integer> businessDeliveryMap = echartsDataVo.getBusinessDeliveryMap();
				List<String> businessNames = new ArrayList<>();
				List<Integer> businessNums = new ArrayList<>();
				
				//业务运费统计
				Map<Integer,Map<Integer, BigDecimal>> businessFreightMap = echartsDataVo.getBusinessFreightMap();
				List<ChildrenEchartsDataVo> childrenEchartsDataVoList = new ArrayList<>();
				for (SysOptionDefinition sys : list) {
					businessNames.add(sys.getTitle());
					if(!businessDeliveryMap.isEmpty() && businessDeliveryMap.containsKey(sys.getSysOptionDefinitionId())){
						businessNums.add(businessDeliveryMap.get(sys.getSysOptionDefinitionId()));
					}else if(ObjectUtils.isEmpty(sys.getRelatedField())){
						businessNums.add(0);
					}
					ChildrenEchartsDataVo ch = new ChildrenEchartsDataVo();
					ch.setName(sys.getTitle());
					if(!businessFreightMap.isEmpty() && businessFreightMap.containsKey(sys.getSysOptionDefinitionId())){
						Map<Integer, BigDecimal> bus = businessFreightMap.get(sys.getSysOptionDefinitionId());
						
						List<String> childrenList = new ArrayList<>();
						for (int i = 1; i < 13; i++) {
							if(!bus.isEmpty() && bus.containsKey(i)){
								childrenList.add(bus.get(i).toString());
							}else{
								childrenList.add("0");
							}
						}
						ch.setChildrenList(childrenList);
					}else{
						List<String> childrenList = new ArrayList<>();
						for (int i = 1; i < 13; i++) {
							childrenList.add("0");
						}
						ch.setChildrenList(childrenList);
					}
					childrenEchartsDataVoList.add(ch);
				}
				
				//出入库，周转率
				
				List<Integer> outWarehouseList = new ArrayList<>();
				
				List<Integer> enterWarehouseList = new ArrayList<>();
				
				List<Double> stockTurnoverList = new ArrayList<>();
				
				Map<Integer, Integer> outWarehouseMap = echartsDataVo.getOutWarehouseMap();
				
				Map<Integer, Integer> enterWarehouseMap = echartsDataVo.getEnterWarehouseMap();
				
				Map<Integer, Double> stockTurnoverMap = echartsDataVo.getStockTurnoverMap();
				for (int i = 1; i < 13; i++) {
					if(outWarehouseMap.containsKey(i)){
						outWarehouseList.add(outWarehouseMap.get(i));
					}else{
						outWarehouseList.add(0);
					}
					if(enterWarehouseMap.containsKey(i)){
						enterWarehouseList.add(enterWarehouseMap.get(i));
					}else{
						enterWarehouseList.add(0);
					}
					if(stockTurnoverMap.containsKey(i)){
						stockTurnoverList.add(stockTurnoverMap.get(i));
					}else{
						stockTurnoverList.add(new Double(0));
					}
				}
				echartsDataVo.setOutWarehouseList(outWarehouseList);
				echartsDataVo.setEnterWarehouseList(enterWarehouseList);
				echartsDataVo.setStockTurnoverList(stockTurnoverList);
				echartsDataVo.setChildrenEchartsDataVoList(childrenEchartsDataVoList);
				echartsDataVo.setBusinessNames(businessNames);
				echartsDataVo.setBusinessNums(businessNums);
				echartsDataVo.setFreightChargesMap(null);
				echartsDataVo.setBusinessDeliveryMap(null);
				echartsDataVo.setBusinessFreightMap(null);
				echartsDataVo.setLogisticsFreightMap(null);
				echartsDataVo.setLogisticsPiecesMap(null);
				echartsDataVo.setOutWarehouseMap(null);
				echartsDataVo.setEnterWarehouseMap(null);
				echartsDataVo.setStockTurnoverMap(null);
			}
			return echartsDataVo;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	
	private SaleEngineerDataVo newSaleEngineerDataVo(){
		SaleEngineerDataVo saleEngineerDataVo = new SaleEngineerDataVo();
		List<Integer> todayDoList = new ArrayList<>();
		todayDoList.add(0);
		todayDoList.add(0);
		todayDoList.add(0);
		todayDoList.add(0);
		todayDoList.add(0);
		todayDoList.add(0);
		saleEngineerDataVo.setTodayDoList(todayDoList);
		List<BigDecimal> thisMonthDataList = new ArrayList<>();
		thisMonthDataList.add(new BigDecimal(0));
		thisMonthDataList.add(new BigDecimal(0));
		thisMonthDataList.add(new BigDecimal(0));
		thisMonthDataList.add(new BigDecimal(0));
		thisMonthDataList.add(new BigDecimal(0));
		thisMonthDataList.add(new BigDecimal(0));
		thisMonthDataList.add(new BigDecimal(0));
		thisMonthDataList.add(new BigDecimal(0));
		saleEngineerDataVo.setThisMonthDataList(thisMonthDataList);
		List<BigDecimal> salePersonalList = new ArrayList<>();
		salePersonalList.add(new BigDecimal(0));
		salePersonalList.add(new BigDecimal(0));
		salePersonalList.add(new BigDecimal(0));
		salePersonalList.add(new BigDecimal(0));
		salePersonalList.add(new BigDecimal(0));
		saleEngineerDataVo.setSalePersonalList(salePersonalList);
		
		return saleEngineerDataVo;
	}
	
	/**
	 * <b>Description:</b><br> 查询销售工程师个人首页数据
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月28日 下午2:57:23
	 */
	@Override
	public SaleEngineerDataVo getSaleEngineerDataVo(SaleEngineerDataVo saleEngineerDataVo) {

//		RTraderJUser rTraderJUser = new RTraderJUser();
//		rTraderJUser.setTraderType(1);
//		rTraderJUser.setUserId(user.getUserId());
//		List<Integer> trdaerIdList = rTraderJUserMapper.getRTraderJUserListByUserId(rTraderJUser);
//		if(trdaerIdList == null || trdaerIdList.size() == 0){
//			return newSaleEngineerDataVo();
//		}
//		saleEngineerDataVo.setTraderIdList(trdaerIdList);
//		CommunicateRecord com = new CommunicateRecord();
//		com.setCompanyId(user.getCompanyId());
//		com.setCommunicateType(242);//客户
//		com.setTraderIds(trdaerIdList);
//		com.setTraderType(1);
//		com.setIsDone(0);
//		saleEngineerDataVo.setTodayTraderCustomerList(getTodayCommunicateTrdaerIdList(com));
//		com.setCommunicateType(244);//商机
//		saleEngineerDataVo.setTodayBussinessChanceList(getTodayCommunicateTrdaerIdList(com));
//		com.setCommunicateType(245);//报价
//		saleEngineerDataVo.setTodayQuoteorderList(getTodayCommunicateTrdaerIdList(com));

		if(saleEngineerDataVo.getAccessType() == null || saleEngineerDataVo.getAccessType() == 1){
			//销售首页待沟通记录
			List<TraderCustomerVo> tcvList = commonReportMapper.getTraderCustomerVoFromCommunicate(saleEngineerDataVo);
			saleEngineerDataVo.setTraderCustomerVoList(tcvList);
		}else if(saleEngineerDataVo.getAccessType() == 2){//商机
			
		}else if(saleEngineerDataVo.getAccessType() == 3){//报价
			List<QuoteorderVo> qvList = commonReportMapper.getQuoteorderVoFromCommunicate(saleEngineerDataVo);
			saleEngineerDataVo.setQuoteorderVoList(qvList);

		}else if(saleEngineerDataVo.getAccessType() == 4){//本月数据
			
		}else if(saleEngineerDataVo.getAccessType() == 5){//个人数据
			
		}
		

		String url = httpUrl + ErpConst.GET_SALEENGINEERDATAVO;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleEngineerDataVo, clientId, clientKey, TypeRef2);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			
//			RTraderJUser rTraderJUser = new RTraderJUser();
//			rTraderJUser.setTraderType(1);
//			rTraderJUser.setUserId(user.getUserId());
//			List<Integer> trdaerIdList = rTraderJUserMapper.getRTraderJUserListByUserId(rTraderJUser);
//			//销售个人数据
//			List<BigDecimal> salePersonalList = new ArrayList<>();
//			salePersonalList.add(new BigDecimal(trdaerIdList.size()));
//			saleEngineerDataVo.setSalePersonalList(salePersonalList);//客户总数
//			
//			JSONObject json = JSONObject.fromObject(result.getData());
//			saleEngineerDataVo = JsonUtils.readValue(json.toString(), SaleEngineerDataVo.class);
//			if(saleEngineerDataVo.getQuoteorderVoList() != null){
//				CommunicateRecord cm = new CommunicateRecord();
//				cm.setCompanyId(user.getCompanyId());
//				cm.setCommunicateType(245);
//				cm.setTraderType(1);
//				//cm.setRelatedId(qv.getQuoteorderId());
//				cm.setQuoteorderVolist(saleEngineerDataVo.getQuoteorderVoList());
//				List<CommunicateRecord> cmList = communicateRecordMapper.getLastCommunicateList(cm);
//
//				for (QuoteorderVo qv : saleEngineerDataVo.getQuoteorderVoList()) {
//					qv.setBelongSales(getUserNameByUserId(qv.getUserId()));
//					for (CommunicateRecord cr : cmList) {
//						if(qv.getQuoteorderId().intValue() == cr.getRelatedId()){
//							qv.setNextContactDate(cm.getNextContactContent());
//							break;
//						}
//					}
//					//cm = communicateRecordMapper.getLastCommunicate(cm);
//				}
//			}
//			
//			List<Integer> todayDoList = new ArrayList<>();//今日 待办数据
//			//沟通客户数
//			CommunicateRecord communicateRecord = new CommunicateRecord();
//			communicateRecord.setCommunicateType(242);//
//			communicateRecord.setCompanyId(user.getCompanyId());
//			communicateRecord.setTraderType(1);
//			communicateRecord.setIsDone(0);
//			communicateRecord.setTraderIds(saleEngineerDataVo.getTraderIdList());
//			Integer gtkh = communicateRecordMapper.getHomePageSum(communicateRecord);//沟通客户数
//			todayDoList.add(gtkh);
//			communicateRecord.setIsDone(1);
//			Integer gtkhwc = communicateRecordMapper.getHomePageSum(communicateRecord);//沟通客户完成数
//			todayDoList.add(gtkhwc);
//			//报价跟单数
//			communicateRecord.setCommunicateType(245);//
//			Integer bjgdwc = communicateRecordMapper.getHomePageSum(communicateRecord);//报价跟单完成数
//			communicateRecord.setIsDone(0);
//			Integer bjgd = communicateRecordMapper.getHomePageSum(communicateRecord);//报价跟单数
//			todayDoList.add(bjgd);
//			todayDoList.add(bjgdwc);
//			//订单跟单数
//			communicateRecord.setCommunicateType(246);//
//			Integer ddgd = communicateRecordMapper.getHomePageSum(communicateRecord);//订单跟单数
//			communicateRecord.setIsDone(1);
//			Integer ddgdwc = communicateRecordMapper.getHomePageSum(communicateRecord);//订单跟单完成数
//			todayDoList.add(ddgd);
//			todayDoList.add(ddgdwc);
//			saleEngineerDataVo.setTodayDoList(todayDoList);
			
			SaleEngineerDataVo sedv = null;
			JSONObject json = JSONObject.fromObject(result.getData());
			sedv = JsonUtils.readValue(json.toString(), SaleEngineerDataVo.class);
			if((saleEngineerDataVo.getAccessType() == null || saleEngineerDataVo.getAccessType() == 1) && sedv != null && sedv.getTraderCustomerVoList() != null && sedv.getTraderCustomerVoList().size() > 0){
				for (TraderCustomerVo tcv : sedv.getTraderCustomerVoList()) {
					tcv.setPersonal(getUserNameByUserId(tcv.getCreator()));
				}
				
			}else if(saleEngineerDataVo.getAccessType() != null && saleEngineerDataVo.getAccessType() == 2 && sedv.getBussinessChanceVoList() != null ){
				
			}else if(saleEngineerDataVo.getAccessType() != null && saleEngineerDataVo.getAccessType() == 3 && sedv.getQuoteorderVoList() != null ){
				for (QuoteorderVo qv : sedv.getQuoteorderVoList()) {
					qv.setBelongSales(getUserNameByUserId(qv.getNowUserId()));
				}
				
				
			}else if(saleEngineerDataVo.getAccessType() != null && saleEngineerDataVo.getAccessType() == 4 ){
				//销售本月数据
				
				//销售目标
				SalesGoalSetting salesGoalSetting = new SalesGoalSetting();
				salesGoalSetting.setCompanyId(saleEngineerDataVo.getCompanyId());
				salesGoalSetting.setUserId(saleEngineerDataVo.getUserId());
				BigDecimal bd = salesGoalSettingMapper.getSalesEngineerGoal(salesGoalSetting);
				if(sedv.getThisMonthDataList() != null ){
					sedv.getThisMonthDataList().add(0,bd);
				}else{
					
				}
			}
			return sedv;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		
		
	}
	
	/**
	 * <b>Description:</b><br> 获取今天要沟通的id的集合（客户，商机，报价）
	 * @param communicateRecord
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月2日 上午9:39:07
	 */
	private List<Integer> getTodayCommunicateTrdaerIdList(CommunicateRecord communicateRecord){
		
		return communicateRecordMapper.getTodayCommunicateTrdaerIdList(communicateRecord);
	}
	
	/**
	 * <b>Description:</b><br> 查询销售总监的参数配置
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月4日 上午10:18:00
	 */
	@Override
	public ParamsConfigVo getParamsConfigVoByParamsKey(ParamsConfigVo paramsConfigVo) {
		return paramsConfigMapper.getParamsConfigVoByParamsKey(paramsConfigVo);
	}
	
	/**
	 * <b>Description:</b><br> 保存编辑设置的参数
	 * @param user
	 * @param quote
	 * @param sale
	 * @param quoteorder
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月4日 上午11:26:31
	 */
	@Transactional
	@Override
	public Integer saveEditConfigParams(User user, ParamsConfigVo paramsConfigVo) {
		//先删除原有的参数配置
		if(paramsConfigVo != null && paramsConfigVo.getParamsConfigIds() != null && paramsConfigVo.getParamsConfigIds()[0] != null 
				&& !"".equals(paramsConfigVo.getParamsConfigIds()[0])){
			//paramsConfigMapper.delParamsConfig(Arrays.asList(paramsConfigVo.getParamsConfigIds()));
			paramsConfigValueMapper.delParamsConfigValue(Arrays.asList(paramsConfigVo.getParamsConfigIds()),user.getCompanyId());
		}
		int res = 0;
		ParamsConfigValue paramsConfigValue = new ParamsConfigValue();
		paramsConfigValue.setCompanyId(user.getCompanyId());
		if(user.getPositType() == 310){//销售总监
			paramsConfigValue.setParamsValue(paramsConfigVo.getQuote());
			paramsConfigValue.setParamsConfigId(4);
			res = paramsConfigValueMapper.insertSelective(paramsConfigValue);
			if(res == 0){
				return 0;
			}
			paramsConfigValue.setParamsConfigValueId(null);
			paramsConfigValue.setParamsValue(paramsConfigVo.getSale());
			paramsConfigValue.setParamsConfigId(5);
			res = paramsConfigValueMapper.insertSelective(paramsConfigValue);
			if(res == 0){
				return 0;
			}
			paramsConfigValue.setParamsConfigValueId(null);
			paramsConfigValue.setParamsValue(paramsConfigVo.getQuoteorder());
			paramsConfigValue.setParamsConfigId(6);
			res = paramsConfigValueMapper.insertSelective(paramsConfigValue);
		}else if(user.getPositType() == 311){//产品总监
			paramsConfigValue.setParamsValue(paramsConfigVo.getParamsValue());
			paramsConfigValue.setParamsConfigId(8);
			res = paramsConfigValueMapper.insertSelective(paramsConfigValue);
		}else if(user.getPositType() == 312){//售后总监
			//删除109的配置
			//先根据公司和109查询,再根据主键删除相应的配置数据
			ParamsConfigVo pcvo  = new ParamsConfigVo();
			pcvo.setParamsKey(109);
			pcvo.setCompanyId(user.getCompanyId());
			List<ParamsConfigVo> pcvList = paramsConfigMapper.getParamsConfigVoList(pcvo);
			if(pcvList != null && pcvList.size() > 0){
				for (ParamsConfigVo p : pcvList) {
					if(ObjectUtils.notEmpty(p.getParamsConfigId())){
						paramsConfigMapper.deleteByPrimaryKey(p.getParamsConfigId());
					}
					if(ObjectUtils.notEmpty(p.getParamsConfigValueId())){
						paramsConfigValueMapper.deleteByPrimaryKey(p.getParamsConfigValueId());
					}
				}
			}
			paramsConfigValue.setParamsValue(paramsConfigVo.getDefaultCharge());
			paramsConfigValue.setParamsConfigId(9);
			res = paramsConfigValueMapper.insertSelective(paramsConfigValue);//默认负责人
			
			//一级销售部门的售后负责人
			String [] charges = paramsConfigVo.getOrgCharges();
			if(charges != null && charges.length > 0){
				for(int i = 0; i < charges.length ; i++){
					String [] orgs = charges[i].split("\\|");
					
					//查询是否存在
					ParamsConfigVo pcv = new ParamsConfigVo();
					pcv.setCompanyId(user.getCompanyId());
					pcv.setParamsKey(109);
					pcv.setTitle(orgs[0]);;
					ParamsConfigVo pc = paramsConfigMapper.getParamsConfigVoByParamsKey(pcv);
					if(pc == null){
						ParamsConfig paramsConfig = new ParamsConfig();
						paramsConfig.setParamsKey(109);
						paramsConfig.setStatus(1);
						paramsConfig.setTitle(orgs[0]);//此字段存储部门的主键值
						paramsConfig.setComments(orgs[2]);
						res = paramsConfigMapper.insertSelective(paramsConfig);
						if(res == 0){
							return 0;
						}
						paramsConfigValue.setParamsValue(orgs[1]);
						paramsConfigValue.setParamsConfigId(paramsConfig.getParamsConfigId());
						res = paramsConfigValueMapper.insertSelective(paramsConfigValue);
					}else{
						if(ObjectUtils.notEmpty(pc.getParamsConfigValueId())){
							paramsConfigValue.setParamsValue(orgs[1]);
							paramsConfigValue.setParamsConfigValueId(pc.getParamsConfigValueId());
							res = paramsConfigValueMapper.updateByPrimaryKeySelective(paramsConfigValue);
						}else{
							paramsConfigValue.setParamsValue(orgs[1]);
							paramsConfigValue.setParamsConfigId(pc.getParamsConfigId());
							res = paramsConfigValueMapper.insertSelective(paramsConfigValue);
						}
						
					}
				}
			}
		}else if(user.getPositType() == 313){//物流总监
			paramsConfigValue.setParamsValue(paramsConfigVo.getLogistics());
			paramsConfigValue.setParamsConfigId(7);
			res = paramsConfigValueMapper.insertSelective(paramsConfigValue);
		}
		return res;
	}
	
	/**
	 * <b>Description:</b><br> 保存新增或编辑发/退货地址
	 * @param addressVo
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月5日 下午6:37:53
	 */
	@Transactional
	@Override
	public int saveAddressAndConfigParamsValue(AddressVo addressVo, User user,Integer paramsConfigId) {
		if(addressVo == null){
			return 0;
		}
		Address address = new Address();
		address.setUpdater(user.getUserId());
		address.setModTime(DateUtil.sysTimeMillis());
		address.setAddress(addressVo.getAddress());
		address.setContactName(addressVo.getContactName());
		address.setMobile(addressVo.getMobile());
		if(ObjectUtils.notEmpty(addressVo.getZone())){
			address.setAreaId(addressVo.getZone());
			address.setAreaIds(addressVo.getProvince()+","+addressVo.getCity()+","+addressVo.getZone());
		}else if(ObjectUtils.isEmpty(addressVo.getZone()) && ObjectUtils.notEmpty(addressVo.getCity())){
			address.setAreaId(addressVo.getCity());
			address.setAreaIds(addressVo.getProvince()+","+addressVo.getCity());
		}
		int res = 0;
		if(ObjectUtils.isEmpty(addressVo.getAddressId())){
			address.setAddTime(DateUtil.sysTimeMillis());
			address.setCreator(user.getUserId());
			address.setCompanyId(user.getCompanyId());
			address.setType(addressVo.getType());
			address.setIsEnable(1);
			res = addressMapper.insertSelective(address);
			if(res == 0){
				return 0;
			}
			ParamsConfigValue paramsConfigValue = new ParamsConfigValue();
			paramsConfigValue.setCompanyId(user.getCompanyId());
			paramsConfigValue.setParamsValue(address.getAddressId().toString());
			paramsConfigValue.setParamsConfigId(paramsConfigId);
			res = paramsConfigValueMapper.insertSelective(paramsConfigValue);
		}else{
			address.setAddressId(addressVo.getAddressId());
			res = addressMapper.updateByPrimaryKeySelective(address);
		}
		return res;
	}
	
	/**
	 * <b>Description:</b><br> 保存新增或编辑采购的收货地址
	 * @param addressVo
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月5日 下午6:37:53
	 */
	@Transactional
	@Override
	public int saveOrUpdateAddress(AddressVo addressVo, User user) {
		if(addressVo == null){
			return 0;
		}
		Address address = new Address();
		address.setUpdater(user.getUserId());
		address.setModTime(DateUtil.sysTimeMillis());
		address.setAddress(addressVo.getAddress());
		address.setContactName(addressVo.getContactName());
		address.setMobile(addressVo.getMobile());
		if(ObjectUtils.notEmpty(addressVo.getZone())){
			address.setAreaId(addressVo.getZone());
			address.setAreaIds(addressVo.getProvince()+","+addressVo.getCity()+","+addressVo.getZone());
		}else if(ObjectUtils.isEmpty(addressVo.getZone()) && ObjectUtils.notEmpty(addressVo.getCity())){
			address.setAreaId(addressVo.getCity());
			address.setAreaIds(addressVo.getProvince()+","+addressVo.getCity());
		}
		int res = 0;
		if(ObjectUtils.isEmpty(addressVo.getAddressId())){
			address.setAddTime(DateUtil.sysTimeMillis());
			address.setCreator(user.getUserId());
			address.setCompanyId(user.getCompanyId());
			address.setType(addressVo.getType());
			address.setIsEnable(1);
			res = addressMapper.insertSelective(address);
		}else{
			address.setAddressId(addressVo.getAddressId());
			res = addressMapper.updateByPrimaryKeySelective(address);
		}
		return res;
	}
	
	/**
	 * <b>Description:</b><br> 设置采购收货默认
	 * @param address
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月6日 下午1:14:42
	 */
	@Transactional
	@Override
	public int saveSetDefaultBuyAddress(Address address, User user) {
		ParamsConfigVo paramsConfig = new ParamsConfigVo();
		paramsConfig.setCompanyId(user.getCompanyId());
		paramsConfig.setParamsKey(101);
		ParamsConfigValue paramsConfigValue = paramsConfigValueMapper.getParamsValue(paramsConfig);
		int res = 0;
		if(paramsConfigValue == null){
			paramsConfigValue = new ParamsConfigValue();
			paramsConfigValue.setCompanyId(user.getCompanyId());
			paramsConfigValue.setParamsConfigId(2);
			paramsConfigValue.setParamsValue(address.getAddressId().toString());
			res = paramsConfigValueMapper.insertSelective(paramsConfigValue);
		}else{
			paramsConfigValue.setParamsValue(address.getAddressId().toString());
			res = paramsConfigValueMapper.updateByPrimaryKey(paramsConfigValue);
		}
		return res;
	}
	
	/**
	 * <b>Description:</b><br> 查询销售一级有效部门的负责人
	 * @param organization
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月6日 下午3:29:28
	 */
	@Override
	public List<User> getSalesOneOrg(Organization organization) {
		List<Integer> orgIdList =  organizationMapper.getOrgIdsList(organization);
		List<User> userList = userMapper.getUserByOrgIdsAndPositLevel(orgIdList, 442);
		return userList;
	}
	
	/**
	 * <b>Description:</b><br> 查询一级销售部门的参数配置
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月4日 上午10:18:00
	 */
	@Override
	public List<ParamsConfigVo> getParamsConfigVoList(ParamsConfigVo paramsConfigVo) {
		
		return paramsConfigMapper.getParamsConfigVoList(paramsConfigVo);
	}
	
	/**
	 * <b>Description:</b><br> 查询当期那用户的下一级部门的当前月的销售目标
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月18日 下午2:42:17
	 */
	@Override
	public List<SalesGoalSetting> getSalesGoalSettingListByOrgIds(User user) {
		List<Integer> orgIds = getOrgIdList(user);
		SaleEngineerDataVo salesGoalSetting = new SaleEngineerDataVo();
		salesGoalSetting.setCompanyId(user.getCompanyId());
		salesGoalSetting.setOrgIds(orgIds);
		List<SalesGoalSetting> list = salesGoalSettingMapper.getNextDeptSalesGoalSettingList(salesGoalSetting);
		return list;
	}
	
	/**
	 * <b>Description:</b><br> 查询地图的客户数据
	 * @param echartsDataVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月19日 下午4:25:49
	 */
	@Override
	public EchartsDataVo getCustomerData(EchartsDataVo echartsDataVo) {
		//获取省份客户数
		List<Region> list = regionService.getRegionByParentId(1);
		List<Integer> areaIdList = new ArrayList<>();
		for (Region reg : list) {
			areaIdList.add(reg.getRegionId());
		}
		echartsDataVo.setAreaIdList(areaIdList);
		String url = httpUrl + ErpConst.GET_ECHARTSVO_CUSTOMER;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, echartsDataVo, clientId, clientKey, TypeRef2);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			JSONObject json = JSONObject.fromObject(result.getData());
			echartsDataVo = JsonUtils.readValue(json.toString(), EchartsDataVo.class);

			if(list != null && list.size() > 0){
				List<String> proNameList = new ArrayList<>();//省份名称
				List<Integer> proCustomerList = new ArrayList<>();//客户数
				Integer min = 10000;
				Integer max = 0;
				Iterator iter = echartsDataVo.getCustomerMap().entrySet().iterator();
				while(iter.hasNext()){
					Map.Entry en = (Map.Entry) iter.next();
					for (Region reg : list) {
						if(reg.getRegionId().equals(en.getKey())){
							proNameList.add(reg.getRegionName());
							Integer num = Integer.valueOf(en.getValue().toString());
							proCustomerList.add(num);
							if(min > num){
								min = num;
							}
							if(num > max){
								max = num;
							}
						}
					}
				}
				echartsDataVo.setMax(max);
				echartsDataVo.setMin(min);
				echartsDataVo.setProCustomerList(proCustomerList);
				echartsDataVo.setProNameList(proNameList);
				echartsDataVo.setCustomerMap(null);
			}
			return echartsDataVo;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	
	/**
	 * <b>Description:</b><br> 查询售后总监首页数据
	 * @param afterSalesDataVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月26日 上午9:59:29
	 */
	@Override
	public AfterSalesDataVo getAfterSalesDataVo(User user) {
		AfterSalesDataVo afterSalesDataVo = new AfterSalesDataVo();
		// 售后人员
		List<User> serviceUserList = userService.getUserListByPositType(SysOptionConstant.ID_312,user.getCompanyId());
		List<Integer> userIds = new ArrayList<>();
		for (User us : serviceUserList) {
			userIds.add(us.getUserId());
		}
		afterSalesDataVo.setAftersalesUserIdList(userIds);
		afterSalesDataVo.setCompanyId(user.getCompanyId());
		
		String url = httpUrl + ErpConst.GET_AFTERSALES_DATAVO;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesDataVo, clientId, clientKey, TypeRef2);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			JSONObject json = JSONObject.fromObject(result.getData());
			AfterSalesDataVo asdv = JsonUtils.readValue(json.toString(), AfterSalesDataVo.class);
			asdv.setAfterUserList(serviceUserList);
			return asdv;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	@SuppressWarnings({ "unchecked"})
	@Override
	public Map<String,Object> getBussinessChanceVoList(BussinessChanceVo bussinessChanceVo,Page page) {
		// TODO Auto-generated method stub
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url = httpUrl + ErpConst.GET_SALE_ENGINEER_DATA;
		List<BussinessChanceVo> list=null;
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, bussinessChanceVo, clientId, clientKey, TypeRef,page);
			list=(List<BussinessChanceVo>) result.getData();
			JSONArray json = JSONArray.fromObject(list);
			list=(List<BussinessChanceVo>)JSONArray.toCollection(json, BussinessChanceVo.class);
			
			//获取最后一次沟通时间
			List<Integer> relatedIds=new ArrayList<Integer>();
			if(list!=null&&list.size()>0){
				for(BussinessChanceVo bussinessChance : list){
					relatedIds.add(bussinessChance.getBussinessChanceId());
				}
			}
			List<CommunicateRecord> recordList=null;
			if(relatedIds!=null&&relatedIds.size()>0){
				recordList=communicateRecordMapper.getLastCommunicateListById(relatedIds,244);
				if(recordList!=null&&recordList.size()>0){
					for(BussinessChanceVo bussinessChance : list){
						for(CommunicateRecord communicateRecord : recordList){
							if(bussinessChance.getBussinessChanceId().equals(communicateRecord.getRelatedId())){
								bussinessChance.setStarttimeLong(communicateRecord.getAddTime());
								//bussinessChance.setNextContactDate(communicateRecord.getNextContactDate());
							}
						}
					}
				}
			}
			map.put("list", list);
			map.put("page", (Page)result.getPage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}
	
	/**
	 * <b>Description:</b><br> 获取售后主管以下今日沟通记录
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年8月6日 上午10:55:17
	 */
	@Override
	public List<CommunicateRecordVo> getAfterSalesCommunicateRecordVoList(User user) {
		AfterSalesDataVo afterSalesDataVo = new AfterSalesDataVo();
		// 售后人员
		afterSalesDataVo.setUserId(user.getUserId());
		afterSalesDataVo.setCompanyId(user.getCompanyId());
		String url = httpUrl + "report/homepage/getaftersalescommunicaterecordvolist.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<CommunicateRecordVo>>> TypeRef2 = new TypeReference<ResultInfo<List<CommunicateRecordVo>>>() {
		};
		try {
			ResultInfo<List<CommunicateRecordVo>> result = (ResultInfo<List<CommunicateRecordVo>>) HttpClientUtils.post(url, afterSalesDataVo, clientId, clientKey, TypeRef2);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			return result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
}
