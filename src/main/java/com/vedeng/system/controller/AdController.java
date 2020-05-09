package com.vedeng.system.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.system.model.Ad;
import com.vedeng.system.model.vo.AdVo;
/**
 * <b>Description:</b><br> 广告管理
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.controller
 * <br><b>ClassName:</b> AdController
 * <br><b>Date:</b> 2018年6月19日 下午2:48:28
 */
import com.vedeng.system.service.AdService;
import com.vedeng.system.service.FtpUtilService;
@Controller
@RequestMapping("/system/ad")
public class AdController extends BaseController {
	
	@Resource
	private AdService adService;
	
	@Autowired
	@Qualifier("ftpUtilService")
	private FtpUtilService ftpUtilService;
	
	/**
	 * <b>Description:</b><br> 获取广告列表
	 * @param request
	 * @param ad
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月19日 下午4:05:36
	 */
	@ResponseBody
    @RequestMapping(value = "/getAdListPage")
	public ModelAndView getAdListPage(HttpServletRequest request,AdVo ad,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(required = false) Integer pageSize){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
		ad.setCompanyId(user.getCompanyId());
		Map<String, Object> map = adService.getAdListPage(ad, page);
		if(map!=null){
			mav.addObject("list", map.get("list"));
			mav.addObject("page", map.get("page"));
			mav.setViewName("/system/ad/index");
			return mav;
		}else{
			return pageNotFound();
		}
	}
	
	/**
	 * <b>Description:</b><br> 新增页面
	 * @param request
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月20日 上午9:06:49
	 */
	@ResponseBody
    @RequestMapping(value = "/toAddPage")
	public ModelAndView toAddPage(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/system/ad/add_ad");
		mav.addObject("domain", domain);
		return mav;
	}
	
	/**
	 * <b>Description:</b><br> 保存新增广告
	 * @param request
	 * @param ad
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月20日 上午11:46:36
	 */
	@ResponseBody
    @RequestMapping(value = "/saveAddAd")
	@SystemControllerLog(operationType = "add",desc = "保存新增广告")
	public ResultInfo<?> saveAddAd(HttpServletRequest request, AdVo ad){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ad.setCompanyId(user.getCompanyId());
		ad.setBannerName("销售首页广告位");
		ad.setAddTime(DateUtil.sysTimeMillis());
		ad.setCreator(user.getUserId());
		ad.setUpdater(user.getUserId());
		ad.setModTime(DateUtil.sysTimeMillis());
		ResultInfo<?> res = adService.saveOrUpdateAd(ad);
		return res;
	}
	
	/**
	 * <b>Description:</b><br> 编辑页面
	 * @param request
	 * @return
	 * @throws IOException 
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月20日 上午9:06:49
	 */
	@ResponseBody
    @RequestMapping(value = "/toEditPage")
	public ModelAndView toEditPage(HttpServletRequest request,Ad advo) throws IOException{
		ModelAndView mav = new ModelAndView("/system/ad/edit_ad");
		mav.addObject("domain", domain);
		Ad ad = adService.getAdDetail(advo);
		mav.addObject("url", ad.getUrl().substring(ad.getUrl().lastIndexOf("/")+1));
		mav.addObject("ad", ad);
		mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(advo)));
		return mav;
	}
	
	/**
	 * <b>Description:</b><br> 保存编辑广告
	 * @param request
	 * @param ad
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月20日 上午11:46:36
	 */
	@ResponseBody
    @RequestMapping(value = "/saveEditAd")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑广告")
	public ResultInfo<?> saveEditAd(HttpServletRequest request, AdVo ad,String beforeParams){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ad.setUpdater(user.getUserId());
		ad.setModTime(DateUtil.sysTimeMillis());
		ResultInfo<?> res = adService.saveOrUpdateAd(ad);
		return res;
	}
	
	/**
	 * <b>Description:</b><br> 保存编辑广告上下架状态
	 * @param request
	 * @param ad
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月20日 下午2:23:58
	 */
	@ResponseBody
    @RequestMapping(value = "/saveAdGrounding")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑广告上下架状态")
	public ResultInfo<?> saveAdGrounding(HttpServletRequest request, AdVo ad){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ad.setUpdater(user.getUserId());
		ad.setModTime(DateUtil.sysTimeMillis());
		ResultInfo<?> res = adService.saveOrUpdateAd(ad);
		return res;
	}
	
	/**
	 * <b>Description:</b><br> ajax异步上传附件操作
	 * @param request
	 * @param response
	 * @param upfile
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月24日 上午9:41:00
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxFileUpload")
	public FileInfo ajaxFileUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("lwfile") MultipartFile lwfile) {
		FileInfo fileInfo = new FileInfo();
		try {
			String path = "/upload/ajax";
			fileInfo = ftpUtilService.uploadFile(lwfile, path,request,"");
			if(fileInfo != null && fileInfo.getCode() == 0){
				 URL url = new URL("http://"+fileInfo.getHttpUrl()+fileInfo.getFilePath()+"/"+fileInfo.getFileName());
			     URLConnection connection = url.openConnection();
			     connection.setDoOutput(true);
			     BufferedImage image = ImageIO.read(connection.getInputStream());  
			     int srcWidth = image.getWidth();      // 源图宽度
			     int srcHeight = image.getHeight();
			     if(srcWidth != 800 || srcHeight != 500){
			    	 fileInfo.setCode(-1);
			    	 fileInfo.setMessage("图片尺寸仅支持800×500");
			     }
			}
		} catch (Exception e) {
			logger.error("ajaxFileUpload:", e);
		}
		return fileInfo;
		
	}
	

}
