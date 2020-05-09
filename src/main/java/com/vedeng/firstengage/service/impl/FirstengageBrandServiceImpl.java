package com.vedeng.firstengage.service.impl;

import com.github.pagehelper.PageHelper;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.firstengage.dao.FirstengageBrandMapper;
import com.vedeng.firstengage.model.FirstengageBrand;
import com.vedeng.firstengage.service.FirstengageBrandService;
import com.vedeng.system.dao.AttachmentMapper;
import com.vedeng.system.model.Attachment;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("firstengageBrandService")
public class FirstengageBrandServiceImpl extends BaseServiceimpl implements FirstengageBrandService {


	@Value("${file_url}")
	protected String domain;

	@Autowired
	@Qualifier("firstengageBrandMapper")
	private FirstengageBrandMapper brandMapper;


	@Autowired
	@Qualifier("attachmentMapper")
	private AttachmentMapper attachmentMapper;



	@Override
	public List<FirstengageBrand> getAllBrand(FirstengageBrand brand) {
		return brandMapper.getAllBrand(brand);
	}

	@Override
	public Map<String, Object> getBrandListPage(FirstengageBrand brand, Page page) {
		List<FirstengageBrand> list = null;
		Map<String,Object> map = new HashMap<>();

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("page", page);
		queryMap.put("brand", brand);

		list = brandMapper.getBrandlistpage(queryMap);

		if(list != null && list.size() > 0){
			for (FirstengageBrand b : list) {

				// 判断是否是今天
				if(DateUtil.isToday(DateUtil.convertString(b.getModTime(), DateUtil.DATE_FORMAT))){
					// 今天返回时间
					b.setModTimeStr(DateUtil.convertString(b.getModTime(), DateUtil.TIME_FORMAT_T));
				}

				// 不是返回日期
				else{
					b.setModTimeStr(DateUtil.convertString(b.getModTime(), DateUtil.DATE_FORMAT));
				}

				if(ObjectUtils.notEmpty(b.getLogoUri())){
					b.setLogoUriName(b.getLogoUri().substring(b.getLogoUri().lastIndexOf("/")+1));
				}
			}
		}
		map.put("list", list);
		map.put("page", page);
		return map;
	}


	@Override
	public ResultInfo<?> saveBrand(FirstengageBrand brand) {

		int j = brandMapper.addBrand(brand);
		if(brand.getBrandId() <= 0){
			return new ResultInfo(CommonConstants.FAIL_CODE,"操作失败");
		}

		//保存附件
		// 1.先删除之前的附件信息
		Map<String, Object> attachmentMap = new HashMap<>();
		// 参数：注册证id
		attachmentMap.put("registrationNumberId", brand.getBrandId());
		// 品牌附件信息
		attachmentMap.put("attachmentType", CommonConstants.ATTACHMENT_TYPE_987);
		// 删除存在的附件信息
		attachmentMapper.deleteByParam(attachmentMap);
		// 2.再添加新增的附件信息
		if(CollectionUtils.isNotEmpty(brand.getProof())){
			// 域名
			attachmentMap.put("domain", domain);
			attachmentMap.put("attachmentsList", brand.getProof());
			// 创建人
			attachmentMap.put("userId", brand.getCreator());
			attachmentMapper.insertAttachmentList(attachmentMap);
		}

		// 3.生产企业
		if(EmptyUtils.isNotBlank(brand.getManufacturer())){
			String[] manufacturer = brand.getManufacturer().split("@");
			attachmentMap.put("manufacturer", manufacturer);
			attachmentMap.put("brandId", brand.getBrandId());
			brandMapper.addBrandAndManufacturer(attachmentMap);
		}

		if(j>0){
			return new ResultInfo(0,"操作成功",brand);
		}
		return new ResultInfo();
	}

	@Override
	public FirstengageBrand getBrandByKey(FirstengageBrand brand) {
		return brandMapper.getBrandByKey(brand.getBrandId());
	}

	@Override
	public ResultInfo<?> editBrand(FirstengageBrand brand) {
		Integer i = brandMapper.vailBrandName(brand);
		if(i!=0){
			return new ResultInfo(-1, "品牌名称不允许重复");
		}
		int j = brandMapper.editBrand(brand);
		// 1.先删除之前的附件信息
		Map<String, Object> attachmentMap = new HashMap<>();
		// 参数：注册证id
		attachmentMap.put("registrationNumberId", brand.getBrandId());
		// 品牌附件信息
		attachmentMap.put("attachmentType", CommonConstants.ATTACHMENT_TYPE_987);
		// 删除存在的附件信息
		attachmentMapper.deleteByParam(attachmentMap);

		// 2.再添加新增的附件信息
		if(CollectionUtils.isNotEmpty(brand.getProof())){
			// 域名
			attachmentMap.put("domain", domain);
			attachmentMap.put("attachmentsList", brand.getProof());
			// 创建人
			attachmentMap.put("userId", brand.getCreator());
			attachmentMapper.insertAttachmentList(attachmentMap);
		}

		// 生产厂商
		if(EmptyUtils.isNotBlank(brand.getManufacturer())){
			// 先删除
			brandMapper.deleteManufacturer(brand.getBrandId());
			// 再添加
			attachmentMap.put("manufacturer", brand.getManufacturer().split("@"));
			attachmentMap.put("brandId", brand.getBrandId());
			brandMapper.addBrandAndManufacturer(attachmentMap);
		}

		if(j==1){
			return new ResultInfo(0, "操作成功");
		}
		return new ResultInfo();
	}

	@Override
	public ResultInfo<?> delBrand(FirstengageBrand brand) {
		ResultInfo result = new ResultInfo();

		// 验证是否已经被删除
		FirstengageBrand bra = brandMapper.getBrandByKey(brand.getBrandId());
		if(null == bra){
			result.setMessage("该品牌已经被删除！");
			return result;
		}

		//验证此品牌下是否存在产品
		Integer i = brandMapper.vailBrandGoods(brand);

		// 1.先删除之前的附件信息
		Map<String, Object> attachmentMap = new HashMap<>();
		// 参数：注册证id
		attachmentMap.put("registrationNumberId", brand.getBrandId());
		// 品牌附件信息
		attachmentMap.put("attachmentType", CommonConstants.ATTACHMENT_TYPE_987);
		// 删除存在的附件信息
		attachmentMapper.deleteByParam(attachmentMap);

		if(i==0){
			int j = brandMapper.delBrand(brand);
			if(j==1){
				result.setCode(0);result.setMessage("操作成功");
			}
		}else{
			result.setMessage("该品牌下有产品，无法删除！");
		}
		return result;
	}

	public List<Map<String,Object>> getTraderSupplierAll(String[] str) {
		List<Map<String,Object>> list = brandMapper.getTraderSupplierAll(str);
		return list;
	}

	@Override
	public FirstengageBrand getBrandByParam(Integer brandId) {
		// 品牌详情
		FirstengageBrand brand = brandMapper.getBrandByKey(brandId);
		// 如果品牌为空 返回
		if(null == brand){
			return brand;
		}
		Map<String, Object> paramMap = new HashMap<>();
		List<Integer> attachmentFunction = new ArrayList<>();
		attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_988);
		paramMap.put("attachmentFunction", attachmentFunction);
		paramMap.put("registrationNumberId", brand.getBrandId());
		// 附件类型
		paramMap.put("attachmentType", CommonConstants.ATTACHMENT_TYPE_987);
		// 授权证明
		List<Attachment> attachments = attachmentMapper.getAttachmentsList(paramMap);
		// 空判断
		if(CollectionUtils.isNotEmpty(attachments)){
			brand.setProof(attachments);
		}
		return brand;
	}

	/**
	 * @description 查询导出的品牌列表
	 * @author bill
	 * @param
	 * @date 2019/5/6
	 */
	@Override
	public List<FirstengageBrand> getBrandListByParam(HttpServletRequest request,Map<String, Object> paramMap) {

		// 查询结果
		List<FirstengageBrand> brandList = brandMapper.getBrandListByParam(paramMap);
		// 如果品牌不为空，查询品牌对应的商品数
		if(CollectionUtils.isNotEmpty(brandList)){
			int brandSize = brandList.size();
			// 品牌对应的商品数
			List<Map<String, Integer>> mapList = brandMapper.getBrandGoodsCountByParam(paramMap);
			int goodsCountSize = mapList.size();
			// 品牌对应商品数不为空
			if(CollectionUtils.isNotEmpty(mapList)){
				// 循环品牌数
				for (int i = 0; i < brandSize; i++){
					FirstengageBrand brand = brandList.get(i);


					String path = "D:/aa.jpg";


					File file2 = new File(path);

//					try {
//
//						//得到session
//						Configuration cfg = new Configuration().configure();
//						ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
//						SessionFactory sf = cfg.buildSessionFactory(sr);
//						Session session = sf.openSession();
//						//得到LobHelper
//						LobHelper lobHelper = session.getLobHelper();
//						//得到图片的blob
//						InputStream in = new FileInputStream(path);
//						Blob blob = lobHelper.createBlob(in, in.available());
//						brand.setLogoObj(blob);
//					} catch (Exception e) {
//						logger.error(Contant.ERROR_MSG, e);
//					}


					// 品牌商品数
					for (int j = 0; j < goodsCountSize; j++){
						Map<String, Integer> map = mapList.get(j);
						// 判断品牌id
						if(brand.getBrandId().equals(map.get("brandId"))){
							brand.setGoodsNum(map.get("GOODS_NUM"));
						}
					}
				}
			}

			// 品牌商品为空
			else{
				// 循环品牌商品数为0
				for (int i = 0; i < brandSize; i++){
					FirstengageBrand brand = brandList.get(i);
					brand.setGoodsNum(0);
				}
			}
		}
		return brandList;
	}

	/**
	 * @description 根据品牌名称查询品牌
	 * @author bill
	 * @param
	 * @date 2019/5/14
	 */
	@Override
	public List<Map<String, String>> getBrandInfoByParam(Map<String, Object> paramMap) {
		PageHelper.startPage(1, NumberUtils.toInt(paramMap.get("pageSize")+"",5));
		return brandMapper.getBrandInfoByParam(paramMap);
	}

	/**
	 * @description 校验品牌
	 * @author bill
	 * @param
	 * @date 2019/5/21
	 */
	@Override
	public void initBrand(FirstengageBrand brand) throws ShowErrorMsgException {
		// 校验商品品牌
		if(brand.getBrandNature() == null){
			throw new ShowErrorMsgException(CommonConstants.RESULTINFO_CODE_FAIL_1 + "", "请选择品牌类型！");
		}

		// 如果品牌类型等于1，校验国产品牌
		if(brand.getBrandNature() == 1 && brand.getBrandName() == null){
			throw new ShowErrorMsgException(CommonConstants.RESULTINFO_CODE_FAIL_1 + "", "请输入品牌中文名！");
		}

		// 如果品牌类型等于2，校验进口品牌
		if(brand.getBrandNature() == 2 && brand.getBrandNameEn() == null){
			throw new ShowErrorMsgException(CommonConstants.RESULTINFO_CODE_FAIL_1 + "", "请输入品牌英文名！");
		}

		Integer i = brandMapper.vailBrandName(brand);
		if(i > 0){
			throw new ShowErrorMsgException(CommonConstants.RESULTINFO_CODE_FAIL_1 + "", "品牌名称不允许重复！");
		}

		// 验证生产企业
//		if(EmptyUtils.isBlank(brand.getManufacturer())){
//			throw new ShowErrorMsgException(CommonConstants.RESULTINFO_CODE_FAIL_1 + "", "请选择生产企业！");
//		}

		// 验证品牌logo
		if(EmptyUtils.isBlank(brand.getLogoUri())){
			throw new ShowErrorMsgException(CommonConstants.RESULTINFO_CODE_FAIL_1 + "", "请上传品牌Logo！");
		}

		// 验证品牌说明
		if(EmptyUtils.isNotBlank(brand.getDescription()) && brand.getDescription().length() > 100000){
			throw new ShowErrorMsgException(CommonConstants.RESULTINFO_CODE_FAIL_1 + "", "品牌说明长度超过限制！");
		}

	}
}
