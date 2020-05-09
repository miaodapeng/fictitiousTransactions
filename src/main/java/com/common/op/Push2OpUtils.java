package com.common.op;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.http.HttpClientUtils4Op;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.model.ResultInfo4Op;
import com.vedeng.goods.model.vo.BaseCategoryVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author hugo
 * @description 向OP系统推送数据
 * @date 2020/1/8
 */
public class Push2OpUtils {
    public Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 向OP系统中推送更改的三级分类名称
     *
     * @param baseCategoryVo
     * @param clientId
     * @param clientKey
     * @param operateUrl
     * @return
     */
    public ResultInfo pushUpdateCategory(BaseCategoryVo baseCategoryVo, String clientId, String clientKey, String operateUrl) {
        try {
            //查询该在OP平台中分类下是否有商品
            // 定义反序列化 数据格式
            final TypeReference<ResultInfo4Op> TypeRef4Query = new TypeReference<ResultInfo4Op>() {
            };
            String queryUrl = operateUrl + ErpConst.GET_GOODSNUM_URL + baseCategoryVo.getBaseCategoryId();
            Object resultInfo = HttpClientUtils4Op.get(queryUrl, null, clientId, clientKey, TypeRef4Query);
            ResultInfo4Op resultInfo4Op = (ResultInfo4Op) resultInfo;
            int goodsNum = resultInfo4Op == null ? 0 : Integer.parseInt(resultInfo4Op.getData().toString());

            //如果存在商品则向OP系统中同步分类名
            if (goodsNum > 0) {
                final TypeReference<ResultInfo4Op> TypeRef4Save = new TypeReference<ResultInfo4Op>() {
                };
                String erpCatNameConcat = "&erpCatNameConcat=" + baseCategoryVo.getFirstLevelCategoryName() + "@" + baseCategoryVo.getSecondLevelCategoryName() + "@" + baseCategoryVo.getBaseCategoryName();
                String url = operateUrl + ErpConst.SAVE_CATEGORY_URL + baseCategoryVo.getBaseCategoryId() + "&erpCategoryName=" + baseCategoryVo.getBaseCategoryName() + erpCatNameConcat;
                HttpClientUtils.post(url, null, clientId, clientKey, TypeRef4Save);
            }
        } catch (IOException e) {
            logger.error("商品分类信息推送异常：", e);
        }
        return new ResultInfo();
    }
}
