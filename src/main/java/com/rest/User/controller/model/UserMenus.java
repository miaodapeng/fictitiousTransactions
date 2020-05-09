package com.rest.User.controller.model;

import com.vedeng.authorization.model.Action;
import com.vedeng.authorization.model.Actiongroup;

import java.io.Serializable;
import java.util.List;

/**
 * 用户菜单项
 * @author wayne.liu
 * @description
 * @date 2019/6/19 13:43
 */
public class UserMenus implements Serializable {


    private static final long serialVersionUID = 134785802092080897L;

    private List<Actiongroup> groupsList;

    private List<Action> actionsList;

    public List<Actiongroup> getGroupsList() {
        return groupsList;
    }

    public void setGroupsList(List<Actiongroup> groupsList) {
        this.groupsList = groupsList;
    }

    public List<Action> getActionsList() {
        return actionsList;
    }

    public void setActionsList(List<Action> actionsList) {
        this.actionsList = actionsList;
    }
}
