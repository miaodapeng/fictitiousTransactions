package com.web.yxg.controller;

import com.google.common.collect.Lists;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.StringUtil;
import com.vedeng.goods.model.vo.GoodsVo;
import com.vedeng.goods.service.GoodsDataService;
import com.vedeng.order.model.GoodsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Description:
 * @Auther: Duke.li
 * @Date: 2019/8/14 09:21
 */
@Controller
@RequestMapping("/yxg/goods")
public class YxgGoodsController {//yxg去掉

    private static final Logger logger = LoggerFactory.getLogger(YxgGoodsController.class);

    @Autowired
    @Qualifier(value = "goodsDataService")
    private GoodsDataService goodsDataService;

    @ResponseBody
    @RequestMapping(value = "/goodsStock")
    public ResultInfo getGoodsStock(){

        logger.info("yxg测试接口");
        return new ResultInfo();
    }


    /**
     * @Description: Multiple goods stock query.
     * @Auther: Tomcat.Hui
     * @Date: 2019/8/15 16:21
     */
    @ResponseBody
    @RequestMapping(value = "/multipleGoodsStock", method = RequestMethod.GET)
    public ResultInfo getMultipleGoodsStock(@RequestParam(value = "queryData")String queryData){
        logger.info("批量查询商品库存开始,查询数据: {}" ,queryData);
        final List<GoodsVo> goodsVoList = Lists.newArrayList();
        try {
            if (StringUtil.isNotBlank(queryData)){

                //get goodids
                List<String> idList = Arrays.asList(queryData.split(","));
                List<Integer> goodIds =Lists.newArrayList();
                idList.stream().forEach(sku -> {
                    GoodsVo goodsVo = new GoodsVo();
                    goodsVo.setSku(sku);
                    Integer goodid = goodsDataService.getGoodsId(sku);
                    if(null != goodid ){
                        goodsVo.setGoodsId(goodid);
                        goodIds.add(goodid);
                    }
                    goodsVo.setCanUseGoodsStock(0);
                    goodsVoList.add(goodsVo);

                });

                if(goodIds.size() == 0){
                    return new ResultInfo(-1,"未查到有效的商品编号");
                }

                //get stock num & occupy num
                List<GoodsData> goodsStockList = goodsDataService.getGoodsStockNumList(goodIds,1);
//                List<GoodsData> goodsOccupyList = goodsDataService.getGoodsOccupyNumList(goodIds,1);

                //calculation
                goodsVoList.stream().forEach(
                        g -> {
                            Integer stockNum = 0;
                            Integer occupyNum = 0;

                            Optional<GoodsData> optionalGoodsData = goodsStockList.stream().filter(stock -> stock.getGoodsId().equals(g.getGoodsId())).findFirst();
                            if(optionalGoodsData.isPresent()){
                                stockNum = optionalGoodsData.get().getStockNum();
                            }
                            logger.info("商品: {} 库存量: {}",g.getGoodsId() ,stockNum);
                            g.setGoodsStock(stockNum);
                            /** 20190823 调整为取库存量 不进行计算
                            optionalGoodsData = goodsOccupyList.stream().filter(occupy -> occupy.getGoodsId().equals(g.getGoodsId())).findFirst();
                            if(optionalGoodsData.isPresent()){
                                occupyNum = optionalGoodsData.get().getOccupyNum();
                            }
                            logger.info("商品: {} 占用库存量: {}",g.getGoodsId() ,occupyNum );

                            g.setCanUseGoodsStock( stockNum - occupyNum);
                            logger.info("商品: {} 可用库存量: {}" ,g.getGoodsId() ,g.getCanUseGoodsStock() ); **/
                        });
                return new ResultInfo(0,"查询成功",goodsVoList);
            } else {
                return new ResultInfo(-1,"参数不能为空");
            }
        } catch (Exception e) {
            logger.error("程序发生错误: {}",e);
            return new ResultInfo(-1,"程序发生错误");

        }
    }
}
