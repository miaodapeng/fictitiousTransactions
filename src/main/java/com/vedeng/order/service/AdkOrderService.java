package com.vedeng.order.service;

import java.util.List;

import com.vedeng.authorization.model.User;
import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.BaseService;
import com.vedeng.order.model.adk.AdkOrder;
import com.vedeng.order.model.adk.TAdkGoods;
import com.vedeng.order.model.adk.TAdkSupplier;

public interface AdkOrderService extends BaseService {

	void importProducts(List<TAdkGoods> list, List<String> skuList, User user);

	void importSuppliers(List<TAdkSupplier> list, User user);

	void remoteAdd(AdkOrder list, ResultInfo<?> result) throws ShowErrorMsgException;
}
