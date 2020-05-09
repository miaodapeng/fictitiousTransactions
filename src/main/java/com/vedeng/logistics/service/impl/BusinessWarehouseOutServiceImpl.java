package com.vedeng.logistics.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.CompanyMapper;
import com.vedeng.authorization.model.Company;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.logistics.model.StorageRoom;
import com.vedeng.logistics.model.Warehouse;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.logistics.model.WarehousePicking;
import com.vedeng.logistics.service.BusinessWarehouseOutService;
import com.vedeng.logistics.service.WarehouseOutService;
import com.vedeng.logistics.service.WarehousePickService;
import com.vedeng.logistics.service.WarehousesService;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.system.service.RegionService;

@Service("businessWarehouseOutService")
public class BusinessWarehouseOutServiceImpl extends BaseServiceimpl implements BusinessWarehouseOutService {

	
}
