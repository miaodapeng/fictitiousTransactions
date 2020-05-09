package com.vedeng.phoneticWriting.controller;

import com.vedeng.authorization.model.User;
import com.vedeng.call.service.CallService;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.phoneticWriting.model.Comment;
import com.vedeng.phoneticWriting.model.ModificationRecord;
import com.vedeng.phoneticWriting.model.PhoneticWriting;
import com.vedeng.phoneticWriting.model.vo.ModificationRecordVo;
import com.vedeng.phoneticWriting.service.PhoneticTranscriptionService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.model.CommunicateRecord;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @Description: 语音转文字功能
* @Param:  
* @return:  
* @Author: scott.zhu 
* @Date: 2019/4/25 
*/
@Controller
@RequestMapping("phoneticTranscription/phonetic")
public class PhoneticTranscriptionController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(PhoneticTranscriptionController.class);

    @Autowired
    @Qualifier("phoneticTranscriptionService")
    private PhoneticTranscriptionService phoneticTranscriptionService;

	@Autowired
	@Qualifier("callService")
	private CallService callService;

    @Resource
    private UserService userService;

	/**
	 *
	 * <b>Description:</b><br>查看录音文件
	 * @param communicateRecord
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2019年4月24日 上午10:24:08
	 */
	@ResponseBody
	@RequestMapping(value = "/viewContent")
	public ModelAndView viewContent(CommunicateRecord communicateRecord) {
		ModelAndView mv = new ModelAndView();

		//查询所有此录音下的规则并处理
        try {
            List<ModificationRecord> mrUseList = phoneticTranscriptionService.processingRecords(communicateRecord);
            mv.addObject("mrList", mrUseList);
        } catch (Exception e) {
            LOG.error("录音文字替换处理失败",e);
        }
		CommunicateRecord cr = callService.getCommunicateRecordById(communicateRecord);
		mv.addObject("cr", cr);
        //查询录音数据
		PhoneticWriting phoneticWriting = new PhoneticWriting();
		phoneticWriting = callService.getPhoneticWriting(communicateRecord);
		mv.addObject("phoneticWriting", phoneticWriting);

		//查询替换的数据list
       /* List<ModificationRecord> mrList = new ArrayList<>();
        mrList = callService.getMrList(communicateRecord);
        mv.addObject("mrList", mrList);*/

		//查询点评
		List<Comment> comList = new ArrayList<>();
		comList = callService.getComList(communicateRecord);
		mv.addObject("comList", comList);
		if(CollectionUtils.isNotEmpty(comList)){
			mv.addObject("num", comList.size());
		}else {
			mv.addObject("num", 0);
		}
        mv.addObject("communicateRecord", communicateRecord);
		mv.setViewName("phoneticTranscription/viewContent");
		return mv;
	}

    /**
    * @Description: 新增点评并返回
    * @Param: [request, session]
    * @return: com.vedeng.common.model.ResultInfo
    * @Author: scott.zhu
    * @Date: 2019/4/25
    */
	@ResponseBody
	@RequestMapping(value = "/addComments")
	public ResultInfo addComments(HttpServletRequest request, HttpSession session,Comment comment) {
		ResultInfo resultInfo = new ResultInfo();
		User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);

		//新增点评
		int num = phoneticTranscriptionService.addComments(comment,curr_user);
		if(num > 0 ){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}else {
			resultInfo.setCode(-1);
			resultInfo.setMessage("操作失败");
		}
		return resultInfo;
	}

	/**
	* @Description: 新增修正、替换词、返回替换日志 
	* @Param: [request, session, modificationRecord] 
	* @return: com.vedeng.common.model.ResultInfo 
	* @Author: scott.zhu 
	* @Date: 2019/4/25 
	*/
	@ResponseBody
	@RequestMapping(value = "/addModificationRecord")
	public ResultInfo addModificationRecord(HttpServletRequest request, HttpSession session,ModificationRecord modificationRecord) {
		ResultInfo resultInfo = new ResultInfo();
		User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
		if(modificationRecord.getIsDel()!=null && modificationRecord.getIsDel() == 1){
		}else {
			if (phoneticTranscriptionService.getTheSameRecordById(modificationRecord)){
				return new ResultInfo<>(-1, "该内容已存在修正记录，无法重复提交！");
			}
		}
		//修正处理
		try {
			int num = phoneticTranscriptionService.updateModificationRecord(modificationRecord,curr_user);
			if(num >= 0 ){
				resultInfo.setCode(0);
				resultInfo.setMessage("操作成功");
			}else {
				resultInfo.setCode(-1);
				resultInfo.setMessage("操作失败");
			}
		}catch (Exception e){
			resultInfo.setCode(-1);
			resultInfo.setMessage("操作失败");
		}
		return resultInfo;
	}
	/**
	* @Description: 修改为全局的修改规则
	* @Param: [request, session, modificationRecord]
	* @return: com.vedeng.common.model.ResultInfo
	* @Author: scott.zhu
	* @Date: 2019/4/28
	*/
    @ResponseBody
    @RequestMapping(value = "/updateAllModificationRecord")
    public ResultInfo updateAllModificationRecord(HttpServletRequest request, HttpSession session,ModificationRecord modificationRecord) {
        ResultInfo resultInfo = new ResultInfo();
        User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
		if (phoneticTranscriptionService.getTheSameRecordByNotId(modificationRecord)){
			return new ResultInfo<>(-1, "修正前内容已存在修正记录，无法重复提交！");
		}
        //修正处理
        try {
            int num = phoneticTranscriptionService.updateAllModificationRecord(modificationRecord,curr_user);
            if(num > 0 ){
                resultInfo.setCode(0);
                resultInfo.setMessage("操作成功");
            }else {
                resultInfo.setCode(-1);
                resultInfo.setMessage("操作失败");
            }
        }catch (Exception e){
            resultInfo.setCode(-1);
            resultInfo.setMessage("操作失败");
        }
        return resultInfo;
    }

	/**
	 * 功能描述: 修正列表
	 * @param: [request, session]
	 * @return: org.springframework.web.servlet.ModelAndView
	 * @auther: barry.xu
	 * @date: 2019/4/28 9:58
	 */
	@ResponseBody
	@RequestMapping(value = "/viewRecordList")
	public ModelAndView modificationRecordList(HttpServletRequest request,
											   @RequestParam(required = false, defaultValue = "1") Integer pageNo, // required
											   // false:可不传入pageNo这个参数，true必须传入，defaultValue默认值，若无默认值，使用Page类中的默认值
											   @RequestParam(required = false, defaultValue = "10") Integer pageSize,
											   ModificationRecordVo modificationRecordVo){
		ModelAndView mv = new ModelAndView("phoneticTranscription/modificationRecord");

		try {

			Page page = getPageTag(request, pageNo, pageSize);
			Map<String,Object> map = new HashMap<>();
			map.put("page", page);
			//针对参数进行处理
			if (null != modificationRecordVo){
				if (EmptyUtils.isNotBlank(modificationRecordVo.getAddTimeStartStr())){
					modificationRecordVo.setAddTimeStart(DateUtil.convertLong(modificationRecordVo.getAddTimeStartStr()+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
				}

				if (EmptyUtils.isNotBlank(modificationRecordVo.getAddTimeEndStr())){
					modificationRecordVo.setAddTimeEnd(DateUtil.convertLong(modificationRecordVo.getAddTimeEndStr()+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
				}

				if (EmptyUtils.isNotBlank(modificationRecordVo.getModTimeEndStr())){
					modificationRecordVo.setModTimeEnd(DateUtil.convertLong(modificationRecordVo.getModTimeEndStr()+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
				}

				if (EmptyUtils.isNotBlank(modificationRecordVo.getModTimeStartStr())){
					modificationRecordVo.setModTimeStart(DateUtil.convertLong(modificationRecordVo.getModTimeStartStr()+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
				}

			}

			map.put("mrVo", modificationRecordVo);
			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
			// 查询所有职位类型为310的员工
			List<Integer> positionType = new ArrayList<>();
			positionType.add(SysOptionConstant.ID_310);//销售
			List<User> userList = userService.getMyUserList(user, positionType, false);
			//查询列表
			List<ModificationRecordVo> list = phoneticTranscriptionService.getModificationRecordList(map);

			if (null != list){
				for(ModificationRecordVo mrVo : list){
					User creator = userService.getUserById(mrVo.getCreator());
					User updater = userService.getUserById(mrVo.getUpdater());
					if (null != creator){
						mrVo.setCreatorName(creator.getUsername());
					}
					if (null != updater){
						mrVo.setUpdaterName(updater.getUsername());
					}
					mrVo.setAddTimeStr(DateUtil.convertString(mrVo.getAddTime(),"yyyy-MM-dd HH:mm:ss"));
					mrVo.setModTimeStr(DateUtil.convertString(mrVo.getModTime(),"yyyy-MM-dd HH:mm:ss"));
				}
			}
			mv.addObject("recordList", list);
			mv.addObject("userList", userList);
			mv.addObject("page", page);
			mv.addObject("modificationRecordVo", modificationRecordVo);

		}catch (Exception e){
			LOG.error("查询修正列表错误：" + e);
		}

		return mv;
	}

	/**
	 * 功能描述: 展示新增页面
	 * @param: []
	 * @return: org.springframework.web.servlet.ModelAndView
	 * @auther: barry.xu
	 * @date: 2019/4/29 14:36
	 */
	@ResponseBody
	@RequestMapping(value = "/addRecordFromList")
	public ModelAndView addRecordFromList(){

		return new ModelAndView("phoneticTranscription/addRecord");
	}

	/**
	 * 功能描述: 保存新的数据
	 * @param: [modificationRecord]
	 * @return: com.vedeng.common.model.ResultInfo<?>
	 * @auther: barry.xu
	 * @date: 2019/4/29 14:36
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRecordFromList")
	public ResultInfo<?> saveRecordFromList(HttpSession session, ModificationRecord modificationRecord){
		ResultInfo<?> res = new ResultInfo<>(CommonConstants.SUCCESS_CODE,CommonConstants.SUCCESS_MSG);

		User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);

		try {
			//参数判断
			if (null != modificationRecord){

				if (EmptyUtils.isBlank(modificationRecord.getControversialContent())){
					return new ResultInfo<>(-1, "请输入修正前内容！");
				}

				if (EmptyUtils.isBlank(modificationRecord.getModifyContent())){
					return new ResultInfo<>(-1, "请输入修正后内容");
				}

				if (phoneticTranscriptionService.getTheSameRecord(modificationRecord.getControversialContent())){
					return new ResultInfo<>(-1, "修正前内容已存在修正记录，无法重复提交！");
				}

				//新增记录
				modificationRecord.setAddTime(System.currentTimeMillis());
				modificationRecord.setCreator(curr_user.getUserId());
				modificationRecord.setType(ErpConst.TYPE_1);

				if (!phoneticTranscriptionService.insertNewRecord(modificationRecord)){
					return new ResultInfo<>(CommonConstants.FAIL_CODE, CommonConstants.FAIL_MSG,204);
				}

			}else {
				return new ResultInfo<>(CommonConstants.FAIL_CODE, CommonConstants.FAIL_MSG, 204);
			}

		}catch (Exception e){
			LOG.error("保存修改记录错误：" + e);
		}

		return res;
	}


	/**
	 * 功能描述: 显示数据
	 * @param: [modificationRecord]
	 * @return: com.vedeng.common.model.ResultInfo<?>
	 * @auther: barry.xu
	 * @date: 2019/4/29 14:36
	 */
	@ResponseBody
	@RequestMapping(value = "/showRecord")
	public ModelAndView showRecordFromList(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("phoneticTranscription/editRecord");
		try {

			String id = request.getParameter("id");

			ModificationRecord mr = phoneticTranscriptionService.getRecord(Integer.valueOf(id));

			mv.addObject("mr", mr);

		}catch (Exception e){
			LOG.error("显示争议记录错误：" + e);
		}
		return mv;
	}

	/**
	 * 功能描述: 编辑数据
	 * @param: [modificationRecord]
	 * @return: com.vedeng.common.model.ResultInfo<?>
	 * @auther: barry.xu
	 * @date: 2019/4/29 14:36
	 */
	@ResponseBody
	@RequestMapping(value = "/editRecordFromList")
	public ResultInfo<?> editRecordFromList(HttpSession session, ModificationRecord modificationRecord){
		ResultInfo<?> res = new ResultInfo<>(CommonConstants.SUCCESS_CODE,CommonConstants.SUCCESS_MSG);

		User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);

		try {
			//参数判断
			if (null != modificationRecord){

				if (EmptyUtils.isBlank(modificationRecord.getModifyContent())){
					return new ResultInfo<>(-1, "请输入修正后内容");
				}

				modificationRecord.setModTime(System.currentTimeMillis());
				modificationRecord.setUpdater(curr_user.getUserId());

				if (!phoneticTranscriptionService.updateRecord(modificationRecord)){
					return new ResultInfo<>(CommonConstants.FAIL_CODE, CommonConstants.FAIL_MSG,204);
				}

			}else {
				return new ResultInfo<>(CommonConstants.FAIL_CODE, CommonConstants.FAIL_MSG, 204);
			}

		}catch (Exception e){
			LOG.error("保存修改记录错误：" + e);
			return new ResultInfo<>(CommonConstants.FAIL_CODE, CommonConstants.FAIL_MSG);
		}

		return res;
	}

	/**
	 * 功能描述: 删除记录
	 * @param: [request]
	 * @return: com.vedeng.common.model.ResultInfo<?>
	 * @auther: barry.xu
	 * @date: 2019/4/29 18:08
	 */
	@ResponseBody
	@RequestMapping(value = "/delRecord")
	public ResultInfo<?> delRecord(HttpServletRequest request){
		ResultInfo<?> res = new ResultInfo<>(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_MSG);
		try {

			String id = request.getParameter("id");

			if (!phoneticTranscriptionService.delRecord(Integer.valueOf(id))){
				return new ResultInfo<>(CommonConstants.FAIL_CODE, CommonConstants.FAIL_MSG);
			}
		}catch (Exception e){
			LOG.error("删除修改记录错误：" + e);
			return new ResultInfo<>(CommonConstants.FAIL_CODE, CommonConstants.FAIL_MSG);
		}

		return res;
	}
}
