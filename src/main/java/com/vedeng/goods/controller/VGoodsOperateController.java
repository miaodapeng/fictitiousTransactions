package com.vedeng.goods.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.goods.GoodsConstants;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.http.HttpURLConstant;
import com.vedeng.common.http.NewHttpClientUtils;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.common.util.HttpRestClientUtil;
import com.vedeng.common.util.StringUtil;
import com.vedeng.common.validator.FormToken;
import com.vedeng.goods.command.SkuAddCommand;
import com.vedeng.goods.dao.CoreOperateInfoGenerateExtendMapper;
import com.vedeng.goods.model.CoreSkuGenerate;
import com.vedeng.goods.model.GoodsAttachment;
import com.vedeng.goods.model.Platfrom;
import com.vedeng.goods.model.dto.CoreSkuBaseDTO;
import com.vedeng.goods.model.dto.CoreSpuBaseDTO;
import com.vedeng.goods.model.vo.CoreOperateInfoGenerateVo;
import com.vedeng.goods.model.vo.OperateSpuVo;
import com.vedeng.goods.service.BaseGoodsService;
import com.vedeng.goods.service.CoreOperateInfoService;
import com.vedeng.goods.service.VgoodsService;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/vgoods/operate")
public class VGoodsOperateController extends BaseController {

	@Autowired
	VgoodsService goodsService;

	@Autowired
	BaseGoodsService baseGoodsService;

	@Autowired
	CoreOperateInfoService coreOperateInfoService;

	@Value("${operate_url}")
	private String operateUrl;

	/**
	 * 打开新增/编辑商品运营信息
	 * @author Cooper
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@FormToken(save = true)
	@RequestMapping(value = "/openOperate")
	public ModelAndView openOperate(CoreOperateInfoGenerateVo coreOperateInfoGenerateVo, HttpServletRequest request,
								   HttpServletResponse response) throws Exception {
		try{
			ModelAndView openOperateMv = new ModelAndView();
			//进行编辑
			if (coreOperateInfoGenerateVo != null ){
				if(coreOperateInfoGenerateVo.getOperateInfoId() != null && !"".equals(coreOperateInfoGenerateVo.getOperateInfoId())) {
					//查询运营信息
					coreOperateInfoGenerateVo = coreOperateInfoService.getCoreOperateInfoById(coreOperateInfoGenerateVo);
					if (coreOperateInfoGenerateVo.getOperateInfoType().equals(CommonConstants.OPERATE_INFO_TYPE_SKU_2)){
						//查询该sku对应的spuId
						SkuAddCommand skuAddCommand = new SkuAddCommand();
						skuAddCommand.setSkuId(coreOperateInfoGenerateVo.getSkuId());
						CoreSkuGenerate coreSkuGenerate = goodsService.initSku(skuAddCommand);
						coreOperateInfoGenerateVo.setUpSpuId(coreSkuGenerate.getSpuId());
					}
				}else{
					if (coreOperateInfoGenerateVo.getSkuId() != null && !"".equals(coreOperateInfoGenerateVo.getSkuId())){//SKUID不为空则绑定的是SKU
						CoreSkuBaseDTO coreSkuBaseDTO = baseGoodsService.selectSkuBaseById(coreOperateInfoGenerateVo.getSkuId());
						if (!coreSkuBaseDTO.getCheckStatus().equals(CommonConstants.GOODS_CHECK_STATUS_3)){
							openOperateMv.setViewName("common/nopower");
							return openOperateMv;
						}
						Integer skuId = coreOperateInfoGenerateVo.getSkuId();
						//查询该sku对应的运营信息
						coreOperateInfoGenerateVo = coreOperateInfoService.getCoreOperateInfoBySkuId(coreOperateInfoGenerateVo.getSkuId());
						if (coreOperateInfoGenerateVo == null){
							coreOperateInfoGenerateVo = new CoreOperateInfoGenerateVo();
							coreOperateInfoGenerateVo.setSkuId(skuId);
							//查询sku名称
							coreOperateInfoGenerateVo.setGoodsName(coreOperateInfoService.getProductNameBySkuId(skuId));
							coreOperateInfoGenerateVo.setOperateInfoType(CommonConstants.OPERATE_INFO_TYPE_SKU_2);
							//查询该sku对应的SPUID
							SkuAddCommand skuAddCommand = new SkuAddCommand();
							skuAddCommand.setSkuId(skuId);
							CoreSkuGenerate coreSkuGenerate = goodsService.initSku(skuAddCommand);
							coreOperateInfoGenerateVo.setUpSpuId(coreSkuGenerate.getSpuId());
							//查询该sku对应的spu图片
							GoodsAttachment goodsAttachment = new GoodsAttachment();
							goodsAttachment.setGoodsId(coreSkuGenerate.getSpuId());
							goodsAttachment.setStatus(CommonConstants.STATUS_1);
							goodsAttachment.setAttachmentType(CommonConstants.ATTACHMENT_TYPE_SPU_1002);
							List<GoodsAttachment> goodsAttachmentList = coreOperateInfoService.getGoodsAttachment(goodsAttachment);
							//处理商品图片，处理成json格式
							String goodsImgJson = this.doGoodsAttachmentToJsonInList(goodsAttachmentList);
							openOperateMv.addObject("goodsImgJson",goodsImgJson);
						}else {
							//查询该sku对应的SPUID
							SkuAddCommand skuAddCommand = new SkuAddCommand();
							skuAddCommand.setSkuId(skuId);
							CoreSkuGenerate coreSkuGenerate = goodsService.initSku(skuAddCommand);
							coreOperateInfoGenerateVo.setUpSpuId(coreSkuGenerate.getSpuId());
						}

					}else if (coreOperateInfoGenerateVo.getSpuId() != null && !"".equals(coreOperateInfoGenerateVo.getSpuId())){//SPUID不为空则绑定的是SPU
						Integer spuId = coreOperateInfoGenerateVo.getSpuId();
						//检查spu的审核状态是否已经通过
						CoreSpuBaseDTO coreSpuBaseDTO = baseGoodsService.selectSpuBaseById(spuId);
						if (!coreSpuBaseDTO.getCheckStatus().equals(CommonConstants.GOODS_CHECK_STATUS_3)){
							openOperateMv.setViewName("common/nopower");
							return openOperateMv;
						}
						//查询该spu对应的运营信息
						coreOperateInfoGenerateVo = coreOperateInfoService.getCoreOperateInfoBySpuId(spuId);
						if (coreOperateInfoGenerateVo == null){
							coreOperateInfoGenerateVo = new CoreOperateInfoGenerateVo();
							//查询spu名称
							coreOperateInfoGenerateVo.setSpuId(spuId);
							coreOperateInfoGenerateVo.setGoodsName(coreOperateInfoService.getProductNameBySpuId(spuId));
							coreOperateInfoGenerateVo.setOperateInfoType(CommonConstants.OPERATE_INFO_TYPE_SPU_1);
						}
					}
				}
			    if (coreOperateInfoGenerateVo.getOperateInfoId() != null && !"".equals(coreOperateInfoGenerateVo.getOperateInfoId())){
					//查询商品图片信息
					GoodsAttachment goodsAttachment = new GoodsAttachment();
					if (coreOperateInfoGenerateVo.getOperateInfoType().equals(CommonConstants.OPERATE_INFO_TYPE_SKU_2)){
						goodsAttachment.setAttachmentType(CommonConstants.ATTACHMENT_TYPE_SKU_1001);
						goodsAttachment.setStatus(CommonConstants.STATUS_1);
						goodsAttachment.setGoodsId(coreOperateInfoGenerateVo.getSkuId());
					}else{
						goodsAttachment.setAttachmentType(CommonConstants.ATTACHMENT_TYPE_SPU_1002);
						goodsAttachment.setStatus(CommonConstants.STATUS_1);
						goodsAttachment.setGoodsId(coreOperateInfoGenerateVo.getSpuId());
					}
					List<GoodsAttachment> goodsAttachmentList = coreOperateInfoService.getGoodsAttachment(goodsAttachment);
					//处理商品图片，处理成json格式
					String goodsImgJson = this.doGoodsAttachmentToJsonInList(goodsAttachmentList);
					openOperateMv.addObject("goodsImgJson",goodsImgJson);
					//处理商品关键词，处理成数组格式
					if(EmptyUtils.isNotBlank(coreOperateInfoGenerateVo.getSeoKeywords())){
						coreOperateInfoGenerateVo.setSeoKeyWordsArray(coreOperateInfoGenerateVo.getSeoKeywords().split(","));
					}
				}
			}
			openOperateMv.addObject("coreOperateInfoGenerateVo",coreOperateInfoGenerateVo);
			openOperateMv.setViewName("goods/vgoods/addOperate");
			return openOperateMv;
		}catch (Exception e){
			logger.error("打开新增/编辑商品运营信息异常：",e);
			return this.page500();
		}
	}

	/**
	 * 保存商品运营信息
	 * @author Cooper
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@FormToken(remove = true)
	@RequestMapping(value = "/saveOperate")
	@ResponseBody
	public ModelAndView saveOperate(CoreOperateInfoGenerateVo coreOperateInfoGenerateVo,HttpServletRequest request,
								  HttpServletResponse response) throws Exception {
		try{
			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
			if (user != null){
				//检查商品是否已审核通过，未通过不可保存
				if (CommonConstants.OPERATE_INFO_TYPE_SPU_1.equals(coreOperateInfoGenerateVo.getOperateInfoType())){
					CoreSpuBaseDTO coreSpuBaseDTO = baseGoodsService.selectSpuBaseById(coreOperateInfoGenerateVo.getSpuId());
					if (!coreSpuBaseDTO.getCheckStatus().equals(CommonConstants.GOODS_CHECK_STATUS_3)){
						return new ModelAndView("common/nopower");
					}
				}else {
					CoreSkuBaseDTO coreSkuBaseDTO = baseGoodsService.selectSkuBaseById(coreOperateInfoGenerateVo.getSkuId());
					if (!coreSkuBaseDTO.getCheckStatus().equals(CommonConstants.GOODS_CHECK_STATUS_3)){
						return new ModelAndView("common/nopower");
					}
				}
                //检查运营信息是否可保存，字段验证
				ResultInfo resultInfo = this.checkOperateInfo(coreOperateInfoGenerateVo);
				if (CommonConstants.SUCCESS_CODE.equals(resultInfo.getCode())){//验证通过
					//处理图片列表
					List<GoodsAttachment> goodsAttachmentList = null;
					if (coreOperateInfoGenerateVo.getOperateInfoType().equals(CommonConstants.OPERATE_INFO_TYPE_SKU_2)){
						goodsAttachmentList = this.doGoodsAttachment(coreOperateInfoGenerateVo.getGoodsImage(),coreOperateInfoGenerateVo.getSkuId(),coreOperateInfoGenerateVo.getOperateInfoType());
					}else{
						goodsAttachmentList = this.doGoodsAttachment(coreOperateInfoGenerateVo.getGoodsImage(),coreOperateInfoGenerateVo.getSpuId(),coreOperateInfoGenerateVo.getOperateInfoType());
					}
					//保存运营信息
					Integer operateInfoId = coreOperateInfoService.saveCoreOperateInfo(coreOperateInfoGenerateVo,goodsAttachmentList);
					if (CommonConstants.OPERATE_INFO_TYPE_SPU_1.equals(coreOperateInfoGenerateVo.getOperateInfoType())){
						goodsService.updateSpuOperateInfoFlag(operateInfoId,coreOperateInfoGenerateVo.getSpuId());
					}else {
						goodsService.updateSkuOperateInfoFlag(operateInfoId,coreOperateInfoGenerateVo.getSkuId());
					}

					if (coreOperateInfoGenerateVo != null && coreOperateInfoGenerateVo.getSpuId() != null) {
						baseGoodsService.updatePushStatusBySpuId(coreOperateInfoGenerateVo.getSpuId(), GoodsConstants.PUSH_STATUS_UN_PUSH);
					} else if (coreOperateInfoGenerateVo != null && coreOperateInfoGenerateVo.getSkuId() != null) {
						baseGoodsService.updatePushStatusBySkuId(coreOperateInfoGenerateVo.getSkuId(), GoodsConstants.PUSH_STATUS_UN_PUSH);
					}

					return new ModelAndView("redirect:./viewOperate.do?operateInfoId="+operateInfoId);
				}else{
					request.setAttribute("error",resultInfo.getMessage());
				}
			}else{
				request.setAttribute("error","登陆信息已失效，请重新登陆！");
			}


			return new ModelAndView("forward:./openNewOperate.do");
		}catch (Exception e){
			logger.error("打开新增/编辑商品运营信息异常：",e);
			return this.page500();
		}
	}


	/**
	 * 转发打开新增/编辑商品运营信息
	 * @author Cooper
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@FormToken(save = true)
	@RequestMapping(value = "/openNewOperate")
	public ModelAndView openNewOperate(CoreOperateInfoGenerateVo coreOperateInfoGenerateVo, HttpServletRequest request,
									HttpServletResponse response) throws Exception {
		try{
			ModelAndView openOperateMv = new ModelAndView();
			//处理商品图片信息,处理成json格式
			String goodsImgJson = this.doGoodsAttachmentToJsonInArray(coreOperateInfoGenerateVo.getGoodsImage());
			openOperateMv.addObject("goodsImgJson",goodsImgJson);
			openOperateMv.addObject("error", request.getAttribute("error"));
			openOperateMv.addObject("coreOperateInfoGenerateVo",coreOperateInfoGenerateVo);
			openOperateMv.setViewName("goods/vgoods/addOperate");
			return openOperateMv;
		}catch (Exception e){
			logger.error("转发打开新增/编辑商品运营信息异常：",e);
			return this.page500();
		}
	}
	/**
	 * 查看商品运营信息
	 * @author Cooper
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewOperate")
	public ModelAndView viewOperate(CoreOperateInfoGenerateVo coreOperateInfoGenerateVo,HttpServletRequest request,
									HttpServletResponse response) throws Exception {
		try{
			ModelAndView viewOprateMv = new ModelAndView();
			if (coreOperateInfoGenerateVo != null ){
				if(coreOperateInfoGenerateVo.getOperateInfoId() != null && !"".equals(coreOperateInfoGenerateVo.getOperateInfoId())) {
					//查询运营信息
					coreOperateInfoGenerateVo = coreOperateInfoService.getCoreOperateInfoById(coreOperateInfoGenerateVo);
					if (coreOperateInfoGenerateVo.getOperateInfoType().equals(CommonConstants.OPERATE_INFO_TYPE_SKU_2)){
						//查询该sku对应的spuId
						SkuAddCommand skuAddCommand = new SkuAddCommand();
						skuAddCommand.setSkuId(coreOperateInfoGenerateVo.getSkuId());
						CoreSkuGenerate coreSkuGenerate = goodsService.initSku(skuAddCommand);
						coreOperateInfoGenerateVo.setUpSpuId(coreSkuGenerate.getSpuId());
					}
				}else{
					if (coreOperateInfoGenerateVo.getSkuId() != null && !"".equals(coreOperateInfoGenerateVo.getSkuId())) {//SKUID不为空则绑定的是SKU
						Integer skuId = coreOperateInfoGenerateVo.getSkuId();

						//查询该sku对应的运营信息
						coreOperateInfoGenerateVo = coreOperateInfoService.getCoreOperateInfoBySkuId(coreOperateInfoGenerateVo.getSkuId());

						if (coreOperateInfoGenerateVo == null) {
							coreOperateInfoGenerateVo = new CoreOperateInfoGenerateVo();
							coreOperateInfoGenerateVo.setSkuId(skuId);
							//查询sku名称
							coreOperateInfoGenerateVo.setGoodsName(coreOperateInfoService.getProductNameBySkuId(skuId));
							coreOperateInfoGenerateVo.setOperateInfoType(CommonConstants.OPERATE_INFO_TYPE_SKU_2);
						}
						//查询该sku对应的SPUID
						SkuAddCommand skuAddCommand = new SkuAddCommand();
						skuAddCommand.setSkuId(skuId);
						CoreSkuGenerate coreSkuGenerate = goodsService.initSku(skuAddCommand);
						coreOperateInfoGenerateVo.setUpSpuId(coreSkuGenerate.getSpuId());
					}else {
						Integer spuId = coreOperateInfoGenerateVo.getSpuId();
						//查询该spu对应的运营信息
						coreOperateInfoGenerateVo = coreOperateInfoService.getCoreOperateInfoBySpuId(spuId);
						if (coreOperateInfoGenerateVo == null){
							coreOperateInfoGenerateVo = new CoreOperateInfoGenerateVo();
							//查询spu名称
							coreOperateInfoGenerateVo.setSpuId(spuId);
							coreOperateInfoGenerateVo.setGoodsName(coreOperateInfoService.getProductNameBySpuId(spuId));
							coreOperateInfoGenerateVo.setOperateInfoType(CommonConstants.OPERATE_INFO_TYPE_SPU_1);
						}
					}
				}
				if (coreOperateInfoGenerateVo.getOperateInfoId() != null && !"".equals(coreOperateInfoGenerateVo.getOperateInfoId())){
					//查询商品图片信息
					List<GoodsAttachment> goodsAttachmentList = null;
					GoodsAttachment goodsAttachment = new GoodsAttachment();
					if (coreOperateInfoGenerateVo.getOperateInfoType().equals(CommonConstants.OPERATE_INFO_TYPE_SKU_2)){
						goodsAttachment.setAttachmentType(CommonConstants.ATTACHMENT_TYPE_SKU_1001);
						goodsAttachment.setStatus(CommonConstants.STATUS_1);
						goodsAttachment.setGoodsId(coreOperateInfoGenerateVo.getSkuId());
						coreOperateInfoGenerateVo.setProductName(coreOperateInfoService.getProductNameBySkuId(coreOperateInfoGenerateVo.getSkuId()));
					}else{
						goodsAttachment.setAttachmentType(CommonConstants.ATTACHMENT_TYPE_SPU_1002);
						goodsAttachment.setStatus(CommonConstants.STATUS_1);
						goodsAttachment.setGoodsId(coreOperateInfoGenerateVo.getSpuId());
						coreOperateInfoGenerateVo.setProductName(coreOperateInfoService.getProductNameBySpuId(coreOperateInfoGenerateVo.getSpuId()));
					}
					goodsAttachmentList = coreOperateInfoService.getGoodsAttachment(goodsAttachment);
					coreOperateInfoGenerateVo.setGoodsAttachmentList(goodsAttachmentList);
					//处理商品关键词,处理成数组形式
					if(coreOperateInfoGenerateVo.getSeoKeywords() != null){
						coreOperateInfoGenerateVo.setSeoKeyWordsArray(coreOperateInfoGenerateVo.getSeoKeywords().split(","));
					}
				}
			}
			if (coreOperateInfoGenerateVo != null && coreOperateInfoGenerateVo.getSkuId() != null) {
				int pushStatus = baseGoodsService.getPushStatusBySkuId(coreOperateInfoGenerateVo.getSkuId());
				viewOprateMv.addObject("pushStatus", pushStatus);
			}
			viewOprateMv.addObject("coreOperateInfoGenerateVo",coreOperateInfoGenerateVo);
			viewOprateMv.setViewName("goods/vgoods/viewOperate");

			try {
				// 获取运营平台中：各个平台集合列表
				ResultInfo<?> resultInfo = NewHttpClientUtils.doGet(operateUrl + HttpURLConstant.OP_PLAT_FROM_LIST);
				if(resultInfo != null && resultInfo.getCode() == 200){
					//1、使用JSONObject
					JSONArray arrayStr= JSONArray.fromObject(resultInfo.getData());
					List<Platfrom> platfromList =(List<Platfrom>)JSONArray.toList(JSONArray.fromObject(arrayStr), Platfrom.class);
					viewOprateMv.addObject("platfromList",platfromList);
				}
			} catch (Exception e) {
				logger.error("运营后台接口异常" + operateUrl + HttpURLConstant.OP_PLAT_FROM_LIST, e);
			}
			return viewOprateMv;

		}catch (Exception e){
			logger.error("查看商品运营信息异常：",e);
			return this.page500();
		}
	}

	/**
	 * @description 预览运营信息
	 * @author cooper
	 * @param
	 * @date 2019/6/20
	 */
	@RequestMapping("/previewOperate")
	public ModelAndView previewOperate(String randomStr, HttpServletRequest request){
		ModelAndView previewMv = new ModelAndView();
		previewMv.addObject("randomStr",randomStr);
		previewMv.setViewName("goods/vgoods/previewOperate");
		return previewMv;
	}

	/**
	 * 处理商品图片信息,处理成list格式
	 * @author Cooper
	 * @param goodsImgArray
	 * @param goodsId
	 * @param operateInfoType
	 * @return
	 * @throws Exception
	 */
	public List<GoodsAttachment> doGoodsAttachment(String[] goodsImgArray,Integer goodsId,Integer operateInfoType){
		List<GoodsAttachment> goodsImgList = new ArrayList<>();
		if (goodsImgArray != null && goodsImgArray.length > 0){
			for (int i = 0; i < goodsImgArray.length; i++){
				GoodsAttachment goodsAttachment = new GoodsAttachment();
				goodsAttachment.setGoodsId(goodsId);
				String path,url_domain = "";
				if(goodsImgArray[i].indexOf("vedeng.com") > 0){
                    path = goodsImgArray[i].substring(goodsImgArray[i].indexOf("vedeng.com") + 10);
                    url_domain = goodsImgArray[i].substring(goodsImgArray[i].indexOf("://") + 3, goodsImgArray[i].indexOf("vedeng.com") + 10).replaceAll("/","");
                } else {
                    path = "/" + goodsImgArray[i].substring(goodsImgArray[i].indexOf("upload"));
                    url_domain = goodsImgArray[i].substring(goodsImgArray[i].indexOf("://")+3, goodsImgArray[i].indexOf("upload")).replaceAll("/","");
                }
				if (operateInfoType.equals(CommonConstants.OPERATE_INFO_TYPE_SPU_1)){
					goodsAttachment.setAttachmentType(CommonConstants.ATTACHMENT_TYPE_SPU_1002);
				}else{
					goodsAttachment.setAttachmentType(CommonConstants.ATTACHMENT_TYPE_SKU_1001);
				}
				goodsAttachment.setUri(path);
				goodsAttachment.setSort(i+1);
				goodsAttachment.setStatus(CommonConstants.STATUS_1);
				goodsAttachment.setDomain(EmptyUtils.isBlank(url_domain)?domain:url_domain);
				goodsImgList.add(goodsAttachment);
			}
		}
		return goodsImgList;
	}

	/**
	 * 处理商品图片信息,处理成json格式
	 * @author Cooper
	 * @param
	 * @return
	 * @throws Exception
	 */
	public String doGoodsAttachmentToJsonInList(List<GoodsAttachment> goodsImgList){
		String imgJson = "[";
		if (CollectionUtils.isNotEmpty(goodsImgList)){
			for (GoodsAttachment goodsAttachment : goodsImgList){
				String uri = goodsAttachment.getUri();
				String fileName = uri.substring(uri.lastIndexOf("/")+1);
				String prefix = uri.substring(uri.lastIndexOf(".")+1);
				String filePath = uri.substring(0,uri.lastIndexOf("/") + 1);
				if(StringUtils.isNotBlank(goodsAttachment.getDomain())){
					imgJson = imgJson + "{\"fileName\":\""+fileName+"\",\"httpUrl\":\""
							+"http://"+goodsAttachment.getDomain()+"\",\"prefix\":\""+prefix+"\",\"filePath\":\""+filePath+"\"},";
				}else{
				    imgJson = imgJson + "{\"fileName\":\""+fileName+"\",\"httpUrl\":\""
						    +"http://"+domain+"\",\"prefix\":\""+prefix+"\",\"filePath\":\""+filePath+"\"},";
				}
			}
		}
		imgJson = imgJson.length() == 1 ? imgJson+"]" : imgJson.substring(0,imgJson.length()-1) + "]";
		return imgJson;
	}

	/**
	 * 处理商品图片信息,处理成json格式
	 * @author Cooper
	 * @param
	 * @return
	 * @throws Exception
	 */
	public String doGoodsAttachmentToJsonInArray(String[] array){
		String imgJson = "[";
		if (array != null && array.length > 0){
			for (String uri : array){
				String fileName = uri.substring(uri.lastIndexOf("/"));
				String prefix = uri.substring(uri.lastIndexOf("."));
				String filePath = uri.substring(0,uri.lastIndexOf("/"));
				imgJson = imgJson + "{\"fileName\":\""+fileName+"\",\"httpUrl\":'"
						+"http://"+domain+"\",\"prefix\":\""+prefix+"\",\"filePath\":\""+filePath+"\"},";
			}
		}
		imgJson = imgJson.length() == 1 ? imgJson+"]" : imgJson.substring(0,imgJson.length()-1) + "]";
		return imgJson;
	}

	/**
	 * 检查字段合法性
	 * @author Cooper
	 * @param
	 * @return
	 * @throws Exception
	 */
	public ResultInfo checkOperateInfo(CoreOperateInfoGenerateVo coreOperateInfoGenerateVo){
		if (coreOperateInfoGenerateVo.getGoodsImage() == null || coreOperateInfoGenerateVo.getGoodsImage().length <= 0){
			return new ResultInfo(CommonConstants.FAIL_CODE,"商品图片至少上传一张，无法提交");
		}
		if (coreOperateInfoGenerateVo.getGoodsImage() != null && coreOperateInfoGenerateVo.getGoodsImage().length > 5){
			return new ResultInfo(CommonConstants.FAIL_CODE,"商品图片最多只能上传5张，无法提交");
		}
		if (coreOperateInfoGenerateVo.getOprateInfoHtml()==null || "".equals(coreOperateInfoGenerateVo.getOprateInfoHtml())){
				return new ResultInfo(CommonConstants.FAIL_CODE,"商品详情不可为空，无法提交");
		}
		/*if (coreOperateInfoGenerateVo.getSeoKeyWordsArray() == null || coreOperateInfoGenerateVo.getSeoKeyWordsArray().length <= 0){
			return new ResultInfo(CommonConstants.FAIL_CODE,"SEO关键词至少填写一个，无法提交");
		}
		if (coreOperateInfoGenerateVo.getSeoTitle()!=null && !"".equals(coreOperateInfoGenerateVo.getSeoTitle())){
			if (coreOperateInfoGenerateVo.getSeoTitle().length()>50){
				return new ResultInfo(CommonConstants.FAIL_CODE,"SEO标题最多只能填写50个字，无法提交");
			}
		}
		if (coreOperateInfoGenerateVo.getSeoDescript()!=null && !"".equals(coreOperateInfoGenerateVo.getSeoDescript())){
			if (coreOperateInfoGenerateVo.getSeoDescript().length()>200){
				return new ResultInfo(CommonConstants.FAIL_CODE,"SEO描述最多只能填写200个字，无法提交");
			}
		}*/
		return new ResultInfo(CommonConstants.SUCCESS_CODE,CommonConstants.SUCCESS_MSG);
	}

	/**
	 * 图片上传压缩
	 * @author Cooper
	 * @param
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "fileUploadImg")
	public FileInfo fileUploadImg(HttpServletRequest request, HttpServletResponse response, @RequestBody MultipartFile file) {
		//		User user = (User)request.getSession().getAttribute(Consts.CURR_USER);

		//临时文件存放地址
		String path = "";

		//String str = request.getParameter("type");

		// 为空
		path = "/upload";

		// 类型1
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		// TODO 待定是否存在压缩
		// add by franlin.wu for [新增压缩尺寸类型 医械购] at 2019-08-17 begin
		map.put(80, 80);
		map.put(160, 160);
		map.put(215, 215);
		map.put(400, 400);
		map.put(60, 60);
		map.put(90, 90);
		map.put(120, 120);
		map.put(150, 150);
		// add by franlin.wu for [新增压缩尺寸类型 医械购] at 2019-08-17 end
		map.put(360, 360);
		map.put(800, 800);
		//判空
		if (StringUtil.isEmpty(path)){
			return new FileInfo(-1,"文件类型非法!");
		}

		FileInfo fi = ftpUtilService.uploadImg(file, path, request, "",map);
		fi.setHttpUrl(api_http + fi.getHttpUrl());
		return fi;
		/* return new FileInfo(0,"ok"); */
	}


	@ResponseBody
	@RequestMapping(value = "pushGoodsInfo")
	public ResultInfo pushGoodsInfo(@RequestParam(required = false, value = "platfromIds") String platfromIds,
									@RequestParam(required = false, value = "skuId") Integer skuId,
									@RequestParam(required = false, value = "spuId") Integer spuId){
		if(StringUtils.isBlank(platfromIds) || skuId == null){
			return new ResultInfo(-1,"参数错误");
		}
		try {
			JSONObject jsonObject = coreOperateInfoService.getPushGoodsInfo(platfromIds, skuId, spuId);
			if(jsonObject != null){
				Map<String,String> map = new HashMap<>();
				map.put("operateInfo", jsonObject.toJSONString());
				logger.info("推送商品运营信息:" + jsonObject.toJSONString());
				// 获取运营平台中：各个平台集合列表
				ResultInfo<?> resultInfo = NewHttpClientUtils.doPost(operateUrl + HttpURLConstant.OP_PUSH_GOODS_INFO, map);
				if(resultInfo != null && resultInfo.getCode() == 200 && "success".equals(JSON.parseObject(resultInfo.getData().toString()).get("code"))){
					logger.info("推送商品运营信息结果:success");
					return new ResultInfo(0,"操作成功");
				} else {
					logger.error("推送商品【skuId:" + skuId + "】运营信息失败：" + resultInfo.toString());
				}
			}
		} catch (Exception e) {
			logger.error("推送商品【skuId:" + skuId + "】时，运营后台接口异常" + operateUrl + HttpURLConstant.OP_PUSH_GOODS_INFO, e);
		}
		return new ResultInfo();
	}


	@ResponseBody
	@RequestMapping(value = "/handleOldData")
	public ResultInfo handleOldData(@RequestParam(required = false, value = "skuNos") String skuNos,
									@RequestParam(required = false, value = "platfromId") String platfromId){
		try {
            logger.info("handleOldData接收到参数skuNos{}" + skuNos + ";platfromId{}" + platfromId);
			List<String> skuNoList = new ArrayList<>();
			if(EmptyUtils.isNotBlank(skuNos)){
				String[] strings = skuNos.split(",");
				skuNoList = Arrays.asList(strings);
			}

			if(CollectionUtils.isEmpty(skuNoList) || EmptyUtils.isBlank(platfromId)){
				return new ResultInfo(-1, "推送平台编号和推送skuNo不允许为空，或格式错误");
			}

			List<Integer> skuIdList = coreOperateInfoService.getCoreSkuInfoBySkuNo(skuNoList);
			for(int i=0;i<skuIdList.size();i++){
				// 默认贝登
				JSONObject jsonObject = coreOperateInfoService.getPushGoodsInfo(platfromId, skuIdList.get(i), null);
				logger.info("推送运营后台初始化数据：" + skuIdList.get(i) + ":" + jsonObject);
				if(jsonObject != null){
					Map<String,String> map = new HashMap<>();
					map.put("operateInfo", jsonObject.toJSONString());
					// 获取运营平台中：各个平台集合列表
					ResultInfo<?> resultInfo = NewHttpClientUtils.doPost(operateUrl + HttpURLConstant.OP_PUSH_GOODS_INFO, map);
					if(resultInfo != null && resultInfo.getCode() == 200 && "success".equals(JSON.parseObject(resultInfo.getData().toString()).get("code"))){
						logger.info("推送商品【skuId:"+skuIdList.get(i)+"】运营信息:success");
					} else {
						logger.error("推送商品【skuId:"+skuIdList.get(i)+"】运营信息失败：" + resultInfo.toString());
					}
//					return new ResultInfo(0,"操作成功");
				}
			}
			return new ResultInfo(0,"操作成功");
		} catch (Exception e) {
			logger.error("运营后台接口异常" + operateUrl + HttpURLConstant.OP_PUSH_GOODS_INFO, e);
		}
		return new ResultInfo();
	}

	@Autowired
	CoreOperateInfoGenerateExtendMapper coreOperateInfoGenerateExtendMapper;


	@ResponseBody
	@RequestMapping("/spuSend")
	public ResultInfo spuSend(@RequestParam("spuIdsn")String  spuIdsn,@RequestParam("platfromId") Integer platfromId){
		String [] spuIds=spuIdsn.split(",");
		List<String> platfromIds=new ArrayList<>();
		platfromIds.add(platfromId.toString());
		for(int i =0;i<spuIds.length;i++){
			OperateSpuVo operateSpuVo = coreOperateInfoGenerateExtendMapper.selectSpuOperateBySpuId(Integer.parseInt(spuIds[i]));
			Map<String,String> map = new HashMap<>();
			map.put("operateInfo", JSON.toJSONString(operateSpuVo));
			try {
				ResultInfo<?> resultInfo = NewHttpClientUtils.doPost("http://localhost:8084/" + HttpURLConstant.OP_PUSH_GOODS_INFO, map);
				if(resultInfo != null && resultInfo.getCode() == 200 && "success".equals(JSON.parseObject(resultInfo.getData().toString()).get("code"))){
					logger.info("推送商品spu运营信息:success");
				}else{
					logger.info("推送商品spu运营信息:fail");
				}
			} catch (Exception e) {
				logger.error("运营后台接口异常" + operateUrl + HttpURLConstant.OP_PUSH_GOODS_INFO, e);
			}

		}
		return new ResultInfo();
	}

}