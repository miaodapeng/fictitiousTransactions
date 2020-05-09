package com.task;

import com.task.model.ReadFirst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.firstengage.service.FirstEngageService;
import org.apache.ibatis.io.Resources;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param $
 * @author bill
 * @description 首营信息导入
 * @date $
 */
public class ReadFirstExcel extends BaseController {

    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private FirstEngageService firstEngageService = (FirstEngageService) context.getBean("firstEngageService");

    @Transactional
    public void readExcel() throws IOException {
        String path = System.getProperty("user.dir");
//        File file =  new File(path + "/src/main/webapp/WEB-INF/tempFile/first.xlsx");
        //获取excel文件的io流
        InputStream is = Resources.getResourceAsStream("first.xlsx");
        Workbook wb = new XSSFWorkbook(is);
        Sheet sheet = wb.getSheetAt(0);
        List<ReadFirst> list = new ArrayList<>();
        for(int i = 2; i < 980; i++){

            Row row = sheet.getRow(i);
            ReadFirst readFirst = new ReadFirst();
            for(int j = 0; j < 31; j++){
                Cell cell = row.getCell(j);
                if(null == cell){
                    continue;
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if(EmptyUtils.isBlank(cell.getStringCellValue())){
                    continue;
                }
                String[] cellvalarray = cell.getStringCellValue().split("/");
                if(null == cellvalarray || cellvalarray.length <= 0){
                    continue;
                }
                String cellval = "";
                if(EmptyUtils.isNotBlank(cellvalarray[0])){
                    cellval = cellvalarray[0];
                }else if(EmptyUtils.isNotBlank(cellvalarray[1])){
                    cellval = cellvalarray[1];
                }else if(EmptyUtils.isNotBlank(cellvalarray[2])){
                    cellval = cellvalarray[2];
                }

                // 遍历列
                switch (j){
                    case 0:
                        // 注册证号/ 备案凭证号
                        readFirst.setRegistrationNumber(cellval);
                        break;
                    case 1:
                        // 管理类别
                        if("一类医疗器械".equals(cellval)){
                            readFirst.setManageCategoryLevel(968);
                        }else if("二类医疗器械".equals(cellval)){
                            readFirst.setManageCategoryLevel(969);
                        }else if("三类医疗器械".equals(cellval)){
                            readFirst.setManageCategoryLevel(970);
                        }else{
                            readFirst.setManageCategoryLevel(0);
                        }
                        break;

                    case 2:
                        // 生产企业（中文注册证名称）
                        readFirst.setProductCompanyChineseName(cellval);
                        break;

                    case 3:
                        // 批准日期
                        if(DateUtil.convertLong(cellval, DateUtil.DATE_FORMAT_P) > 0){
                            readFirst.setIssuingDate(DateUtil.convertLong(cellval, DateUtil.DATE_FORMAT_P));
                        }else{
                            readFirst.setIssuingDate(DateUtil.convertLong(cellval, DateUtil.DATE_FORMAT));
                        }
                        break;

                    case 4:
                        // 有效期至
                        if(DateUtil.convertLong(cellval, DateUtil.DATE_FORMAT_P) > 0){
                            readFirst.setEffectiveDate(DateUtil.convertLong(cellval, DateUtil.DATE_FORMAT_P));
                        }else{
                            readFirst.setEffectiveDate(DateUtil.convertLong(cellval, DateUtil.DATE_FORMAT));
                        }
                        break;

                    case 6:
                        // 生产地址
                        readFirst.setProductionAddress(cellval);
                        break;

                    case 7:
                        // 企业地址（注册人住所）
                        readFirst.setProductCompanyAddress(cellval);
                        break;

                    case 8:
                        // 产品名称（中文）
                        readFirst.setProductChineseName(cellval);
                        break;

                    case 9:
                        // 产品名称（英文）
                        readFirst.setProductEnglishName(cellval);
                        break;

                    case 10:
                        // 审批部门
                        readFirst.setApprovalDepartment(cellval);
                        break;

                    case 11:
                        // 型号、规格
                        readFirst.setModel(cellval);
                        break;

                    case 12:
                        // 代理人名称
                        readFirst.setRegisteredAgent(cellval);
                        break;

                    case 13:
                        // 代理人住所
                        readFirst.setRegisteredAgentAddress(cellval);
                        break;

                    case 14:
                        // 注册商标
                        readFirst.setTrademark(cellval);
                        break;

                    case 15:
                        // 邮编
                        readFirst.setZipCode(cellval);
                        break;

                    case 16:
                        // 结构及组成
                        readFirst.setProPerfStruAndComp(cellval);
                        break;

                    case 17:
                        // 适用范围
                        readFirst.setProductUseRange(cellval);
                        break;

                    case 18:
                        // 其他内容
                        readFirst.setOtherContents(cellval);
                        break;

                    case 19:
                        // 备注
                        readFirst.setRemarks(cellval);
                        break;

                    case 20:
                        // 产品标准
                        readFirst.setProductStandards(cellval);
                        break;

                    case 21:
                        // 预期用途（体外诊断试剂）
                        readFirst.setExpectedUsage(cellval);
                        break;

                    case 22:
                        // 主要组成成分（体外诊断试剂）
                        readFirst.setMainProPerfStruAndComp(cellval);
                        break;

                    case 23:
                        String bbb = "";
                        String[] aaa = cellval.split("、");
                        if(EmptyUtils.isNotBlank(aaa[0])){
                            bbb = aaa[0];
                        }else if(EmptyUtils.isNotBlank(aaa[1])){
                            bbb = aaa[1];
                        }
                        // 变更日期
                        if(DateUtil.convertLong(bbb, DateUtil.DATE_FORMAT_P) > 0){
                            readFirst.setChangeDate(DateUtil.convertLong(bbb, DateUtil.DATE_FORMAT_P));
                        }else{
                            readFirst.setChangeDate(DateUtil.convertLong(bbb, DateUtil.DATE_FORMAT));
                        }
                        break;

                    case 24:
                        // 变更情况
                        readFirst.setChangeContents(cellval);
                        break;

                    case 26:
                        // 商品类型
                        if("器械设备".equals(cellval)){
                            readFirst.setGoodsType(316);
                        }else if("试剂".equals(cellval)){
                            readFirst.setGoodsType(318);
                        }else if ("耗材".equals(cellval)){
                            readFirst.setGoodsType(317);
                        }else {
                            readFirst.setGoodsType(0);
                        }
                        break;


                    case 29:
                        // 国标类型
                        if("新国标".equals(cellval)){
                            readFirst.setStandardCategoryType(1);
                        }else if("旧国标".equals(cellval)){
                            readFirst.setStandardCategoryType(2);
                        }else{
                            readFirst.setStandardCategoryType(0);
                        }
                        break;

                    case 30:
                        // 国标分类（旧国标分类）
                        // 根据旧国标名称查询旧国标分类id
                        if(EmptyUtils.isNotBlank(cellval)){
                            readFirst.setNewStandardCategoryId(firstEngageService.getOldStandardId(cellval));
                        }
                        break;

                    case 31:
                        // 国标分类（新国标分类）
                        // 根据新国标名称查询新国标分类id
                        String[] cellV = cellval.split("->");
                        if(null != cellV && cellV.length == 2){
                            // 根据最小级分类名称和父级分类名称查询最小级分类id
                            Map<String, Object> paramMap = new HashMap<>();
                            paramMap.put("parent", cellV[1]);
                            paramMap.put("child", cellV[2]);
                            readFirst.setNewStandardCategoryId(firstEngageService.getNewStandardId(paramMap));
                        }


                        break;

                    case 32:
                        // 储存条件1
                        if("常温".equals(cellval)){
                            readFirst.setConditionOne(983);
                        }else if("冷藏".equals(cellval)){
                            readFirst.setConditionOne(984);
                        }else if("冷冻".equals(cellval)){
                            readFirst.setConditionOne(985);
                        }else{
                            readFirst.setConditionOne(0);
                        }
                        break;
                }
            }
            list.add(readFirst);
        }


        // 循环list
        for (int k = 0; k < list.size(); k++){
            ReadFirst readFirst = list.get(k);
            // 添加企业信息
            firstEngageService.addProductCompany(readFirst);

            // 添加注册证信息
            firstEngageService.addRegisterNumber(readFirst);

            // 新增首营信息
            firstEngageService.addFirstInfo(readFirst);

        }


        is.close();



    }

//    public static void main(String[] args) throws IOException {

//        String aaa = ReadFirstExcel.class.getResource("").getPath();
//        System.out.println(aaa);

//        String path = System.getProperty("user.dir");
//        File file =  new File(path + "/src/main/webapp/WEB-INF/tempFile/first.xlsx");

//        System.out.println(file.getPath());
//        System.out.println(file.getAbsolutePath());
//        //获取excel文件的io流
//        InputStream is = Resources.getResourceAsStream("first.xlsx");
//        Workbook wb = new XSSFWorkbook(is);
//        ReadFirstExcel aa = new ReadFirstExcel();
//        try {
//            aa.readExcel();
//        } catch (IOException e) {
//            logger.error(Contant.ERROR_MSG, e);
//        }
//
//        String aaa = "2022-11-29";
//        if(DateUtil.convertLong(aaa, DateUtil.DATE_FORMAT_P) > 0){
//            System.out.println(DateUtil.convertLong(aaa, DateUtil.DATE_FORMAT_P));
//            System.out.println(DateUtil.convertString(DateUtil.convertLong(aaa, DateUtil.DATE_FORMAT_P), DateUtil.DATE_FORMAT));
//        }else{
//            System.out.println(DateUtil.convertLong(aaa, DateUtil.DATE_FORMAT));
//            System.out.println(DateUtil.convertString(DateUtil.convertLong(aaa, DateUtil.DATE_FORMAT), DateUtil.DATE_FORMAT));
//        }
//    }

}
