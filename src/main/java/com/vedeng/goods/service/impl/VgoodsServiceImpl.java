package com.vedeng.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.constant.goods.GoodsCheckStatusEnum;
import com.vedeng.common.constant.goods.GoodsConstants;
import com.vedeng.common.constant.goods.LogTypeEnum;
import com.vedeng.common.constant.goods.SpuLevelEnum;
import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.GoodsNoDict;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.StringUtil;
import com.vedeng.firstengage.dao.FirstEngageMapper;
import com.vedeng.firstengage.model.FirstEngage;
import com.vedeng.firstengage.service.FirstEngageService;
import com.vedeng.firstengage.service.FirstengageBrandService;
import com.vedeng.goods.command.SkuAddCommand;
import com.vedeng.goods.command.SpuAddCommand;
import com.vedeng.goods.command.SpuSearchCommand;
import com.vedeng.goods.dao.*;
import com.vedeng.goods.model.*;
import com.vedeng.goods.model.dto.*;
import com.vedeng.goods.model.vo.*;
import com.vedeng.goods.service.BaseCategoryService;
import com.vedeng.goods.service.BaseGoodsService;
import com.vedeng.goods.service.VgoodsService;
import com.vedeng.logistics.model.WarehouseStock;
import com.vedeng.logistics.service.WarehouseStockService;
import com.vedeng.logistics.model.vo.WarehouseGoodsOperateLogVo;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.ordergoods.dao.SaleorderGoodsGenerateMapper;
import com.vedeng.ordergoods.model.SaleorderGoodsGenerate;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.dao.AttachmentMapper;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.SysOptionDefinition;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class VgoodsServiceImpl extends BaseServiceimpl implements VgoodsService {

    Logger logger = LoggerFactory.getLogger(VgoodsServiceImpl.class);

    @Autowired
    private GoodsAttachmentGenerateMapper goodsAttachmentGenerateMapper;

    @Autowired
    private FirstengageBrandService firstengageBrandService;

    @Autowired
    private FirstEngageMapper firstEngageMapper;

    @Autowired
    private SpuDepartmentMappingGenerateMapper spuDepartmentMappingGenerateMapper;

    @Autowired
    private CoreSpuGenerateExtendMapper coreSpuGenerateExtendMapper;

    @Autowired
    private BaseAttributeMapper baseAttributeMapper;

    @Autowired
    private GoodsGenerateMapper goodsGenerateMapper;

    @Autowired
    private BrandGenerateMapper brandGenerateMapper;

    /*@Autowired
    private BaseService baseService;*/

    @Autowired
    private BaseGoodsService baseGoodsService;

    @Autowired
    private BaseCategoryService baseCategoryService;

    @Autowired
    private FirstEngageService firstEngageService;

    @Autowired
    private SpuAttrMappingGenerateMapper spuAttrMappingGenerateMapper;

    @Autowired
    private SkuAttrMappingGenerateMapper skuAttrMappingGenerateMapper;

    @Value("${api_http}")
    protected String api_http;

    @Value("${file_url}")
    protected String domain;

    @Value("${redis_dbtype}")
    protected String dbType;// 开发redis，测试redis

    @Autowired
    private LogCheckGenerateMapper logCheckGenerateMapper;

    @Autowired
    private VerifiesInfoGenerateMapper verifiesInfoGenerateMapper;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private TransGoodsToSkuService transGoodsToSkuService;

    @Autowired
    private CoreSpuGenerateMapper coreSpuGenerateMapper;

    @Autowired
    private CoreSkuGenerateMapper coreSkuGenerateMapper;
    @Autowired
    VedengSoapService vedengSoapService;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    private WarehouseStockService warehouseStockService;

    @Override
    public void updateSpuOperateInfoFlag(Integer optInfoId, Integer spuId) {
        if (optInfoId == null) {
            optInfoId = 0;
        }
        if (spuId == null) {
            return;
        }
        CoreSpuGenerate generate = new CoreSpuGenerate();
        generate.setSpuId(spuId);
        generate.setOperateInfoId(optInfoId);
        generate.setOperateInfoFlag(CommonConstants.STATUS_1);
        baseGoodsService.mergeSpu(generate);
        //
    }

    @Override
    public void updateSkuOperateInfoFlag(Integer optInfoId, Integer skuId) {
        if (optInfoId == null) {
            optInfoId = 0;
        }
        if (skuId == null) {
            return;
        }
        CoreSkuGenerate generate = new CoreSkuGenerate();
        generate.setSkuId(skuId);
        generate.setOperateInfoId(optInfoId);
        baseGoodsService.mergeSku(generate);
    }

    @Override
    @Transactional
    public void saveSpu(SpuAddCommand spuCommand) throws ShowErrorMsgException {
        if (spuCommand.getSpuId() == null) {
            logger.info("添加spu::" + JSON.toJSONString(spuCommand));
            CoreSpuGenerate spu = addSpu(spuCommand);
            spuCommand.setSpuId(spu.getSpuId());
            baseGoodsService.updatePushStatusBySpuId(spu.getSpuId(),GoodsConstants.PUSH_STATUS_UN_PUSH);
            if (!SpuLevelEnum.isTempSpu(spuCommand.getSpuLevel())) {
                saveSpuPics(spuCommand);
            } else {
                addTempSku(spuCommand.getUser(), spuCommand.getSkuInfo(), spu.getSpuId(), null, spuCommand.getSpuType()
                        , spu.getShowName(), spu);
            }
        } else {
            logger.info("修改spu::" + JSON.toJSONString(spuCommand));
            CoreSpuGenerate spu = checkUpdateCondition(spuCommand);
            updateSpu(spuCommand);
            baseGoodsService.updatePushStatusBySpuId(spuCommand.getSpuId(), GoodsConstants.PUSH_STATUS_UN_PUSH);
            if (!SpuLevelEnum.isTempSpu(spuCommand.getSpuLevel())) {
                deleteSpuPics(spuCommand);
                saveSpuPics(spuCommand);


            } else {
                //临时商品，需要修改所有skud的名称
                List<CoreSkuGenerate> skuGenerates = selectSkuListBySpuId(spu.getSpuId());
                if (CollectionUtils.isNotEmpty(skuGenerates)) {
                    for (CoreSkuGenerate generate : skuGenerates) {
                        BrandGenerate brandGenerate = brandGenerateMapper.selectByPrimaryKey(spu.getBrandId());
                        String brandName = brandGenerate == null ? "" : brandGenerate.getBrandNature() == 2 ? brandGenerate.getBrandNameEn() : brandGenerate.getBrandName();
                        generate.setShowName(spuCommand.getShowName());
                        generate.setSkuName(spuCommand.getShowName());
                        generate.setUpdater(spuCommand.getUser().getUserId());
                        generate.setModTime(new Date());
                        baseGoodsService.mergeSku(generate);
                    }
                }

            }
            tempSpuToCore(spuCommand, spu);
        }
        if (!SpuLevelEnum.isTempSpu(spuCommand.getSpuLevel())) {
            mergeAttr(spuCommand);
            mergeDepartments(spuCommand);
        }
        baseGoodsService.flushSpu();
    }

    private void tempSpuToCore(SpuAddCommand spuCommand, CoreSpuGenerate spu) {
        if (!SpuLevelEnum.isTempSpu(spuCommand.getSpuLevel()) && SpuLevelEnum.isTempSpu(spu.getSpuLevel())) {//临时商品转为非临时商品，sku置为待完善
            List<CoreSkuGenerate> skuGenerates = selectSkuListBySpuId(spu.getSpuId());
            if (CollectionUtils.isNotEmpty(skuGenerates)) {
                for (CoreSkuGenerate generate : skuGenerates) {
                    checkSku(generate.getSkuId(), "临时商品转为非临时商品", GoodsCheckStatusEnum.NEW.getStatus(), spuCommand.getUser());
                }
            }
        }
    }


    private void mergeDepartments(SpuAddCommand spuCommand) {
        Integer[] postIds = spuCommand.getDepartmentIds();
        SpuDepartmentMappingGenerateExample oldExample = new SpuDepartmentMappingGenerateExample();
        oldExample.createCriteria().andSpuIdEqualTo(spuCommand.getSpuId());
        List<SpuDepartmentMappingGenerate> listed = spuDepartmentMappingGenerateMapper.selectByExample(oldExample);

//        List<Integer> oldAttr = Lists.newArrayList();
        List<Integer> newAttr = Lists.newArrayList(postIds);
//        if (CollectionUtils.isNotEmpty(listed)) {
//            for (SpuDepartmentMappingGenerate generate : listed) {
//                oldAttr.add(generate.getDepartmentId());
//            }
//        }
        SpuDepartmentMappingGenerateExample example = new SpuDepartmentMappingGenerateExample();
        example.createCriteria().andSpuIdEqualTo(spuCommand.getSpuId()).andStatusEqualTo(1);
        spuDepartmentMappingGenerateMapper.deleteByExample(example);
//        List<Integer> addAttr = Lists.newArrayList(newAttr);
//        addAttr.removeAll(oldAttr);
//
//        List<Integer> deleteAttr = Lists.newArrayList(oldAttr);
//        deleteAttr.removeAll(newAttr);

//        System.out.println("[addAttr]:"+JSON.toJSONString(addAttr));

        if (CollectionUtils.isNotEmpty(newAttr)) {
            for (Integer attr : newAttr) {
                SpuDepartmentMappingGenerate generate = new SpuDepartmentMappingGenerate();
                generate.setSpuId(spuCommand.getSpuId());
                generate.setStatus(CommonConstants.STATUS_1);
                generate.setAddTime(new Date());
                generate.setModTime(new Date());
                generate.setCreator(spuCommand.getUser().getUserId());
                generate.setUpdater(spuCommand.getUser().getUserId());
                generate.setDepartmentId(attr);
                spuDepartmentMappingGenerateMapper.insert(generate);
            }
        }
//        System.out.println("++++++++++++"+JSON.toJSONString(deleteAttr));
//        if (CollectionUtils.isNotEmpty(deleteAttr)) {
//            List<Integer> deleteAttrIntegerList = Lists.newArrayList();
//            for (Integer attr : deleteAttr) {
//                deleteAttrIntegerList.add(attr);
//            }
//            System.out.println("++++++++++++"+JSON.toJSONString(deleteAttrIntegerList));
//            SpuDepartmentMappingGenerate generate = new SpuDepartmentMappingGenerate();
//            generate.setStatus(CommonConstants.STATUS_0);
//            generate.setModTime(new Date());
//            generate.setUpdater(spuCommand.getUser().getUserId());
//            System.out.println("++++++++++++"+JSON.toJSONString(generate));
//
//            SpuDepartmentMappingGenerateExample example1 = new SpuDepartmentMappingGenerateExample();
//            example1.createCriteria().andDepartmentIdIn(deleteAttrIntegerList)
//                    .andSpuIdEqualTo(spuCommand.getSpuId()).andStatusEqualTo(CommonConstants.STATUS_1);
//            spuDepartmentMappingGenerateMapper.updateByExampleSelective(generate, example1);
//        }
    }

    //BaseAttributeIdsHidden
    private void mergeAttr(SpuAddCommand spuCommand) throws ShowErrorMsgException {
        Integer[] baseAttrArray = spuCommand.getBaseAttributeIds();
        if (ArrayUtils.isEmpty(baseAttrArray)) {
            //VD-3952
            SpuAttrMappingGenerate generate = new SpuAttrMappingGenerate();
            generate.setStatus(CommonConstants.STATUS_0);
            generate.setModTime(new Date());
            generate.setUpdater(spuCommand.getUser().getUserId());
            SpuAttrMappingGenerateExample example1 = new SpuAttrMappingGenerateExample();
            example1.createCriteria()
                    .andSpuIdEqualTo(spuCommand.getSpuId()).andStatusEqualTo(CommonConstants.STATUS_1);
            spuAttrMappingGenerateMapper.updateByExampleSelective(generate, example1);

            //删除所有SKU属性
           List<CoreSkuGenerate> list= selectSkuListBySpuId(spuCommand.getSpuId());
           if(CollectionUtils.isNotEmpty(list)){
               for(CoreSkuGenerate sku:list){
                   SkuAttrMappingGenerate kgenerate = new SkuAttrMappingGenerate();
                   kgenerate.setStatus(CommonConstants.STATUS_0);
                   kgenerate.setModTime(new Date());
                   kgenerate.setUpdater(spuCommand.getUser().getUserId());
                   SkuAttrMappingGenerateExample skuAttrMappingGenerateExample=new SkuAttrMappingGenerateExample();
                   skuAttrMappingGenerateExample.createCriteria().andSkuIdEqualTo(sku.getSkuId())
                   .andStatusEqualTo(CommonConstants.STATUS_1);
                   skuAttrMappingGenerateMapper.updateByExampleSelective(kgenerate ,skuAttrMappingGenerateExample );
               }
           }
            return;
        }
        SpuAttrMappingGenerateExample example = new SpuAttrMappingGenerateExample();
        example.createCriteria().andSpuIdEqualTo(spuCommand.getSpuId()).andStatusEqualTo(CommonConstants.STATUS_1);
        List<SpuAttrMappingGenerate> list = spuAttrMappingGenerateMapper.selectByExample(example);

        List<Integer> oldAttr = Lists.newArrayList();
        List<Integer> newAttr = Lists.newArrayList(baseAttrArray);
        if (CollectionUtils.isNotEmpty(list)) {
            for (SpuAttrMappingGenerate generate : list) {
                oldAttr.add(generate.getBaseAttributeId());
            }
        }

        List<Integer> addAttr = Lists.newArrayList(newAttr);
        addAttr.removeAll(oldAttr);

        List<Integer> deleteAttr = Lists.newArrayList(oldAttr);
        deleteAttr.removeAll(newAttr);

        if (CollectionUtils.isNotEmpty(addAttr)) {
            for (Integer attr : addAttr) {
                SpuAttrMappingGenerate generate = new SpuAttrMappingGenerate();
                generate.setSpuId(spuCommand.getSpuId());
                generate.setStatus(CommonConstants.STATUS_1);
                generate.setAddTime(new Date());
                generate.setModTime(new Date());
                generate.setCreator(spuCommand.getUser().getUserId());
                generate.setUpdater(spuCommand.getUser().getUserId());
                generate.setBaseAttributeId(attr);
                spuAttrMappingGenerateMapper.insert(generate);
            }
        }

        if (CollectionUtils.isNotEmpty(deleteAttr)) {
            List<Integer> deleteAttrIntegerList = Lists.newArrayList();
            for (Integer attr : deleteAttr) {
                deleteAttrIntegerList.add(attr);
            }


            SpuAttrMappingGenerate generate = new SpuAttrMappingGenerate();
            generate.setStatus(CommonConstants.STATUS_0);
            generate.setModTime(new Date());
            generate.setUpdater(spuCommand.getUser().getUserId());
            SpuAttrMappingGenerateExample example1 = new SpuAttrMappingGenerateExample();
            example1.createCriteria().andBaseAttributeIdIn(deleteAttrIntegerList)
                    .andSpuIdEqualTo(spuCommand.getSpuId()).andStatusEqualTo(CommonConstants.STATUS_1);
            spuAttrMappingGenerateMapper.updateByExampleSelective(generate, example1);


            //删除所有SKU属性
            List<CoreSkuGenerate> skuList= selectSkuListBySpuId(spuCommand.getSpuId());
            if(CollectionUtils.isNotEmpty(list)){
                for(CoreSkuGenerate sku:skuList){
                    SkuAttrMappingGenerate kgenerate = new SkuAttrMappingGenerate();
                    kgenerate.setStatus(CommonConstants.STATUS_0);
                    kgenerate.setModTime(new Date());
                    kgenerate.setUpdater(spuCommand.getUser().getUserId());
                    SkuAttrMappingGenerateExample skuAttrMappingGenerateExample=new SkuAttrMappingGenerateExample();
                    skuAttrMappingGenerateExample.createCriteria().andSkuIdEqualTo(sku.getSkuId())
                            .andStatusEqualTo(CommonConstants.STATUS_1).andBaseAttributeIdIn(deleteAttrIntegerList);
                    skuAttrMappingGenerateMapper.updateByExampleSelective(kgenerate ,skuAttrMappingGenerateExample );
                }
            }
            return;
        }
    }

    private CoreSpuGenerate checkUpdateCondition(SpuAddCommand spuCommand) throws ShowErrorMsgException {
        if (spuCommand.getSpuId() == null) {
            throw new ShowErrorMsgException("商品ID不能为空。");
        }
        CoreSpuGenerate generate = coreSpuGenerateMapper.selectByPrimaryKey(spuCommand.getSpuId());
        if (generate == null) {
            throw new ShowErrorMsgException("找不到对应的商品。");
        }


//        if (!FirstEngageCheckStatusEnum.isApprove(firstEngage.getStatus())) {
//            throw new ShowErrorMsgException("当前首营信息审核中，请先对首营信息进行审核。");
//        }
        //TODO 临时取消 状态为待审核，不能更新
//        if (GoodsCheckStatusEnum.isPre(generate.getCheckStatus())) {
//            throw new ShowErrorMsgException("商品状态为审核中 不能修改。");
//        }

        BaseCategoryVo dbCat = baseCategoryService.getBaseCategoryByParam(generate.getCategoryId());
        BaseCategoryVo updateCat = baseCategoryService.getBaseCategoryByParam(spuCommand.getCategoryId());
        if (updateCat == null) {
            throw new ShowErrorMsgException("找不到分类。");
        }
        FirstEngage firstEngage = firstEngageMapper.selectByPrimaryKey(spuCommand.getFirstEngageId());
        //医疗器械分类时必填
        if (firstEngage == null && updateCat.getBaseCategoryType() == 1) {
            throw new ShowErrorMsgException("找不到对应的首营信息。");
        }
        //非医疗器械首营为空
        if (updateCat.getBaseCategoryType() == 2) {
            spuCommand.setFirstEngageId(null);
        }
        //TODO 临时取消 初始化数据的时候，
//        if (dbCat != null && updateCat != null && dbCat.getBaseCategoryType() != updateCat.getBaseCategoryType()) {
//            throw new ShowErrorMsgException("分类的类型不能发生变更。");
//        }
        return generate;
    }

    private void updateSpu(SpuAddCommand spuCommand) {
        CoreSpuGenerate generate = new CoreSpuGenerate();
        generate.setSpuId(spuCommand.getSpuId());
        copySpuProperties(spuCommand, generate);
        //  coreSpuGenerateMapper.updateByPrimaryKeySelective(generate);
        baseGoodsService.mergeSpu(generate);
       // baseGoodsService.selectSpuBaseById( );

    }

    private CoreSpuGenerate addSpu(SpuAddCommand spuCommand) {
        CoreSpuGenerateExample example = new CoreSpuGenerateExample();
        example.createCriteria().andShowNameEqualTo(spuCommand.getShowName()).andStatusEqualTo(CommonConstants.STATUS_1);
        if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_SPU_NAME + spuCommand.getShowName()) || coreSpuGenerateMapper.countByExample(example) > 0) {
            throw new ShowErrorMsgException(spuCommand.getShowName() + "已经存在，请勿重复提交。");
        }
        JedisUtils.set(dbType + ErpConst.KEY_PREFIX_SPU_NAME + spuCommand.getShowName(), CommonConstants.STATUS_1 + "", 30);
        CoreSpuGenerate generate = new CoreSpuGenerate();
        copySpuProperties(spuCommand, generate);
        generate.setCreator(spuCommand.getUser().getUserId());
        generate.setAddTime(new Date());
        // coreSpuGenerateMapper.insert(generate);
        baseGoodsService.mergeSpu(generate);
        CoreSpuGenerate generateNo = new CoreSpuGenerate();
        generateNo.setSpuNo(GoodsNoDict.getSpuNo(generate.getSpuId()));
        generate.setSpuNo(generateNo.getSpuNo());
        generateNo.setSpuId(generate.getSpuId());
        generateNo.setModTime(new Date());
        baseGoodsService.mergeSpu(generate);
        // coreSpuGenerateMapper.updateByPrimaryKeySelective(generateNo);
        return generate;
    }

    private void copySpuProperties(SpuAddCommand spuCommand, CoreSpuGenerate generate) {
        generate.setShowName(spuCommand.getShowName());
        generate.setCategoryId(spuCommand.getCategoryId());
        generate.setBrandId(spuCommand.getBrandId());
        if (StringUtils.isBlank(spuCommand.getSpuName())) {
            generate.setSpuName(spuCommand.getShowName());
        } else {
            generate.setSpuName(spuCommand.getSpuName());
        }
        generate.setCheckStatus(GoodsCheckStatusEnum.PRE.getStatus());//直接转为待审核
        generate.setSpuLevel(spuCommand.getSpuLevel());
        generate.setStatus(ErpConst.ONE);
        generate.setSpuType(spuCommand.getSpuType());
        generate.setFirstEngageId(spuCommand.getFirstEngageId());
        generate.setRegistrationIcon(spuCommand.getRegistrationIcon());
        generate.setWikiHref(spuCommand.getWikiHref());
        generate.setOperateInfoFlag(ErpConst.ZERO);
        generate.setAssignmentAssistantId(spuCommand.getAssignmentAssistantId());
        generate.setAssignmentManagerId(spuCommand.getAssignmentManagerId());
        if (StringUtils.isNotBlank(spuCommand.getHospitalTags())) {
            String[] tags = StringUtils.split(spuCommand.getHospitalTags(), "@_@");
            if (ArrayUtils.isNotEmpty(tags)) {
                Set<String> set = Sets.newHashSet();
                for (String tag : tags) {
                    set.add(tag);
                }
                generate.setHospitalTags(StringUtils.join(set, "@_@"));
            }
        }
        generate.setUpdater(spuCommand.getUser().getUserId());
        generate.setModTime(new Date());
    }


    @Override
    public List<CoreSpuBaseVO> selectSpuListPage(SpuSearchCommand spuCommand, Page page) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        if(StringUtil.isNotBlank(spuCommand.getSearchValue())&&StringUtil.isNumeric(spuCommand.getSearchValue())){
            spuCommand.setSpuIdSearch(NumberUtils.toInt(spuCommand.getSearchValue()));
        }
        map.put("command", spuCommand);
        spuCommand.setSearchValue(StringUtils.trim(spuCommand.getSearchValue()));
        spuCommand.setBrandName(StringUtils.trim(spuCommand.getBrandName()));
        spuCommand.setProductCompanyName(StringUtils.trim(spuCommand.getProductCompanyName()));
        spuCommand.setDepartmentName(StringUtils.trim(spuCommand.getDepartmentName()));
        Date dateTemp = spuCommand.getModTimeEnd();
        if (spuCommand.getModTimeEnd() != null) {
            spuCommand.setModTimeEnd(DateUtils.addHours(spuCommand.getModTimeEnd(), 23));
            spuCommand.setModTimeEnd(DateUtils.addMinutes(spuCommand.getModTimeEnd(), 59));
            spuCommand.setModTimeEnd(DateUtils.addSeconds(spuCommand.getModTimeEnd(), 59));
        }

        List<SpuSkuIdForListDTO> list = coreSpuGenerateExtendMapper.selectSpuListPage(map);
        spuCommand.setModTimeEnd(dateTemp);
        if (CollectionUtils.isNotEmpty(list)) {
            List<CoreSpuBaseVO> result = Lists.newArrayList();
            for (SpuSkuIdForListDTO dto : list) {
                CoreSpuBaseDTO baseDto = baseGoodsService.selectSpuBaseById(dto.getSpuId());
                if (baseDto == null) {
                    continue;
                }
                CoreSpuBaseVO spuVo = new CoreSpuBaseVO();
                BeanUtils.copyProperties(spuVo, baseDto);
                result.add(spuVo);
                String[] skuIds = StringUtils.split(dto.getSkuIds(), ",");
                if (!ArrayUtils.isEmpty(skuIds)) {
                    spuVo.setSkuTotalSize(skuIds.length);
                    spuVo.setSkuIdsInSpu(dto.getSkuIds());
                    List<CoreSkuBaseDTO> skuList = baseGoodsService
                            .selectSkuBaseByIds(ArrayUtils.subarray(skuIds, 0, MAX_SKU_PAGE_SIZE));
                    if (CollectionUtils.isNotEmpty(skuList)) {
                        for (CoreSkuBaseDTO sku : skuList) {
                            CoreSkuBaseVO skuVo = new CoreSkuBaseVO();
                            BeanUtils.copyProperties(skuVo, sku);
                            skuVo.setAssignmentAssistantId(spuVo.getAssignmentAssistantId());
                            skuVo.setAssignmentManagerId(spuVo.getAssignmentManagerId());
                            spuVo.getCoreSkuBaseVOList().add(skuVo);
                        }
                    }
                }
            }
            return result;
        }
        return Collections.emptyList();
    }

    /**
     * @throws Exception
     */
    @Override
    public void initSpu(SpuAddCommand spuCommand) throws Exception {
        if (spuCommand.getSpuId() == null) {
            if (spuCommand.getCategoryId() != null) {
                BaseCategoryVo categoryVo = baseCategoryService.getBaseCategoryByParam(spuCommand.getCategoryId());
                if (categoryVo != null) {
                    spuCommand.setCategoryType(categoryVo.getBaseCategoryType());//
                }
            }
            return;
        }
        CoreSpuGenerate generate = coreSpuGenerateMapper.selectByPrimaryKey(spuCommand.getSpuId());
        if (generate == null) {
            throw new ShowErrorMsgException("找不到对应的商品！");
        }
//        spuCommand.setSpuId(generate.getSpuId());
//        spuCommand.setCategoryId(generate.getCategoryId());
//        spuCommand.setSpuLevel(generate.getSpuLevel());
//        spuCommand.setBrandId(generate.getBrandId());
//        spuCommand.setSpuName(generate.getSpuName());
//        spuCommand.setShowName(generate.getShowName());
//        spuCommand.setSpuCheckStatus(generate.getCheckStatus());
//        spuCommand.setRegistrationIcon(generate.getRegistrationIcon());
//        spuCommand.setSpuId(generate.getSpuId());
//        spuCommand.setWikiHref(generate.getWikiHref());
//        spuCommand.setHospitalDeptTags(generate.getHospitalTags());
//        spuCommand.setSpuType(generate.getSpuType());
        BeanUtils.copyProperties(spuCommand, generate);
        BaseCategoryVo categoryVo = baseCategoryService.getBaseCategoryByParam(generate.getCategoryId());
        if (categoryVo != null) {
            spuCommand.setCategoryType(categoryVo.getBaseCategoryType());//
        }

        // 首营信息
        FirstEngage firstEngage = firstEngageMapper.selectByPrimaryKey(generate.getFirstEngageId());
        if (firstEngage != null) {
            spuCommand.setFirstEngage(firstEngage);
            spuCommand.setFirstEngageId(firstEngage.getFirstEngageId());
        }

        List<FileInfo> skuCheck = spuFilePathList(getOptionIdByOptionType(SysOptionConstant.SPU_CHECK_FILES), spuCommand.getSpuId());
        List<FileInfo> skuPatent = spuFilePathList(getOptionIdByOptionType(SysOptionConstant.SPU_PATENT_FILES), spuCommand.getSpuId());

        // 产品检测报告
        spuCommand.setSpuCheckFileJson(JsonUtils.translateToJson(skuCheck));
        // 产品专利文件
        spuCommand.setSpuPatentFilesJson(JsonUtils.translateToJson(skuPatent));

        spuCommand.setSkuCheck(skuCheck);
        spuCommand.setSkuPatent(skuPatent);

        SpuDepartmentMappingGenerateExample example = new SpuDepartmentMappingGenerateExample();
        example.createCriteria().andSpuIdEqualTo(spuCommand.getSpuId()).andStatusEqualTo(CommonConstants.STATUS_1);
        List<SpuDepartmentMappingGenerate> deptList = spuDepartmentMappingGenerateMapper.selectByExample(example);
        // 科室
        if (CollectionUtils.isNotEmpty(deptList)) {
            ArrayList<Integer> departmentIds = new ArrayList<>(deptList.size());
            for (SpuDepartmentMappingGenerate g : deptList) {
                departmentIds.add(g.getDepartmentId());
            }
            spuCommand.setDepartmentIds(departmentIds.toArray(new Integer[0]));
        }
        //获取商品的已经选择的属性
        List<BaseAttributeVo> selectedAttr = coreSpuGenerateExtendMapper.selectAllAttributeBySpuId(spuCommand.getSpuId());

        List<Integer> baseAttributeIds = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(selectedAttr)) {
            for (BaseAttributeVo attr : selectedAttr) {
                baseAttributeIds.add(attr.getBaseAttributeId());
            }
            spuCommand.setBaseAttributeIds(baseAttributeIds.toArray(new Integer[0]));
        }
    }

    public List<FileInfo> spuFilePathList(Integer attacheType, Integer spuId) {
        GoodsAttachmentGenerateExample goodsAttachmentExample658 = new GoodsAttachmentGenerateExample();
        goodsAttachmentExample658.createCriteria().andAttachmentTypeEqualTo(attacheType).andGoodsIdEqualTo(spuId)
                .andStatusEqualTo(CommonConstants.STATUS_1);
        List<GoodsAttachmentGenerate> list = goodsAttachmentGenerateMapper.selectByExample(goodsAttachmentExample658);

        if (CollectionUtils.isNotEmpty(list)) {
            List<FileInfo> fileInfos = Lists.newArrayList();
            for (GoodsAttachmentGenerate g : list) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setHttpUrl(api_http + domain);
                int lastSplit = StringUtils.lastIndexOf(g.getUri(), "/");
                if (lastSplit > 1) {
                    fileInfo.setFilePath(StringUtils.substring(g.getUri(), 0, lastSplit + 1));
                    fileInfo.setFileName(StringUtils.substring(g.getUri(), lastSplit + 1));
                }
                fileInfo.setPrefix("jpg");
                fileInfos.add(fileInfo);
            }
            return fileInfos;
        }
        return Collections.emptyList();
    }

    @Override
    public void backupSku(SkuAddCommand command) {
        logger.info("批量设置备货::" + JSON.toJSONString(command));
        if (!command.isHasEditAuth()) {
            throw new ShowErrorMsgException("权限不足");
        }
        List<Integer> list = Lists.newArrayList();
        if (StringUtils.isNotBlank(command.getSkuIds())) {
            String[] skuids = StringUtils.split(command.getSkuIds(), ",");
            if (ArrayUtils.isNotEmpty(skuids)) {
                for (String skuId : skuids) {
                    list.add(NumberUtils.toInt(skuId));
                }
            }
        }
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        CoreSkuGenerate generate = new CoreSkuGenerate();
        generate.setIsStockup(command.getHasBackupMachine());
        generate.setUpdater(command.getUser().getUserId());
        generate.setModTime(new Date());
        baseGoodsService.mergeSkuByIds(generate, list);
        //coreSkuGenerateMapper.updateByExampleSelective(generate, example);
    }

    @Override
    @Deprecated
    public void submitToCheck(SpuAddCommand spuCommand) throws ShowErrorMsgException {
        if (spuCommand.getSpuId() == null) {
            throw new ShowErrorMsgException("找不到对应的商品！");
        }
        CoreSpuGenerate generate = coreSpuGenerateMapper.selectByPrimaryKey(spuCommand.getSpuId());
        if (generate == null) {
            throw new ShowErrorMsgException("找不到对应的商品！");
        }
        //检查权限
        if (SpuLevelEnum.isTempSpu(generate.getSpuLevel()) && !spuCommand.isHasEditTempAuth()) {
            throw new ShowErrorMsgException("权限不足");
        }
        if (!SpuLevelEnum.isTempSpu(generate.getSpuLevel()) && !spuCommand.isHasEditAuth()) {
            throw new ShowErrorMsgException("权限不足");
        }
        if (GoodsCheckStatusEnum.isPre(generate.getCheckStatus())) {
            throw new ShowErrorMsgException("审核中的商品不能提交审核");
        }
        coreSpuGenerateExtendMapper.submitToCheck(spuCommand.getSpuId(), spuCommand.getUser().getUserId());
    }

    @Override
    @Transactional
    public void checkSpu(SpuAddCommand spuCommand) throws ShowErrorMsgException {
        logger.info("审核SPU::" + JSON.toJSONString(spuCommand));
        if (spuCommand.getSpuId() == null) {
            throw new ShowErrorMsgException("找不到对应的商品！");
        }
        CoreSpuBaseDTO baseSpu = baseGoodsService.selectSpuBaseById(spuCommand.getSpuId());

        if (baseSpu == null) {
            throw new ShowErrorMsgException("找不到对应的商品！");
        }
        if (GoodsCheckStatusEnum.isReject(spuCommand.getSpuCheckStatus()) && StringUtils.isBlank(spuCommand.getLastCheckReason())) {
            throw new ShowErrorMsgException("审核不通过原因不能为空！");
        }
        CoreSpuGenerate generate = new CoreSpuGenerate();
        // generate.setCheckStatus(GoodsCheckStatusEnum.PRE.getStatus());
        generate.setUpdater(spuCommand.getUser().getUserId());
        generate.setModTime(new Date());
        generate.setSpuId(spuCommand.getSpuId());
        generate.setCheckStatus(spuCommand.getSpuCheckStatus());

        generate.setChecker(spuCommand.getUser().getUserId());
        generate.setCheckTime(new Date());
        generate.setLastCheckReason(spuCommand.getLastCheckReason());
        // 首营信息审核不通过，不能更新
        BaseCategoryVo categoryVo = baseCategoryService.getBaseCategoryByParam(baseSpu.getCategoryId());
        if (categoryVo != null && CommonConstants.CATEGORY_TYPE_YILIAO.equals(categoryVo.getBaseCategoryType())) {
            FirstEngage firstEngage = firstEngageMapper.selectByPrimaryKey(baseSpu.getFirstEngageId());
            if (firstEngage == null) {
                throw new ShowErrorMsgException("找不到对应的首营信息。");
            }
            if (!GoodsCheckStatusEnum.isApprove(firstEngage.getStatus())) {
                throw new ShowErrorMsgException("当前首营信息不是审核通过状态，请先对首营信息进行审核。");
            }
        }
        baseGoodsService.mergeSpu(generate);
        // coreSpuGenerateMapper.updateByPrimaryKeySelective(generate);
        //如果是审核不通过，则sku都审核不通过
        if (GoodsCheckStatusEnum.isReject(spuCommand.getSpuCheckStatus())) {
            List<CoreSkuGenerate> skuList = selectSkuListBySpuId(spuCommand.getSpuId());
            if (!CollectionUtils.isEmpty(skuList)) {
                for (CoreSkuGenerate skuInDb : skuList) {
                    checkSku(skuInDb.getSkuId(), spuCommand.getLastCheckReason(), GoodsCheckStatusEnum.REJECT.getStatus(), spuCommand.getUser());
                }
            }
        }
        baseGoodsService.flushSku();
        baseGoodsService.flushSpu();
        LogCheckGenerate logCheckGenerate = new LogCheckGenerate();
        logCheckGenerate.setAddTime(new Date());
        logCheckGenerate.setCreator(spuCommand.getUser().getUserId());
        logCheckGenerate.setLogBizId(spuCommand.getSpuId());
        logCheckGenerate.setCreatorName(spuCommand.getUser().getUsername());
        logCheckGenerate.setLogMessage(spuCommand.getLastCheckReason());
        logCheckGenerate.setLogType(LogTypeEnum.SPU.getLogType());
        logCheckGenerate.setLogStatus(spuCommand.getSpuCheckStatus());
        logCheckGenerate.setLogStatusName(GoodsCheckStatusEnum.statusName(spuCommand.getSpuCheckStatus()));
        logCheckGenerateMapper.insert(logCheckGenerate);


    }

    private String getSkuInfoBySpuType(Integer spuType, String model, String spec) {
        SysOptionDefinition option = getSysOptionDefinitionById(spuType);
        if (option == null) {
            throw new ShowErrorMsgException("商品类型未定义");
        }
        if (SysOptionConstant.SPU_TYPE_HAOCAI.equals(option.getOptionType())
                || SysOptionConstant.SPU_TYPE_3.equals(option.getOptionType())
                || SysOptionConstant.SPU_TYPE_4.equals(option.getOptionType())
        ) {
            return spec;
        } else {
            return model;
        }

    }

    /**
     * @param user
     * @param skuInfo
     * @param spuType
     * @return CoreSkuGenerate
     */
    public CoreSkuGenerate addTempSku(User user, String skuInfo, Integer spuId, Integer skuId, Integer spuType, String spuName, CoreSpuGenerate spu) {
        logger.info("addTempSku::" + JSON.toJSONString(user) + ",skuInfo=" + skuInfo + ",spuId=" + spuId + ",skuId=" + skuId + ",spuType=" + spuType + ",spuName=" + spuName);
        //有意思的业务
        SysOptionDefinition option = getSysOptionDefinitionById(spuType);
        if (option == null) {
            throw new ShowErrorMsgException("商品类型未定义");
        }
        CoreSkuGenerate skuGenerate = new CoreSkuGenerate();
        skuGenerate.setAddTime(new Date());
        skuGenerate.setCreator(user.getUserId());
        skuGenerate.setSpuId(spuId);

        skuGenerate.setCheckStatus(GoodsCheckStatusEnum.PRE.getStatus());
        if (SysOptionConstant.SPU_TYPE_HAOCAI.equals(option.getOptionType())
                || SysOptionConstant.SPU_TYPE_3.equals(option.getOptionType())
                || SysOptionConstant.SPU_TYPE_4.equals(option.getOptionType())
        ) {
            if (StringUtils.isBlank(skuInfo)) {
                throw new ShowErrorMsgException("耗材商品规格不能为空");
            } else {
                skuGenerate.setSpec(skuInfo);
            }
        } else {
            if (StringUtils.isBlank(skuInfo)) {
                throw new ShowErrorMsgException("制造商型号不能为空");
            } else {
                skuGenerate.setModel(skuInfo);
            }
        }
        if (skuId != null) {
            CoreSkuGenerate skuInDb = coreSkuGenerateMapper.selectByPrimaryKey(skuId);
            if (skuInDb == null) {
                throw new ShowErrorMsgException("sku不存在" + skuId);
            }
            if (GoodsCheckStatusEnum.isPre(skuInDb.getCheckStatus())) {
                throw new ShowErrorMsgException("sku当前状态为审核中，无法修改。");
            }
            skuGenerate.setAddTime(null);
            skuGenerate.setCreator(null);
            skuGenerate.setSkuId(skuId);
            skuGenerate.setUpdater(user.getUserId());
            skuGenerate.setModTime(new Date());
            skuGenerate.setSkuName(spuName);
            skuGenerate.setShowName(spuName + skuInfo);
            skuGenerate.setCheckStatus(GoodsCheckStatusEnum.PRE.getStatus());
            baseGoodsService.mergeSku(skuGenerate);
            //  coreSkuGenerateMapper.updateByPrimaryKeySelective(skuGenerate);
            skuUncheckSync(skuId, spu, skuGenerate, user);
            oldSkuToUnchkUpdate(skuGenerate.getSkuId(), 0, user.getUsername());
        } else {

            if (tempSkuExsit(spuId, skuGenerate.getModel(), skuGenerate.getSpec())) {
                throw new ShowErrorMsgException("重复提交或者此规格型号已经存在");
            }
            skuGenerate.setSkuName(spuName);
            skuGenerate.setShowName(spuName + skuInfo);
            insertWithSkuNo(skuGenerate);
            skuUncheckSync(null, spu, skuGenerate, user);
            oldSkuToUnchk(skuGenerate.getSkuId(), 0, user.getUsername());
        }
        baseGoodsService.flushSku();
        return skuGenerate;
    }

    private boolean tempSkuExsit(Integer spuId, String model, String spec) {
        return coreSpuGenerateExtendMapper.countSkuBySpuIdAndSkuInfo(spuId, model, spec) > 0;
    }

    private void insertWithSkuNo(CoreSkuGenerate skuGenerate) {
        baseGoodsService.mergeSku(skuGenerate);
        CoreSkuGenerate temp = new CoreSkuGenerate();
        temp.setSkuNo(GoodsNoDict.getSkuNo(skuGenerate.getSkuId()));
        temp.setSkuId(skuGenerate.getSkuId());
        temp.setModTime(new Date());
        temp.setStatus(CommonConstants.STATUS_1);
        baseGoodsService.mergeSku(temp);
        skuGenerate.setSkuNo(temp.getSkuNo());
    }

    private void checkSku(int skuId, String reason, int checkStatus, User user) {

        logger.info("checkSku::" + JSON.toJSONString(user) + ",checkStatus=" + checkStatus + ",reason=" + reason + ",skuId=" + skuId);
        CoreSkuGenerate toOld = coreSkuGenerateMapper.selectByPrimaryKey(skuId);
        if (toOld == null || CommonConstants.STATUS_0 == toOld.getStatus()) {
            throw new ShowErrorMsgException("SKU已经删除，不能审核此SKU");
        }
        CoreSpuGenerate spuGenerate = coreSpuGenerateMapper.selectByPrimaryKey(toOld.getSpuId());
        if (spuGenerate == null || CommonConstants.STATUS_0 == spuGenerate.getStatus()) {
            throw new ShowErrorMsgException("SPU已经删除，不能审核此SKU");
        }
        CoreSkuGenerate skuGenerate = new CoreSkuGenerate();
        skuGenerate.setCheckStatus(checkStatus);
        skuGenerate.setChecker(user.getUserId());
        skuGenerate.setCheckTime(new Date());
        skuGenerate.setLastCheckReason(reason);
        skuGenerate.setSkuId(skuId);
        skuGenerate.setUpdater(user.getUserId());
        skuGenerate.setModTime(new Date());
        baseGoodsService.mergeSku(skuGenerate);
        //如果sku审核通过，同步数据到老表中
        int status = CommonConstants.STATUS_1;
        if (GoodsCheckStatusEnum.isApprove(checkStatus)) {
            //同步到goods表
            skuUncheckSync(skuId, spuGenerate, skuGenerate, user);
            //同步到官网
//            try{ 
//            vedengSoapService.goodsSync(skuId);
//            }catch(Exception e){
//                logger.error("同步到php失败",e);
//            }
        }
        VerifiesInfoGenerateExample example = new VerifiesInfoGenerateExample();
        example.createCriteria().andRelateTableEqualTo("T_GOODS")
                .andRelateTableKeyEqualTo(skuId);
        if (verifiesInfoGenerateMapper.countByExample(example) > 0) {
            oldSkuToUnchkUpdate(skuId, checkStatus, user.getUsername());
        } else {
            //默认审核通过
            oldSkuToUnchk(skuId, checkStatus, user.getUsername());
        }

        //同步更新时间
        GoodsGenerateWithBLOBs goodsGenerate = new GoodsGenerateWithBLOBs();
        goodsGenerate.setGoodsId(skuGenerate.getSkuId());
        goodsGenerate.setModTime(skuGenerate.getModTime() == null ? 0 : skuGenerate.getModTime().getTime());
        goodsGenerate.setUpdater(user.getUserId());
        goodsGenerateMapper.updateByPrimaryKeySelective(goodsGenerate);

        LogCheckGenerate logCheckGenerate = new LogCheckGenerate();
        logCheckGenerate.setAddTime(new Date());
        logCheckGenerate.setCreator(user.getUserId());
        logCheckGenerate.setLogBizId(skuId);
        logCheckGenerate.setCreatorName(user.getUsername());
        logCheckGenerate.setLogMessage(reason);
        logCheckGenerate.setLogType(LogTypeEnum.SKU.getLogType());
        logCheckGenerate.setLogStatus(checkStatus);
        logCheckGenerate.setLogStatusName(GoodsCheckStatusEnum.statusName(checkStatus));
        logCheckGenerateMapper.insert(logCheckGenerate);
        baseGoodsService.flushSku();
    }

    @Autowired
    BaseCategoryMapper baseCategoryMapper;

    private void skuUncheckSync(Integer skuId, CoreSpuGenerate spuGenerate, CoreSkuGenerate skuGenerate, User user) {
        if (skuId != null) {
            skuGenerate = coreSkuGenerateMapper.selectByPrimaryKey(skuId);
        }
        if (spuGenerate == null) {
            spuGenerate = coreSpuGenerateMapper.selectByPrimaryKey(skuGenerate.getSpuId());
        }
        GoodsGenerateWithBLOBs goodsGenerate = transGoodsToSkuService.sku2Goods(skuGenerate, spuGenerate);
        // 首营信息审核不通过，不能更新
        FirstEngage firstEngage = firstEngageService.getFirstSearchBaseInfo(spuGenerate.getFirstEngageId());

        BaseCategoryVo categoryVo = baseCategoryMapper.selectByPrimaryKey(spuGenerate.getCategoryId());
        //非医疗器械不同步首营信息
        if (firstEngage != null && categoryVo != null && 1 == categoryVo.getBaseCategoryType()) {
            //新国标分类
            goodsGenerate.setStandardCategoryId(firstEngage.getNewStandardCategoryId());
            //旧国标
            goodsGenerate.setManageCategory(firstEngage.getOldStandardCategoryId());
            //管理级别 老的：339 340 341 新的： 968,969,970
            if (firstEngage.getRegistration() != null) {
                goodsGenerate.setManageCategoryLevel(firstEngage.getRegistration().getManageCategoryLevel() - 629);
                goodsGenerate.setBegintime(firstEngage.getRegistration().getIssuingDate());
                goodsGenerate.setEndtime(firstEngage.getRegistration().getEffectiveDate());
                goodsGenerate.setProductAddress(firstEngage.getRegistration().getProductionAddress());

                try {
                    //同步注册证图片
                    CoreSkuGenerateExample skuGenerateExample = new CoreSkuGenerateExample();
                    skuGenerateExample.createCriteria().andSpuIdEqualTo(spuGenerate.getSpuId());
                    List<CoreSkuGenerate> skuGenerateList = coreSkuGenerateMapper.selectByExample(skuGenerateExample);
                    if (CollectionUtils.isNotEmpty(skuGenerateList)) {
                        for (CoreSkuGenerate genereate : skuGenerateList) {
                            //注册证与备案
                            GoodsAttachmentGenerateExample goodsAttachmentExample344680 = new GoodsAttachmentGenerateExample();
                            goodsAttachmentExample344680.createCriteria()
                                    .andAttachmentTypeIn(Lists.newArrayList(344, 680))
                                    .andGoodsIdEqualTo(genereate.getSkuId());
                            goodsAttachmentGenerateMapper.deleteByExample(goodsAttachmentExample344680);
                        }
                    }
                    //医疗器械分类时必填
                    Integer registerNumberId = null;
                    if (firstEngage != null&&firstEngage.getRegistration()!=null) {
                        registerNumberId = firstEngage.getRegistration().getRegistrationNumberId();
                    }
                    if(registerNumberId!=null){
                    Map<String, Object> paramMap = new HashMap<>();
                    List<Integer> attachmentFunction = new ArrayList<>();
                    attachmentFunction.add(CommonConstants.ATTACHMENT_FUNCTION_975);
                    paramMap.put("attachmentFunction", attachmentFunction);

                    // 附件类型
                    paramMap.put("attachmentType", CommonConstants.ATTACHMENT_TYPE_974);
                    paramMap.put("registrationNumberId", registerNumberId);
                    List<Attachment> attrList = attachmentMapper.getAttachmentsList(paramMap);

                    StringBuilder sb = new StringBuilder();
                    if (CollectionUtils.isNotEmpty(attrList)) {
                        for (Attachment a : attrList) {
                            sb.append(CommonConstants.PIC_SPLIT + a.getUri());
                        }
                        //注册证类型
                        if(firstEngage.getRegistration().getManageCategoryLevel()>968){
                            savePics(344, skuGenerate.getSkuId(), sb.toString());
                        }else{
                            savePics(680, skuGenerate.getSkuId(), sb.toString());
                        }


                    }
                    }
                } catch (Exception e) {
                    logger.error("",e);
                }


            }
            goodsGenerate.setManufacturer(firstEngage.getProductCompanyChineseName());
            if (!StringUtils.isBlank(firstEngage.getRegistrationNumber())) {
                goodsGenerate.setRegistrationNumber(firstEngage.getRegistrationNumber());
                goodsGenerate.setRecordNumber(firstEngage.getRegistrationNumber());
            }

        }
        if (goodsGenerateMapper.selectByPrimaryKey(goodsGenerate.getGoodsId()) != null) {
            goodsGenerate.setModTime(skuGenerate.getModTime() == null ? 0 : skuGenerate.getModTime().getTime());
            goodsGenerate.setUpdater(user.getUserId());
            goodsGenerateMapper.updateByPrimaryKeySelective(goodsGenerate);
        } else {
            goodsGenerate.setModTime(skuGenerate.getModTime() == null ? 0 : skuGenerate.getModTime().getTime());
            goodsGenerate.setAddTime(skuGenerate.getAddTime() == null ? 0 : skuGenerate.getAddTime().getTime());
            goodsGenerate.setUpdater(user.getUserId());
            goodsGenerate.setCreator(user.getUserId());
            coreSpuGenerateExtendMapper.insertGoods(goodsGenerate);

        }
    }

    private void oldSkuToUnchk(Integer skuId, Integer checkStatus, String userName) {
        VerifiesInfoGenerateExample example = new VerifiesInfoGenerateExample();
        example.createCriteria().andRelateTableEqualTo("T_GOODS")
                .andRelateTableKeyEqualTo(skuId);
        VerifiesInfoGenerate verifiesInfoGenerate = new VerifiesInfoGenerate();
        verifiesInfoGenerate.setRelateTable("T_GOODS");
        verifiesInfoGenerate.setRelateTableKey(skuId);
        verifiesInfoGenerate.setVerifiesType(618);
        verifiesInfoGenerate.setModTime(System.currentTimeMillis());
        verifiesInfoGenerate.setLastVerifyUsername(userName);
        verifiesInfoGenerate.setVerifyUsername(userName);


        verifiesInfoGenerate.setStatus(GoodsCheckStatusEnum.transToOld(checkStatus));//审核中或者审核不通过
        verifiesInfoGenerateMapper.insertSelective(verifiesInfoGenerate);
    }

    private void oldSkuToUnchkUpdate(Integer skuId, Integer checkStatus, String userName) {
        VerifiesInfoGenerateExample example = new VerifiesInfoGenerateExample();
        example.createCriteria().andRelateTableEqualTo("T_GOODS")
                .andRelateTableKeyEqualTo(skuId);
        VerifiesInfoGenerate verifiesInfoGenerate = new VerifiesInfoGenerate();
        verifiesInfoGenerate.setRelateTable("T_GOODS");
        verifiesInfoGenerate.setRelateTableKey(skuId);
        verifiesInfoGenerate.setVerifiesType(618);
        verifiesInfoGenerate.setModTime(System.currentTimeMillis());
        verifiesInfoGenerate.setLastVerifyUsername(userName);
        verifiesInfoGenerate.setVerifyUsername(userName);
        verifiesInfoGenerate.setStatus(GoodsCheckStatusEnum.transToOld(checkStatus));//审核中或者审核不通过
        verifiesInfoGenerateMapper.updateByExampleSelective(verifiesInfoGenerate, example);
    }

    @Override
    public Integer countSpuByCheckStatus(Integer checkStatus) {
        try {
            return countCache.get(checkStatus);
        } catch (ExecutionException e) {
            return 0;
        }
    }

    private LoadingCache<Integer, Integer> countCache = CacheBuilder
            .newBuilder().maximumSize(5).expireAfterWrite(30, TimeUnit.SECONDS)
            .build(new CacheLoader<Integer, Integer>() {
                public Integer load(Integer checkStatus) throws Exception {
                    return loadCountByStatus(checkStatus);
                }
            });

    private Integer loadCountByStatus(Integer checkStatus) {
        CoreSpuGenerateExample example = new CoreSpuGenerateExample();
        example.createCriteria().andStatusEqualTo(CommonConstants.STATUS_1).andCheckStatusEqualTo(checkStatus);
        return coreSpuGenerateMapper.countByExample(example);
    }

    @Override
    public CoreSkuGenerate initSku(SkuAddCommand command) throws Exception {
        if (command.getSkuId() == null || command.getSkuId() <= 0) {
            throw new ShowErrorMsgException("SkuId不存在！");
        }
        //编辑
        CoreSkuGenerate skuInfoDb = coreSkuGenerateMapper.selectByPrimaryKey(command.getSkuId());
        if (skuInfoDb == null || skuInfoDb.getStatus() == 0) {
            throw new ShowErrorMsgException("SKU已经被删除或者不存在！");
        }

        List<FileInfo> skuCheck = spuFilePathList(getOptionIdByOptionType(SysOptionConstant.SKU_CHECK_FILES), command.getSkuId());
        List<FileInfo> skuPatent = spuFilePathList(getOptionIdByOptionType(SysOptionConstant.SKU_PATENT_FILES), command.getSkuId());
        List<FileInfo> skuPart = spuFilePathList(getOptionIdByOptionType(SysOptionConstant.SKU_CORE_PART_PRICE_FILE), command.getSkuId());
        // 产品检测报告
        command.setSkuCheckFilesJson(JsonUtils.translateToJson(skuCheck));
        // 产品专利文件
        command.setSkuPatentFilesJson(JsonUtils.translateToJson(skuPatent));
        //核心零部件
        command.setCorePartPriceFileJson(JsonUtils.translateToJson(skuPart));
        command.setSkuCheck(skuCheck);
        command.setSkuPatent(skuPatent);
        command.setSkuPart(skuPart);
        //回显
        String params1 = skuInfoDb.getTechnicalParameter();
        String params2 = skuInfoDb.getSpecParameter();
        String params3 = skuInfoDb.getPerformanceParameter();

        List<List<String>> paramslist1 = stringToArray(params1);
        if (CollectionUtils.isNotEmpty(paramslist1) && ArrayUtils.isEmpty(command.getParamsName1())) {
            command.setParamsName1(paramslist1.get(0).toArray(new String[0]));
            command.setParamsValue1(paramslist1.get(1).toArray(new String[0]));
        }
        List<List<String>> paramslist2 = stringToArray(params2);
        if (CollectionUtils.isNotEmpty(paramslist2) && ArrayUtils.isEmpty(command.getParamsName2())) {
            command.setParamsName2(paramslist2.get(0).toArray(new String[0]));
            command.setParamsValue2(paramslist2.get(1).toArray(new String[0]));
        }
        List<List<String>> paramslist3 = stringToArray(params3);
        if (CollectionUtils.isNotEmpty(paramslist3) && ArrayUtils.isEmpty(command.getParamsName3())) {
            command.setParamsName3(paramslist3.get(0).toArray(new String[0]));
            command.setParamsValue3(paramslist3.get(1).toArray(new String[0]));
        }
        return skuInfoDb;
    }

    private List<List<String>> stringToArray(String params) {
        String[] param = StringUtils.split(params, ";");
        List<List<String>> list = Lists.newArrayList();
        if (ArrayUtils.isNotEmpty(param)) {
            List<String> key = Lists.newArrayList();
            List<String> value = Lists.newArrayList();
            for (String p : param) {
                String[] kv = StringUtils.split(p, ":");
                if (ArrayUtils.isNotEmpty(kv) && kv.length == 2) {
                    if (StringUtils.isNotBlank(kv[0]) && StringUtils.isNotBlank(kv[1])) {
                        key.add(kv[0]);
                        value.add(kv[1]);
                    }
                }
            }
            list.add(key);
            list.add(value);
        }
        return list;
    }


    private Integer getOptionIdByOptionType(String optionType) {
        SysOptionDefinition option = getFirstSysOptionDefinitionList(optionType);
        if (option != null) {
            return option.getSysOptionDefinitionId();
        }
        return -1;
    }

    @Override
    @Transactional
    public void deleteSpu(SpuSearchCommand spuCommand) throws ShowErrorMsgException {
        logger.info("deleteSpu::" + JSON.toJSONString(spuCommand));
        CoreSpuGenerate dbSpu = coreSpuGenerateMapper.selectByPrimaryKey(spuCommand.getSpuId());
        if (dbSpu == null || CommonConstants.STATUS_0.equals(dbSpu.getStatus()) || GoodsCheckStatusEnum.DELETE.getStatus() == dbSpu.getCheckStatus()) {
            throw new ShowErrorMsgException("spu不存在或者已经被删除。");
        }
        boolean hasAuth = false;
        if (SpuLevelEnum.isTempSpu(dbSpu.getSpuLevel()) && spuCommand.isHasEditTempAuth()) {
            hasAuth = true;
        }
        if (!SpuLevelEnum.isTempSpu(dbSpu.getSpuLevel()) && spuCommand.isHasEditAuth()) {
            hasAuth = true;
        }
        if (!hasAuth) {
            throw new ShowErrorMsgException("权限不足");
        }
        if (GoodsCheckStatusEnum.isPre(dbSpu.getCheckStatus())) {
            throw new ShowErrorMsgException("所选内容中包括审核中的SPU或者SKU商品，不可删除。");
        }
        //先删除SPU
        CoreSpuGenerate generate = new CoreSpuGenerate();
        generate.setSpuId(spuCommand.getSpuId());
        generate.setStatus(CommonConstants.STATUS_0);
        generate.setCheckStatus(GoodsCheckStatusEnum.DELETE.getStatus());
        generate.setModTime(new Date());
        generate.setUpdater(spuCommand.getUser().getUserId());
        generate.setDeleteReason(spuCommand.getDeleteReason());
        baseGoodsService.mergeSpu(generate);
        //再删除sku
        CoreSkuGenerateExample skuExample = new CoreSkuGenerateExample();
        skuExample.createCriteria().andSpuIdEqualTo(spuCommand.getSpuId()).andStatusEqualTo(CommonConstants.STATUS_1);
        List<CoreSkuGenerate> list = coreSkuGenerateMapper.selectByExample(skuExample);
        for (CoreSkuGenerate sku : list) {
            deleteSkuById(sku.getSkuId(), spuCommand.getDeleteReason(), spuCommand.getUser(), true);
        }
        baseGoodsService.flushSpu();
    }

    @Override
    @Transactional
    public void deleteSku(SpuSearchCommand spuCommand) throws ShowErrorMsgException {
        if (spuCommand.getSkuId() == null || spuCommand.getSpuId() == null) {
            throw new ShowErrorMsgException(CommonConstants.FAIL_CODE,
                    "skuid 和 spuid 都不能为空,skuId:" + spuCommand.getSkuId() + ",spuId:" + spuCommand.getSpuId());
        }
        CoreSpuBaseDTO baseDto = baseGoodsService.selectSpuBaseById(spuCommand.getSpuId());
        if (baseDto == null) {
            throw new ShowErrorMsgException("spu不存在或者已经被删除。");
        }
        //判断权限
        boolean hasAuth = false;
        if (SpuLevelEnum.isTempSpu(baseDto.getSpuLevel()) && spuCommand.isHasEditTempAuth()) {
            hasAuth = true;
        }
        if (!SpuLevelEnum.isTempSpu(baseDto.getSpuLevel()) && spuCommand.isHasEditAuth()) {
            hasAuth = true;
        }
        if (!hasAuth) {
            throw new ShowErrorMsgException("权限不足");
        }
        deleteSkuById(spuCommand.getSkuId(), spuCommand.getDeleteReason(), spuCommand.getUser(), false);
    }

    private void deleteSkuById(Integer skuId, String reason, User user, boolean isfromspu) throws ShowErrorMsgException {
        logger.info("deleteSkuById::" + skuId + reason + user.getUsername());
        CoreSkuGenerate newSku = coreSkuGenerateMapper.selectByPrimaryKey(skuId);
        if (newSku == null || CommonConstants.STATUS_0.equals(newSku.getStatus())) {
            if (isfromspu) {
                throw new ShowErrorMsgException(CommonConstants.FAIL_CODE, "spu中有sku不存在或者已经删除。" + skuId);
            } else {
                throw new ShowErrorMsgException(CommonConstants.FAIL_CODE, "sku不存在或者已经删除。" + skuId);
            }
        }
        if (GoodsCheckStatusEnum.isPre(newSku.getCheckStatus())) {
            throw new ShowErrorMsgException("所选内容中包括审核中SKU，不可删除。");
        }


        CoreSkuGenerate sku = new CoreSkuGenerate();
        sku.setSpuId(newSku.getSpuId());
        sku.setSkuId(skuId);
        sku.setStatus(CommonConstants.STATUS_0);
        sku.setModTime(new Date());
        sku.setUpdater(user.getUserId());
        sku.setDeleteReason(reason);

        baseGoodsService.mergeSku(sku);
        // 删除老数据
        GoodsGenerateExample oldExample = new GoodsGenerateExample();
        oldExample.createCriteria().andSkuEqualTo(newSku.getSkuNo());
        GoodsGenerateWithBLOBs old = new GoodsGenerateWithBLOBs();
        old.setIsDiscard(CommonConstants.STATUS_1);
        old.setDiscardReason(reason);
        old.setDiscardTime(System.currentTimeMillis());
        goodsGenerateMapper.updateByExampleSelective(old, oldExample);
        baseGoodsService.flushSku();
    }

    @Override
    public List<CoreSkuBaseVO> selectSkuListPage(SpuSearchCommand spuCommand,  Page page) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", new Page(1, 10));
        map.put("command", spuCommand);
        List<SpuSkuIdForListDTO> list = coreSpuGenerateExtendMapper.selectSpuListPage(map);
        if (CollectionUtils.isNotEmpty(list)) {
            List<CoreSkuBaseVO> result = Lists.newArrayList();
            for (SpuSkuIdForListDTO dto : list) {
                String[] skuIds = StringUtils.split(dto.getSkuIds(), ",");
                if (!ArrayUtils.isEmpty(skuIds)) {
                    page.setTotalRecord(skuIds.length);
                    String[] subIds = ArrayUtils.subarray(skuIds, page.getStartRecord(), page.getPageSize() * page.getPageNo());
                    if (!ArrayUtils.isEmpty(subIds)) {
                        CoreSpuBaseDTO spuBaseDTO=   baseGoodsService.selectSpuBaseById( dto.getSpuId());
                        List<CoreSkuBaseDTO> skuList = baseGoodsService
                                .selectSkuBaseByIds(subIds);
                        if (CollectionUtils.isNotEmpty(skuList)) {

                            for (CoreSkuBaseDTO sku : skuList) {
                                CoreSkuBaseVO skuVo = new CoreSkuBaseVO();
                                BeanUtils.copyProperties(skuVo, sku);
                                skuVo.setAssignmentAssistantId(spuBaseDTO.getAssignmentAssistantId());
                                skuVo.setAssignmentManagerId(spuBaseDTO.getAssignmentManagerId());
                                result.add(skuVo);
                            }
                        }
                    }
                }
                break;
            }
            return result;
        }
        return Collections.emptyList();
    }

    @Override
    public List<CoreSpuBaseVO> exportSpuList(String spuIds) throws Exception {
        String[] spuIdArray = StringUtils.split(spuIds, ",");
        if (!ArrayUtils.isEmpty(spuIdArray)) {
            List<CoreSpuBaseVO> result = Lists.newArrayList();
            for (String spuId : spuIdArray) {
                CoreSpuBaseDTO baseDto = baseGoodsService.selectSpuBaseById(NumberUtils.toInt(spuId));
                if (baseDto == null) {
                    continue;
                }
                CoreSpuBaseVO spuVo = new CoreSpuBaseVO();
                BeanUtils.copyProperties(spuVo, baseDto);
                result.add(spuVo);
                List<CoreSkuGenerate> skuList = selectSkuListBySpuId(NumberUtils.toInt(spuId));

                if (!CollectionUtils.isEmpty(skuList)) {
                    String[] skuArray = new String[skuList.size()];
                    for (int i = 0; i < skuList.size(); i++) {
                        skuArray[i] = String.valueOf(skuList.get(i).getSkuId());
                    }
                    List<CoreSkuBaseDTO> baseSkuList = baseGoodsService.selectSkuBaseByIds(skuArray);
                    if (CollectionUtils.isNotEmpty(baseSkuList)) {
                        for (CoreSkuBaseDTO sku : baseSkuList) {
                            CoreSkuBaseVO skuVo = new CoreSkuBaseVO();
                            BeanUtils.copyProperties(skuVo, sku);
                            spuVo.getCoreSkuBaseVOList().add(skuVo);
                        }
                    }
                }
            }
            return result;
        }
        return Collections.emptyList();
    }

    @Override
    public List<BaseAttributeVo> selectAllAttributeBySkuId(Integer spuId, Integer skuId) {
        CoreSpuBaseDTO coreSpuBaseDTO = baseGoodsService.selectSpuBaseById(spuId);
        if (coreSpuBaseDTO == null) {
            throw new ShowErrorMsgException("SPU不存在");
        }
        List<Integer> oldAttr = getSelectedValuesBySkuId(skuId);
        System.out.println(StringUtils.join(oldAttr, ","));
        List<BaseAttributeVo> attrVos = coreSpuGenerateExtendMapper.selectAllAttributeBySpuId(spuId);
        if (CollectionUtils.isNotEmpty(attrVos)) {
            for (BaseAttributeVo vo : attrVos) {
                List<BaseAttributeValueVo> values = coreSpuGenerateExtendMapper.selectAllAttributeValueByAttrId(vo.getBaseAttributeId(), coreSpuBaseDTO.getCategoryId());
                if (CollectionUtils.isNotEmpty(values)) {
                    for (BaseAttributeValueVo valueVo : values) {
                        if (org.springframework.util.CollectionUtils.contains(oldAttr.iterator(), valueVo.getBaseAttributeValueId())) {
                            valueVo.setSelected(true);
                        }
                    }
                }
                vo.setAttrValue(values);
            }
        }
        return attrVos;
    }

    @Override
    public List<BaseAttributeVo> selectCheckedAttributeBySpuId(Integer spuId) {
        return null;
    }

    @Override
    @Transactional
    public void saveSku(SkuAddCommand command, CoreSkuGenerate skuGenerate) throws UnsupportedEncodingException {
        //发票不允许有这些字符串
        if(skuGenerate==null){
            return;
        }
        CoreSpuBaseDTO coreSpuDto =baseGoodsService.selectSpuBaseById(skuGenerate.getSpuId());

        if (getOptionIdByOptionType(SysOptionConstant.SPU_TYPE_HAOCAI).equals(NumberUtils.toInt(coreSpuDto.getSpuType()))
                || getOptionIdByOptionType(SysOptionConstant.SPU_TYPE_3).equals(NumberUtils.toInt(coreSpuDto.getSpuType()))
                || getOptionIdByOptionType(SysOptionConstant.SPU_TYPE_4).equals(NumberUtils.toInt(coreSpuDto.getSpuType()))
        ) {
            command.setSkuType(GoodsConstants.SKU_TYPE_CONSUMABLES);
        } else {
            command.setSkuType(GoodsConstants.SKU_TYPE_INSTRUMENT);
        }

        checkInvoiceCondition(command,skuGenerate);

        skuGenerate.setUpdater(command.getUser().getUserId());
        skuGenerate.setModTime(new Date());
        if (command.getSkuId() == null) {
            logger.info("addSku::" + JSON.toJSONString(command));
            CoreSkuGenerate spu = addSku(command, skuGenerate);
            command.setSkuId(spu.getSkuId());
            saveSkuPics(command);
            skuUncheckSync(null, null, skuGenerate, command.getUser());
            oldSkuToUnchk(spu.getSkuId(), 0, command.getUser().getUsername());
        } else {
            logger.info("updateSku::" + JSON.toJSONString(command));
            checkSkuUpdateCondition(command, skuGenerate);
            updateSku(command, skuGenerate);
            deleteSkuPics(command);
            saveSkuPics(command);
            skuUncheckSync(skuGenerate.getSkuId(), null, skuGenerate, command.getUser());
            oldSkuToUnchkUpdate(skuGenerate.getSkuId(), 0, command.getUser().getUsername());
        }
        mergeSkuAttr(command);
        baseGoodsService.flushSku();
    }


    private void checkInvoiceCondition(SkuAddCommand command,CoreSkuGenerate skuGenerate) throws UnsupportedEncodingException {
        if(isMoreStringlength(skuGenerate.getSkuName(),"GBK",68)){
            throw new ShowErrorMsgException( "由于开票原因，商品名称不得超过限定长度。");
        }
        if(GoodsConstants.SKU_TYPE_INSTRUMENT==command.getSkuType()){
            if(isMoreStringlength(skuGenerate.getModel(),"GBK",40)){
                throw new ShowErrorMsgException( "由于开票原因, 型号不得超过限定长度。");
            }
        }else{
        if(isMoreStringlength(skuGenerate.getSpec(),"GBK",40)){
            throw new ShowErrorMsgException( "由于开票原因，规格不得超过限定长度。");
        }
        }



        if(StringUtils.contains(skuGenerate.getSkuName(),"*")||StringUtils.contains(skuGenerate.getSkuName(),"<")
                ||StringUtils.contains(skuGenerate.getSkuName(),">")||
                StringUtils.contains(skuGenerate.getSkuName(),"'")){
            throw new ShowErrorMsgException( "由于开票原因，商品名称不允许4个字符  * < > '");
        }

    }
    private boolean isMoreStringlength(String string,String format,Integer maxLength) throws UnsupportedEncodingException {
        if(StringUtils.isBlank(string)){
            return false;
        }
        int length = string.getBytes(format).length;
        if(length > maxLength){
            return true;
        }
        return false;
    }

    @Override
    public List<BaseAttributeVo> getAttributeInfoByCategoryId(Integer categoryId) {
        return coreSpuGenerateExtendMapper.selectAllAttributeByCategoryId(categoryId);
    }

    @Override
    public List<LogCheckGenerate> listSpuCheckLog(Integer spuId) {
        LogCheckGenerateExample example = new LogCheckGenerateExample();
        example.createCriteria().andLogBizIdEqualTo(spuId).andLogTypeEqualTo(LogTypeEnum.SPU.getLogType());
        example.setOrderByClause(" add_time desc ");
        return logCheckGenerateMapper.selectByExample(example);
    }

    @Override
    public List<LogCheckGenerate> listSkuCheckLog(Integer skuId) {
        LogCheckGenerateExample example = new LogCheckGenerateExample();
        example.createCriteria().andLogBizIdEqualTo(skuId).andLogTypeEqualTo(LogTypeEnum.SKU.getLogType());
        ;
        example.setOrderByClause(" add_time desc ");
        return logCheckGenerateMapper.selectByExample(example);
    }

    @Override
    public void saveTempSku(SkuAddCommand skuCommand) {
        logger.info("saveTempSku::" + JSON.toJSONString(skuCommand));
        CoreSpuGenerate coreSpuBaseDTO = coreSpuGenerateMapper.selectByPrimaryKey(skuCommand.getSpuId());
        if (coreSpuBaseDTO == null) {
            throw new ShowErrorMsgException("SPU不存在");
        }
        if (!SpuLevelEnum.isTempSpu(coreSpuBaseDTO.getSpuLevel())) {
            throw new ShowErrorMsgException("SPU不为临时商品");
        }


        addTempSku(skuCommand.getUser(), skuCommand.getSkuInfo(), coreSpuBaseDTO.getSpuId(), skuCommand.getSkuId(), coreSpuBaseDTO.getSpuType()
                , coreSpuBaseDTO.getShowName(), coreSpuBaseDTO);

    }

    @Override
    public List<Map<String, Object>> searchFirstEngageListPage(String searchValue, Page page) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("searchValue", searchValue);
        return coreSpuGenerateExtendMapper.searchFirstEngageListPage(map);
    }

    @Override
    @Transactional
    public void changeSpuStatusByCategoryChange(Integer categoryId, User user) {
        logger.info("changeSpuStatusByCategoryChange::" + categoryId + user.getUsername());
        Map<String, Object> map = new HashMap<String, Object>();
        Page page = Page.newBuilder(1, 10000, "");
        map.put("page", page);
        SpuSearchCommand spuCommand = new SpuSearchCommand();
        spuCommand.setCategoryId(categoryId);
        map.put("command", spuCommand);
        List<SpuSkuIdForListDTO> list = coreSpuGenerateExtendMapper.selectSpuListPage(map);
        if (page.getTotalRecord() > 10000) {//TODO
            throw new ShowErrorMsgException("当前分类下超过10000条商品审核状态会变成待审核,请线下处理");
        }
        for (SpuSkuIdForListDTO dto : list) {
            SpuAddCommand command = new SpuAddCommand();
            command.setSpuId(dto.getSpuId());
            command.setCheckStatus(GoodsCheckStatusEnum.PRE.getStatus());
            checkSpu(command);
        }
        baseGoodsService.flushSpu();
        baseGoodsService.flushSku();
    }


    @Override
    public void checkSku(SkuAddCommand command) {
        logger.info("checkSku::" + JSON.toJSONString(command));
        CoreSpuBaseDTO coreSpuBaseDTO = baseGoodsService.selectSpuBaseById(command.getSpuId());
        if (coreSpuBaseDTO == null) {
            throw new ShowErrorMsgException("SPU不存在");
        }
        if (GoodsCheckStatusEnum.isReject(coreSpuBaseDTO.getCheckStatus()) || GoodsCheckStatusEnum.isPre(coreSpuBaseDTO.getCheckStatus())) {
            throw new ShowErrorMsgException("SPU审核通过才能对SKU审核");
        }
        checkSku(command.getSkuId(), command.getLastCheckReason(), command.getCheckStatus(), command.getUser());
    }

    private List<Integer> getSelectedValuesBySkuId(Integer skuId) {
        if (skuId == null) {
            return Collections.emptyList();
        }
        SkuAttrMappingGenerateExample example = new SkuAttrMappingGenerateExample();
        example.createCriteria().andSkuIdEqualTo(skuId).andStatusEqualTo(CommonConstants.STATUS_1);
        List<SkuAttrMappingGenerate> list = skuAttrMappingGenerateMapper.selectByExample(example);
        List<Integer> oldAttr = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(list)) {
            for (SkuAttrMappingGenerate generate : list) {
                if (generate.getBaseAttributeValueId() != null) {
                    oldAttr.add(generate.getBaseAttributeValueId());
                }

            }
        }
        return oldAttr;
    }

    @Autowired
    BaseAttributeValueMapper baseAttributeValueMapper;

    private void mergeSkuAttr(SkuAddCommand command) {
        Integer attrValues[] = command.getBaseAttributeValueId();
        if (ArrayUtils.isNotEmpty(attrValues)) {
            SkuAttrMappingGenerateExample example = new SkuAttrMappingGenerateExample();
            example.createCriteria().andSkuIdEqualTo(command.getSkuId()).andStatusEqualTo(CommonConstants.STATUS_1);
            List<SkuAttrMappingGenerate> list = skuAttrMappingGenerateMapper.selectByExample(example);

            List<Integer> oldAttr = getSelectedValuesBySkuId(command.getSkuId());
            List<Integer> newAttr = Lists.newArrayList(attrValues);
            List<Integer> addAttr = Lists.newArrayList(newAttr);
            addAttr.removeAll(oldAttr);

            List<Integer> deleteAttr = Lists.newArrayList(oldAttr);
            deleteAttr.removeAll(newAttr);

            if (CollectionUtils.isNotEmpty(addAttr)) {
                for (Integer attr : addAttr) {
                    BaseAttributeValue value = baseAttributeValueMapper.selectByPrimaryKey(attr);
                    SkuAttrMappingGenerate generate = new SkuAttrMappingGenerate();
                    generate.setSkuId(command.getSkuId());
                    generate.setStatus(CommonConstants.STATUS_1);
                    generate.setAddTime(new Date());
                    generate.setModTime(new Date());
                    generate.setCreator(command.getUser().getUserId());
                    generate.setUpdater(command.getUser().getUserId());
                    generate.setBaseAttributeValueId(attr);
                    generate.setBaseAttributeId(value.getBaseAttributeId());
                    skuAttrMappingGenerateMapper.insert(generate);
                }
            }
            if (CollectionUtils.isNotEmpty(deleteAttr)) {
                List<Integer> deleteAttrIntegerList = Lists.newArrayList();
                for (Integer attr : deleteAttr) {
                    deleteAttrIntegerList.add(attr);
                }
                SkuAttrMappingGenerate generate = new SkuAttrMappingGenerate();
                generate.setStatus(CommonConstants.STATUS_0);
                generate.setModTime(new Date());
                generate.setUpdater(command.getUser().getUserId());
                SkuAttrMappingGenerateExample example1 = new SkuAttrMappingGenerateExample();
                example1.createCriteria().andBaseAttributeValueIdIn(deleteAttrIntegerList)
                        .andSkuIdEqualTo(command.getSkuId()).andStatusEqualTo(CommonConstants.STATUS_1);
                skuAttrMappingGenerateMapper.updateByExampleSelective(generate, example1);
            }
        }
    }


    private void updateSku(SkuAddCommand command, CoreSkuGenerate skuGenerate) {
        logger.info("updateSku::" + JSON.toJSONString(command) + "\t" + JSON.toJSONString(skuGenerate));
        skuGenerate.setCheckStatus(GoodsCheckStatusEnum.PRE.getStatus());
        skuGenerate.setTechnicalParameter(paramArrayToString(command.getParamsName1(), command.getParamsValue1()));
        skuGenerate.setSpecParameter(paramArrayToString(command.getParamsName2(), command.getParamsValue2()));
        skuGenerate.setPerformanceParameter(paramArrayToString(command.getParamsName3(), command.getParamsValue3()));
        skuGenerate.setSkuName(StringUtils.trim(skuGenerate.getSkuName()));
        skuGenerate.setShowName(skuGenerate.getSkuName());
        //对一些数字字段做兼容
        baseGoodsService.mergeSku(skuGenerate);
        //coreSkuGenerateMapper.updateByPrimaryKeySelective(skuGenerate);
    }

    private void checkSkuUpdateCondition(SkuAddCommand command, CoreSkuGenerate skuGenerate) {
        if (command.getSpuId() == null) {
            throw new ShowErrorMsgException("商品ID不能为空。");
        }
        CoreSkuGenerate generate = coreSkuGenerateMapper.selectByPrimaryKey(command.getSkuId());
        if (generate == null) {
            throw new ShowErrorMsgException("找不到对应的SKU。");
        }
        skuGenerate.setSkuNo(generate.getSkuNo());
//        // 首营信息审核不通过，不能更新
//        FirstEngage firstEngage = firstEngageMapper.selectByPrimaryKey(command.getFirstEngageId());
//        if (firstEngage == null) {
//            throw new ShowErrorMsgException("找不到对应的首营信息。");
//        }
//        if (!FirstEngageCheckStatusEnum.isApprove(firstEngage.getStatus())) {
//            throw new ShowErrorMsgException("当前首营信息审核中，请先对首营信息进行审核。");
//        }
        CoreSpuBaseDTO coreSpuDto = baseGoodsService.selectSpuBaseById(command.getSpuId());
        if (coreSpuDto == null) {
            throw new ShowErrorMsgException("找不到对应的SPU。");
        }
        //检查是否有权限
        if (SpuLevelEnum.isTempSpu(coreSpuDto.getSpuLevel()) && !command.isHasEditTempAuth()) {
            throw new ShowErrorMsgException(CommonConstants.RESULTINFO_CODE_FAIL_1 + "", "您没有此操作的权限！");
        }
        if (!SpuLevelEnum.isTempSpu(coreSpuDto.getSpuLevel()) && !command.isHasEditAuth()) {
            throw new ShowErrorMsgException(CommonConstants.RESULTINFO_CODE_FAIL_1 + "", "您没有此操作的权限！");
        }

        // 状态为审核中，不能更新
        if (GoodsCheckStatusEnum.isPre(generate.getCheckStatus())) {
            throw new ShowErrorMsgException("SKU状态为审核中不能修改。");
        }
    }

    private String paramArrayToString(String[] a, String[] b) {
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

    private CoreSkuGenerate addSku(SkuAddCommand command, CoreSkuGenerate skuGenerate) {
        logger.info("addSku::" + JSON.toJSONString(command) + "\t" + JSON.toJSONString(skuGenerate));
        //
        //重复判断
        CoreSkuGenerateExample skuExample = new CoreSkuGenerateExample();
        skuExample.createCriteria().andStatusEqualTo(CommonConstants.STATUS_1)
                .andShowNameEqualTo(StringUtils.trim(skuGenerate.getSkuName()));
        if (coreSkuGenerateMapper.countByExample(skuExample) > 0) {
            throw new ShowErrorMsgException(skuGenerate.getSkuName() + "已经存在，请勿重复提交。");
        }

        skuGenerate.setAddTime(new Date());
        skuGenerate.setCreator(command.getUser().getUserId());
        skuGenerate.setCheckStatus(GoodsCheckStatusEnum.PRE.getStatus());
        skuGenerate.setTechnicalParameter(paramArrayToString(command.getParamsName1(), command.getParamsValue1()));
        skuGenerate.setSpecParameter(paramArrayToString(command.getParamsName2(), command.getParamsValue2()));
        skuGenerate.setPerformanceParameter(paramArrayToString(command.getParamsName3(), command.getParamsValue3()));
        skuGenerate.setSkuName(StringUtils.trim(skuGenerate.getSkuName()));
        // TODO CUSTOM
        skuGenerate.setShowName(StringUtils.trim(skuGenerate.getSkuName()));
        insertWithSkuNo(skuGenerate);
        return skuGenerate;
    }

    private List<CoreSkuGenerate> selectSkuListBySpuId(Integer spuId) {
        if (spuId == null) {
            return Collections.emptyList();
        }
        CoreSkuGenerateExample skuExample = new CoreSkuGenerateExample();
        skuExample.createCriteria().andSpuIdEqualTo(spuId).andStatusEqualTo(CommonConstants.STATUS_1);
        List<CoreSkuGenerate> skuList = coreSkuGenerateMapper.selectByExample(skuExample);
        return skuList;
    }

    private void deleteSkuPics(SkuAddCommand command) {
        if (command.getSpuId() == null) {
            return;
        }
        // 先删除产品检测报告// 先删除产品专利文件
        GoodsAttachmentGenerateExample goodsAttachmentExample658 = new GoodsAttachmentGenerateExample();
        goodsAttachmentExample658.createCriteria()
                .andAttachmentTypeIn(Lists.newArrayList(getOptionIdByOptionType(SysOptionConstant.SKU_CHECK_FILES),
                        getOptionIdByOptionType(SysOptionConstant.SKU_PATENT_FILES),
                        getOptionIdByOptionType(SysOptionConstant.SKU_CORE_PART_PRICE_FILE))
                )
                .andGoodsIdEqualTo(command.getSkuId())
                .andStatusEqualTo(CommonConstants.STATUS_1);
        GoodsAttachmentGenerate generate = new GoodsAttachmentGenerate();
        generate.setStatus(CommonConstants.STATUS_0);
        goodsAttachmentGenerateMapper.updateByExampleSelective(generate, goodsAttachmentExample658);
        //老数据兼容
        GoodsAttachmentGenerateExample goodsAttachmentExample658659 = new GoodsAttachmentGenerateExample();
        goodsAttachmentExample658659.createCriteria()
                .andAttachmentTypeIn(Lists.newArrayList(658, 659)
                )
                .andGoodsIdEqualTo(command.getSkuId());
        goodsAttachmentGenerateMapper.deleteByExample(goodsAttachmentExample658659);
    }

    private void deleteSpuPics(SpuAddCommand spuCommand) {
        if (spuCommand.getSpuId() == null) {
            return;
        }
        // 先删除产品检测报告// 先删除产品专利文件
        GoodsAttachmentGenerateExample goodsAttachmentExample658 = new GoodsAttachmentGenerateExample();
        goodsAttachmentExample658.createCriteria()
                .andAttachmentTypeIn(Lists.newArrayList(getOptionIdByOptionType(SysOptionConstant.SPU_CHECK_FILES),
                        getOptionIdByOptionType(SysOptionConstant.SPU_PATENT_FILES)))
                .andGoodsIdEqualTo(spuCommand.getSpuId()).andStatusEqualTo(CommonConstants.STATUS_1);
        GoodsAttachmentGenerate generate = new GoodsAttachmentGenerate();
        generate.setStatus(CommonConstants.STATUS_0);
        goodsAttachmentGenerateMapper.updateByExampleSelective(generate, goodsAttachmentExample658);

    }

    private void saveSkuPics(SkuAddCommand command) {
        // 产品检测报告
        savePics(getOptionIdByOptionType(SysOptionConstant.SKU_CHECK_FILES), command.getSkuId(), command.getSkuCheckFiles());
        // 产品专利文件
        savePics(getOptionIdByOptionType(SysOptionConstant.SKU_PATENT_FILES), command.getSkuId(), command.getSkuPatentFiles());

        // 产品检测报告（老商品）
        savePics(SysOptionConstant.ID_658, command.getSkuId(), command.getSkuCheckFiles());
        // 产品专利文件（老商品）
        savePics(SysOptionConstant.ID_659, command.getSkuId(), command.getSkuPatentFiles());


        //核心零部件
        savePics(getOptionIdByOptionType(SysOptionConstant.SKU_CORE_PART_PRICE_FILE), command.getSkuId(), command.getCorePartPriceFile());
    }


    private void saveSpuPics(SpuAddCommand spuCommand) {
        // 产品检测报告
        savePics(getOptionIdByOptionType(SysOptionConstant.SPU_CHECK_FILES), spuCommand.getSpuId(), spuCommand.getSpuCheckFiles());
        // 产品专利文件
        savePics(getOptionIdByOptionType(SysOptionConstant.SPU_PATENT_FILES), spuCommand.getSpuId(), spuCommand.getSpuPatentFiles());
    }

    private void savePics(Integer type, Integer id, String urls) {
        String[] urlArray = StringUtils.split(urls, CommonConstants.PIC_SPLIT);
        if (ArrayUtils.isNotEmpty(urlArray)) {
            for (int i = 0; i < urlArray.length; i++) {
                GoodsAttachmentGenerate generate = new GoodsAttachmentGenerate();
                generate.setAttachmentType(type);
                generate.setUri(urlArray[i]);
                generate.setStatus(CommonConstants.STATUS_1);
                generate.setDomain(domain);
                generate.setSort(i);
                generate.setGoodsId(id);

                goodsAttachmentGenerateMapper.insert(generate);
            }
        }
    }


    @Override
    public List<Map<String, Object>> searchSkuWithDepartment(String skuName) {
        List<Map<String, String>> dbData = coreSpuGenerateExtendMapper.searchSkuWithDepartment(skuName);
        List<Map<String, Object>> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dbData)) {
            for (Map<String, String> map : dbData) {
                Map<String, Object> row = new HashMap<>();
                String skuId = String.valueOf(map.get("SKU_ID"));
                String skuNameN = map.get("SKU_NAME");
                String deptIds = map.get("DEPARTMENT_IDS");
                String deptNames = map.get("DEPARTMENT_NAMES");
                String skuUnitName = map.get("SKU_NUIT_NAME");
                String purpose = map.get("PURPOSE");
                String status = String.valueOf(map.get("STATUS"));
                String checkStatus = String.valueOf(map.get("CHECK_STATUS"));
                //组装
                String deptArray[] = StringUtils.split(deptIds, ",");
                String deptNameArray[] = StringUtils.split(deptNames, ",");
                List<Map<String, String>> deptList = Lists.newArrayList();
                if (ArrayUtils.isNotEmpty(deptArray)) {
                    for (int i = 0; i < Math.min(deptArray.length, deptNameArray.length); i++) {
                        if (StringUtils.isNotBlank(deptArray[i]) && StringUtils.isNotBlank(deptNameArray[i])) {
                            Map<String, String> dept = Maps.newHashMap();
                            dept.put("departmentId", deptArray[i]);
                            dept.put("departmentName", deptNameArray[i]);
                            deptList.add(dept);
                        }
                    }
                }
                row.put("skuId", skuId);
                row.put("skuName", skuNameN);
                row.put("departments", deptList);
                row.put("skuUnitName", skuUnitName);
                row.put("purpose", purpose);
                row.put("status", status);
                row.put("checkStatus", checkStatus);
                result.add(row);
            }
            return result;
        } else {
            return Collections.emptyList();
        }

    }
    LoadingCache<Integer, Map<String, Object>> goodsCache = CacheBuilder
            .newBuilder().maximumSize(10000).expireAfterWrite(5, TimeUnit.MINUTES)
            .build(new CacheLoader<Integer, Map<String, Object>>() {
                public Map<String, Object> load(Integer skuId) throws Exception {
                    return skuTipDb(skuId);
                }
            });
    public Map<String, Object> skuTipDb(Integer skuId) {
        Map<String, Object> goodsInfo= goodsMapper.getGoodsInfoTips(skuId);

        if(goodsInfo == null){
            return Maps.newHashMap();
        }

        String manager=goodsInfo.get("USERNAME")==null?"":goodsInfo.get("USERNAME").toString();
        String assi=goodsInfo.get("ASSIS")==null?"":goodsInfo.get("ASSIS").toString();
        Set<String> set=Sets.newHashSet();
        set.add(manager);
        set.add(assi);
        goodsInfo.put("PRODUCTMANAGER",StringUtils.join(set,"<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
        goodsInfo.put("PRODUCTMANAGER_NO_SPACE",StringUtils.join(set,"<br>"));
        String sku="V"+skuId;
        Map<String, WarehouseStock> stockInfo = warehouseStockService.getStockInfo(Lists.newArrayList(sku));
        if(stockInfo!=null&&stockInfo.containsKey(sku)){
            WarehouseStock stock=stockInfo.get(sku);
            goodsInfo.put("STOCKNUM",stock.getStockNum());
            goodsInfo.put("AVAILABLESTOCKNUM",stock.getAvailableStockNum());
            goodsInfo.put("OCCUPYNUM",stock.getOccupyNum());
        }else{
            goodsInfo.put("STOCKNUM",0);
            goodsInfo.put("AVAILABLESTOCKNUM",0);
            goodsInfo.put("OCCUPYNUM",0);
        }
        return goodsInfo;
    }

    @Override
    public Map<String, Object> skuTip(Integer skuId) {
        try {
                return goodsCache.get(skuId);
        } catch (Exception e) {
            logger.error("",e);
            return Collections.emptyMap();
        }
    }

    @Override
    public List<Map<String, Object>> skuTipList(List<Integer> skuIds) {

        if(CollectionUtils.isEmpty(skuIds)){
            return Lists.newArrayList();
        }

        List<Map<String, Object>> goodsInfoList= goodsMapper.skuTipList(skuIds);
        goodsInfoList.stream().forEach(goodsInfo->{

            Set<String> set=Sets.newHashSet();
            set.add(goodsInfo.get("USERNAME")==null?"":goodsInfo.get("USERNAME").toString());
            set.add(goodsInfo.get("ASSIS")==null?"":goodsInfo.get("ASSIS").toString());
            goodsInfo.put("PRODUCTMANAGER",StringUtils.join(set,"<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));

            String skuNo = goodsInfo.get("SKU_NO").toString();
            Map<String, WarehouseStock> stockInfo = warehouseStockService.getStockInfo(Lists.newArrayList(skuNo));
            if(stockInfo!=null&&stockInfo.containsKey(skuNo)){
                WarehouseStock stock=stockInfo.get(skuNo);
                goodsInfo.put("STOCKNUM",stock.getStockNum());
                goodsInfo.put("AVAILABLESTOCKNUM",stock.getAvailableStockNum());
                goodsInfo.put("OCCUPYNUM",stock.getOccupyNum());
            }else{
                goodsInfo.put("STOCKNUM",0);
                goodsInfo.put("AVAILABLESTOCKNUM",0);
                goodsInfo.put("OCCUPYNUM",0);
            }
        });

        return goodsInfoList;
    }

    @Override
    public List<CoreSpuGenerate> findSpuNamesBySpuIds(List<Integer> goodsIds) {
        return goodsMapper.findSpuNamesBySpuIds(goodsIds);

    }


    @Autowired
    SaleorderGoodsGenerateMapper  saleorderGoodsGenerateMapper;
    @Override
    public Map<String, Object> getCostPrice(Integer orderDetailId) {
        Map<String, Object> result=new HashMap<>(   );
//<a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewfinancebuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>'+ b +'", "link":"./finance/buyorder/viewBuyorder.do?buyorderId='+data.data[i].buyorderList[b].buyorderId+'","title":"订单信息"}\'>'+data.data[i].buyorderList[b].buyorderNo+'</a><br>
        getCostPrice2(orderDetailId,result);

        String ids= result.get("BUYORDERID")==null?"":result.get("BUYORDERID")+"";
        String nos=result.get("BUYORDERNO")==null?"":result.get("BUYORDERNO")+"";

        if(StringUtils.isNotBlank(ids)){
            String idarray[]=StringUtils.split(ids,",");
            String noarray[]=StringUtils.split(nos,",");
            String url="<a class='loadMoreAddtitle' href='javascript:void(0);' tabTitle='{\"num\":\"viewfinancebuyorder##id##\", \"link\":\"./finance/buyorder/viewBuyorder.do?buyorderId=##id##\",\"title\":\"订单信息\"}'>##no##</a>";
            Set<String> links=Sets.newHashSet();
            for(int i=0;i<idarray.length;i++){
               String linksTemp=url.replaceAll("##id##",idarray[i]);
                linksTemp=linksTemp.replaceAll("##no##",noarray[i]);

                links.add(linksTemp );
            }
            result.put("BUYNOLINK",StringUtils.join(links,"<br>"));
        }
        return result;
    }

    private void getCostPrice2(Integer orderDetailId,Map<String, Object> result){
        //1 直接通过采购单关联关系获取
        //1直发
        SaleorderGoodsGenerate saleorderGoodsGenerate=saleorderGoodsGenerateMapper.selectByPrimaryKey(orderDetailId);
        if(saleorderGoodsGenerate==null){
            return;
        }
        if(NumberUtils.toInt(saleorderGoodsGenerate.getDeliveryDirect()+"")==1){
           List<WarehouseGoodsOperateLogVo> directPrice= goodsMapper.getBuyPricesDerictOrderDetailId(orderDetailId);
           average(directPrice,result);
           logger.info("直发订单"+saleorderGoodsGenerate.getSaleorderGoodsId()+" 找 采购单"+result);
           return;
        }
        //2 非直发 通过入库采购单直接查找
        List<WarehouseGoodsOperateLogVo> noDirectList=goodsMapper.getBuyPricesNoDirectDetailId(orderDetailId);
        if(CollectionUtils.isNotEmpty(noDirectList)) { //入库是否有采购单
            average(noDirectList,result);
            return;
        }
        //直接通过入库条码找到采购订单
        List<WarehouseGoodsOperateLogVo> priceListIn=goodsMapper.getBuyPricesOrderId(1,orderDetailId);
        if(CollectionUtils.isNotEmpty(priceListIn)) { //入库是否有采购单
            average(priceListIn,result);
        }else{
            Integer times=NumberUtils.toInt(result.get("times")+"");
            if(times>5){
                return;
            }
            result.put("times",times+1);
            //通过销售售后条码找售后入库的订单goodid
            List<Integer> orderDetailIdByAftersale=goodsMapper.getAfterSaleOrderDetailId(orderDetailId);
            if(CollectionUtils.isEmpty(orderDetailIdByAftersale)){
                logger.info("售后单也找不到，"+saleorderGoodsGenerate.getSaleorderGoodsId()+" ");
                //那就是通过采购售后入库的

                List<WarehouseGoodsOperateLogVo> buyorderPriceids=goodsMapper.getBuyPriceByBuyorderAftersale( orderDetailId);
                average(buyorderPriceids,result);
                return;
            }
            List<Map<String, Object>> list=Lists.newArrayList();
            for(int i=0;i<orderDetailIdByAftersale.size();i++ ){
                Map<String, Object> resulttemp=new HashMap<>(   );
                if(i==0){
                    resulttemp.put("times",result.get("times"));//遍历深度只计算一次
                }
                getCostPrice2(orderDetailIdByAftersale.get(i),resulttemp);
//                Integer times2=NumberUtils.toInt(resulttemp.get("times")+"");
//                if(i==0){
//                    result.put("times",times2+times);//遍历深度只计算一次
//                }
                list.add(resulttemp);
            }
            //求平均值
            BigDecimal total=new BigDecimal(0);
            Set<String> buyorderId=new HashSet<>();
            Set<String> buyorderNo=new HashSet<>();
            int totalHavePriceCount=0;
            for(Map<String, Object> logVo :list){
                if(!logVo.containsKey("PRICE")){
                    break;
                }
                totalHavePriceCount++;
                total=total.add(new BigDecimal(logVo.get("PRICE")+""));


                buyorderId.add(logVo.get("BUYORDERID")+"") ;
                buyorderNo.add(logVo.get("BUYORDERNO")+"") ;
            }
            BigDecimal resultPrice= total.divide(new BigDecimal(totalHavePriceCount),2,BigDecimal.ROUND_HALF_UP);
            result.put("PRICE",resultPrice.toString());
            result.put("BUYORDERID",StringUtils.join(buyorderId,","));
            result.put("BUYORDERNO",StringUtils.join(buyorderNo,","));
        }
    }

    void average( List<WarehouseGoodsOperateLogVo> priceListIn,Map<String, Object> result){
        if(CollectionUtils.isEmpty(priceListIn)){
            return;
        }
        BigDecimal totalPrice=new BigDecimal(0);
        Integer totalNum=0;
        Set<String> buyorderId=new HashSet<>();
        Set<String> buyorderNo=new HashSet<>();

        for(WarehouseGoodsOperateLogVo logVo :priceListIn){
            if(logVo.getNum()>0){
                totalPrice= totalPrice.add(logVo.getPrice().multiply(new BigDecimal(logVo.getNum())));
                totalNum=totalNum+logVo.getNum();
                buyorderId.add(logVo.getBuyorderId()+"") ;
                buyorderNo.add(logVo.getBuyorderNo()+"-"+logVo.getNum()) ;
            }
        }
        if(totalNum==0){
            return;
        }
        BigDecimal resultPrice= totalPrice.divide(new BigDecimal(totalNum),2,BigDecimal.ROUND_HALF_UP);
        result.put("PRICE",resultPrice.toString());
        result.put("BUYORDERID",StringUtils.join(buyorderId,","));
        result.put("BUYORDERNO",StringUtils.join(buyorderNo,","));
    }

}
