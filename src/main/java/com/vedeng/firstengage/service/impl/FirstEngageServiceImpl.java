package com.vedeng.firstengage.service.impl;

import com.alibaba.fastjson.JSON;
import com.common.constants.Contant;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.task.model.ReadFirst;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.firstengage.FirstEngageCheckStatusEnum;
import com.vedeng.common.constant.goods.LogTypeEnum;
import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.firstengage.dao.*;
import com.vedeng.firstengage.model.*;
import com.vedeng.firstengage.service.FirstEngageService;
import com.vedeng.goods.dao.FirstEngageGenerateMapper;
import com.vedeng.goods.dao.LogCheckGenerateMapper;
import com.vedeng.goods.model.*;
import com.vedeng.system.dao.AttachmentMapper;
import com.vedeng.system.dao.StandardCategoryMapper;
import com.vedeng.system.dao.SysOptionDefinitionMapper;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.SysOptionDefinition;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
/**
 * 首营信息
 * <p>Title: FirstEngageServiceImpl</p>
 * <p>Description: </p>
 * @author Bill
 * @date 2019年3月20日
 */
@Service("firstEngageService")
public class FirstEngageServiceImpl implements FirstEngageService{
	public static Logger logger = LoggerFactory.getLogger(FirstEngageServiceImpl.class);

	@Value("${file_url}")
	protected String domain;

	@Autowired
	@Qualifier("firstEngageMapper")
	private FirstEngageMapper firstEngageMapper;

	@Autowired
	@Qualifier("registrationNumberMapper")
	private RegistrationNumberMapper registrationNumberMapper;

	@Autowired
	@Qualifier("productCompanyMapper")
	private ProductCompanyMapper productCompanyMapper;


	@Autowired
	@Qualifier("firstEngageStorageConditionMapper")
	private FirstEngageStorageConditionMapper firstEngageStorageConditionMapper;

	@Autowired
	@Qualifier("standardCategoryMapper")
	private StandardCategoryMapper standardCategoryMapper;

	@Autowired
	@Qualifier("sysOptionDefinitionMapper")
	private SysOptionDefinitionMapper sysOptionDefinitionMapper;

	@Autowired
	@Qualifier("firstEngageSearchInfoMapper")
	private FirstEngageSearchInfoMapper firstEngageSearchInfoMapper;

	@Autowired
	@Qualifier("attachmentMapper")
	private AttachmentMapper attachmentMapper;

	@Value("${api_http}")
	protected String api_http;

	@Autowired
	LogCheckGenerateMapper logCheckGenerateMapper;

	/**
	 * 时间排序由近到远（升序）
	 */
	private static final Integer TIME_SORT_5 = 5;

	/**
	 * 时间排序由远到近（降序）
	 */
	private static final Integer TIME_SORT_6 = 6;

	/**
	 * 批量插入数量
	 */
	private static final int INSERT_NUM = 800;

	/**
	 * 获取商品首营列表
	 */
	@Override
	public Map<String, Object> getFirstEngageInfoListPage(Map<String, Object> paramMap, Page page, FirstEngage firstEngage1) {

//		updateRegister();

		// 如果关键词搜索不为空，保存数据库
		if(null != paramMap.get("keyWords") && EmptyUtils.isNotBlank(paramMap.get("keyWords").toString())){
			Map<String, Object> delMap = new HashMap<>();
			delMap.put("keyWords", paramMap.get("keyWords").toString());
			delMap.put("userId", Integer.valueOf(paramMap.get("userId").toString()));
			// 先删除已经存在的关键词
			firstEngageSearchInfoMapper.deleteByContent(delMap);

			// 再将搜索内容添加搜索记录中
			firstEngageSearchInfoMapper.insertSelectiveInfo(delMap);
		}
		// 查询各个状态对应的数量
		Map<String, Integer> countMap = firstEngageMapper.getStatusCountByParam(paramMap);

		Map<String, Object> resultMap = new HashMap<>();
		// 全部
		resultMap.put("total", 0);
		// 待审核
		resultMap.put("one", 0);
		// 审核通过
		resultMap.put("two", 0);
		// 审核不通过
		resultMap.put("three", 0);
		if(null != countMap){
			// 全部
			resultMap.put("total", countMap.get("total"));
			// 待审核
			resultMap.put("one", countMap.get("one"));
			// 审核通过
			resultMap.put("two", countMap.get("two"));
			// 审核不通过
			resultMap.put("three", countMap.get("three"));
		}


		// 6月 时间戳
		Long firstEngageDateStart = DateUtil.convertLong(DateUtil.getDayOfMonth(0), DateUtil.DATE_FORMAT);
		// 6个月前
		Long firstEngageDateEnd = DateUtil.convertLong(DateUtil.getDayOfMonth(6), DateUtil.DATE_FORMAT);
		paramMap.put("overDateStart", firstEngageDateStart);
		paramMap.put("overDateEnd", firstEngageDateEnd);
		paramMap.put("pageNo",page.getPageNo());
		paramMap.put("pageSize",page.getPageSize());

		// 查询即将过期注册证数量
//		resultMap.put("overDateCount", firstEngageMapper.getOverDateCount(paramMap));
		com.github.pagehelper.Page page1=PageHelper.startPage(page.getPageNo(),page.getPageSize());
		// 首营列表
		List<FirstEngage> firstEngageList = firstEngageMapper.getFirstEngageInfoList(paramMap);
		//获取总数
		List<GoodsCount> maps = firstEngageMapper.getCountAll();
		Integer total = 0;
		Integer one = 0;
		Integer two = 0;
		Integer three = 0;
		for (GoodsCount s : maps){
			Integer status = s.getStatus();
			Integer count = s.getCount();
			if(status == 1)
				one = count;
			if(status == 3)
				two = count;
			if(status == 2)
				three = count;
		}
		total = one + two + three;

		resultMap.put("firstEngageList", firstEngageList);
		if(CollectionUtils.isNotEmpty(firstEngageList)){
			// 总条数
			page.setTotalRecord(total);
			// 判断审核状态
			if(null == paramMap.get("status")){
				resultMap.put("total", total);
			}
			// 待审核
			if(null != paramMap.get("status") && "1".equals(paramMap.get("status").toString())){
				resultMap.put("one", one);
			}
			// 审核通过
			if(null != paramMap.get("status") && "3".equals(paramMap.get("status").toString())){
				resultMap.put("two", two);
			}
			// 审核不通过
			if(null != paramMap.get("status") && "2".equals(paramMap.get("status").toString())){
				resultMap.put("three", three);
			}

			// 分页数据
//			List<FirstEngage> firstEngageList = firstEngageListT.subList(
//					(page.getPageNo() - 1) * page.getPageSize(), page.getTotalRecord() -
//							(page.getPageNo()) * page.getPageSize() >= 0 ? (page.getPageNo()) * page.getPageSize() : page.getTotalRecord());
			// 根据新国标分类id查询新国标分类
			List<Map<String, Object>> mapList = standardCategoryMapper.getStandardCategoryStrMap(firstEngageList);

			int size = firstEngageList.size();
			for (int i = 0; i < size; i++) {
				FirstEngage firstEngage = firstEngageList.get(i);

				// 首营更新时间
				if(null != firstEngage.getModTime() && firstEngage.getModTime() > 0){

					// 判断是否是今天
					if(DateUtil.isToday(DateUtil.convertString(firstEngage.getModTime(), DateUtil.DATE_FORMAT))){
						// 今天返回时间
						firstEngage.setModTimeStr(DateUtil.convertString(firstEngage.getModTime(), DateUtil.TIME_FORMAT_T));
					}

					// 不是返回日期
					else{
						firstEngage.setModTimeStr(DateUtil.convertString(firstEngage.getModTime(), DateUtil.DATE_FORMAT));
					}
				}else{
					firstEngage.setModTimeStr("--");
				}

				// 注册证至
				if(null != firstEngage.getRegistrationEffectiveDate() && firstEngage.getRegistrationEffectiveDate() > 0){
					firstEngage.setRegistrationEffectiveDateStr(DateUtil.convertString(firstEngage.getRegistrationEffectiveDate(), DateUtil.DATE_FORMAT));
				}else{
					firstEngage.setRegistrationEffectiveDateStr("--");
				}

				// 新国标分类赋值
				if(CollectionUtils.isNotEmpty(mapList)){
					int mapSize = mapList.size();
					for (int j = 0; j < mapSize; j++) {
						Map<String, Object> mapRes = mapList.get(j);
						// 如果根据新国标分类id能获取到数据
						if(firstEngage.getNewStandardCategoryId() > 0 && firstEngage.getNewStandardCategoryId().toString().equals(mapRes.get("standardCategoryId").toString())){
							firstEngage.setNewStandardCategoryName(mapRes.get("categoryName").toString());
						}
					}
				}
			}


			resultMap.put("firstEngageList", firstEngageList);
		}else{
			// 判断审核状态
			if(null == paramMap.get("status")){
				resultMap.put("total", total);
			}
			// 待审核
			if(null != paramMap.get("status") && "1".equals(paramMap.get("status").toString())){
				resultMap.put("one", one);
			}
			// 审核通过
			if(null != paramMap.get("status") && "3".equals(paramMap.get("status").toString())){
				resultMap.put("two", two);
			}
			// 审核不通过
			if(null != paramMap.get("status") && "2".equals(paramMap.get("status").toString())){
				resultMap.put("three", three);
			}
		}
		page.setTotalRecord(NumberUtils.toInt(page1.getTotal()+""));

		resultMap.put("page", page);

		return resultMap;
	}

	/**
	 * 获取搜索列表
	 */
	@Override
	public List<FirstEngageSearchInfo> getFirstSearchInfo(Map<String, Object> paramMap) {

		return firstEngageMapper.getFirstSearchInfo(paramMap);
	}


	/**
	 * 删除首映商品信息
	 */
	@Override
	public Integer deleteFirstEngage(Map<String, Object> paramMap) {

		return firstEngageMapper.deleteFirstEngage(paramMap);
	}


	/**
	 * 根据输入查询注册证
	 */
	@Override
	public List<RegistrationNumber> getRegistrationInfoByStr(Map<String, Object> paramMap) {

		return registrationNumberMapper.getRegistrationInfoByStr(paramMap);
	}


	/**
	 * 添加商品首营信息
	 */
	@Transactional
	@Override
	public Integer addFirstEngageInfo(FirstEngage firstEngage, User sessUser) {

		RegistrationNumber registrationNumber = firstEngage.getRegistration();
		// 判断注册证信息是否为空
		if(null == registrationNumber){
			return 0;
		}

/***************************** 企业信息 **********************************************************/
		// 公司信息
		ProductCompany productCompany = registrationNumber.getProductCompany();
		if(null == productCompany || EmptyUtils.isBlank(productCompany.getProductCompanyChineseName())){
			return 0;
		}

		// 判断公司id是否为空，如果不为空，编辑，如果为空，新增
		if(null != productCompany.getProductCompanyId()){
			Integer updateCompanyResult = productCompanyMapper.updateByPrimaryKeySelective(productCompany);
			// 修改失败
			if(updateCompanyResult <= 0){
				return 0;
			}
		}
		// 如果为空，则新增公司信息
		else{
			List<Map<String,Object>> existCompanys=productCompanyMapper.getallcompany(productCompany.getProductCompanyChineseName());
			if(CollectionUtils.isNotEmpty(existCompanys)){
				for(Map<String,Object> map:existCompanys){
					Integer id=Integer.parseInt(map.get("value")+"");
					registrationNumber.setProductCompanyId(id);
					productCompany.setProductCompanyId(id);
					productCompanyMapper.updateByPrimaryKeySelective(productCompany);
					break;
				}
			}else{
				// 1--添加企业公司信息
				Integer addCompanyResult = productCompanyMapper.insertSelective(productCompany);
				// 如果增失败
				if(addCompanyResult <= 0){
					return 0;
				}
				// 新增成功，返回企业公司id
				registrationNumber.setProductCompanyId(productCompany.getProductCompanyId());
			}
		}


/***************************** 注册证信息 **********************************************************/
		// 将日期转成时间戳  批准日期
		if(EmptyUtils.isNotBlank(registrationNumber.getIssuingDateStr())){
			registrationNumber.setIssuingDate(DateUtil.convertLong(registrationNumber.getIssuingDateStr(), DateUtil.DATE_FORMAT));
		}
		// 有效期至
		if(EmptyUtils.isNotBlank(registrationNumber.getEffectiveDateStr())){
			registrationNumber.setEffectiveDate(DateUtil.convertLong(registrationNumber.getEffectiveDateStr(), DateUtil.DATE_FORMAT));
		}
		// 变更日期 changeDateStr
		if(EmptyUtils.isNotBlank(registrationNumber.getChangeDateStr())){
			registrationNumber.setChangeDate(DateUtil.convertLong(registrationNumber.getChangeDateStr(), DateUtil.DATE_FORMAT));
		}


		// 判断注册证id是否存在
		if(null != registrationNumber.getRegistrationNumberId()){
			registrationNumber.setUpdater(sessUser.getUserId());
			// 修改注册证信息
			Integer updateRegistrationResult = registrationNumberMapper.updateByPrimaryKeySelective(registrationNumber);
			// 编辑失败
			if(updateRegistrationResult <= 0){
				return 0;
			}
		}
		// 新增
		else{
			// 添加注册证之前查询是否有重复的
			RegistrationNumber registrationNumber1 = registrationNumberMapper.getRegistrationInfoByNumber(registrationNumber.getRegistrationNumber());
			if(null != registrationNumber1){
				return 0;
			}
			registrationNumber.setCreator(sessUser.getUserId());
			// 2--新增注册证信息
			Integer addRegistrationResult = registrationNumberMapper.insertSelective(registrationNumber);
			// 如果增失败
			if(addRegistrationResult <= 0){
				return 0;
			}
		}
		firstEngage.setRegistrationNumberId(registrationNumber.getRegistrationNumberId());

/***************************** 添加附件信息 *****************************************************************************/

		// 注册证
		List<Attachment> attachmentsList = new ArrayList<>();
		// 1.先删除之前的附件信息
		Map<String, Object> attachmentMap = new HashMap<>();
		// 参数：注册证id
		attachmentMap.put("registrationNumberId", registrationNumber.getRegistrationNumberId());
		// 首营附件信息模块
		attachmentMap.put("attachmentType", CommonConstants.ATTACHMENT_TYPE_974);
		// 删除存在的附件信息
		attachmentMapper.deleteByParam(attachmentMap);

		// 注册证附件/备案凭证附件
		if(CollectionUtils.isNotEmpty(registrationNumber.getZczAttachments())){
			attachmentsList.addAll(registrationNumber.getZczAttachments());
		}

		// 营业执照
		if(CollectionUtils.isNotEmpty(registrationNumber.getYzAttachments())){
			attachmentsList.addAll(registrationNumber.getYzAttachments());
		}

		// 说明书
		if(CollectionUtils.isNotEmpty(registrationNumber.getSmsAttachments())){
			attachmentsList.addAll(registrationNumber.getSmsAttachments());
		}
		// 生产企业卫生许可证
		if(CollectionUtils.isNotEmpty(registrationNumber.getWsAttachments())){
			attachmentsList.addAll(registrationNumber.getWsAttachments());
		}
		// 生产企业生产许可证
		if(CollectionUtils.isNotEmpty(registrationNumber.getScAttachments())){
			attachmentsList.addAll(registrationNumber.getScAttachments());
		}
		// 商标注册证
		if(CollectionUtils.isNotEmpty(registrationNumber.getSbAttachments())){
			attachmentsList.addAll(registrationNumber.getSbAttachments());
		}
		// 注册登记表附件
		if(CollectionUtils.isNotEmpty(registrationNumber.getDjbAttachments())){
			attachmentsList.addAll(registrationNumber.getDjbAttachments());
		}
		// 产品图片（单包装/大包装）
		if(CollectionUtils.isNotEmpty(registrationNumber.getCpAttachments())){
			attachmentsList.addAll(registrationNumber.getCpAttachments());
		}

		// 2.再添加新增的附件信息
		if(CollectionUtils.isNotEmpty(attachmentsList)){
			// 域名
			attachmentMap.put("domain", domain);
			// 添加人
			attachmentMap.put("userId", sessUser.getUserId());
			//
			attachmentMap.put("attachmentsList", attachmentsList);
			Integer delAttachmentRes = attachmentMapper.insertAttachmentList(attachmentMap);
			if(delAttachmentRes < 0){
				return 0;
			}
		}

/***************************** 新增首营信息 **********************************************************/

		firstEngage.setCreator(sessUser.getUserId());
		// 如果是器械
		if(null != firstEngage.getGoodsType() && firstEngage.getGoodsType().equals(316)){
			firstEngage.setEffectiveDays(0);
			firstEngage.setEffectiveDayUnit(1);
		}

		// 新旧国标分类
		if(2 == firstEngage.getStandardCategoryType()){
			firstEngage.setNewStandardCategoryId(0);
		}


		// 待审核
		firstEngage.setStatus(CommonConstants.FIRST_ENGAGE_STATUS_1);

		// 转换注册证至
        if(CommonConstants.EFFECTIVE_DAY_UNIT_1.equals(firstEngage.getEffectiveDayUnit())){
            firstEngage.setSortDays(firstEngage.getEffectiveDays());
        }
        // 判断单位 月
        if(CommonConstants.EFFECTIVE_DAY_UNIT_2.equals(firstEngage.getEffectiveDayUnit())){
            firstEngage.setSortDays(firstEngage.getEffectiveDays().intValue() * 30);
        }
        // 判断单位 年
        if(CommonConstants.EFFECTIVE_DAY_UNIT_3.equals(firstEngage.getEffectiveDayUnit())){
            firstEngage.setSortDays(firstEngage.getEffectiveDays().intValue() * 365);
        }


		// 如果首营信息id不为空，修改操作
		if(null != firstEngage.getFirstEngageId()){
			firstEngage.setUpdater(sessUser.getUserId());
			Integer updateFirstEngageResult = firstEngageMapper.updateByPrimaryKeySelective(firstEngage);
			// 修改失败
			if(updateFirstEngageResult <= 0){
				return 0;
			}
		}

		// 3--新增首营信息
		else{
			Integer addFirstEngageResult = firstEngageMapper.insertSelective(firstEngage);
			// 如果增失败
			if(addFirstEngageResult <= 0){
				return 0;
			}
		}

/****************************** 存储信息  **************************************************************/

		// 参数
		Map<String, Object> paramMap = new HashMap<>();
		// 注册证id
		paramMap.put("firstEngageId", firstEngage.getFirstEngageId());
		// 删除已经存在的存储信息
		Integer deleteRes = firstEngageStorageConditionMapper.deleteByPrimaryParam(paramMap);
		// 4--存储信息(必填)
		if(CollectionUtils.isNotEmpty(firstEngage.getStorageCondition())){
			// 去除存储信息某个属性为null的元素
			List<FirstEngageStorageCondition> conditionList = firstEngage.getStorageCondition().stream().filter(x -> x.getName() != null ).collect(Collectors.toList());
			// 存储信息
			paramMap.put("storageCondition", conditionList);
			// 添加存储信息
			Integer storageResult = firstEngageStorageConditionMapper.insertSelectiveList(paramMap);
			if(storageResult <= 0){
				return 0;
			}
		}

		return firstEngage.getFirstEngageId();
	}

	/**
	 * @description 校验首营信息
	 * @author bill
	 * @param
	 * @date 2019/5/28
	 */
	public void initFirstEngageInfo(FirstEngage firstEngage) throws ShowErrorMsgException {

		if(null == firstEngage){
			throw new ShowErrorMsgException(CommonConstants.RESULTINFO_CODE_FAIL_1 + "", "请填写首营信息！");
		}

		// 注册证
		if(firstEngage.getRegistration() == null){
			throw new ShowErrorMsgException(CommonConstants.RESULTINFO_CODE_FAIL_1 + "", "注册证信息！");
		}

		// 注册证号
        if(firstEngage.getRegistration().getRegistrationNumber() == null){
			throw new ShowErrorMsgException(CommonConstants.RESULTINFO_CODE_FAIL_1 + "", "请选择或输入注册证号/备案凭证号！");
		}else{
			// 参数集
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("registrationStr1", firstEngage.getRegistration().getRegistrationNumber());

			// 如果当前操作是编辑首营信息，去重要排除当前编辑被引用的首营id
			if(null != firstEngage.getFirstEngageId()){
				paramMap.put("firstEngageId", firstEngage.getFirstEngageId());
			}
			List<RegistrationNumber> res = registrationNumberMapper.getRegistrationInfoByStr(paramMap);
			// 如果已经被引用，报错
			if(CollectionUtils.isNotEmpty(res)){
				throw new ShowErrorMsgException(CommonConstants.RESULTINFO_CODE_FAIL_1 + "", "该注册证号/备案凭证号已经被引用！");
			}
		}



	}

	@Override
	public ResultInfo dealstatus(Integer registrationNumberId) {

		Integer res = registrationNumberMapper.dealstatus(registrationNumberId);
		if(res <= 0){
			return new ResultInfo(CommonConstants.FAIL_CODE, "操作失败");
		}

		return new ResultInfo(CommonConstants.SUCCESS_CODE, "操作成功");
	}

	/**
	 * @description 首营信息审核
	 * @author bill
	 * @param
	 * @date 2019/6/10
	 */
	@Override
	public void checkFirstengage(FirstEngage firstEngage, User sessUser) throws ShowErrorMsgException {

		// 首营信息id
		if(null == firstEngage || null == firstEngage.getFirstEngageId()){
			throw new ShowErrorMsgException("找不到对应的首营信息！");
		}
		if (CommonConstants.FIRST_ENGAGE_STATUS_2.equals(firstEngage.getStatus()) && EmptyUtils.isBlank(firstEngage.getReason())) {
			throw new ShowErrorMsgException("审核不通过原因不能为空！");
		}
		// 修改审核状态
		FirstEngage firstEngage1 = new FirstEngage();
		firstEngage1.setFirstEngageId(firstEngage.getFirstEngageId());
		firstEngage1.setStatus(firstEngage.getStatus());
		firstEngage1.setCheckAgain(CommonConstants.CHECKA_GAIN_1);
		firstEngage1.setUpdater(sessUser.getUserId());
		firstEngageMapper.updateByPrimaryKeySelective(firstEngage1);

		// 添加审核记录
		LogCheckGenerate logCheckGenerate=new LogCheckGenerate();
		logCheckGenerate.setAddTime(new Date());
		logCheckGenerate.setCreator(sessUser.getUserId());
		logCheckGenerate.setLogBizId(firstEngage.getFirstEngageId());
		logCheckGenerate.setCreatorName(sessUser.getUsername());
		logCheckGenerate.setLogMessage(firstEngage.getReason());
		logCheckGenerate.setLogType(LogTypeEnum.FIRSTENGAGE.getLogType());
		logCheckGenerate.setLogStatus(firstEngage.getStatus());
		logCheckGenerate.setLogStatusName(FirstEngageCheckStatusEnum.statusName(firstEngage.getStatus()));
		logCheckGenerateMapper.insert(logCheckGenerate);
	}

	@Override
	public List<LogCheckGenerate> listSkuCheckLog(Integer firstEngageId) {
		LogCheckGenerateExample example=new LogCheckGenerateExample();
		example.createCriteria().andLogBizIdEqualTo(firstEngageId).andLogTypeEqualTo(LogTypeEnum.FIRSTENGAGE.getLogType());;
		example.setOrderByClause(" add_time desc ");
		return logCheckGenerateMapper.selectByExample(example);
	}

	@Override
	public Integer getOldStandardId(String cellval) {

		return firstEngageMapper.getOldStandardId(cellval);
	}

	@Override
	public Integer getNewStandardId(Map<String, Object> param) {
		return firstEngageMapper.getNewStandardId(param);
	}

	@Override
	public void addProductCompany(ReadFirst readFirst) {

		List<Map<String,Object>> existCompanys=productCompanyMapper.getallcompany(readFirst.getProductCompanyChineseName());
		ProductCompany productCompany=new ProductCompany();
		productCompany.setProductCompanyChineseName(readFirst.getProductCompanyChineseName());
		productCompany.setProductCompanyEnglishName(readFirst.getProductEnglishName());
		productCompany.setProductCompanyAddress(readFirst.getProductCompanyAddress());
		if(CollectionUtils.isNotEmpty(existCompanys)){
			for(Map<String,Object> map:existCompanys){

				Integer id=Integer.parseInt(map.get("value")+"");
				readFirst.setProductCompanyId(id);
				productCompany.setProductCompanyId(id);

				try{
					if(StringUtils.isNotBlank(readFirst.getProductCompanyChineseName())||StringUtils.isNotBlank(readFirst.getProductEnglishName())){
						productCompanyMapper.updateByPrimaryKeySelective(productCompany);
					}
				}catch(Exception e){
				logger.error(Contant.ERROR_MSG, e);
				}
				break;
			}
		}else{
			// 1--添加企业公司信息
			if(StringUtils.isNotBlank(readFirst.getProductCompanyChineseName())||StringUtils.isNotBlank(readFirst.getProductEnglishName())){
			Integer addCompanyResult = productCompanyMapper.insertSelective(productCompany);
			readFirst.setProductCompanyId(addCompanyResult);
			}
		}
		//firstEngageMapper.addProductCompany(readFirst);
	}

	@Override
	public void addRegisterNumber(ReadFirst readFirst) {
		RegistrationNumber registrationNumber1 = registrationNumberMapper.getRegistrationInfoByNumber(readFirst.getRegistrationNumber());
		if(registrationNumber1!=null){
			firstEngageMapper.updateExcelRegisterNumber(readFirst);
			readFirst.setFirstEngageId(registrationNumber1.getFirstEngageId());
			readFirst.setRegistrationNumberId(registrationNumber1.getRegistrationNumberId());
		}else{
			firstEngageMapper.addRegisterNumber(readFirst);

		}
	}
@Autowired
	FirstEngageGenerateMapper firstEngageGenerateMapper;
	@Override
	public void addFirstInfo(ReadFirst readFirst) {
		if(readFirst.getFirstEngageId()==null){
			firstEngageMapper.addFirstInfo(readFirst);
		}else{
			if(readFirst.getRegistrationNumberId()==null){
				return;
			}
			FirstEngageGenerateExample example=new FirstEngageGenerateExample();
			example.createCriteria().andRegistrationNumberIdEqualTo(readFirst.getRegistrationNumberId());


			List<FirstEngageGenerate> list= firstEngageGenerateMapper.selectByExample(example);
			if(CollectionUtils.isNotEmpty(list)){
				readFirst.setFirstEngageId(list.get(0).getFirstEngageId());
				firstEngageMapper.updateExcelFirstEngage(readFirst);
			}
		}
	}

    @Override
    public List<FirstEngage> test(Map<String, Object> paramMap) {
        List<FirstEngage> firstEngageListT = firstEngageMapper.getFirstEngageInfoList(paramMap);
        return firstEngageListT;
    }

	@Override
	public FirstEngage getFirstSearchDetailOnlyZczAttachment(Map<String, Object> paramMap,Integer firstEngageId) {
		// 返回信息
		FirstEngage firstEngage = firstEngageMapper.selectByPrimaryKey(firstEngageId);
		if(firstEngage==null){
			return null;
		}
		// 新国标
		if( null != firstEngage.getNewStandardCategoryId() && firstEngage.getNewStandardCategoryId() > 0){
			List<FirstEngage> firstEngageList = new ArrayList<>();
			firstEngageList.add(firstEngage);
			// 根据新国标分类id查询新国标分类
			List<Map<String, Object>> mapList = standardCategoryMapper.getStandardCategoryStrMap(firstEngageList);
			if(CollectionUtils.isNotEmpty(mapList)){
				firstEngage.setNewStandardCategoryName(mapList.get(0).get("categoryName").toString());
			}
		}

		// 旧国标
		if(null != firstEngage.getOldStandardCategoryId() && firstEngage.getOldStandardCategoryId() > 0){
			SysOptionDefinition sysOptionDefinition = sysOptionDefinitionMapper.selectByPrimaryKey(firstEngage.getOldStandardCategoryId());
			firstEngage.setOldStandardCategoryName(sysOptionDefinition.getTitle());
		}


		// 存储条件
		List<FirstEngageStorageCondition> storageCondition = firstEngageStorageConditionMapper.selectByParam(paramMap);
		if(CollectionUtils.isNotEmpty(storageCondition)){
			firstEngage.setStorageCondition(storageCondition);
		}

		// 注册证信息
		RegistrationNumber registrationNumber = registrationNumberMapper.selectByPrimaryKey(firstEngage.getRegistrationNumberId());

		// 转换时间 批准日期
		if(null != registrationNumber.getIssuingDate() && registrationNumber.getIssuingDate() > 0){
			registrationNumber.setIssuingDateStr(DateUtil.convertString(registrationNumber.getIssuingDate(), DateUtil.DATE_FORMAT));
		}
		// 转换时间 有效期至
		if(null != registrationNumber.getEffectiveDate() && registrationNumber.getEffectiveDate() > 0){
			registrationNumber.setEffectiveDateStr(DateUtil.convertString(registrationNumber.getEffectiveDate(), DateUtil.DATE_FORMAT));
		}
		// 转换时间 有效期至
		if(null != registrationNumber.getChangeDate() && registrationNumber.getChangeDate() > 0){
			registrationNumber.setChangeDateStr(DateUtil.convertString(registrationNumber.getChangeDate(), DateUtil.DATE_FORMAT));
		}

		// 附件类型
		paramMap.put("attachmentType", CommonConstants.ATTACHMENT_TYPE_974);
		List<Integer> attachmentFunction = new ArrayList<>();
		// 注册证附件/备案凭证附件
		attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_975);
//		// 说明书
//		attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_976);
//		// 营业执照
//		attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_1000);
//		// 生产企业卫生许可证
//		attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_977);
//		// 生产企业生产许可证
//		attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_978);
//		// 商标注册证
//		attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_979);
//		// 注册登记表附件
//		attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_980);
//		// 产品图片（单包装/大包装）
//		attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_981);
		paramMap.put("attachmentFunction", attachmentFunction);
		paramMap.put("registrationNumberId", registrationNumber.getRegistrationNumberId());
		// 所有附件
		List<Attachment> attachments = attachmentMapper.getAttachmentsList(paramMap);

		if(CollectionUtils.isNotEmpty(attachments)){
			// 注册证附件/备案凭证附件
			List<Attachment> zczAttachments = new ArrayList<>();
			// 编辑注册证附件信息
			List<Map<String, Object>> zczMapList = new ArrayList<>();

			// 营业执照
			List<Attachment> yzAttachments = new ArrayList<>();
			List<Map<String, Object>> yzMapList = new ArrayList<>();

			// 说明书
			List<Attachment> smsAttachments = new ArrayList<>();
			List<Map<String, Object>> smsMapList = new ArrayList<>();

			// 生产企业卫生许可证
			List<Attachment> wsAttachments = new ArrayList<>();
			List<Map<String, Object>> wsMapList = new ArrayList<>();

			// 生产企业生产许可证
			List<Attachment> scAttachments = new ArrayList<>();
			List<Map<String, Object>> scMapList = new ArrayList<>();

			// 商标注册证
			List<Attachment> sbAttachments = new ArrayList<>();
			List<Map<String, Object>> sbMapList = new ArrayList<>();

			// 注册登记表附件
			List<Attachment> djbAttachments = new ArrayList<>();
			List<Map<String, Object>> djbMapList = new ArrayList<>();

			// 产品图片（单包装/大包装）
			List<Attachment> cpAttachments = new ArrayList<>();
			List<Map<String, Object>> cpMapList = new ArrayList<>();

			int size = attachments.size();
			for (int i = 0; i < size; i++) {
				Attachment attachment = attachments.get(i);
				// 编辑注册证附件信息
				Map<String, Object> attachmentMap = new HashMap<>();
				attachmentMap.put("message", "操作成功");
				attachmentMap.put("httpUrl", api_http+domain);
				// uri
				String uri = attachment.getUri();
				if(EmptyUtils.isEmpty(uri)){
					continue;
				}
				String[] uriArray = uri.split("/");
				String fileName = uriArray[uriArray.length-1];
				String fileNameTemp = "/" + fileName;
				// 文件后缀
				String[] prefixArray = fileNameTemp.split("\\.");
				String prefix = prefixArray[prefixArray.length-1];
				// 去除路径名
				String filePath = uri.replaceAll(fileNameTemp, "");
				attachmentMap.put("fileName", fileName);
				attachmentMap.put("filePath", filePath);
				attachmentMap.put("prefix", prefix);

				// 将注册证分组
				if(CommonConstants.ATTACHMENT_FUNCTION_975.equals(attachment.getAttachmentFunction())){
					zczAttachments.add(attachment);
					zczMapList.add(attachmentMap);
				}

				// 营业执照
				if(CommonConstants.ATTACHMENT_FUNCTION_1000.equals(attachment.getAttachmentFunction())){
					yzAttachments.add(attachment);
					yzMapList.add(attachmentMap);
				}
				// 说明书
				if(CommonConstants.ATTACHMENT_FUNCTION_976.equals(attachment.getAttachmentFunction())){
					smsAttachments.add(attachment);
					smsMapList.add(attachmentMap);
				}
				// 生产企业卫生许可证
				if(CommonConstants.ATTACHMENT_FUNCTION_977.equals(attachment.getAttachmentFunction())){
					wsAttachments.add(attachment);
					wsMapList.add(attachmentMap);
				}
				// 生产企业生产许可证
				if(CommonConstants.ATTACHMENT_FUNCTION_978.equals(attachment.getAttachmentFunction())){
					scAttachments.add(attachment);
					scMapList.add(attachmentMap);
				}
				// 商标注册证
				if(CommonConstants.ATTACHMENT_FUNCTION_979.equals(attachment.getAttachmentFunction())){
					sbAttachments.add(attachment);
					sbMapList.add(attachmentMap);
				}
				// 注册登记表附件
				if(CommonConstants.ATTACHMENT_FUNCTION_980.equals(attachment.getAttachmentFunction())){
					djbAttachments.add(attachment);
					djbMapList.add(attachmentMap);
				}
				// 产品图片（单包装/大包装）
				if(CommonConstants.ATTACHMENT_FUNCTION_981.equals(attachment.getAttachmentFunction())){
					cpAttachments.add(attachment);
					cpMapList.add(attachmentMap);
				}
			}
			firstEngage.setZczMapList(zczMapList);
			firstEngage.setYzMapList(yzMapList);
			firstEngage.setSmsMapList(smsMapList);
			firstEngage.setWsMapList(wsMapList);
			firstEngage.setScMapList(scMapList);
			firstEngage.setSbMapList(sbMapList);
			firstEngage.setDjbMapList(djbMapList);
			firstEngage.setCpMapList(cpMapList);

			registrationNumber.setZczAttachments(zczAttachments);
			registrationNumber.setYzAttachments(yzAttachments);
			registrationNumber.setSmsAttachments(smsAttachments);
			registrationNumber.setWsAttachments(wsAttachments);
			registrationNumber.setScAttachments(scAttachments);
			registrationNumber.setSbAttachments(sbAttachments);
			registrationNumber.setDjbAttachments(djbAttachments);
			registrationNumber.setCpAttachments(cpAttachments);
		}
		registrationNumber.setAttachments(attachments);

// 企业信息

		ProductCompany productCompany = productCompanyMapper.selectByPrimaryKey(registrationNumber.getProductCompanyId());
		registrationNumber.setProductCompany(productCompany);
		firstEngage.setRegistration(registrationNumber);
		firstEngage.setRegistrationNumber(registrationNumber!=null?registrationNumber.getRegistrationNumber():"");
		firstEngage.setProductCompanyChineseName(productCompany!=null? StringUtils.isBlank(productCompany.getProductCompanyChineseName())?productCompany.getProductCompanyEnglishName():productCompany.getProductCompanyChineseName():"");
		firstEngage.setEffectStartDate(registrationNumber.getIssuingDateStr());
		firstEngage.setEffectEndDate(registrationNumber.getEffectiveDateStr());
		//管理类别
		firstEngage.setManageCategoryLevelShow(
				registrationNumber.getManageCategoryLevel()+""
		);
		firstEngage.setRegistration(registrationNumber);
		return firstEngage;
	}


	/*
	 * 查询注册证信息
	 * <p>Title: getRegistrationNumberInfoById</p>
	 * <p>Description: </p>
	 * @param paramMap
	 * @return
	 * @see com.vedeng.firstengage.service.FirstEngageService#getRegistrationNumberInfoById(java.util.Map)
	 */
	@Override
	public RegistrationNumber getRegistrationNumberInfoById(Map<String, Object> paramMap) {
		RegistrationNumber registrationNumber = registrationNumberMapper.getRegistrationInfoById(paramMap);
		// 处理日期
		if(null != registrationNumber){
			// 批准日期
			if(null != registrationNumber.getIssuingDate() && registrationNumber.getIssuingDate() > 0){
				registrationNumber.setIssuingDateStr(DateUtil.convertString(registrationNumber.getIssuingDate(), DateUtil.DATE_FORMAT));
			}
			// 有效期至
			if(null != registrationNumber.getEffectiveDate() && registrationNumber.getEffectiveDate() > 0){
				registrationNumber.setEffectiveDateStr(DateUtil.convertString(registrationNumber.getEffectiveDate(), DateUtil.DATE_FORMAT));
			}
		}
		return registrationNumber;
	}


	/**
	 * 新国标分类
	 */
	@Override
	public List<Map<String, Object>> getNewStandardCategory(Map<String, Object> paramMap) {

		// 查询新国标分类
		List<StandardCategory> newStandCategoryList = standardCategoryMapper.getNewStandardCategory(paramMap);
		if(CollectionUtils.isNotEmpty(newStandCategoryList)){
			// 一级新国标分类
			List<Map<String, Object>> categoryOneList = new ArrayList<>();
			// 一级分类
			int oneSize = newStandCategoryList.size();
			for (int i = 0; i < oneSize; i++) {
				StandardCategory categoryOne = newStandCategoryList.get(i);
				Map<String, Object> oneMap = new HashMap<>();
				// 名称--前段定义--label
				oneMap.put("label", categoryOne.getCategoryName());
				// id--前段定义--value
				oneMap.put("value", categoryOne.getStandardCategoryId().toString());

				// 二级分类
				if(CollectionUtils.isNotEmpty(categoryOne.getStandardCategoryList())){
					// 二级新国标分类
					List<Map<String, Object>> categoryTwoList = new ArrayList<>();
					int twoSize = categoryOne.getStandardCategoryList().size();
					for (int j = 0; j < twoSize; j++) {
						StandardCategory categoryTwo = categoryOne.getStandardCategoryList().get(j);
						Map<String, Object> twoMap = new HashMap<>();
						// 名称--前段定义--label
						twoMap.put("label", categoryTwo.getCategoryName());
						// id--前段定义--value
						twoMap.put("value", categoryTwo.getStandardCategoryId().toString());
						// 三级分类
						if(CollectionUtils.isNotEmpty(categoryTwo.getStandardCategoryList())){
							// 三级新国标分类
							List<Map<String, Object>> categoryThreeList = new ArrayList<>();
							int threeSize = categoryTwo.getStandardCategoryList().size();
							for (int k = 0; k < threeSize; k++) {
								StandardCategory categoryThree = categoryTwo.getStandardCategoryList().get(k);
								Map<String, Object> threeMap = new HashMap<>();
								// 名称--前段定义--label
								threeMap.put("label", categoryThree.getCategoryName());
								// id--前段定义--value
								threeMap.put("value", categoryThree.getStandardCategoryId().toString());
								categoryThreeList.add(threeMap);
							}
							twoMap.put("child", categoryThreeList);
						}
						// 如果为null，返回null
						else{
							twoMap.put("child", null);
						}
						categoryTwoList.add(twoMap);
					}
					oneMap.put("child", categoryTwoList);
				}
				// 如果为null，返回null
				else{
					oneMap.put("child", null);
				}
				categoryOneList.add(oneMap);
			}
			return categoryOneList;
		}
		return null;
	}

	@Override
	public FirstEngage getFirstSearchBaseInfo( Integer firstEngageId) {
		// 返回信息
		FirstEngage firstEngage = firstEngageMapper.selectByPrimaryKey(firstEngageId);
		if(firstEngage==null){
			return null;
		}
		// 新国标
		if(null != firstEngage.getNewStandardCategoryId() && firstEngage.getNewStandardCategoryId() > 0){
			List<FirstEngage> firstEngageList = new ArrayList<>();
			firstEngageList.add(firstEngage);
			// 根据新国标分类id查询新国标分类
			List<Map<String, Object>> mapList = standardCategoryMapper.getStandardCategoryStrMap(firstEngageList);
			if(CollectionUtils.isNotEmpty(mapList)){
				firstEngage.setNewStandardCategoryName(mapList.get(0).get("categoryName").toString());
			}
		}
		// 旧国标
		if(null != firstEngage.getOldStandardCategoryId() && firstEngage.getOldStandardCategoryId() > 0){
			SysOptionDefinition sysOptionDefinition = sysOptionDefinitionMapper.selectByPrimaryKey(firstEngage.getOldStandardCategoryId());
			firstEngage.setOldStandardCategoryName(sysOptionDefinition.getTitle());
		}
		// 注册证信息
		RegistrationNumber registrationNumber = registrationNumberMapper.selectByPrimaryKey(firstEngage.getRegistrationNumberId());
		// 转换时间 批准日期
		if(null != registrationNumber.getIssuingDate() && registrationNumber.getIssuingDate() > 0){
			registrationNumber.setIssuingDateStr(DateUtil.convertString(registrationNumber.getIssuingDate(), DateUtil.DATE_FORMAT));
		}
		// 转换时间 有效期至
		if(null != registrationNumber.getEffectiveDate() && registrationNumber.getEffectiveDate() > 0){
			registrationNumber.setEffectiveDateStr(DateUtil.convertString(registrationNumber.getEffectiveDate(), DateUtil.DATE_FORMAT));
		}
		// 转换时间 有效期至
		if(null != registrationNumber.getChangeDate() && registrationNumber.getChangeDate() > 0){
			registrationNumber.setChangeDateStr(DateUtil.convertString(registrationNumber.getChangeDate(), DateUtil.DATE_FORMAT));
		}
		// 企业信息

		ProductCompany productCompany = productCompanyMapper.selectByPrimaryKey(registrationNumber.getProductCompanyId());
		registrationNumber.setProductCompany(productCompany);
		firstEngage.setRegistration(registrationNumber);
		firstEngage.setRegistrationNumber(registrationNumber!=null?registrationNumber.getRegistrationNumber():"");
		firstEngage.setProductCompanyChineseName(productCompany!=null? StringUtils.isBlank(productCompany.getProductCompanyChineseName())?productCompany.getProductCompanyEnglishName():productCompany.getProductCompanyChineseName():"");
		firstEngage.setEffectStartDate(registrationNumber.getIssuingDateStr());
		firstEngage.setEffectEndDate(registrationNumber.getEffectiveDateStr());
		//管理类别
		firstEngage.setManageCategoryLevelShow(
				 registrationNumber.getManageCategoryLevel()+""
		);
		return firstEngage;
	}
	@Override
	public FirstEngage getFirstSearchDetail(Map<String, Object> paramMap, Integer firstEngageId) {


		// 返回信息
		FirstEngage firstEngage = firstEngageMapper.selectByPrimaryKey(firstEngageId);

		// 新国标
		if(null != firstEngage.getNewStandardCategoryId() && firstEngage.getNewStandardCategoryId() > 0){
			List<FirstEngage> firstEngageList = new ArrayList<>();
			firstEngageList.add(firstEngage);
			// 根据新国标分类id查询新国标分类
			List<Map<String, Object>> mapList = standardCategoryMapper.getStandardCategoryStrMap(firstEngageList);
			if(CollectionUtils.isNotEmpty(mapList)){
				firstEngage.setNewStandardCategoryName(mapList.get(0).get("categoryName").toString());
			}
		}

		// 旧国标
		if(null != firstEngage.getOldStandardCategoryId() && firstEngage.getOldStandardCategoryId() > 0){
			SysOptionDefinition sysOptionDefinition = sysOptionDefinitionMapper.selectByPrimaryKey(firstEngage.getOldStandardCategoryId());
			firstEngage.setOldStandardCategoryName(sysOptionDefinition.getTitle());
		}


		// 存储条件
		List<FirstEngageStorageCondition> storageCondition = firstEngageStorageConditionMapper.selectByParam(paramMap);
		if(CollectionUtils.isNotEmpty(storageCondition)){
			firstEngage.setStorageCondition(storageCondition);
		}

		// 注册证信息
		RegistrationNumber registrationNumber = registrationNumberMapper.selectByPrimaryKey(firstEngage.getRegistrationNumberId());

		// 转换时间 批准日期
		if(null != registrationNumber.getIssuingDate() && registrationNumber.getIssuingDate() > 0){
			registrationNumber.setIssuingDateStr(DateUtil.convertString(registrationNumber.getIssuingDate(), DateUtil.DATE_FORMAT));
		}
		// 转换时间 有效期至
		if(null != registrationNumber.getEffectiveDate() && registrationNumber.getEffectiveDate() > 0){
			registrationNumber.setEffectiveDateStr(DateUtil.convertString(registrationNumber.getEffectiveDate(), DateUtil.DATE_FORMAT));
		}
		// 转换时间 有效期至
		if(null != registrationNumber.getChangeDate() && registrationNumber.getChangeDate() > 0){
			registrationNumber.setChangeDateStr(DateUtil.convertString(registrationNumber.getChangeDate(), DateUtil.DATE_FORMAT));
		}

		// 附件类型
		paramMap.put("attachmentType", CommonConstants.ATTACHMENT_TYPE_974);
		List<Integer> attachmentFunction = new ArrayList<>();
		// 注册证附件/备案凭证附件
		attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_975);
		// 说明书
		attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_976);
		// 营业执照
		attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_1000);
		// 生产企业卫生许可证
		attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_977);
		// 生产企业生产许可证
		attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_978);
		// 商标注册证
		attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_979);
		// 注册登记表附件
		attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_980);
		// 产品图片（单包装/大包装）
		attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_981);
		paramMap.put("attachmentFunction", attachmentFunction);
		paramMap.put("registrationNumberId", registrationNumber.getRegistrationNumberId());
		// 所有附件
		List<Attachment> attachments = attachmentMapper.getAttachmentsList(paramMap);

		if(CollectionUtils.isNotEmpty(attachments)){
			// 注册证附件/备案凭证附件
			List<Attachment> zczAttachments = new ArrayList<>();
			// 编辑注册证附件信息
			List<Map<String, Object>> zczMapList = new ArrayList<>();

			// 营业执照
			List<Attachment> yzAttachments = new ArrayList<>();
			List<Map<String, Object>> yzMapList = new ArrayList<>();

			// 说明书
			List<Attachment> smsAttachments = new ArrayList<>();
			List<Map<String, Object>> smsMapList = new ArrayList<>();

			// 生产企业卫生许可证
			List<Attachment> wsAttachments = new ArrayList<>();
			List<Map<String, Object>> wsMapList = new ArrayList<>();

			// 生产企业生产许可证
			List<Attachment> scAttachments = new ArrayList<>();
			List<Map<String, Object>> scMapList = new ArrayList<>();

			// 商标注册证
			List<Attachment> sbAttachments = new ArrayList<>();
			List<Map<String, Object>> sbMapList = new ArrayList<>();

			// 注册登记表附件
			List<Attachment> djbAttachments = new ArrayList<>();
			List<Map<String, Object>> djbMapList = new ArrayList<>();

			// 产品图片（单包装/大包装）
			List<Attachment> cpAttachments = new ArrayList<>();
			List<Map<String, Object>> cpMapList = new ArrayList<>();

			int size = attachments.size();
			for (int i = 0; i < size; i++) {
				Attachment attachment = attachments.get(i);
				// 编辑注册证附件信息
				Map<String, Object> attachmentMap = new HashMap<>();
				attachmentMap.put("message", "操作成功");
				attachmentMap.put("httpUrl", api_http+domain);
				// uri
				String uri = attachment.getUri();
				if(EmptyUtils.isEmpty(uri)){
					continue;
				}
				String[] uriArray = uri.split("/");
				String fileName = uriArray[uriArray.length-1];
				String fileNameTemp = "/" + fileName;
				// 文件后缀
				String[] prefixArray = fileNameTemp.split("\\.");
				String prefix = prefixArray[prefixArray.length-1];
				// 去除路径名
				String filePath = uri.replaceAll(fileNameTemp, "");
				attachmentMap.put("fileName", fileName);
				attachmentMap.put("filePath", filePath);
				attachmentMap.put("prefix", prefix);

				// 将注册证分组
				if(CommonConstants.ATTACHMENT_FUNCTION_975.equals(attachment.getAttachmentFunction())){
					zczAttachments.add(attachment);
					zczMapList.add(attachmentMap);
				}

				// 营业执照
				if(CommonConstants.ATTACHMENT_FUNCTION_1000.equals(attachment.getAttachmentFunction())){
					yzAttachments.add(attachment);
					yzMapList.add(attachmentMap);
				}
				// 说明书
				if(CommonConstants.ATTACHMENT_FUNCTION_976.equals(attachment.getAttachmentFunction())){
					smsAttachments.add(attachment);
					smsMapList.add(attachmentMap);
				}
				// 生产企业卫生许可证
				if(CommonConstants.ATTACHMENT_FUNCTION_977.equals(attachment.getAttachmentFunction())){
					wsAttachments.add(attachment);
					wsMapList.add(attachmentMap);
				}
				// 生产企业生产许可证
				if(CommonConstants.ATTACHMENT_FUNCTION_978.equals(attachment.getAttachmentFunction())){
					scAttachments.add(attachment);
					scMapList.add(attachmentMap);
				}
				// 商标注册证
				if(CommonConstants.ATTACHMENT_FUNCTION_979.equals(attachment.getAttachmentFunction())){
					sbAttachments.add(attachment);
					sbMapList.add(attachmentMap);
				}
				// 注册登记表附件
				if(CommonConstants.ATTACHMENT_FUNCTION_980.equals(attachment.getAttachmentFunction())){
					djbAttachments.add(attachment);
					djbMapList.add(attachmentMap);
				}
				// 产品图片（单包装/大包装）
				if(CommonConstants.ATTACHMENT_FUNCTION_981.equals(attachment.getAttachmentFunction())){
					cpAttachments.add(attachment);
					cpMapList.add(attachmentMap);
				}
			}
			firstEngage.setZczMapList(zczMapList);
			firstEngage.setYzMapList(yzMapList);
			firstEngage.setSmsMapList(smsMapList);
			firstEngage.setWsMapList(wsMapList);
			firstEngage.setScMapList(scMapList);
			firstEngage.setSbMapList(sbMapList);
			firstEngage.setDjbMapList(djbMapList);
			firstEngage.setCpMapList(cpMapList);

			registrationNumber.setZczAttachments(zczAttachments);
			registrationNumber.setYzAttachments(yzAttachments);
			registrationNumber.setSmsAttachments(smsAttachments);
			registrationNumber.setWsAttachments(wsAttachments);
			registrationNumber.setScAttachments(scAttachments);
			registrationNumber.setSbAttachments(sbAttachments);
			registrationNumber.setDjbAttachments(djbAttachments);
			registrationNumber.setCpAttachments(cpAttachments);
		}
		registrationNumber.setAttachments(attachments);
		// 企业信息
		ProductCompany productCompany = productCompanyMapper.selectByPrimaryKey(registrationNumber.getProductCompanyId());
		registrationNumber.setProductCompany(productCompany);
		firstEngage.setRegistration(registrationNumber);

		return firstEngage;
	}

	/**
	 * @description 根据名称查询新国标分类
	 * @author bill
	 * @param
	 * @date 2019/4/24
	 */
	@Override
	public List<Map<String, Object>> getNewStandardCategoryByName(Map<String, Object> paramMap) {

		List<StandardCategory> standardCategoryList = standardCategoryMapper.getNewStandardCategoryByName(paramMap);
		// 如果新国标分类不为空
		if(CollectionUtils.isNotEmpty(standardCategoryList)){
			List<Map<String, Object>> listMap = new ArrayList<>();
			int newStandSize = standardCategoryList.size();
			for (int i=0; i<newStandSize; i++){
				Map<String, Object> map = new HashMap<>();
				StandardCategory standardCategory = standardCategoryList.get(i);
				// 新国标名称
				map.put("label", standardCategory.getCategoryName());
				// 最小id
				map.put("value", standardCategory.getStandardCategoryId());
				listMap.add(map);
			}
			return listMap;
		}
		return null;
	}

	/**
	 * @description 新国标分类
	 * @author bill
	 * @param
	 * @date 2019/5/21
	 */
	@Override
	public List<StandardCategory> getallStandard() {
		Map<String,Object> paramMap= Maps.newHashMap();
		paramMap.put("status","1");
		paramMap.put("parentId","0");
		return standardCategoryMapper.getNewStandardCategory(paramMap);
	}

	/**
	 * @description 所有公司
	 * @author bill
	 * @param
	 * @date 2019/5/21
	 */
	@Override
	public List<Map<String,Object>>  getallcompany(String productCompanyName) {

		return productCompanyMapper.getallcompany(productCompanyName);
	}

	@Override
	public List<RegistrationNumber> getRefreshFirstList(Map<String, Object> paramMap) {

		return registrationNumberMapper.getRefreshFirstList(paramMap);
	}

	/**
	 * @description 刷临效期状态
	 * @author bill
	 * @param
	 * @date 2019/5/28
	 */
	@Override
	public void refreshFirstList(List<RegistrationNumber> list) {
		Map<String, Object> param = new HashMap<>();
		param.put("list", list);
		registrationNumberMapper.refreshFirstList(param);
	}


	private void updateRegister(){

		// 爬虫注册证数据
		List<RegistrationNumber> list = registrationNumberMapper.getPCInfo();

		// 批量插入
		int size = list.size();
		int toIndex = INSERT_NUM;
		for (int i = 0; i < size; i += INSERT_NUM){
			if(i + INSERT_NUM > size){
				toIndex = size - i;
			}
			List<RegistrationNumber> list2 = list.subList(i, i + toIndex);
			// 生产企业
			productCompanyMapper.insertList(list2);
			// 插入操作
			registrationNumberMapper.insertList(list2);
		}
	}


	// 同步老数据
	private void synchronizationGoods(){
		List<RegistrationNumber> list = registrationNumberMapper.getHisRegFromGoods();
	}

}
