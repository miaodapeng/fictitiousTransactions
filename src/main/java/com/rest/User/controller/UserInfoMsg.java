package com.rest.User.controller;

import com.rest.User.controller.model.UserInfo;
import com.rest.User.controller.model.UserMenus;
import com.rest.User.controller.model.UserRoleAndPermission;
import com.rest.traderMsg.controller.TraderMsg;
import com.vedeng.authorization.model.*;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.system.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 对外提供用户信息的Api接口
 * @author wayne.liu
 * @description 
 * @date 2019/6/14 14:57
 */
@RestController
@RequestMapping("/userCenter")
public class UserInfoMsg {

    protected static final Logger LOGGER = LoggerFactory.getLogger(TraderMsg.class);

    @Autowired
    @Qualifier("companyService")
    protected CompanyService companyService;

    @Autowired
    @Qualifier("userService")
    protected UserService userService;

    @Autowired
    @Qualifier("roleService")
    protected RoleService roleService;

    @Autowired
    @Qualifier("actionService")
    protected ActionService actionService;

    @Autowired
    @Qualifier("actiongroupService")
    protected ActiongroupService actiongroupService;

    @Autowired
    @Qualifier("positService")
    protected PositService positService;


    /**
      * 获取登陆公司
      * @author wayne.liu
      * @date  2019/6/14 14:59
      * @param 
      * @return 
      */
    @RequestMapping(value = {"/getLoginCompany"}, method = RequestMethod.GET)
    public ResultInfo getLoginCompany() {

        try {

            Company company = new Company();
            company.setIsEnable(1);
            //返回有效公司
            return new ResultInfo(0, "操作成功",companyService.getCompanyList(company));

        } catch (Exception e) {
            LOGGER.error("Error sendTraderMsg", e);
            return new ResultInfo(-1, "操作失败");
        }
    }

    /**
     * 获取登陆公司
     * @author wayne.liu
     * @date  2019/6/14 14:59
     * @param loginUser 用户
     * @return
     */
    @RequestMapping(value = {"/userLogin"}, method = RequestMethod.POST)
    public ResultInfo userLogin(@RequestBody User loginUser) {

        try {

            User user = userService.login(loginUser.getUsername(),loginUser.getPassword(),loginUser.getCompanyId());
            LOGGER.info("userLogin user==="+loginUser.getUsername());
            try{
            if(user != null){

                List<Position> positions = positService.getPositionByUserId(user.getUserId());
                if(CollectionUtils.isNotEmpty(positions)){
                    //只取第一个职位
                    user.setPositType(positions.get(0).getType());
                    user.setPositLevel(positions.get(0).getLevel());
                }
            }}catch(Exception e){
                LOGGER.error("Error userLogin", e);
            }
            return new ResultInfo(0, "操作成功",user);

        } catch (Exception e) {
            LOGGER.error("Error userLogin", e);
            return new ResultInfo(-1, "操作失败");
        }
    }

    /**
     * 获取用户的角色权限
     * @author wayne.liu
     * @date  2019/6/14 14:59
     * @param loginUser 用户
     * @return
     */
    @RequestMapping(value = {"/getUserRoleAndPermission"}, method = RequestMethod.POST)
    public ResultInfo getUserRoleAndPermission(@RequestBody User loginUser) {

        try {

            List<Role> roles = roleService.getUserRoles(loginUser);
            Set<String> roleSet = new HashSet<>();
            if (CollectionUtils.isNotEmpty(roles)) {
                for (Role r : roles) {
                    roleSet.add(r.getRoleName());
                }
            }

            Set<String> permissionSet = new HashSet<>();

            List<Action> list = actionService.getActionListByUserName(loginUser);
            if (CollectionUtils.isNotEmpty(list)) {
                for (Action ac : list) {
                    if(ac.getModuleName().startsWith("http")){
                        permissionSet.add(ac.getModuleName());
                    }else{
                        StringBuilder sb = new StringBuilder();
                        sb.append("/").append(ac.getModuleName()).append("/").append(ac.getControllerName()).append("/").append(ac.getActionName()).append(".do");
                        permissionSet.add(sb.toString());
                    }
                }
            }

            UserRoleAndPermission user = new UserRoleAndPermission();
            user.setRoleSet(roleSet);
            user.setPermissionSet(permissionSet);

            return new ResultInfo(0, "操作成功",user);

        } catch (Exception e) {
            LOGGER.error("Error getUserRoleAndPermission", e);
            return new ResultInfo(-1, "操作失败");
        }
    }


    /**
     * 获取用户的菜单栏
     * @author wayne.liu
     * @date  2019/6/14 14:59
     * @param loginUser 用户
     * @return
     */
    @RequestMapping(value = {"/getUserMenus"}, method = RequestMethod.POST)
    public ResultInfo getUserMenus(@RequestBody User loginUser) {

        try {
            //功能分组
            List<Actiongroup> groupsList = actiongroupService.getMenuActionGroupListForApi(loginUser);
            //功能点
            List<Action> actionsList = actionService.getActionListForApi(loginUser);

            UserMenus user = new UserMenus();
            user.setGroupsList(groupsList);
            user.setActionsList(actionsList);

            return new ResultInfo(0, "操作成功",user);

        } catch (Exception e) {
            LOGGER.error("Error getUserMenus", e);
            return new ResultInfo(-1, "操作失败");
        }
    }

    /**
     * 获取当前用户的下级用户
     * @author wayne.liu
     * @date  2019/6/14 14:59
     * @param loginUser 用户
     * @return
     */
    @RequestMapping(value = {"/getSubStaff"}, method = RequestMethod.POST)
    public ResultInfo getSubStaff(@RequestBody User loginUser) {

        try {

            if(loginUser.getUserId() == null){
                throw new Exception("userId is null");
            }

            if(loginUser.getCompanyId() == null){
                throw new Exception("companyId is null");
            }

            //读取当前用户的某个职位下的所有员工
            Set<User> subUsers = userService.getSubUserListForSaleApi(loginUser);

            List<UserInfo> userForApi = new ArrayList<>();

            subUsers.forEach(e->{
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(e.getUserId());
                userInfo.setUserName(e.getUsername());
                userForApi.add(userInfo);
            });

            return new ResultInfo(0, "操作成功",userForApi);

        } catch (Exception e) {
            LOGGER.error("Error getSubStaff", e);
            return new ResultInfo(-1, "操作失败");
        }
    }
}
