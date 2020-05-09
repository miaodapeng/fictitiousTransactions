package com.vedeng.common.validator.goods;

import com.baidu.unbiz.fluentvalidator.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vedeng.common.constant.goods.GoodsCheckStatusEnum;
import com.vedeng.common.constant.goods.GoodsConstants;
import com.vedeng.common.constant.goods.SpuLevelEnum;
import com.vedeng.common.validator.*;
import com.vedeng.goods.command.SkuAddCommand;
import com.vedeng.goods.command.SpuAddCommand;
import com.vedeng.goods.command.SpuSearchCommand;
import com.vedeng.goods.model.CoreSkuGenerate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public class SpuValidate {
	public static Result check(SpuAddCommand spuCommand) {
		Result ret = FluentValidator.checkAll().on(spuCommand.getCategoryId(), new NumberValidate("请选择分类"))
				.on(spuCommand.getCategoryId(), new ValidatorHandler<Integer>() {
					public boolean validate(ValidatorContext context, Integer t) {
						return true;// TODO 判断是否叶子节点
					}
				}).on(spuCommand.getBrandId(), new NumberValidate("请选择品牌信息"))
				//
//				.on(spuCommand.getFirstEngageId(), new NullValidate("注册证/备案证号不能为空"))
//				.when(SysOptionConstant.ID_316.equals(spuCommand.getSpuType()))
				//
				.on(spuCommand.getSpuType(), new NumberValidate("请选择商品类型"))
				//
				// .on(spuCommand.getFirstEngageId(), new NumberValidate("请选择首营品种资质"))
				//
				.on(spuCommand.getSpuName(), new NullValidate("请输入通用名"))
				.when(!SpuLevelEnum.isTempSpu(spuCommand.getSpuLevel()))
				.on(spuCommand.getSpuName(), new MaxLengthValidate(ValidateConstants.MAX_SPU_NAME_LENGTH, "通用名长度超过限制"))
				.when(!SpuLevelEnum.isTempSpu(spuCommand.getSpuLevel()))
				.on(spuCommand.getRegistrationIcon(), new MaxLengthValidate(128, "注册商标长度超过限制"))
				.when(!SpuLevelEnum.isTempSpu(spuCommand.getSpuLevel()))
				//
				.on(spuCommand.getShowName(), new NullValidate("请输入商品名称"))
				.on(spuCommand.getShowName(),
						new MaxLengthValidate(ValidateConstants.MAX_SPU_NAME_LENGTH, "商品名称长度超过限制"))
				.on(spuCommand.getSpuName(),
						new MaxLengthValidate(ValidateConstants.MAX_SPU_NAME_LENGTH, "通用名长度超过限制"))
				.when(!SpuLevelEnum.isTempSpu(spuCommand.getSpuLevel()))
				//
				.on(spuCommand.getWikiHref(), new ValidatorHandler<String>() {
					public boolean validate(ValidatorContext context, String t) {
						try {
							if (StringUtils.isNotBlank(t)) {
								new URL(t);
							}
						} catch (MalformedURLException e) {
							context.addErrorMsg("wiki地址无法访问");
							return false;
						}
						if(StringUtils.length(t)>250){
							context.addErrorMsg("wiki地址超过长度");
							return false;
						}
						return true;// TODO 判断是否叶子节点
					}
				}).when(!SpuLevelEnum.isTempSpu(spuCommand.getSpuLevel()))
				.on(spuCommand.getDepartmentIds(), new NullValidate("请选择科室"))
				.when(!SpuLevelEnum.isTempSpu(spuCommand.getSpuLevel()))
				// .on(spuCommand.getSpuPatentFiles(),new NullValidate("请上传专利文件"))
				.doValidate().result(ResultCollectors.toSimple());

		hasEditAuth(spuCommand, ret);

		if(ret.isSuccess()&& GoodsCheckStatusEnum.isPre(spuCommand.getCheckStatus())||GoodsCheckStatusEnum.isApprove(spuCommand.getCheckStatus()) ){
			ret.setIsSuccess(false);
			ret.getErrors().add("审核通过或已经提交审核的商品不能编辑");
		}

		spuCommand.setErrors(ret.getErrors());
		return ret;
	}

	private static void hasEditAuth(SpuAddCommand spuCommand, Result ret) {
		if(CollectionUtils.isEmpty(ret.getErrors()) ){
			ret.setErrors(Lists.newArrayList());
		}

		if (ret.isSuccess()&&SpuLevelEnum.isTempSpu(spuCommand.getSpuLevel()) && !spuCommand.isHasEditTempAuth()) {
			ret.setIsSuccess(false);
			ret.getErrors().add("权限不足");
		}



		if (ret.isSuccess()&&!SpuLevelEnum.isTempSpu(spuCommand.getSpuLevel()) && !spuCommand.isHasEditAuth()) {
			ret.setIsSuccess(false);
			ret.getErrors().add("权限不足");
		}

	}

	public static Result checkDeleteSpu(SpuSearchCommand spuCommand) {
		Result ret = FluentValidator.checkAll().on(spuCommand.getSpuId(), new NumberValidate("请选择Spu"))
				.on(spuCommand.getDeleteReason(), new MinLengthValidate(10, "最少需要10个字符"))

				.on(spuCommand.getDeleteReason(), new MaxLengthValidate(300, "不能超过300个字符")).doValidate()
				.result(ResultCollectors.toSimple());
		spuCommand.setErrors(ret.getErrors());
		return ret;
	}

	public static Result checkDeleteSku(SpuSearchCommand spuCommand) {
		Result ret = FluentValidator.checkAll().on(spuCommand.getSkuId(), new NumberValidate("请选择Sku"))
				.on(spuCommand.getDeleteReason(), new MinLengthValidate(10, "最少需要10个字符"))
				.on(spuCommand.getDeleteReason(), new MaxLengthValidate(300, "不能超过300个字符")).doValidate()
				.result(ResultCollectors.toSimple());
		spuCommand.setErrors(ret.getErrors());
		return ret;
	}

	public static Result checkCopySku(SkuAddCommand spuCommand) {
		Result ret = FluentValidator.checkAll().on(spuCommand.getSkuId(), new NumberValidate("请选择Sku")).doValidate()
				.result(ResultCollectors.toSimple());
		spuCommand.setErrors(ret.getErrors());
		return ret;
	}

    public static Result checkSku(SkuAddCommand command, CoreSkuGenerate skuGenerate) {

		FluentValidator validate=FluentValidator.checkAll();

		validate.on(skuGenerate.getEffectiveDays(),new NumberValidate("产品有效期必须为数字类型"))
				.when(StringUtils.isNotBlank(skuGenerate.getEffectiveDays()));
		validate.on(skuGenerate.getSkuName(), new NullValidate("请输入Sku名称")).on(skuGenerate.getSkuName(),
				new MaxLengthValidate(ValidateConstants.MAX_SPU_NAME_LENGTH, "通用名长度超过限制"));

		//耗材
		if(GoodsConstants.SKU_TYPE_CONSUMABLES==command.getSkuType()){
			validate
					.on(skuGenerate.getUnitId(), new NullValidate("请选择商品最小单位"))
					.on(skuGenerate.getChangeNum(), new NumberValidate("请输入内含最小商品数量"))
					.on(skuGenerate.getMinOrder(), new NumberValidate("请输入内含最小起订量"))
					.on(skuGenerate.getStorageConditionOne(), new NumberValidate("请选择存储条件1"))
					.on(skuGenerate.getReturnGoodsConditions(), new NumberValidate("请选择退货条件"))
					.on(skuGenerate.getFreightIntroductions(),new NullValidate("请输入运费说明"));
		}else{

            String parameterLength = paramArrayToString(command.getParamsName1(), command.getParamsValue1());

			validate
					.on(skuGenerate.getModel(), new NullValidate("请输入制造商型号"))
					.on(command.getParamsName1(), new NullValidate("请输入技术参数"))
                    .on(parameterLength, new MaxLengthValidate(ValidateConstants.MAX_TECHNICAL_PARAMETER_LENGTH,"技术参数长度超过限制"))
					.on(skuGenerate.getBaseUnitId(), new NullValidate("请选择SKU商品单位"))
					.on(skuGenerate.getAfterSaleContent(), new NullValidate("请选择SKU售后内容"))
					.on(skuGenerate.getQaYears(), new NumberValidate("请正确填写质保年限"))
					.on(skuGenerate.getReturnGoodsConditions(), new NumberValidate("请选择退货条件 "))
					.on(skuGenerate.getFreightIntroductions(), new NullValidate("请选择运费说明 "))
					;
		}




		Result ret = validate.doValidate().result(ResultCollectors.toSimple());

		command.setErrors(ret.getErrors());
		return ret;
    }

    private static String paramArrayToString(String[] a, String[] b) {
        StringBuilder temp = new StringBuilder();
        Set<String> checkExist = Sets.newHashSet();
        if (ArrayUtils.isNotEmpty(a) && ArrayUtils.isNotEmpty(b)) {
            for (int i = 0; i < a.length; i++) {
                if (!checkExist.contains(a[i] + ":" + b[i])) {
                    checkExist.add(a[i] + ":" + b[i]);
                } else {
                    continue;
                }
                if (StringUtils.isNotBlank(a[i]) && StringUtils.isNotBlank(b[i]) && b.length > i) {
                    temp.append(a[i] + ":" + b[i] + ";");
                }
            }
        }
        return temp.toString();
    }

    public static Result checkSubmitCheck(SpuAddCommand spuCommand) {
		FluentValidator validate=FluentValidator.checkAll();
		validate.on(spuCommand.getSpuId(), new NumberValidate("SpuId不存在"))
				 ;
		Result ret = validate.doValidate().result(ResultCollectors.toSimple());
		spuCommand.setErrors(ret.getErrors());
		return ret;
    }

	public static Result checkCheckSpu(SpuAddCommand spuCommand) {
		FluentValidator validate=FluentValidator.checkAll();
		validate.on(spuCommand.getSpuId(), new NumberValidate("SpuId不存在"))
				.on(spuCommand.getLastCheckReason(),new MinLengthValidate(10,"最少10个字符"))
				.when(GoodsCheckStatusEnum.isReject(spuCommand.getCheckStatus()) )
				.on(spuCommand.getLastCheckReason(),new MaxLengthValidate(300,"最多300个字符"))
				.when(GoodsCheckStatusEnum.isReject(spuCommand.getCheckStatus())) ;
		Result ret = validate.doValidate().result(ResultCollectors.toSimple());

		hasEditAuth(spuCommand, ret);

		if(ret.isSuccess()&&GoodsCheckStatusEnum.isNew(spuCommand.getCheckStatus())||GoodsCheckStatusEnum.isReject(spuCommand.getCheckStatus())){
			ret.setIsSuccess(false);
			ret.getErrors().add("待完善或者审核不通过的商品不能审核");
		}
		spuCommand.setErrors(ret.getErrors());
		return ret;
	}

	public static Result checkTempSku(SkuAddCommand spuCommand ) {
		FluentValidator validate=FluentValidator.checkAll();
		validate.on(spuCommand.getSpuId(), new NumberValidate("SpuId不存在")
				 ).on(spuCommand.getSkuInfo(),new NullValidate("制造商型号或者规格为必填"))
		;
		Result ret = validate.doValidate().result(ResultCollectors.toSimple());

		spuCommand.setErrors(ret.getErrors());
		return ret;
	}

	public static Result checkCheckSku(SkuAddCommand command) {
		FluentValidator validate=FluentValidator.checkAll();
		validate.on(command.getSkuId(), new NumberValidate("SkuId不存在")
		) ;
		Result ret = validate.doValidate().result(ResultCollectors.toSimple());
		command.setErrors(ret.getErrors());
		return ret;
	}

    public static Result checkBackupSku(SkuAddCommand command) {
		FluentValidator validate=FluentValidator.checkAll();
		validate
				.on(command.getSkuIds(), new NullValidate("SkuId不存在"))
				.on(command.getHasBackupMachine(), new NullValidate("备货状态不能为空")) ;
		Result ret = validate.doValidate().result(ResultCollectors.toSimple());
		command.setErrors(ret.getErrors());
		return ret;
    }
}
