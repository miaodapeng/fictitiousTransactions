package com.newtask;

import com.google.common.collect.Maps;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.service.BaseService;
import com.vedeng.firstengage.dao.FirstengageBrandMapper;
import com.vedeng.goods.dao.*;
import com.vedeng.goods.model.BrandGenerate;
import com.vedeng.goods.model.BrandGenerateExample;
import com.vedeng.goods.service.VgoodsService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * 任务Handler示例（Bean模式）
 * <p>
 * 开发步骤：
 * 1、继承"IJobHandler"：“com.xxl.job.core.handler.IJobHandler”；
 * 2、注册到Spring容器：添加“@Component”注解，被Spring容器扫描为Bean实例；
 * 3、注册到执行器工厂：添加“@JobHandler(value="自定义jobhandler名称")”注解，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author xuxueli 2015-12-19 19:43:36
 */
@JobHandler(value = "brankAndPCHandler")
@Component
public class BrankAndPCHandler extends IJobHandler {

    @Autowired
    GoodsGenerateMapper mapper;
    @Autowired
    GoodsAttachmentGenerateMapper attachmentGenerateMapper;
    @Autowired
    BaseService baseService;
    @Autowired
    com.vedeng.goods.dao.CoreSpuGenerateExtendMapper coreSpuGenerateExtendMapper;
    @Autowired
    CoreSkuGenerateMapper skuGenerateMapper;
    @Autowired
    CoreSpuGenerateMapper spuGenerateMapper;
    @Autowired
    VgoodsService vgoodsService ;
    @Autowired
    BrandGenerateMapper brandGenerateMapper;
    @Autowired
    @Qualifier("firstengageBrandMapper")
    private FirstengageBrandMapper brandMapper;
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("transGoodsToSkuHandler start.");
// 生产厂商
        BrandGenerateExample example=new BrandGenerateExample();
        example.createCriteria().andIsDeleteEqualTo(CommonConstants.STATUS_0);
        List<BrandGenerate> list= brandGenerateMapper.selectByExample(example);

        if(CollectionUtils.isNotEmpty (list)){
            for(BrandGenerate brand:list){
               if(StringUtils.isNotBlank(brand.getManufacturer())){
                // 先删除
                Map<String,Object> map= Maps.newHashMap();
                brandMapper.deleteManufacturer(brand.getBrandId());
                // 再添加
                map.put("manufacturer", brand.getManufacturer().split("@"));
                map.put("brandId", brand.getBrandId());
                brandMapper.addBrandAndManufacturer(map);
               }
            }

        }
        return SUCCESS;
    }


}
