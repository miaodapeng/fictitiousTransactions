package com.newtask;

import com.beust.jcommander.internal.Lists;
import com.github.pagehelper.PageHelper;
import com.task.model.ReadFirst;
import com.vedeng.department.dao.DepartmentsHospitalGenerateMapper;
import com.vedeng.department.model.DepartmentsHospitalGenerate;
import com.vedeng.department.model.DepartmentsHospitalGenerateExample;
import com.vedeng.goods.dao.*;
import com.vedeng.goods.model.*;
import com.vedeng.goods.model.vo.BaseAttributeValueVo;
import com.vedeng.goods.service.BaseGoodsService;
import com.vedeng.system.service.SysOptionDefinitionService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.io.Resources;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


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
@JobHandler(value = "initCateOwnerHandler")
@Component
public class InitCateOwnerHandler extends IJobHandler {
    Logger logger = LoggerFactory.getLogger(InitCateOwnerHandler.class);
    @Autowired
    GoodsGenerateMapper mapper;
    @Autowired
    DepartmentsHospitalGenerateMapper deptMapper;
    @Autowired
    BaseGoodsService goodsService;
    @Autowired
    SpuDepartmentMappingGenerateMapper mappingGenerateMapper;

    @Autowired
    CoreSpuGenerateExtendMapper coreSpuGenerateExtendMapper;
    @Autowired
    CoreSpuGenerateMapper coreSpuGenerateMapper;
    @Autowired
    CategoryAttrValueMappingGenerateMapper categoryAttrValueMappingGenerateMapper;
    @Autowired
    BaseAttributeGenerateMapper baseAttributeGenerateMapper;
    @Autowired
    BaseAttributeValueGenerateMapper baseAttributeValueGenerateMapper;
    @Autowired
    SkuAttrMappingGenerateMapper skuAttrMappingGenerateMapper;
    @Autowired
    SpuAttrMappingGenerateMapper spuAttrMappingGenerateMapper;


    public static void main(String[] args) throws Exception {
        InitCateOwnerHandler a = new InitCateOwnerHandler();
        a.execute("");
    }

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        InputStream is = Resources.getResourceAsStream("a.xlsx");
        Workbook wb = new XSSFWorkbook(is);
        Sheet sheet = wb.getSheetAt(0);
        List<ReadFirst> list = new ArrayList<>();
        for (int i = 0; i < 239; i++) {
            try {
                Row row = sheet.getRow(i);
                if (row == null) {
                    break;
                }
                ReadFirst readFirst = new ReadFirst();

                String cell0 = row.getCell(0).getStringCellValue();
                goodsService.selectSkuBaseById(Integer.parseInt(cell0.replace("V", "")));


                String cell1 = row.getCell(2).getStringCellValue();

                String cell2 = row.getCell(3).getStringCellValue();
                String cell3 = row.getCell(4).getStringCellValue();

                String cell4 = row.getCell(7).getStringCellValue();
                String cell5 = row.getCell(8).getStringCellValue();
                String cell6 = row.getCell(9).getStringCellValue();
                String cell7 = row.getCell(11).getStringCellValue();

//				//1获取老的分类
//				int cat2=0;
//				try {
//					  cat2 = coreSpuGenerateExtendMapper.selectOldCateId(cell3, cell2, cell1);
//				}catch(Exception e){
//					XxlJobLogger.log("transGoodsToSkuHandler start."+cell0,e);
//				}


                int cat = 0;
                //2获取新的分类
                try {
                    cat = coreSpuGenerateExtendMapper.selectCateId(cell6, cell5, cell4);
                } catch (Exception e) {
                    logger.error("分类错误", e);
                    logger.info(cell0 + cell1 + cell2 + cell3 + cell4 + cell5 + cell6);
                    continue;
                }
                List<BaseAttributeValueVo> oldAttr = coreSpuGenerateExtendMapper.selectOldCateIdAttr(Integer.parseInt(cell0.replace("V", "")));
                //1新分类下添加属性
                if (CollectionUtils.isNotEmpty(oldAttr)) {
                    for (BaseAttributeValueVo vo : oldAttr) {
                        int attrId = 0;
                        if (StringUtils.isNotBlank(vo.getBaseAttributeName())) {
                            //判断属性是否存在

                            BaseAttributeGenerateExample baseAttributeGenerateExample = new BaseAttributeGenerateExample();
                            baseAttributeGenerateExample.createCriteria().andBaseAttributeNameEqualTo(vo.getBaseAttributeName());
                            List<BaseAttributeGenerate> listx = baseAttributeGenerateMapper.selectByExample(baseAttributeGenerateExample);
                            if (CollectionUtils.isEmpty(listx)) {
                                BaseAttributeGenerate generate = new BaseAttributeGenerate();
                                generate.setAddTime(new Date());
                                generate.setBaseAttributeName(vo.getBaseAttributeName());
                                generate.setIsDeleted(0);
                                generate.setIsUnit(0);
                                baseAttributeGenerateMapper.insertSelective(generate);
                                attrId = generate.getBaseAttributeId();
                            } else {
                                attrId = listx.get(0).getBaseAttributeId();
                            }
                        }
                        int valueID = 0;
                        //判断属性是否存在
                        if (StringUtils.isNotBlank(vo.getAttrValue())) {
                            BaseAttributeValueGenerateExample baseAttributeValueGenerateExample = new BaseAttributeValueGenerateExample();
                            baseAttributeValueGenerateExample.createCriteria().andAttrValueEqualTo(vo.getAttrValue());
                            List<BaseAttributeValueGenerate> listxy = baseAttributeValueGenerateMapper.selectByExample(baseAttributeValueGenerateExample);
                            if (CollectionUtils.isEmpty(listxy)) {
                                BaseAttributeValueGenerate generate = new BaseAttributeValueGenerate();
                                generate.setAddTime(new Date());
                                generate.setAttrValue(vo.getAttrValue());
                                generate.setBaseAttributeId(attrId);
                                generate.setIsDeleted(0);
                                baseAttributeValueGenerateMapper.insertSelective(generate);
                                valueID = generate.getBaseAttributeValueId();
                            } else {
                                valueID = listxy.get(0).getBaseAttributeValueId() == null ? 0 : listxy.get(0).getBaseAttributeValueId();
                            }
                        }
                        if (attrId > 0 && valueID > 0) {
                            CategoryAttrValueMappingGenerateExample mappingGenerate = new CategoryAttrValueMappingGenerateExample();
                            mappingGenerate.createCriteria().andBaseAttributeIdEqualTo(attrId)
                                    .andBaseAttributeValueIdEqualTo(valueID).andBaseCategoryIdEqualTo(cat);
                            List<CategoryAttrValueMappingGenerate> listz = categoryAttrValueMappingGenerateMapper.selectByExample(mappingGenerate);
                            int valueId = 0;
                            if (CollectionUtils.isEmpty(listz)) {
                                CategoryAttrValueMappingGenerate generate = new CategoryAttrValueMappingGenerate();
                                generate.setAddTime(new Date());
                                generate.setBaseAttributeId(attrId);
                                generate.setBaseAttributeValueId(valueID);
                                generate.setBaseCategoryId(cat);
                                generate.setIsDeleted(0);
                                categoryAttrValueMappingGenerateMapper.insertSelective(generate);
                            } else {
                            }
                            SkuAttrMappingGenerateExample example1 = new SkuAttrMappingGenerateExample();
                            example1.createCriteria().andBaseAttributeValueIdEqualTo(valueID).andSkuIdEqualTo(Integer.parseInt(cell0.replace("V", "")))
                                    .andStatusEqualTo(1) .andBaseAttributeIdEqualTo(attrId);
                            Integer count1 = skuAttrMappingGenerateMapper.countByExample(example1);
                            if(count1 == 0) {
                                SkuAttrMappingGenerate skuAttrMappingGenerate = new SkuAttrMappingGenerate();
                                skuAttrMappingGenerate.setBaseAttributeValueId(valueID);
                                skuAttrMappingGenerate.setSkuId(Integer.parseInt(cell0.replace("V", "")));
                                skuAttrMappingGenerate.setStatus(1);
                                skuAttrMappingGenerate.setBaseAttributeId(attrId);
                                skuAttrMappingGenerateMapper.insertSelective(skuAttrMappingGenerate);
                            }
                            SpuAttrMappingGenerateExample example2 = new SpuAttrMappingGenerateExample();
                            example2.createCriteria().andBaseAttributeIdEqualTo(attrId).andBaseAttributeValueIdEqualTo(valueID).andStatusEqualTo(1);
                            Integer count2 = spuAttrMappingGenerateMapper.countByExample(example2);
                            if(count2 == 0) {
                                SpuAttrMappingGenerate spuAttrMappingGenerate = new SpuAttrMappingGenerate();
                                spuAttrMappingGenerate.setBaseAttributeId(attrId);
                                spuAttrMappingGenerate.setBaseAttributeValueId(valueID);
                                spuAttrMappingGenerate.setStatus(1);
                                spuAttrMappingGenerateMapper.insertSelective(spuAttrMappingGenerate);
                            }
                        }else{
                            logger.info(cell0 + cell1 + cell2 + cell3 + cell4 + cell5 + cell6+" id 不正常");
                        }

                    }
                }


                CoreSpuGenerate generate = new CoreSpuGenerate();
                generate.setCategoryId(cat);
                generate.setSpuId(Integer.parseInt(cell0.replace("V", "")));
                coreSpuGenerateMapper.updateByPrimaryKeySelective(generate);

                //获取科室
                String c7[] = StringUtils.split(cell7, "、");
                if (ArrayUtils.isNotEmpty(c7)) {
                    for (String c : c7) {
                        DepartmentsHospitalGenerateExample departmentsHospitalGenerateExample = new DepartmentsHospitalGenerateExample();
                        departmentsHospitalGenerateExample.createCriteria().andDepartmentNameEqualTo(c);
                        List<DepartmentsHospitalGenerate> list1 = deptMapper.selectByExample(departmentsHospitalGenerateExample);
                        if (CollectionUtils.isNotEmpty(list1)) {
                            SpuDepartmentMappingGenerate mm = new SpuDepartmentMappingGenerate();
                            mm.setDepartmentId(list1.get(0).getDepartmentId());
                            mm.setSpuId(Integer.parseInt(cell0.replace("V", "")));
                            mappingGenerateMapper.insertSelective(mm);
                        }
                    }
                }


            } catch (Exception e) {
                XxlJobLogger.log("transGoodsToSkuHandler start.", e);
                logger.error("", e);
            }
        }
//		}
        return SUCCESS;
    }

}
