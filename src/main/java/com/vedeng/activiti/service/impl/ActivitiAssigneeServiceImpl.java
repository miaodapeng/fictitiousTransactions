package com.vedeng.activiti.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vedeng.activiti.service.ActivitiAssigneeService;
import com.vedeng.authorization.dao.RoleMapper;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.goods.dao.RCategoryJUserMapper;
import com.vedeng.goods.service.GoodsChannelPriceService;
import com.vedeng.homepage.service.MyHomePageService;
import com.vedeng.order.model.Buyorder;
import com.vedeng.order.model.QuoteorderGoods;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.SaleorderModifyApply;
import com.vedeng.order.model.SaleorderModifyApplyGoods;
import com.vedeng.order.model.vo.BuyorderGoodsVo;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.order.service.BuyorderService;
import com.vedeng.order.service.QuoteService;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.system.model.vo.ParamsConfigVo;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.service.TraderCustomerService;

@Service("activitiAssigneeService")
public class ActivitiAssigneeServiceImpl implements ActivitiAssigneeService {
    
    @Resource
    private WebServiceContext webServiceContext;
    
    @Autowired
    @Qualifier("roleMapper")
    private RoleMapper roleMapper;
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    @Autowired
    @Qualifier("saleorderService")
    private SaleorderService saleorderService;
    @Autowired
    @Qualifier("buyorderService")
    private BuyorderService buyorderService;
    @Autowired
    @Qualifier("quoteService")
    private QuoteService quoteService;
    @Autowired
    @Qualifier("myHomePageService")
    private MyHomePageService myHomePageService;
    @Autowired
    @Qualifier("orgService")
    private OrgService orgService;
    
    @Autowired
    @Qualifier("rCategoryJUserMapper")
    private RCategoryJUserMapper rCategoryJUserMapper;
    
    @Autowired
    @Qualifier("goodsChannelPriceService")
    private GoodsChannelPriceService goodsChannelPriceService;
    
    @Autowired
    @Qualifier("traderCustomerService")
    private TraderCustomerService traderCustomerService;// 客户-交易者
    
    /**
     * 返回Session中的用户Name
     */
    @Override
    public String getSessionUser() {
	 ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	 HttpServletRequest request =  ra.getRequest();
	 User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
	return curr_user.getUsername();
    }
    /**
     * 根据角色名称获取对应角色的人
     */
    @Override
    public List<String> getUserListByRole(String roleName) {
	 ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	 HttpServletRequest request=null ;
	 if(ra!=null){
			request =  ra.getRequest();
	 }
	 User curr_user=null;
	 if(request!=null) {
		 curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		 if(curr_user==null||curr_user.getCompanyId()==null){
		 	curr_user=new User();
		 	curr_user.setCompanyId(1);
		 }
	 }else{
	 	curr_user=new User();
	 	curr_user.setCompanyId(1);
	    curr_user.setUsername("njadmin");
	    curr_user.setUserId(1);
	 }
	 //角色名称，当前登陆人的所属公司
	List<String> userNameList= roleMapper.getUserNameByRoleName(roleName,curr_user.getCompanyId());
	if(userNameList.isEmpty()){
	    userNameList.add(curr_user.getUsername());
	}
	return userNameList;
    }
    /**
     * 根据对人的名称获取他的直接上级
     */
    @Override
    public String getUserParentsUser(String userName) {
	 ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	 HttpServletRequest request =  ra.getRequest();
	 User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
	 //用户名称，当前登陆人所属公司
	 User assigneeObj = userService.getUserParentInfo(userName, curr_user.getCompanyId());
	 if (assigneeObj != null && assigneeObj.getpUsername() != null) {
		return assigneeObj.getpUsername();
	}else{
	    return curr_user.getUsername();
	}
    }
    /**
     * 根据部门orgId和对应的职位等级获取对应的人名字的集合
     */
    public List<String> getUserByLevel(Integer orgId,Integer level){
	 ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	 HttpServletRequest request =  ra.getRequest();
	 User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
	List<User> userList = userService.getUserByPositLevel(orgId, level);
	List<String> userNameList= new ArrayList<>();
	if(!userList.isEmpty()){
	    for(User u :userList){
		userNameList.add(u.getUsername());
	    }
	}
	if(userNameList.isEmpty()){
	    userNameList.add(curr_user.getUsername());
	}
	return userNameList;
    }
    /**
     * 根据分类ID获取对应的分类归属人的名字集合
     */
    @Override
    public List<String> getUserByCategory(Integer categoryId){
	ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	HttpServletRequest request =  ra.getRequest();
	User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
	String categoryUsers=rCategoryJUserMapper.getUserByCategoryNm(categoryId, curr_user.getCompanyId());
	List<String> result = new ArrayList<>();
	if(categoryUsers!=null){
	     result = Arrays.asList(categoryUsers.split(","));
	     return result;
	}else{
	    ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
	    paramsConfigVo.setCompanyId(curr_user.getCompanyId());
	    paramsConfigVo.setParamsKey(107);
	    ParamsConfigVo quote = myHomePageService.getParamsConfigVoByParamsKey(paramsConfigVo);
	    User defaultUser = userService.getUserById(Integer.parseInt(quote.getParamsValue()));
	    result.add(defaultUser.getUsername());
	    return result;
	}
    }
    /**
     * 根据ID获取对应单的产品分类归属人的名字集合
     * type=1 订单 type=2 报价单 type=3 采购单 type = 4 订单修改
     * userType = 445 产品专员  userType = 609 产品经理  userType = 444 产品主管
     */
    @Override
    public List<String> getUserByOrderId(Integer orderId, Integer type, Integer userType) {
	ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	HttpServletRequest request =  ra.getRequest();
	User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
	List<String> users = new ArrayList<>();
	List<Integer> categoryList = new ArrayList<>();
	if(type == 1){
	    //订单
	     Saleorder saleorder=new Saleorder();
	     saleorder.setSaleorderId(orderId);
	     saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
	     List<SaleorderGoods> SaleorderGoodsList = saleorderService.getSaleorderGoodsById(saleorder);
	     // 根据客户ID查询客户信息
	     TraderCustomerVo customer = traderCustomerService.getCustomerBussinessInfo(saleorder.getTraderId());
	     SaleorderGoodsList = goodsChannelPriceService.getSaleChannelPriceList(saleorder.getSalesAreaId(), saleorder.getCustomerNature(),customer.getOwnership(), SaleorderGoodsList);
	     if(!SaleorderGoodsList.isEmpty()){
			 for(SaleorderGoods s:SaleorderGoodsList){
			     if(s.getIsDelete() == 0){
				 BigDecimal settlementPrice= saleorderService.getSaleorderGoodsSettlementPrice(s.getGoodsId(),curr_user.getCompanyId());
				 //区域管制，货期大于核价货期，直发，报价小于结算价,参考成本为0
				 if(s.getAreaControl().equals(1) || !s.getDeliveryCycle().equals(s.getChannelDeliveryCycle()) || s.getDeliveryDirect().equals(1) ||  (null != settlementPrice && settlementPrice.compareTo(s.getPrice())==1)  || s.getReferenceCostPrice().compareTo(BigDecimal.ZERO)==0){
				     if(null != s.getGoods().getCategoryId()){
					 categoryList.add(s.getGoods().getCategoryId());
				     }
				 }
			     }
			     
			 }
	     }
	    
	}else if(type == 2){
	    //报价单
	    Map<String, Object> quoteorderGoodsInfo= quoteService.getQuoteGoodsByQuoteId(orderId, curr_user.getCompanyId(),request.getSession(),null,null);
	    List<QuoteorderGoods> quoteList= (List<QuoteorderGoods>) quoteorderGoodsInfo.get("quoteGoodsList");
	    if(!quoteList.isEmpty()){
			for(QuoteorderGoods qg:quoteList){
			    if(qg.getIsDelete() == 0){
			    categoryList.add(qg.getCategoryId());
			    }
			}
	    }
		
	}else if(type == 3){
	    //采购单
	    Buyorder buyorder = new Buyorder();
	    buyorder.setBuyorderId(orderId);
	    BuyorderVo buyorderInfo= buyorderService.getBuyorderVoDetail(buyorder, curr_user);
	    if(!buyorderInfo.getBuyorderGoodsVoList().isEmpty()){
			for(BuyorderGoodsVo bg:buyorderInfo.getBuyorderGoodsVoList()){
			    if(bg.getIsDelete() == 0){
			    categoryList.add(bg.getCategoryId());
			    }
			}
	    }
	    
	}else if(type == 4){
	  //订单
	     Saleorder saleorder=new Saleorder();
	     saleorder.setSaleorderId(orderId);
	     saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
	     List<SaleorderGoods> SaleorderGoodsList = saleorderService.getSaleorderGoodsById(saleorder);
	     if(!SaleorderGoodsList.isEmpty()){
			 for(SaleorderGoods s:SaleorderGoodsList){
			     if(s.getIsDelete() == 0){
				     if(null != s.getGoods().getCategoryId()){
					 categoryList.add(s.getGoods().getCategoryId());
				     }
			     }
			     
			 }
	     }
	   
	}
	//如果分类不为空，去查询分类对应归属
		List<User> userList = new ArrayList<>();
		if(!categoryList.isEmpty()) {
			 userList=rCategoryJUserMapper.getTypeUserByCategoryIds(categoryList,curr_user.getCompanyId(), userType);
			 //分类去重
			 HashSet h = new HashSet(categoryList);   
			 categoryList.clear();   
			 categoryList.addAll(h);
			//如果有分类没有归属
			 if(userList.size() != categoryList.size()){
			     	    ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
				    paramsConfigVo.setCompanyId(curr_user.getCompanyId());
				    paramsConfigVo.setParamsKey(107);
				    ParamsConfigVo quote = myHomePageService.getParamsConfigVoByParamsKey(paramsConfigVo);
				    User defaultUser = userService.getUserById(Integer.parseInt(quote.getParamsValue()));
				    defaultUser.setOwners(defaultUser.getUsername());
				    userList.add(defaultUser);
			 }
		}
		if(!userList.isEmpty()){
		    for(User u:userList){
			if(null != u.getOwners()){
			    List<String> ownerList = Arrays.asList(u.getOwners().split(",")); 
			     users.addAll(ownerList);
			}
		    }
		}
		users = new ArrayList(new HashSet(users));
	if(users.isEmpty()){
	    ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
	    paramsConfigVo.setCompanyId(curr_user.getCompanyId());
	    paramsConfigVo.setParamsKey(107);
	    ParamsConfigVo quote = myHomePageService.getParamsConfigVoByParamsKey(paramsConfigVo);
	    User defaultUser = userService.getUserById(Integer.parseInt(quote.getParamsValue()));
	    users.add(defaultUser.getUsername());
	    return users;
	}else{
	    return users;
	}
	
    }
    
    /**
     * 获取售后负责人
     */
    @Override
    public String getAfterSaleUser(Integer orgId){
	 ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	 HttpServletRequest request =  ra.getRequest();
	 User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
	 ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
	 paramsConfigVo.setCompanyId(curr_user.getCompanyId());
	 paramsConfigVo.setParamsKey(109);
	 List<ParamsConfigVo> params = myHomePageService.getParamsConfigVoList(paramsConfigVo);
	 List<Organization> organizationList= orgService.getParentOrgList(orgId);
	 for(ParamsConfigVo pcv:params){
	     for(Organization o:organizationList){
		 if(pcv.getTitle().equals(o.getOrgId().toString())){
		     return pcv.getParamsValue();
		 }
	     }
	 }
	    ParamsConfigVo paramsConfigVoInfo = new ParamsConfigVo();
	    paramsConfigVoInfo.setCompanyId(curr_user.getCompanyId());
	    paramsConfigVoInfo.setParamsKey(108);
	    ParamsConfigVo paramsInfo = myHomePageService.getParamsConfigVoByParamsKey(paramsConfigVoInfo);
	    return paramsInfo.getParamsValue();
    }
    /**
     * 根据订单修改信息返回对应的采购人员
     */
    public List<String> getBuyorderUserIdBySMA(SaleorderModifyApply saleorderModifyApply){
    	//销售订单产品id集合
    	List<Integer> saleorderGoodsIds = new ArrayList<>();
    	//修改的商品信息
    	List<SaleorderModifyApplyGoods> goodsList= saleorderModifyApply.getGoodsList();
    	for(SaleorderModifyApplyGoods smag: goodsList) {
    		saleorderGoodsIds.add(smag.getSaleorderGoodsId());
    	}
    	//根据销售订单产品id集合获取对应的采购归属人id
    List<Integer> buyorderUserIds= buyorderService.getBuyorderUserBySaleorderGoodsIds(saleorderGoodsIds);
    List<String> buyorderUserNames = new ArrayList<>();
    if(null != buyorderUserIds && !buyorderUserIds.isEmpty()) {
    		List<User> buyorderUsers= userService.getUserByUserIds(buyorderUserIds);
    		for(User u:buyorderUsers) {
    			buyorderUserNames.add(u.getUsername());
    		}
    }
    //如果找不到对应的采购归属人，返回默认的归属人
    if(buyorderUserNames.isEmpty()) {
    		ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    		HttpServletRequest request =  ra.getRequest();
    		User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
    		ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
    	    paramsConfigVo.setCompanyId(curr_user.getCompanyId());
    	    paramsConfigVo.setParamsKey(107);
    	    ParamsConfigVo quote = myHomePageService.getParamsConfigVoByParamsKey(paramsConfigVo);
    	    User defaultUser = userService.getUserById(Integer.parseInt(quote.getParamsValue()));
    	    buyorderUserNames.add(defaultUser.getUsername());
    	    return buyorderUserNames;
    }else {
	    	return buyorderUserNames;
    }
    }
    
}
