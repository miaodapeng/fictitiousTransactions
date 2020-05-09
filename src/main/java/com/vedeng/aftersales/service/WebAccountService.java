package com.vedeng.aftersales.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.aftersales.model.BDTraderCertificate;
import com.vedeng.authorization.model.User;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.trader.model.WebAccount;
import com.vedeng.trader.model.vo.WebAccountVo;

public interface WebAccountService extends BaseService {

	/**
	 * <b>Description:</b><br> 注册用户信息
	 * @param webAccount
	 * @param used 1售后 2销售
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月11日 下午3:16:21
	 */
	Map<String, Object> getWebAccount(WebAccount webAccount,Integer used);

	/**
	 * <b>Description:</b><br> 注册用户列表
	 * @param webAccountVo
	 * @param page
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月11日 下午3:19:17
	 */
	Map<String, Object> getWebAccountListPage(WebAccountVo webAccountVo, Page page, HttpSession session);

	/**
	 * <b>Description:</b><br> 保存编辑/分配注册用户
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月11日 下午6:29:57
	 */
	Integer saveEdit(WebAccountVo webAccountVo, HttpSession session, HttpServletRequest request)throws Exception;
	
	/**
	 * <b>Description:</b><br>
	 * 
	 *
	 * @param :[webAccount]
	 * @return :java.util.List<com.vedeng.trader.model.WebAccount>
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2019/5/20 3:49 PM
	 */
	List<WebAccount> getWebAccountByTraderContactId(WebAccount webAccount);

    /**
     * 功能描述: 验证分配的用户和选择的客户归属是否同一个人
     * @param: [webAccount]
     * @return: com.vedeng.common.model.ResultInfo
     * @auther: duke.li
     * @date: 2019/7/27 14:15
     */
    ResultInfo vailTraderUser(WebAccountVo webAccountVo);

	/**
	* @Title: getWebAccountIdByMobile
	* @Description: TODO 注册手机号获取注册id
	* @param @param traderContactTelephone
	* @param @return    参数
	* @return Integer    返回类型
	* @author strange
	* @throws
	* @date 2019年7月23日
	*/
	Integer getWebAccountIdByMobile(String traderContactTelephone);

	/**
	 * @Description: 获取归属销售id和客户id
	 * @Param: [webAccount]
	 * @return: com.vedeng.trader.model.WebAccount
	 * @Author: addis
	 * @Date: 2019/8/13
	 */
	WebAccount getWebAccontAuthous(WebAccount webAccount);

	/**
	 * <b>Description:</b>分类获取资质信息<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/3
	 */
	Map<String,Object> getCertificateByCategory(Integer webAccountId);

	/**
	 * <b>Description:</b>转移资质<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/3
	 */
	ResultInfo transferCertificate(Integer erpAccountId, Integer traderId, int type, User user);


	/**
	 * 获取注册账号的归属平台
	 * 若未关联公司且无归属销售，显示注册账号的注册平台；
	 * 若未关联公司但有归属销售，显示归属销售的所属平台；
	 * 若已关联公司，显示关联的公司的归属平台。
	 * @param userId 归属销售
	 * @param traderId 关联企业客户
	 * @param registerPlatform 注册平台
	 * @return 归属平台
	 */
	Integer getBelongPlatformOfAccount(Integer userId, Integer traderId, Integer registerPlatform);


	/**
	 * <b>Description:</b>贝登前台修改客户<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/12
	 */
	ResultInfo updateBDTraderCertificate(BDTraderCertificate bdTraderCertificate);

	/**
	 * <b>Description:</b>贝登前台新增客户资质<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/12
	 */
	ResultInfo addBDTraderCertificate(BDTraderCertificate bdTraderCertificate);
}
