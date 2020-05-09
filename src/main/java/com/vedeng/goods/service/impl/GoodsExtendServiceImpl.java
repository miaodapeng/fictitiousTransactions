package com.vedeng.goods.service.impl;

import java.io.IOException;
import java.util.List;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.goods.model.GoodsExtend;
import com.vedeng.goods.service.GoodsExtendService;

@Service("goodsExtendService")
public class GoodsExtendServiceImpl extends BaseServiceimpl implements GoodsExtendService {
	public static Logger logger = LoggerFactory.getLogger(GoodsExtendServiceImpl.class);

    @Override
    public List<GoodsExtend> getGoodsExtendByOrderIdList(GoodsExtend goodsExtend) {
	// 定义反序列化 数据格式
	final TypeReference<ResultInfo<List<GoodsExtend>>> TypeRef = new TypeReference<ResultInfo<List<GoodsExtend>>>() {
	};
	String url = httpUrl + "goods/getgoodsextendbyorderidlist.htm";
	try {
	    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsExtend, clientId, clientKey, TypeRef);
	    List<GoodsExtend> list = (List<GoodsExtend>) result.getData();
	    return list;
	} catch (IOException e) {
	    logger.error(Contant.ERROR_MSG, e);
	    return null;
	}

    }

}
