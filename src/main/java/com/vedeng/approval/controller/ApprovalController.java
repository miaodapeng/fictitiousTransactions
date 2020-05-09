package com.vedeng.approval.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sun.org.apache.xpath.internal.operations.And;
import com.vedeng.approval.model.ApprovalEntity;
import com.vedeng.approval.model.ApprovalRecordEntity;
import com.vedeng.approval.service.ApprovalService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.system.service.UserService;

/**
 * <b>Description:</b><br>15市批准公示
 * @author hugh.yu
 * @Note
 * <b>ProjectName:</b> erp.vedeng.com
 * <br><b>PackageName:</b> com.vedeng.approval.controller
 * <br><b>ClassName:</b> ApprovalController
 * <br><b>Date:</b> 2019年1月16日 下午7:20:12
 */
@Controller
@RequestMapping("/approval/approval")
public class ApprovalController extends BaseController {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private UserService userService;

    /**
     * <b>Description:</b>批准公示列表
     * 
     * @param request
     * @param approvalEntity
     * @param pageNo
     * @param pageSize
     * @param session
     * @return ModelAndView
     * @Note <b>Author：</b>hugh.yu <b>Date:</b> 2019年1月4日 上午9:45:05
     */
    @ResponseBody
    @RequestMapping(value = "/index")
    public ModelAndView queryListPage(HttpServletRequest request, ApprovalEntity approvalEntity,
            @RequestParam(required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(required = false) Integer pageSize, HttpSession session) {

        ModelAndView mv = new ModelAndView("approval/approval_index");
        Page page = getPageTag(request, pageNo, pageSize);
        // 如果isInclude为空或者为2，查出收录的数据
        if (approvalEntity.getIsInclude() != null && approvalEntity.getIsInclude() == 1) {
            approvalEntity.setIsInclude(1);
        }
        else {
            approvalEntity.setIsInclude(2);
        }
        List<ApprovalEntity> approvalList = approvalService.queryListPage(approvalEntity, page);

        if (approvalEntity.getIsInclude() == 2) { // 是否收录 (1：不收录，2：收录)
            mv.addObject("isLimit", 0);
        }
        else {
            mv.addObject("isLimit", 1); // isLimit 表示当查询不再收录的数据时，不允许左下角的"不再收录"点击
        }

        mv.addObject("approvalList", approvalList);
        mv.addObject("page", page);
        return mv;
    }

    /**
     * <b>Description:根据Id查询一条信息 (主表数据 + 多条子表数据)</b>
     * 
     * @param approvalId
     * @return ModelAndView
     * @Note <b>Author：</b>hugh.yu <b>Date:</b> 2019年1月4日 下午3:42:07
     */
    @ResponseBody
    @RequestMapping(value = "/approval_info")
    public ModelAndView queryByApprovalId(HttpServletRequest request, int approvalId,
            @RequestParam(required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(required = false) Integer pageSize, HttpSession session) {

        ModelAndView mv = new ModelAndView("approval/approval_info");

        ApprovalEntity approvalEntity = approvalService.queryByApprovalId(approvalId);

        Page page = getPageTag(request, pageNo, pageSize);
        List<ApprovalRecordEntity> approvalRecordList = approvalService.queryZiBiaolistpage(approvalId, page);

        if (page.getTotalRecord() <= 10) {
            mv.addObject("isPage", 0); // 底下要带个 mv.addObject("page", page);
        }
        else {
            mv.addObject("isPage", 1);
        }

        for (ApprovalRecordEntity aEntity : approvalRecordList) {
            aEntity.setCreateName(userService.getUserById(aEntity.getCreator()).getUsername());
            aEntity.setModTime(DateUtil.convertString(Long.parseLong(aEntity.getModTime()), DateUtil.TIME_FORMAT)); // 时间转换
        }
        approvalEntity.setApprovalRecordList(approvalRecordList);

        mv.addObject("approvalEntity", approvalEntity);
        mv.addObject("page", page);
        mv.addObject("approvalRecordList", approvalRecordList);
        return mv;
    }

    /**
     * <b>Description:跳转到新增</b>
     * 
     * @return ModelAndView
     * @Note <b>Author：</b>hugh.yu <b>Date:</b> 2019年1月4日 下午3:42:25
     */
    @ResponseBody
    @RequestMapping(value = "/toadd")
    public ModelAndView add() {
        ModelAndView mv = new ModelAndView("approval/approval_add");
        return mv;
    }

    /**
     * <b>Description:新增的页面</b> 由于基础数据已经由爬虫提供入表，所以此处暂时不需要新增功能。
     * 
     * @param approvalEntity
     * @param session
     * @return ModelAndView
     * @Note focusState(1:未关注,2:关注) <b>Author：</b>hugh.yu <b>Date:</b> 2019年1月4日 下午3:42:38
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    @Transactional
    public ModelAndView insertApproval(ApprovalEntity approvalEntity, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (user != null) {
            approvalEntity.setFollowUserId(user.getUserId());
            approvalEntity.setCreator(user.getUserId());
            approvalEntity.setCreateName(user.getUsername());
            approvalEntity.setAddTime(DateUtil.sysTimeMillis() + "");
            approvalEntity.setUpdater(user.getUserId());
            approvalEntity.setUpdateName(user.getUsername());
            approvalEntity.setModTime(DateUtil.sysTimeMillis() + "");
        }
        approvalService.insertApproval(approvalEntity);
        int approvalId = approvalEntity.getApprovalId();

        Optional<List<ApprovalRecordEntity>> approvalRecordList =
                Optional.ofNullable(approvalEntity.getApprovalRecordList());
        if (approvalRecordList.isPresent() && !approvalRecordList.get().isEmpty()) {
            approvalRecordList.get().forEach(app -> {
                app.setApprovalId(approvalId);
                app.setCreator(user.getUserId());
                app.setCreateName(user.getUsername());
                app.setAddTime(DateUtil.sysTimeMillis() + "");
                app.setUpdater(user.getUserId());
                app.setUpdateName(user.getUsername());
                app.setModTime(DateUtil.sysTimeMillis() + "");
            });
            int success = approvalService.addApprovalRecord(approvalRecordList.get());
            if (success != approvalRecordList.get().size()) {
                return fail(mv);
            }
        }
        return success(mv);
    }

    /**
     * <b>Description:跳转到修改，带着主键查出数据,同时传page</b>
     * 
     * @param approvalEntity
     * @return ModelAndView
     * @Note <b>Author：</b>hugh.yu <b>Date:</b> 2019年1月4日 下午3:42:57
     */
    @ResponseBody
    @RequestMapping(value = "/tomodify")
    @Transactional
    public ModelAndView toModify(HttpServletRequest request, ApprovalEntity approvalEntity,
            @RequestParam(required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(required = false) Integer pageSize, HttpSession session) {
        ModelAndView mv = new ModelAndView("approval/approval_modify");

        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER); // 用户
        Page page = getPageTag(request, pageNo, pageSize);

        Integer approvalId = approvalEntity.getApprovalId();
        if (approvalId == null) {
            return fail(mv);
        }
        ApprovalEntity entity = approvalService.queryByApprovalId(approvalId); // 批准公示表数据
        if (entity.getUpdateName() == null) { // 如果没有人操作过,updateName为空，则表示没有 数据

            approvalService.updateApproval(approvalEntity);
        }
        else {
            List<ApprovalRecordEntity> approvalRecordList =
                    approvalService.queryApprovalRecordByApprovalIdQueryListPage(approvalId, page).stream()
                            .filter(s -> s.getRecordContent().length() > 0).collect(Collectors.toList()); // recordContent跟进记录需要有值,过滤掉记录详情为空的数据

            // userId相等表示用户相同，设置isLimit为1，给前台做判断，判断是否展示 编辑、删除 的按钮
            for (int i = 0; i < approvalRecordList.size(); i++) {
                if (user.getUserId().equals(approvalRecordList.get(i).getCreator())) {
                    approvalRecordList.get(i).setIsLimit(1);
                }
            }

            if (page.getTotalRecord() <= 10) {
                mv.addObject("isPage", 0);
            }
            else {
                mv.addObject("isPage", 1);
            }

            entity.setApprovalRecordList(approvalRecordList);

            mv.addObject("approvalEntity", entity);
            mv.addObject("approvalRecordList", approvalRecordList);
            mv.addObject("page", page);
        }
        return mv;
    }

    /**
     * <b>Description:修改的页面</b>
     * 
     * @param approvalEntity
     * @param session
     * @return ModelAndView
     * @Note <b>Author：</b>hugh.yu <b>Date:</b> 2019年1月4日 下午3:43:15
     */
    @ResponseBody
    @RequestMapping(value = "/modify")
    @Transactional
    public ModelAndView updateApproval(ApprovalEntity approvalEntity, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView();

        int approvalId = approvalEntity.getApprovalId();
        if (approvalId == 0) {
            return fail(mv);
        }

        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (user != null) {
            approvalEntity.setUpdater(user.getUserId());
            approvalEntity.setUpdateName(user.getUsername());
            approvalEntity.setModTime(DateUtil.sysTimeMillis() + "");
        }

        // 2019-01-14 修改跟进记录里面的关注没反应 (所有字段都是相似的问题)
        if (approvalEntity.getFocusState() == null) {
            approvalEntity.setFocusState(1);
        }

        if (approvalEntity.getOrganizeName().isEmpty()) {
            approvalEntity.setOrganizeName("");
        }
        if (approvalEntity.getOrganizeType().isEmpty()) {
            approvalEntity.setOrganizeType("");
        }
        if (approvalEntity.getOrganizeSubject().isEmpty()) {
            approvalEntity.setOrganizeSubject("");
        }
        if (approvalEntity.getContactAddress().isEmpty()) {
            approvalEntity.setContactAddress("");
        }
        if (approvalEntity.getContactPhone().isEmpty()) {
            approvalEntity.setContactPhone("");
        }

        boolean success = approvalService.updateApproval(approvalEntity);// 修改主表数据
        if (!success) {
            return fail(mv);
        }
        // 先删
        // approvalService.deleteApprovalRecord(approvalId);

        List<ApprovalRecordEntity> approvalRecordList = approvalEntity.getApprovalRecordList();

        approvalRecordList.forEach(app -> {
            app.setApprovalId(approvalId);
            app.setCreator(user.getUserId());
            app.setCreateName(user.getUsername());
            app.setAddTime(DateUtil.sysTimeMillis() + "");
            app.setUpdater(user.getUserId());
            app.setUpdateName(user.getUsername());
            app.setModTime(DateUtil.sysTimeMillis() + "");
        });

        for (int i = 0; i < approvalRecordList.size(); i++) {
            if (!approvalRecordList.get(i).getRecordContent().isEmpty()) { // 如果跟进记录的内容不为空，则正常新增。

                // 增加
                approvalService.addApprovalRecord(approvalRecordList);
            }
        }
        mv.addObject("url", "./tomodify.do?approvalId=" + approvalId);
        return success(mv);
    }

    /**
     * <b>Description:根据id删除一条主表</b>
     * 
     * @param approvalEntity
     * @param session
     * @return ResultInfo<?>
     * @Note <b>Author：</b>hugh.yu <b>Date:</b> 2019年1月4日 下午3:43:25
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/delete")
    public ResultInfo<?> deleteByApprovalId(ApprovalEntity approvalEntity, HttpServletRequest request) {
        if (approvalEntity == null) {
            return new ResultInfo<>(-1, "参数为空");
        }
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (user != null) {
            approvalEntity.setUpdater(user.getUserId());
            approvalEntity.setUpdateName(user.getUsername());
            approvalEntity.setModTime(DateUtil.sysTimeMillis() + "");
        }
        approvalEntity.setIsDelete(0);
        ResultInfo<?> result = null;
        int success = approvalService.deleteByApprovalId(approvalEntity);
        if (success > 0) {
            result = new ResultInfo(0, "删除成功");
        }
        else {
            result = new ResultInfo(-1, "删除失败");
        }
        return result;
    }

    /**
     * <b>Description:批量删除 (现在改成了批量不再收录)</b>
     * 
     * @param request
     * @param approvalIds
     * @return ResultInfo<?>
     * @Note <b>Author：</b>hugh.yu <b>Date:</b> 2019年1月4日 下午3:43:47
     */
    @ResponseBody
    @RequestMapping(value = "/delApprovalBatch")
    public ResultInfo<?> delApprovalBatch(HttpServletRequest request, String approvalIds) {

        List<Integer> idList = new ArrayList<>();
        String[] params = approvalIds.split("_");
        for (int i = 0; i < params.length; i++) {
            Integer approvalId = Integer.parseInt(params[i]);
            idList.add(approvalId);
        }
        ResultInfo<?> result = approvalService.delApprovalBatch(idList);

        return result;
    }

    /**
     * <b>Description:关注状态 (手动修改状态值)</b>
     * 
     * @param request
     * @return ResultInfo<?>
     * @Note <b>Author：</b>hugh.yu <b>Date:</b> 2019年1月4日 下午3:43:58
     */
    @ResponseBody
    @RequestMapping("/updateFocusState")
    public ResultInfo<?> updateFocusState(HttpServletRequest request) {
        ResultInfo<?> result = new ResultInfo<>();

        String focusState = request.getParameter("focusState");
        String approvalId = request.getParameter("approvalId");
        if (focusState != null && approvalId != null) {
            ApprovalEntity approvalEntity = new ApprovalEntity();
            approvalEntity.setFocusState(Integer.parseInt(focusState));
            approvalEntity.setApprovalId(Integer.valueOf(approvalId));

            User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
            if (user != null) {
                approvalEntity.setUpdater(user.getUserId());
                approvalEntity.setUpdateName(user.getUsername());
                approvalEntity.setModTime(DateUtil.sysTimeMillis() + "");
            }
            try {
                result = approvalService.updateFocusState(approvalEntity);
            }
            catch (Exception e) {
                logger.error("updateFocusState:", e);
            }
        }
        return result;
    }

    /**
     * <b>Description:收录状态 (手动修改状态值)</b>
     * 
     * @param request
     * @return ResultInfo<?>
     * @Note <b>Author：</b>hugh.yu <b>Date:</b> 2019年1月4日 下午3:43:58
     */
    @ResponseBody
    @RequestMapping("/updateIsInclude")
    public ResultInfo<?> updateIsInclude(HttpServletRequest request) {
        ResultInfo<?> result = new ResultInfo<>();

        String isInclude = request.getParameter("isInclude"); // 收录状态(1：不收录、2：收录)
        String approvalId = request.getParameter("approvalId");
        if (isInclude != null && approvalId != null) {
            ApprovalEntity approvalEntity = new ApprovalEntity();
            approvalEntity.setIsInclude(Integer.parseInt(isInclude));
            approvalEntity.setApprovalId(Integer.valueOf(approvalId));

            User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
            if (user != null) {
                approvalEntity.setUpdater(user.getUserId());
                approvalEntity.setUpdateName(user.getUsername());
                approvalEntity.setModTime(DateUtil.sysTimeMillis() + "");
            }
            try {
                result = approvalService.updateIsInclude(approvalEntity);
            }
            catch (Exception e) {
                logger.error("updateIsInclude:", e);
            }
        }
        return result;
    }

    /**
     * <b>Description:弹框--跳转到修改</b>
     * 
     * @param approvalEntity
     * @return ModelAndView
     * @Note <b>Author：</b>hugh.yu <b>Date:</b> 2019年1月4日 下午3:42:57
     */
    @ResponseBody
    @RequestMapping(value = "/tomodifyrecord")
    public ModelAndView tomodifyrecord(HttpServletRequest request, ApprovalRecordEntity approvalRecordEntity,
            @RequestParam(required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(required = false) Integer pageSize, HttpSession session) {
        ModelAndView mv = new ModelAndView("approval/approval_modify");

        Page page = getPageTag(request, pageNo, pageSize);

        Integer recordId = approvalRecordEntity.getRecordId();
        if (recordId == null) {
            return fail(mv);
        }
        List<ApprovalRecordEntity> approvalRecordList = approvalService.querylistpage(recordId, page); // 小分页

        for (ApprovalRecordEntity aEntity : approvalRecordList) {
            aEntity.setCreateName(userService.getUserById(aEntity.getCreator()).getUsername());
            aEntity.setModTime(DateUtil.convertString(Long.parseLong(aEntity.getModTime()), DateUtil.TIME_FORMAT));

            mv.addObject("approvalRecordList", approvalRecordList);
            mv.addObject("page", page);
        }
        // 如果数据在10条以内，则不出现分页器。
        if (page.getTotalRecord() <= 10) {
            mv.addObject("isPage", 0);
        }
        else {
            mv.addObject("isPage", 1);
        }

        return mv;
    }

    /**
     * <b>Description:编辑 弹框 返回json</b>
     * 
     * @param request
     * @param approvalRecordEntity
     * @return ResultInfo<?>
     * @Note <b>Author：</b>hugh.yu <b>Date:</b> 2019年1月7日 下午3:29:58
     */
    @ResponseBody
    @RequestMapping(value = "/modifyrecord")
    @Transactional
    public ResultInfo<?> updateApprovalRecord(HttpServletRequest request, ApprovalRecordEntity approvalRecordEntity) {

        int approvalId = approvalRecordEntity.getApprovalId();
        System.out.println(approvalId);

        ResultInfo<ApprovalRecordEntity> resultInfo = new ResultInfo<ApprovalRecordEntity>();

        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        if (user != null) {
            approvalRecordEntity.setUpdater(user.getUserId());
            approvalRecordEntity.setModTime(DateUtil.gainNowDate() + "");

            if (approvalRecordEntity.getRecordContent().isEmpty()) {
                resultInfo.setCode(-1);
                resultInfo.setMessage("跟进记录不能为空！"); // 此处不生效,在approval_modify.js生效
                return resultInfo;
            }
            if (approvalRecordEntity.getRecordContent().length() > 500) {
                resultInfo.setCode(-2);
                resultInfo.setMessage("跟进记录最大输入不能超过500字！"); // 此处不生效,在approval_modify.js生效
                return resultInfo;
            }

            // Pattern pattern = Pattern.compile("([0-9a-zA-Z])\1{50}");
            // Matcher matcher = pattern.matcher(approvalRecordEntity.getRecordContent());
            // if (matcher.find()) {
            // resultInfo.setCode(-3);
            // resultInfo.setMessage("请勿重复输入相同的字符！");
            // return resultInfo;
            // }

            /*
             * if (approvalRecordEntity.getRecordContent().matches("[a-zA-Z]+") &&
             * approvalRecordEntity.getRecordContent().length()>48) { resultInfo.setCode(-3);
             * resultInfo.setMessage("请勿重复输入相同的字母！"); return resultInfo; }
             */

            boolean bol = approvalService.updateApprovalRecord(approvalRecordEntity);
            if (bol) {
                List<ApprovalRecordEntity> approvalRecordList =
                        approvalService.queryApprovalRecord(approvalId).stream()
                                .filter(s -> s.getRecordContent().length() > 0).collect(Collectors.toList()); // 无参查子表(现在需要入参)
                                                                                                              // (过滤掉记录详情为空的数据)

                for (ApprovalRecordEntity aEntity : approvalRecordList) {
                    aEntity.setCreateName(userService.getUserById(aEntity.getCreator()).getUsername());
                    aEntity.setModTime(DateUtil.convertString(Long.parseLong(aEntity.getModTime()),
                            DateUtil.TIME_FORMAT));
                }

                for (int i = 0; i < approvalRecordList.size(); i++) {
                    if (user.getUserId().equals(approvalRecordList.get(i).getCreator())) { // userId相等表示用户相同
                        approvalRecordList.get(i).setIsLimit(1);
                    }
                }

                if (approvalRecordList != null) {
                    resultInfo.setCode(0);
                    resultInfo.setMessage("操作成功");
                    resultInfo.setListData(approvalRecordList);
                    resultInfo.setParam(approvalRecordList);
                }

                return resultInfo;
            }
        }
        return resultInfo;
    }

    /**
     * <b>Description:根据recordId删除一条子表,逻辑删</b>
     * 
     * @param approvalRecordEntity
     * @param session
     * @return ResultInfo<?>
     * @Note <b>Author：</b>hugh.yu <b>Date:</b> 2019年1月4日 下午3:43:25
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/deleterecord")
    public ResultInfo<?> deleterecord(ApprovalRecordEntity approvalRecordEntity, HttpServletRequest request) {
        if (approvalRecordEntity == null) {
            return new ResultInfo<>(-1, "参数为空");
        }
        ResultInfo<?> result = null;
        // 之前是批量删除
        // boolean bol = approvalService.deleterecord(approvalRecordEntity.getRecordId());
        // 现在是批量不再关注
        boolean bol = approvalService.deleterecord(approvalRecordEntity.getRecordId());

        if (bol) {
            result = new ResultInfo(0, "删除成功");
        }
        else {
            result = new ResultInfo(-1, "删除失败");
        }
        return result;
    }

    
    
}
